//////////////////////////////////////////////////////////////////////////////////
//2017 87 . Q4
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>


typedef struct fhotographers {
	int id;
	int maxdays;
	int recom;
	struct fhotographers* next;
}photographers;



void Node(photographers** Rhead, int id, int maxdays, int recom) {
	if (*Rhead == NULL) {
		photographers* head = (photographers*)malloc(sizeof(photographers));
		head->id = id;
		head->maxdays = maxdays;
		head->recom = recom;
		head->next = head;
		*Rhead = head;
	}
	else {
		photographers* temp = (photographers*)malloc(sizeof(photographers));
		temp->id = id;
		temp->maxdays = maxdays;
		temp->recom = recom;
		photographers* p = *Rhead;
		while (p->next != *Rhead) {
			p = p->next;
		}
		p->next = temp;
		temp->next = *Rhead;
	}
}

//1 for success o, 0 for not found id
int search(photographers** head , int id) {
	//if list is missing
	if ((*head) == NULL) {
		printf("List is missing , search and delete cannot be completed\n");
			return 0;
	}


	photographers* tmpptr = *head;
	do
	{
		if (id == tmpptr->id)
		{
			 delete(&tmpptr,head);
			 return 1;
		}

		tmpptr = tmpptr->next;
	} while (tmpptr != *head);
	if (tmpptr == *head)
	{
		printf("the id wasn't found");
	}

	return 0;

}

int delete (photographers** node, photographers** head) {
	photographers* tempprev = *node;
	//edge case only one node return null ptr
	if ((*node)->next == (*node)) {
		free((*node));
		(*head) = NULL;
		printf("the list has been deleted \n");
	}
	else
	{
		while (tempprev->next != *node) {
			tempprev = tempprev->next;
		}
		
		tempprev->next = (*node)->next;
		if ( *node == *head ) //here
			*head = (*node)->next;
		free((*node));
		
	}
	return 1;
}

//for testing
printlist(photographers** headptr) {
	photographers*temp = *headptr;
	do
	{
		if (temp == NULL) {
			break;
		}
		//testing
		printf("id: %d , Max days: %d ,recom :%d\n", temp->id, temp->maxdays, temp->recom);
		temp = temp->next;
	} while (temp != *headptr);

}

int main() {
	int i = 0;
	int temp = 1;
	int id = 0;
	int maxdays = 0;
	int recom = 0;
	photographers * head=0;
	photographers * tmpptr = 0;
	while ( id != -1 && maxdays != -1 && recom != -1)// -1 -1 -1 to stop entring
	{

			printf("Enter Id ,Maxdays, recom \nif you wish to stop enter -1 in all the fields \n");
			scanf(" %d  %d  %d", &id, &maxdays, &recom);
			
			Node(&head, id, maxdays, recom);
	

			
	}
	tmpptr = head;
	search(&head, -1);

	printlist(&head);

	search(&head,3);
	printlist(&head);
}



//////////////////////////////////////////////////////////////////////////////////
////2017 87 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define summery(funcName,type)\
	type* funcName(type* arr ,int size) {\
			type* sum = (type*)malloc(sizeof(type));\
			*sum= 0;\
			int i = 0; \
			for(;i<size;i++){\
			*sum +=  arr[i];\
			}\
			return sum;\
	}



summery(intarrsum,int)


int main() {
	
	int test[] = {3,5,1,-10,13}; /*len =5*/
	int z = *intarrsum(test, 5);
	printf("The sum is %d ", z);
	

	return 666;
}

//////////////////////////////////////////////////////////////////////////////////
////2017 85 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define positiveSum(funcName,type)\
	type* funcName(type* arr, int Len){\
		type sum = 0;\
		int i=0;\
		type* Newarr = (type*)malloc(sizeof(type)*Len);\
		for(;i<Len;i++){\
			sum+=arr[i];\
			if(sum<0)\
				Newarr[i]=0;\
			else\
				Newarr[i]=sum;\
		}\
		return Newarr;\
	}

positiveSum(intarr, int)

void printArray_(int *a, int Len) {
	for (int i = 0; i < Len; i++) printf("%d ", a[i]);
}




int main() {
	
	int test[] = {3,5,1,-10,13}; /*len =5*/
	printArray_(intarr(test, 5),5);
	
	
	


	return 666;
}


//////////////////////////////////////////////////////////////////////////////////
////2017 86 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define arraysize 2// change here

//counts bits in array, the array size is fixed to 12 as requested


#define my_count(func_name,type)\
		int func_name(type* arr){\
		int i=0;\
		int temp = 0;\
		int countbits=0;\
		for(;i<arraysize;i++){\
				temp=arr[i];\
				while( temp > 0){\
					if(temp%10 == 0|| temp==1){\
						countbits++;}\
						temp=(temp/10);}	\
		}\
		return countbits;\
}	

my_count(intarray,float)

int main() {
	
	float a[] = { 10110110,10110001 }; //needs to be 9
	printf("count bits is:%d",intarray(a));
	return 666;
}


//////////////////////////////////////////////////////////////////////////////////
////2017 86 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define arraysize 2// change here

//counts bits in array, the array size is fixed to 12 as requested


#define my_count(func_name,type)\
		int func_name(type* arr){\
		int i=0;\
		int mask= 1;\
		int temp = 0;\
		int countbits=0;\
		for(;i<arraysize;i++){\
				temp=arr[i];\
				while( temp > 0){\
					if(temp&mask){\
						countbits++;}\
						temp =temp>>1;}	\
		}\
		return countbits;\
}	

my_count(intarray,float)

int main() {
	
	float a[] = { 10110110,10110001 }; //needs to be 9
	printf("count bits is:%d",intarray(a));
	return 666;
}

//////////////////////////////////////////////////////////////////////////////////
////2017 84 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define my_count(func_name,type)\
		int func_name(type* arr,int size, type target){\
		int i =0;\
		for(;i< size;i++ ){\
		 if(arr[i] == target){\
			return i+1;}\
		}\
		printf("the number wasn't found ");\
		return 0 ;\
}

my_count(doubleseek,double)

int main() {
	
	double a[] = {1,3,10 }; 
	printf("%d", doubleseek(a, 3, 10));
	return 666;
}


//////////////////////////////////////////////////////////////////////////////////
////2017 84 a . Q4
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Members{
	int id;
	int price;
	int exp;
	struct Members* next;
	struct members* prev;
}members;


//testing proposes
void printstruct(members* node){
	//if there is no list 
	if (!node) {
		printf("there is no list\n");
		return;
	}
	while (node) {

		printf("id:%d \n", node->id);
		printf("price:%d \n", node->price);
		printf("exp:%d \n", node->exp);
		node = node->prev;
	}
}

void addnode(members** head,int id , int price, int exp) {
	members* temp = (members*)malloc(sizeof(members));
	if (!(*head)) {
		temp->id = id;
		temp->price = price;
		temp->exp= exp;
		temp->next = NULL;
		temp->prev = NULL;
		*head = temp;
	}
	else
	{
		//adds to end of list and becomes head 
		temp->id = id;
		temp->price = price;
		temp->exp = exp;
		temp->prev = *head;
		temp->next = NULL;
		(*head)->next = temp;
		*head = temp;
	}

}


//searching and removing a member by his id
void seekndistory(members** head,int id) {
	members* temp = *head;
	while (temp)
	{
		if (temp->id == id) {
			dellmem(temp,head);
			return;
		}
		temp = temp->prev;
	}
	printf("there is no memeber with the id %d on the list\n", id);
}

dellmem(members* dellnode, members** head) {
	members* tempprev = dellnode->prev;
	members* tempnext = dellnode->next;
	if (tempprev) {
		tempprev->next = tempnext;
	}
	if (tempnext) {
		tempnext->prev = tempprev;
	}
	//edge case delete the head
	if (dellnode->id == (*head)->id) {
		*head = tempprev;
		free(dellnode);
	}
	else
	{
		free(dellnode);
	}

}

int main() {

	members* head = NULL;
	int id = 0;
	int price =0;
	int exp = 0;

	while (id != -1) {
		printf("please enter the following details: id price and exp of the member \nenter -1 if you wish to stop\n");
		scanf(" %d %d %d", &id, &price, &exp);
		if (id != -1) {
			addnode(&head,id,price,exp);
			
		}
	}

	printstruct(head);
	seekndistory(&head,5);
	printstruct(head);


	return 1;
}

//////////////////////////////////////////////////////////////////////////////////
////2018 87 a . Q4
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define RAND_MAX 6

/*menu structure*/
typedef struct cmd {
	char* name;
	void(*func)();
}Cmd;

typedef struct Node {
	char* name;
	struct Node* next;
}node;


//adds one to the begning of the list and then becomes head
addnode(node** head,char* name) {
	node * temp = (node*)malloc(sizeof(node));
	if (!(*head)) {
		temp->name = name;
		temp->next = NULL;
		*head = temp;
	}
	else
	{
		temp->name = name;
		temp->next = *head;
		*head = temp;

	}


}

func0(void) {
	printf("func0 has been activated\n");

}

func1(void) {
	printf("func1 has been activated\n");
}

func2(void) {
	printf("func2 has been activated\n");
}

func3(void) {
	printf("func3 has been activated\n");
}
func4(void) {
	printf("func4 has been activated\n");
}
func5(void) {
	printf("func5 has been activated\n");
}
func6(void) {
	printf("func6 has been activated\n");
}

Cmd menu[] = {
	{0,func0},
	{1,func1},
	{2,func2},
	{3,func3},
	{4,func4},
	{5,func5},
	{5,func6}
};

int main() {
	int temp = 0;
	int i = 0;
	node* head = NULL;
	node* ptrtmp = NULL;
	int a = 10;// larger than 7 wont work
	int b = 10;
	while (i<7) {
		
		while(1){
			a = rand()%10;
			b = rand() % 10;
			if( a<7 && b<7)
				break;
		}

		menu[a].func();
		menu[b].func();
		printf("/////////////////////////////////\n");
		i++;
		////////////////////////////////////////////
		//part b

	}
	
	addnode(&head, "facebook");
	addnode(&head, "instagram");
	addnode(&head, "snapchat");
	addnode(&head, "waze");
	addnode(&head, "google");
	addnode(&head, "ynet");
	addnode(&head, "amazon");

	//to get 7
	while (1) {
		a = rand() % 10;
		b = rand() % 10;
		if (a < 7 && b < 7)
			break;
	}

	ptrtmp = head;
	for (i=0; i < a; i++)
	{
		ptrtmp = ptrtmp->next;
	}
	printf("%s\n", ptrtmp->name);
	ptrtmp = head;
	for (i = 0; i <b; i++)
	{
		ptrtmp = ptrtmp->next;
	}
	printf("%s\n", ptrtmp->name);
	printf("/////////////////////////////////\n");

	return 666;
}

//////////////////////////////////////////////////////////////////////////////////
////2016 90 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define find_3min(func_name,type)\
	type func_name(type n1,type n2,type n3){\
		type minNum = 0;\
		if( n2 > n1 ) {minNum= n1;} else{ minNum= n2; }\
		if( (minNum) > n3){ minNum = n3;}\
		return minNum;}

#define arrSize 5
find_3min(inttype,int)
find_3min(doubletype, double)


void arrmin(int arr1[], int arr2[], int arr3[] ,int arr4[]) {
	int i = 0;
	for (; i < arrSize; i++) {
		arr4[i] = inttype(arr1[i], arr2[i], arr3[i]);
	}

}


int main() {
	int i = 0;
	int a = 1;
	int b = 2;
	int c = -3;

	double a2 = 12.3;
	double b2 = -14.4;
	double c2 = 0;

	int arr1[] = { 2,6,5,99,7 };
	int arr2[] = { 0,9,8,-3,9 };
	int arr3[] = { 5,6,1,12,0 };
	int arr4[] = { 0,0,0,0,0 };
	
	arrmin(arr1, arr2, arr3, arr4);

	for (; i < arrSize; i++)
		printf("%d ", arr4[i]);

	//testing
	printf("\n%d\n", inttype(a, b, c));
	printf("%f\n", doubletype(a2, b2, c2));

	return 666;
}

//////////////////////////////////////////////////////////////////////////////////
////2016 87 b . Q2
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



#define oddbitcount(func_name,type)\
		int func_name(type n1){\
		int countoddbits =0;\
		int mask = 1;\
		int newn1 = (int) n1;\
		newn1 = newn1>>1;\
		while (newn1 != 0){\
		if (newn1 & mask ){\
			countoddbits++;}\
		newn1 = newn1>>2;\
		}\
		return countoddbits;\
		}

oddbitcount(intcount, int)

int main() {

	printf("%d", intcount(149));

	return 666;
}

//////////////////////////////////////////////////////////////////////////////////
////2017 93 a . Q4
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define SIZE 200

typedef struct Bullshit {
	int id;
	char type[SIZE];
	int Price;
	char artistName[SIZE];
	struct Bullshit *next;
}art;

void addnode(art** head, int id, char* type, int price, char* artistname) {

	art* New = (art*)malloc(sizeof(art));
	art* tail=*head;
	New->id = id;
	strcpy(New->type, type);
	New->Price = price;
	strcpy(New->artistName, artistname);
	New->next = NULL;
	if (!(*head)) {
		*head = New;
	}
	else
	{
		while (tail->next) {
			tail = tail->next;
		}
		tail->next = New;
	}

}

void Merge(art** head1, art** head2) {

	art* tail = NULL;
	art* temp=NULL;
	art* newHead=NULL;
	while (*head1 || *head2) {
		if (*head1&&*head2) {
			if ((*head1)->id < (*head2)->id) {
				temp = *head1;
				*head1 = (*head1)->next;
				temp->next = NULL;
				if (!newHead)
					newHead = temp;
				else {
					tail = newHead;
					while (tail->next) {
						tail = tail->next;
					}
					tail->next = temp;
				}
			}
			else {
				temp = *head2;
				*head2 = (*head2)->next;
				temp->next = NULL;
				if (!newHead)
					newHead = temp;
				else {
					tail = newHead;
					while (tail->next) {
						tail = tail->next;
					}
					tail->next = temp;
				}
			}
		}
		else if (*head1) {
			temp = *head1;
			*head1 = (*head1)->next;
			temp->next = NULL;
			if (!newHead)
				newHead = temp;
			else {
				tail = newHead;
				while (tail->next) {
					tail = tail->next;
				}
				tail->next = temp;
			}
		}
		else {
			temp = *head2;
			*head2 = (*head2)->next;
			temp->next = NULL;
			if (!newHead)
				newHead = temp;
			else {
				tail = newHead;
				while (tail->next) {
					tail = tail->next;
				}
				tail->next = temp;
			}
		}
	}
	*head1 = newHead;
	*head2 = newHead;

}
void printa(art* head) {
	while (head) {
		printf("|%d\t%s\t%d\t%s| --> ", head->id, head->type, head->Price, head->artistName);
		head = head->next;
	}
	printf("\n");
}

int main() { 

	int Continue = 1;
	int tempid;
	char temptype[SIZE] = { 0 };
	int tempprice;
	char tempartistName[SIZE];
	art* head1 = NULL;
	art* head2 = NULL;

	while (Continue) {
		printf("Enter all the #1 BS\n");
		scanf("%d %s %d %s",&tempid,&temptype,&tempprice,&tempartistName);
		addnode(&head1, tempid, temptype, tempprice, tempartistName);
		printf("DU U W T CONT?!?!!? 0 if not\n");
		scanf("%d", &Continue);
	}
	Continue = 1;
	printf("Your 1nd List:\n");
	printa(head1);
	while (Continue) {
		printf("Enter all the BS #2\n");
		scanf("%d %s %d %s", &tempid, &temptype, &tempprice, &tempartistName);
		addnode(&head2, tempid, temptype, tempprice, tempartistName);
		printf("DU U W T CONT?!?!!? 0 if not\n");
		scanf("%d", &Continue);
	}
	printf("Your 2nd List:\n");
	printa(head2);
	printf("Your Merged List is:\n");
	Merge(&head1, &head2);
	printa(head1);

	
	
	return 666;
} 
