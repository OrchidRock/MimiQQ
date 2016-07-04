#ifndef __GET_NUM_H__
#define __GET_NUM_H__

#define GN_NONNEG 	01 /*Value must be >=0*/
#define GN_GT_0  	02 /*Value must be >0*/
			/*By default,intergers are deciaml*/
#define GN_ANY_BASE	0100 /*Can use ant case*/
#define GN_BASE_8	0200 /*Value is compressed in octal*/
#define GN_BASE_16	0400 /*Value is compressed in Hex*/

long getLong(const char* arg,int flags,const char* name);
int getInt(const char* arg,int flags,const char* name);

#endif

