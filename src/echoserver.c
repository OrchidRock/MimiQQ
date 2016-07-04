/*
 *Describe: server.c
 *	 The server leading end.Recv the UDP TDU
 *	from client.
 */
#include "../lib/mimiqq.h"
#include "../lib/rio.h"
#include "udp_case.h"
#include "sbuf.h"
#include "transaction.h"

void * thread(void * vargp);

/* for concurrency */
static int SBUFSIZE=0;
static int PRETHREAD_N=0;
sbuf_t sbuf;

void load_conf(){
	FILE* conffile=NULL;
	if((conffile=fopen("../etc/server.conf","r"))
			==NULL)	
		errExit("fopen");
	char buf[64];
	int line=0; /* the current line count*/
	char varname[20];/*The name of variable */
	char value[10];
	strcat(buf,"Hello");
	printf("%s\n",buf);
	while(fgets(buf,64,conffile)!=NULL){
		line++;
		int i=0;
		while(isspace(buf[i]))
			i++;
		//printf("line %d : %s %d i=%d\n",line,buf,strlen(buf),i);
		if(buf[i]=='#'|| i>=strlen(buf))
			continue;
		//int j=i;/*tag the '=' location */
		int j=0;
		while(buf[i]!='\n' && buf[i]!='='&& buf[i]!=' '
				&& buf[i]!='\t'){
			varname[j++]=buf[i++];
		}
		varname[j]='\0';
		//printf("name=%s\n",varname);

		while(buf[i]!='\n'&& buf[i]!='\"')
			i++;
		if(buf[i]=='\n'){
			fprintf(stderr,"error at line at %d\n",line);
			continue;
		}
		j=0;i++;
		while(buf[i]!='\n' && buf[i]!='\"'){
			value[j++]=buf[i++];
		}
		value[j]='\0';
		//printf("value=%s\n",value);

		int v=getInt(value,GN_GT_0,NULL);
		//
		if(strcmp(varname,"PORT")==0)
			SERVER_PORT=v;
		else if(strcmp(varname,"BUF_SIZE_RECV")==0)
			BUF_SIZE=v;
		else if(strcmp(varname,"PRETHREAD_N")==0)
			PRETHREAD_N=v;
		else if(strcmp(varname,"SBUFSIZE")==0)
			SBUFSIZE=v;
	}
	fclose(conffile);
}

int main(int argc,char* argv[]){
	struct sockaddr_in svaddr,claddr;
	int sfd;
	socklen_t len;
	ssize_t numBytes=0;

	//locd conf file
	load_conf();
	
	//sbuf_init
	sbuf_init(&sbuf,SBUFSIZE);

	//bind socket
	char buf[BUF_SIZE];
	char claddStr[INET_ADDRSTRLEN];
	
	sfd=socket(AF_INET,SOCK_DGRAM,0);
	if(sfd==-1)
		errExit("socket");
	memset(&svaddr,0,sizeof(struct sockaddr_in));
	svaddr.sin_family=AF_INET;
	svaddr.sin_addr.s_addr=htonl(INADDR_ANY);
	svaddr.sin_port=htons(SERVER_PORT);
	if(bind(sfd,(SA*)&svaddr,sizeof(struct sockaddr_in))==-1)
		errExit("bind");
	printf("%s : %u open\n",argv[0],SERVER_PORT);

	// create worker thread
	pthread_t tid;int s=0;
	for(int i=0;i<PRETHREAD_N;i++){ 
		s=pthread_create(&tid,NULL,thread,NULL);
		if(s!=0)
			errExitEN(s,"pthread_create");
	}

	while(TRUE){
		len=sizeof(struct sockaddr_in);
		memset(buf,0,sizeof(buf));
		numBytes=recvfrom(sfd,buf,BUF_SIZE,0,
				(SA*)&claddr,&len);
		if(numBytes==-1){
			errExit("recvfrom");
		}

		if(inet_ntop(AF_INET,&claddr.sin_addr,
			claddStr,INET_ADDRSTRLEN)==NULL)
			fprintf(stderr,"Couldn't convert client address to string\n");
		else
			printf("Server received %ld bytes from (%s,%u)\n",
		(long)numBytes,claddStr,ntohs(claddr.sin_port));
		printf("%s\n",buf);
		fflush(stdout);

		tdu_t newtdu;
		tdu_init(&newtdu);
		printf("init done.");
		if(xmlparser(&newtdu,buf,numBytes,TRUE)<0)
			sprintf("the tdu come from (%s,%u) parser failed!\n",
					claddStr,ntohs(claddr.sin_port));
		else
			sbuf_insert(&sbuf,newtdu);

		/*printf("%s\n",buf);
		fflush(stdout);
		for(int j=0;j<numBytes;j++)
			buf[j]=toupper((unsigned char)buf[j]);
		if(sendto(sfd,buf,numBytes,0,(SA*)&claddr,len)!=numBytes)
			fatal("sendto");*/
	}

	exit(EXIT_SUCCESS);
}

void *thread(void *vargp){
	pthread_t self_tid=pthread_self();
	int s;
	if((s=pthread_detach(self_tid))<0)
		errExitEN(s,"pthread_detach");
	while(TRUE){
		tdu_t tdu=sbuf_remove(&sbuf);
		//printTdu(&tdu);
		transactionProcess(&tdu);	
		tdu_deinit(&tdu);		
	}
}
