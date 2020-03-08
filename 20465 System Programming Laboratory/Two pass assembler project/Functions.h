#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include "Assembler.h"
#include <stdio.h>

/* This function creates a file name by appending suitable extension (by type) to the Name string. */
char *createFileExtantion(char *,int);

/* This function writes all 3 output files (if they should be created)*/
int writeOutputFiles(char *);

/* This function opens a file with writing permissions, given the original input filename and the
 * wanted file extension (by type)
 */
FILE* openFile(char *, int );

/*this funciton resets all the global variables between file readings*/
void resetGlobalVars();

/* Converting a word to base 4 (as a string), uses the extracting bits function */
char *convertToBase4(unsigned int );

/* This function writes the .ob file output.*/
void writeOutputOb(FILE *);

/* This function writes the output of the .ext file. */
void writeOutputExtern(FILE *);

/* This function writes the output of the .ent file.*/
void writeOutputEntry(FILE *);

/* Function that ignores a line if it's blank/a comment (uses skip spaces for the beginning of the line.)*/
int ignore(char *);

/* This function skips spaces of a string and returns a pointer to the first non-blank character. */
char *skipSpaces(char *);

/*This function checks if the given Label is in the SymbolTable list. and returns it.
Otherwise it returns NULL */
Label* getLabel(Label *, char*);

/*Checks if the label valid,(second is a flag if colon exist).
plus, if the label is valid, it deletes the ":" from the label. */
int CheckValidLabel(char* , int );

/*Checks if a specific token is a Label. */
int CheckIfLabel(char* );

/*Adds a label to the SymbolTable list */
Label* AddNewLabel(Label**,char*,unsigned,int,int,int,int);

/*Checks if end of the line reachs */
int endOfLine(char* );

/*Takes a token from a string (line) */
void copyToken(char* ,char* );

/*Takes a token from a string in case of macro. (splits it also on the '=' char).*/
void copyTokenMacro(char* , char* );

/*Skip the token that we have already used. gets to the beginning of the next token.*/
char* skipToken(char* );

/*Skip the token that we have already used. gets to the beginning of the next token.
also stops at '=' char, unlike the regular skip token.*/
char* skipTokenMacro(char* );

/*Checking if the number can be encoded into 12 Bits.*/
int Valid12BitNumber(int );

/*Checking if the number can be encoded into 14 Bits.*/
int Valid14BitNumber(int );

/*The function is used to find the Dir type of the token.
Uses the findIndex, and returns the int num of the index in the enum directives */
int findDir(char* );

/*gets string and array of strings. finds if the string exists in the array.
this function is used mainly to find dir types and commands */
int findIndex(char* , const char* array[],int );

/*gets a name of the label, and deletes it from the structure.
Function returns TRUE or FALSE if it succeded.*/
int deleteLabel(Label** ,char* );

/*this extractes into a buffer a valid index label, it is used only after checking that
the the token is a valid index method */
void extractingLabel(char* ,char LabelToken[MAX_LABEL_LENGTH]);;

/*this function copies the next parameter of the list seperated by a coma (,) to the des.
returns a pointer to the first character after the taken parameter.  */
char* NextListParameter(char* ,char* );

/*checks if a Token (or any string) contains a valid number, can start with + or - */
int isNumber(char* );

/*this function encodes a given number to data */
void writeNumToData(int );

/* This function copies next string into dest array and returning a pointer to the
first character after it*/
char* NextTokenString(char* , char* );

/*Cheks if a token is a string (starts and ends with quotation marks) */
int isString(char* );

/* This function encodes a given string to data */
void writeStringToData(char *);

/*Checks if the token is a command, using the FindIndex function*/
int findCommand(char* );

/*Checks if the token is a register, using the FindIndex function*/
int findRegister(char* );

/*Adds a colon in the end of the token. used to check labels validity who doesn't have colons (':') */
void addColon(char* );

/*The function detects which adressing method is used. returns -1 if not found. */
int detectMethod(char* );

/*Checks if the number of given parameters is correct according to the Command type.  */
int IfNumberCommand(int ,int ,int );

/*Checks if the method is index method (if there are [] in the parameter)*/
int ifIndexMethod(char* );

/*Checking the validty of the index method*/
int ValidIndexMethod(char* );

/* This function checks for the validity of given addressing methods according to the opcode */
int commandValidMethods(int , int , int );

/*Function find if a macro is in the SymbolTable*/
int FindExistMacro(Label* ,char*);

/*encoding macro into Data*/
void writeMacroToData(Label* ,char* );

/*This funciton returns the value of the macro, assuming there is one in the SymbolTabel.*/
int MacroValue(Label * ,char* );

/* This function inserts a given word to instructions memory */
void encodeToInstructions(unsigned );

/* This function inserts given A/R/E 2 bits into given info bit-sequence (the info is being shifted left) */
unsigned int insertARE(unsigned int , int );

/*This function encodes the first word of the command. */
unsigned int buildFirstWord(int ,int ,int ,int ,int );

/* This function calculates number of additional words for a command */
int numWordOfCommand(int , int , int , int );

/* This function returns how many additional words an addressing method requires */
int numWords(int );

/*This function extact bits given the starting and ending position of the bit sequence */
unsigned int extractingBits(unsigned ,int , int );

#endif
