/*This header contains all the Assembler related enums, constants, externals and defines.*/

#ifndef ASSEMBLER_H
#define ASSEMBLER_H

/*True, False aren't defined in ANSI C:*/
#define TRUE 1
#define FALSE 0

#define BASE4_SEQUENCE_LENGTH 8 /* A base4 sequence of a word consists of 7 digits and '\0' */
#define MAX_EXTANTION_LENGTH 5
#define MAX_LINE_LENGTH 81 /*80 is the max size + \0"*/
#define NO_ERROR 0
#define NO_LINE -1 /*If error has no connection to any line.*/
#define MAX_LABEL_LENGTH 31
#define EXTERN_DEFAULT_ADRESS 0
#define LINE_START 1
#define NOT_FOUND -1
#define NUM_OF_DIR 4
#define NUM_OF_COMMANDS 16
#define NUM_OF_REGISTERS 9
#define MACHINE_RAM 4096
#define MEMORY_START 100
#define BITS_IN_METHOD 2
#define BITS_IN_ARE 2
#define SOURECE_METHOD_STARTING_POSITION 4
#define SOURECE_METHOD_ENDING_POSITION 5
#define DESTINATION_METHOD_STARTING_POSITION 2
#define DESTINATION_METHOD_ENDING_POSITION 3
#define BITS_IN_REGISTER 3
#include "Error.h"
#include "structs.h"

/*External values */
extern int ic, dc;
extern int error;
extern int ErrorsFound;
extern int line_num; /*Curr line starts from 1*/
extern int EntryExist;
extern int ExternExist;
extern Label* SymbolTable;
extern Ext* extList;
extern const char* DIR[];
extern const char* COMMANDS[];
extern const char* REGISTERS_NAMES[];
extern const char base4[];
extern unsigned int data[];
extern unsigned int instructions[];




/* Types of files that indicate what is the desirable file extension */
enum filetypes {FILE_INPUT,FILE_OBJECT, FILE_ENTRY, FILE_EXTERN};
/*All the directive types.*/
enum directives {DATA, STRING, ENTRY, EXTERN, UNKNOWN_DIR};
/*All the commands*/
enum commands {MOV, CMP, ADD, SUB, NOT, CLR, LEA, INC, DEC, JMP, BNE, RED, PRN, JSR, RTS, STOP, UNKNOWN_COMMAND};
/* Addressing methods */
enum methods {METHOD_IMMEDIATE, METHOD_DIRECT,METHOD_INDEX, METHOD_REGISTER, METHOD_UNKNOWN};
/* A/R/E modes ordered by their numerical value */
enum ARE {ABSOLUTE, EXTERNAL, RELOCATABLE};

#endif