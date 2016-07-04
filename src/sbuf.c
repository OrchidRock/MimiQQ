#include "sbuf.h"
#include "../lib/mimiqq.h"

/* Wrapper function for sem_wait */
static void P(sem_t* s){
	if(sem_wait(s)<0)
		errExit("sem_wait");
}
/* Wrapper function for sem_post */
static void V(sem_t* s){
	if(sem_post(s)<0)
		errExit("sem_post");
}
static void Sem_init(sem_t* sp,int pshared,unsigned int value){
	if(sem_init(sp,pshared,value)<0)
		errExit("sem_init");
}
void sbuf_init(sbuf_t* sp,int n){
	sp->buf=calloc(n,sizeof(tdu_t));
	if(sp->buf==NULL)
		errExit("calloc");
	sp->n=n;
	sp->front=sp->rear=0;
	Sem_init(&sp->mutex,0,1);
	Sem_init(&sp->slots,0,n);
	Sem_init(&sp->items,0,0);
}
void sbuf_deinit(sbuf_t * sp){
	free(sp->buf);
}
void sbuf_insert(sbuf_t* sp,tdu_t item){
	P(&sp->slots);          /* Waiting for available slot */
	P(&sp->mutex);	        /* Lock the buffer */
	sp->buf[(++sp->rear)%(sp->n)]=item;
	V(&sp->mutex);
	V(&sp->items);		/* Announce available item */
}
tdu_t sbuf_remove(sbuf_t* sp){
	tdu_t item;
	P(&sp->items);      /* Waiting for available item */
	P(&sp->mutex);	    /* Lock the buffer */
	item=sp->buf[(++sp->front)%(sp->n)];
	V(&sp->mutex);
	V(&sp->slots);      /* Announce available slot */
	return item;
}
