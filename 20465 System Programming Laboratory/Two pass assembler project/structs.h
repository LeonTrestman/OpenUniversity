/*This file contains the structs that are used in the project */
#ifndef STRUCTS_H
#define STRUCTS_H

#include "Assembler.h"

/*Struct that is used in the symboltable, holds all the labels and macros */
typedef struct label{
    char name[MAX_LABEL_LENGTH];/*The label itself. */
    int adress;
    int isExtern; /*Flag holds if the label is external */
    int isActionStatement; /*Flag if the label is in action statement. */
    int isEntry; /*Flag holds if the label is entry */
    int isMacro;
    struct label* next;
}Label;

/*Struct that is used in the external list that is circular two way linked list*/
typedef struct ext{
    char name[MAX_LABEL_LENGTH]; /* name of the external label */
    unsigned int adress; /*the adress in the memory where the external adress should be. */
    /*pointers to the next and the prev extern in the list. */
    struct ext *next;
    struct ext *prev;
}Ext;

#endif