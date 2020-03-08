#ifndef SECONDPASS_H
#define SECONDPASS_H
#include <stdio.h>
#include <stdlib.h>
#include "Assembler.h"

/*Function that deals with the second pass of the file*/
void SecondPass(FILE*, char *);

/*readline in second pass, reads a line and handles with all the tokens.*/
void readlineP2(char* line);

/* This function handles commands for the second pass - encoding additional words */
int handleCommandsP2(int , char *);

/* This function searches a label in the list and changes his entry field to TRUE and returns TRUE
else if the label doesn't exist return FALSE. */
int makeEntry(Label * ,char* );

/* This function determines if source and destination operands exist by opcode */
void commandOperandsExist(int , int *, int *);

/* This function builds the additional word for a register operand */
unsigned int buildRegisterWord(int , char *);

/* This function encodes an additional word to instructions memory, given the addressing method */
void encodeAdditionalWord(int , int , char *);

/* This function encodes the additional words of the operands to instructions memory */
int encodeAdditionalWords(char *, char *, int , int , int ,int );

/*This funciton encodes a Label to the memory */
void encodeLabel(char* );

/* This function adds a node to the end of the list, and returns the connected node */
Ext* addExt(Ext** , char *, unsigned int );

/*This functions free the memory allocation of all the labels */
void FreeLabel(Label** );

/*This functions free the memory allocation of all the externals */
void FreeExt(Ext** );

/*This function encode the index method to the memory, first the label,
then the index (that might also be a defined macro) */
void encodeIndex(char* );

#endif
