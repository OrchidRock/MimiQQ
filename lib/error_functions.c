#include <stdarg.h>
#include "error_functions.h"
#include "miniqq.h"

#ifndef __GUNC__
__attribute__ ((__noreturn__))
#endif

static void mysqlErrorPrint(MYSQL* connect,const char * format,...){
	if(connect==NULL){
		fprintf(stderr,"UsageError:MYSQL handler is null.\n");
		return;
	}
	va_list arglist;
	va_start(arglist,format);
	fprintf(stderr,format,arglist);
	int errnoTemp;
	if((errnoTemp=mysql_errno(connect))){
		fprintf(stderr,"%d: %s\n",errnoTemp,mysql_error(connect));
	}
	
	va_end(arglist);
}
