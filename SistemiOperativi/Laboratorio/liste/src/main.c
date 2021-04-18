#include <stdio.h>
#include <assert.h>
#include "list.h"

int main(void){
    List *head = create_list(4);
    printf("%d\n", head->value);

    int i;
    for(i = 0; i < 4; i++){
	    add_value(i, head);
    }

    printf("Aggiunti 4 elementi\n");
    List *iterator = head;
    while(iterator != NULL){
	    printf("%d\n", iterator->value);
	    iterator = iterator->next;
    }

    printf("Rimosso ultimo elemento\n");
	remove_last(head);

    iterator = head;
	while(iterator != NULL){
		printf("%d\n", iterator->value);
		iterator = iterator->next;
	}

	clear(&head);

	assert(head == NULL);

	printf("Lista da array:\n");
	int list[] = {4, 5, 6};
	List *head2 = create_list_from_array(list, 3);
	iterator = head2;
	while(iterator != NULL) {
		printf("%d\n", iterator->value);
		iterator = iterator->next;
	}
}