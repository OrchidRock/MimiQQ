#ifndef __SBUF_H__
#define __SBUF_H__

#include "tdu.h"
#include <semaphore.h>

typedef struct {
	tdu_t * buf;  /* Buffer array */
	int n;	      /* Maxinum number of slots*/
	int front;    /* buf[(front+1)%n] is first item*/
	int rear;     /* buf[rear%n] is last item */
	sem_t mutex;  /* Protects accesses to buf */ 
	sem_t slots;  /* Counts available slots */
	sem_t items;  /* Counts available items */
}sbuf_t;

/* Create an empty,bounded,shared FIFO buffer with n slots */
extern void sbuf_init(sbuf_t* sp,int n);

extern void sbuf_deinit(sbuf_t* sp) ;

/* Insert item  onto the rear of shared buffer sp */
extern void sbuf_insert(sbuf_t* sp,tdu_t item);

/* Remove and return the first item from buffer */
extern tdu_t sbuf_remove(sbuf_t* sp);

#endif
