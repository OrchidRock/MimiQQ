/*
 *Describe:
 *	There are some global include files and macros.
 *Date:
 *	2016-4-23
 *Author:
 *	Rock.
 */

#ifndef __MINI_QQ_H__
#define __MINI_QQ_H__

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <pthread.h>
#include <mysql/mysql.h> /*mysql api*/

#include <unistd.h> /*Prototypes of commonly for system call*/
#include <string.h>
#include <errno.h>

#include "error_functions.h"
#include "get_num.h"

typedef enum {FALSE,TRUE} boolean;
#define MIN(m,n) ((m)<(n) ? (m) : (n)) 
#define MAX(m,n) ((m)>(n) ? (m) : (n)) 

#endif
