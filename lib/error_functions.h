#ifndef __ERROR_FUNCTIONS_H__
#define __ERROR_FUNCTIONS_H__

#ifdef __GUNC__
#define NORETURN __attribute__ ((__noreturn__))
#else
#define NORETURN
#endif

#include <mysql/mysql.h>

void mysqlErrorPrint(MYSQL*,const char* format,...) NORETURN;

#endif
