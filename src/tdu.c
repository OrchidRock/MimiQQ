#include "tdu.h"
#include "udp_case.h"
#include "../lib/mimiqq.h"
#include <expat.h>

void 
tdu_init(tdu_t* tp){
	tp->text=NULL;
	tp->textcount=0;
	tp->type=NONE;
	tp->index=-1;
}
void 
tdu_deinit(tdu_t* tp){
	for(int i=0;i<tp->textcount;i++){
		free(tp->text[i]);
	}
	free(tp->text);
}
void
printTdu(tdu_t* tp){
	if(tp->text==NULL) return;
	printf("type= %d\n",tp->type);
	for(int i=0;i<tp->textcount;i++){
		if(tp->text[i]==NULL) continue;
		printf("%s\n",tp->text[i]);
	}
}
static void 
startElementHandler(void* data,const char* name,const char** attr){
	tdu_t* tp=(tdu_t*)data;
	if(strcmp(name,"mimiqq")==0)
		return;
	for(int i=0;attr[i]!=NULL;i++){
		size_t size=MIN(strlen(attr[i]),TEXT_ITEM_MAX_SIZE);
		strncpy(tp->text[++tp->index],attr[i],size);
	}
	if(tp->type==NONE){
		if(strcmp(name,"login")==0)
			tp->type=LOGIN;
		else if(strcmp(name,"signin")==0)	
			tp->type=SIGNIN;
		else if(strcmp(name,"record")==0)	
			tp->type=RECORD;
		else if(strcmp(name,"search")==0)	
			tp->type=SEARCH;
		else if(strcmp(name,"CRA")==0)	
			tp->type=CRA;
		else if(strcmp(name,"friendadd")==0)	
			tp->type=FRIENDADD;
		else if(strcmp(name,"flocknumber")==0)	
			tp->type=FLOCKNUMBER;
		else if(strcmp(name,"frienddelete")==0)	
			tp->type=FRIENDDELETE;
		else if(strcmp(name,"flockcreate")==0)	
			tp->type=FLOCKCREATE;
		else if(strcmp(name,"flockdelete")==0)	
			tp->type=FLOCKDELETE;
		else if(strcmp(name,"flocknumbersreq")==0)	
			tp->type=FLOCKNUMBERSREQ;
		else if(strcmp(name,"getonlineuserap")==0)	
			tp->type=GETONLINEUSERAP;
		else if(strcmp(name,"exit")==0)	
			tp->type=EXIT;
		else{
		}
	}	

}
static void 
endElementHandler(void* data,const char* name){
	tdu_t* tp=(tdu_t*)data;
}

static void 
characterDataHandler(void* data,const char* name,int len){	
	tdu_t* tp=(tdu_t*)data;
	char tmp[len];
        strncpy(tmp,name,len);
	tmp[len]='\0';	
	if(tp->textcount==0){
	    int count=getInt(tmp,GN_GT_0,NULL);
	    tp->textcount=count;
	    tp->text=(char**)calloc(count,sizeof(char*));
	    for(int i=0;i<count;i++)
	         tp->text[i]=(char*)calloc(TEXT_ITEM_MAX_SIZE,
				     sizeof(char));
	    return;
	}
	size_t size=MIN(len,TEXT_ITEM_MAX_SIZE);
	strncpy(tp->text[++tp->index],tmp,size);
}
static void
create_XML_Parser(){

	parser=XML_ParserCreate(NULL);
	if(!parser){
		errExit("XML_ParserCreate");
	}
	XML_SetElementHandler(parser,startElementHandler
			,endElementHandler);
	XML_SetCharacterDataHandler(parser,characterDataHandler);
	XML_UseForeignDTD(parser,XML_TRUE);
}
/* parser data to tp */
int 
xmlparser(tdu_t* tp,const char* data,int size,int done){
	if(parser==NULL)
		create_XML_Parser();
	XML_SetUserData(parser,tp);
	if(!XML_Parse(parser,data,size,done)){
		XML_ErrorString(XML_GetErrorCode(parser));
		//errExit("XML_Parser");
		return FALSE;
	}
	return TRUE;
}
static void 
loginback(tdu_t* tp,char * buf){
	
}
static void 
signinback(tdu_t* tp,char * buf){}

static void 
recordreqback(tdu_t* tp,char * buf){}

static void 
searchback(tdu_t* tp,char * buf){}

static void 
flocknumbersreqback(tdu_t* tp,char * buf){
	
}
static void 
getonlineuserapback(tdu_t* tp,char * buf){}

static void 
record(tdu_t* tp,char * buf){}

static void 
craback(tdu_t* tp,char * buf){}

static void 
flockcreateback(tdu_t* tp,char * buf){
}
int sendTduByXML(tdu_t* tp,const char* ipaddress,unsigned int port){
	char buf[BUF_SIZE];
	char tmp[TEXT_ITEM_MAX_SIZE];
	tp->index=-1;
	strcat(buf,"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	strcat(buf,"<!DOCTYPE mimiqq SYSTEM \"xml/mimiqq.dtd\">");
	strcat(buf,"<mimiqq xmlns=\"www.orchid.party/mimiqq\">");
	//sprintf(tmp,"<textcount>%d</textcount>",tp->textcount);
	//strncat(buf,tmp,strlen(tmp));
	switch(sp->type){
		case LOGINBACK :
			strcat(buf,"<loginback>");
			loginback(tp,buf);
			strcat(buf,"</loginback>");
			break;
		case SIGNINBACK :
			strcat(buf,"<signinback>");
			signinback(tp,buf);
			strcat(buf,"</signinback>");
			break;
		case RECORDREQBACK :
			strcat(buf,"<recordreqback>");
			recordreqback(tp,buf);
			strcat(buf,"</recordreqback>");
			break;
		case SEARCHBACK :
			strcat(buf,"<searchback>");
			searchback(tp,buf);
			strcat(buf,"</searchback>");
			break;
		case FLOCKNUMBERSREQBACK :
			strcat(buf,"<flocknumbersreqback>");
			flocknumbersreqback(tp,buf);
			strcat(buf,"</flocknumbersreqback>");
			break;
		case GETONLINEUSERAPBACK :
			strcat(buf,"<getonlineuserapback>");
			getonlineuserapback(tp,buf);
			strcat(buf,"</getonlineuserapback>");
			breaak;
		case RECORD :
			strcat(buf,"<record ");
			sprintf(tmp,"recordtype=\"%s\">",
					tp->text[++sp->index]);
			recordreqback(tp,buf);
			strcat(buf,"</recordreqback>");
			break;
		case CRABACK :
			strcat(buf,"<craback>");
			craback(tp,buf);
			strcat(buf,"</craback>");
			break;
		case FLOCKCREATEBACK :
			strcat(buf,"<flockcreateback>");
			flockcreateback(tp,buf);
			strcat(buf,"</flockcreateback>");
			break;
		default :
			break;
	}
	strcat(buf,"</mimiqq>");
	
	//send udp packet
	struct sockaddr_in svaddr;
	int sfd;
	ssize_t msgLen;
	sfd=socket(AF_INET,SOCK_DGRAM,0);
	if(sfd==-1)
		errExit("socket");
	memset(&svaddr,0,sizeof(struct sockaddr_in));
	svaddr.sin_family=AF_INET;
	svaddr.sin_port=htons(port);
	if(inet_aton(ipaddress,&svaddr.sin_addr)<=0)
		errExit("inet_aton");
	msgLen=strlen(buf);
	if(sendto(sfd,buf,msgLen,0,(SA*)&svaddr,
				sizeof(struct sockaddr_in))!=msgLen){
		errMsg("sendto");
		return FALSE;
	}
	return TRUE;
}

