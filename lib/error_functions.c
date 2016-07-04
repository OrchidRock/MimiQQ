#include <stdarg.h>
#include "error_functions.h"
#include "mimiqq.h"
#include "enames.c.inc"
#ifdef __GUNC__
__attribute__ ((__noreturn__))
#endif

void mysqlErrorPrint(MYSQL* connect,const char * format,...){
	if(connect==NULL){
		fprintf(stderr,"UsageError:MYSQL handler is null.\n");
	}else{
	va_list arglist;
	va_start(arglist,format);
	fprintf(stderr,format,arglist);
	int errnoTemp;
	if((errnoTemp=mysql_errno(connect))){
		fprintf(stderr,"%d: %s\n",errnoTemp,mysql_error(connect));
	}
	va_end(arglist);
	}
}

static void outputError(boolean useErr,int err,
		boolean flushStdout,const char* format,va_list ap){
#define BUF_SIZE 500
	char buf[BUF_SIZE],userMsg[BUF_SIZE],errText[BUF_SIZE];
	vsnprintf(userMsg,BUF_SIZE,format,ap);
	if(useErr){
		snprintf(errText,BUF_SIZE,"[%s %s]",
				(err>0 && err<MAX_ENAME) ?
				ename[err]:"?UNKNOWN?",strerror(err));
	}else
		snprintf(errText,BUF_SIZE,":");
	snprintf(buf,BUF_SIZE,"ERROR%s %s\n",errText,userMsg);
	if(flushStdout)
		fflush(stdout);
	fputs(buf,stderr);

	fflush(stderr);
}
void errMsg(const char* format,...){
	va_list arglist;
	int savedErrno;
	savedErrno=errno;
	va_start(arglist,format);
	outputError(TRUE,errno,TRUE,format,arglist);
	va_end(arglist);
	errno=savedErrno;
}




static void terminate(boolean useExit3){
	        /*Dump core*/
	char *s;
	s=getenv("EF_DUMPCORE");
	if (s!=NULL && *s!='\0')
		abort();
	else if(useExit3)
		exit(EXIT_FAILURE);
	else
		_exit(EXIT_FAILURE);

}

void errExit(const char* format,...){
	va_list arglist;

	va_start(arglist,format);
	outputError(TRUE,errno,TRUE,format,arglist);
	va_end(arglist);

	terminate(TRUE);
}
void err_exit(const char* format,...){
	va_list arglist;

	va_start(arglist,format);
	outputError(TRUE,errno,FALSE,format,arglist);
	va_end(arglist);

	terminate(FALSE);
}
void errExitEN(int errnum,const char* format,...){
	va_list arglist;
	va_start(arglist,format);
	outputError(TRUE,errnum,TRUE,format,arglist);
	va_end(arglist);
	terminate(TRUE);
}
void fatal(const char* format,...){
	va_list arglist;

	va_start(arglist,format);
	outputError(FALSE,0,TRUE,format,arglist);
	va_end(arglist);

	terminate(TRUE);
}

void usageErr(const char* format,...){
	va_list arglist;
	fflush(stdout); /* Flush any pending stdout*/
	fprintf(stderr,"Usage:");

	va_start(arglist,format);
	vfprintf(stderr,format,arglist);

	va_end(arglist);
	fflush(stderr);
	exit(EXIT_FAILURE);

}

void cmdLineErr(const char* format,...){
	va_list arglist;
	fflush(stdout); /* Flush any pending stdout*/
	fprintf(stderr,"Comand-line usage error :");

	va_start(arglist,format);
	vfprintf(stderr,format,arglist);

	va_end(arglist);
	fflush(stderr);
	exit(EXIT_FAILURE);
									        
}

