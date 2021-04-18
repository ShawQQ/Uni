#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "list.h"

List *create_list(int value){
	List *head = (List*)malloc(sizeof(List));
	head->value = value;
	head->next = NULL;

	return head;
}

int add_value(int value, List *list){
	assert(list != NULL);
	while(list->next != NULL){
		list = list->next;
	}

	List *item = (List*) malloc(sizeof(List));
	if(item == NULL){
		return 0;
	}

	item->value = value;
	item->next = NULL;
	list->next = item;
	return 1;
}

void remove_last(List *list){
	assert(list != NULL);
	List *prev = NULL;
	while(list->next != NULL){
		prev = list;
		list = list->next;
	}

	free(list);
	prev->next = NULL;
}

void clear(List **list){
	List *current = *list;
	List *next = NULL;
	while(current != NULL){
		next = current->next;
		free(current);
		current = next;
	}

	*list = NULL;
}

List *create_list_from_array(int *values, int size){
	int i;
	List *head = create_list(*values);
	values++;
	for(i = 0; i < size - 1; i++){
		add_value(*values, head);
		values++;
	}

	return head;
}

