#ifndef __TDU_H__
#define __TDU_H__

#include <expat.h>

#define TEXT_ITEM_MAX_SIZE 512
typedef enum {
	NONE,LOGIN,SIGNIN,RECORDREQ,RECORD,
	SEARCH,CRA,CRABACK,FRIENDADD,FLOCKNUMBER,FRIENDDELETE,
	FLOCKCREATE,FLOCKDELETE,FLOCKNUMBERSREQ,GETONLINEUSERAP,
	EXIT,LOGINBACK,SIGNINBACK,RECORDREQBACK,SEARCHBACK,
	FLOCKCREATEBACK,FLOCKNUMBERSREQBACK,GETONLINEUSERAPBACK
}tdu_type;
typedef struct {
	char** text;		/* the all text of xml doc*/
	int textcount;		/* the count of text */
	int index;		/* the offset pointer of text*/
	tdu_type type;		/* the tdu type */
}tdu_t;

static XML_Parser parser=NULL;

void tdu_init(tdu_t* tp);
void tdu_deinit(tdu_t* tp);
void printTdu(tdu_t* tp);
int xmlparser(tdu_t* tp,const char* data,int size,int done);
int sendTduByXML(tdu_t* tp,const char* ipaddress,unsigned int port);
#endif
