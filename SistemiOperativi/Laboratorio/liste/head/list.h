/*Linked list di interi*/
typedef struct list_t List;

struct list_t{
    int value;
    List *next;
};

List *create_list(int value);

int add_value(int value, List *list);

void remove_last(List *list);

void clear(List **list);

List *create_list_from_array(int *values, int size);



