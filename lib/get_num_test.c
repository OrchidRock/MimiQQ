#include "mimiqq.h"

int main(){
	char name[]="Rock";
	errMsg("Hello,This is %s 's error",name);

	char * test[]={"+3515376157","-367163",
		"399.391830","399asa","12343"};
	for(int i=0;i<5;i++){
		printf("Result %d: %ld\n",i,getLong(test[i],
					0,"rock"));
	}

	return 0;

}
