#!/bin/sh
gcc -g -c tdu.c
gcc -g -c sbuf.c
gcc -g -o ../bin/echoserver echoserver.c tdu.o sbuf.o -I../lib -L../lib -lmimiqq -lpthread -lexpat -lmysqlclient
