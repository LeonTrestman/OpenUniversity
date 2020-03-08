
/*
 *      ___    _____                                                _     _
 *     |__ \  |  __ \                  /\                          | |   | |
 *        ) | | |__) |_ _ ___ ___     /  \   ___ ___  ___ _ __ ___ | |__ | | ___ _ __
 *       / /  |  ___/ _` / __/ __|   / /\ \ / __/ __|/ _ \ '_ ` _ \| '_ \| |/ _ \ '__|
 *      / /_  | |  | (_| \__ \__ \  / ____ \\__ \__ \  __/ | | | | | |_) | |  __/ |
 *     |____| |_|   \__,_|___/___/ /_/    \_\___/___/\___|_| |_| |_|_.__/|_|\___|_|
 *
 *
 *      
 *
 */

/*main.c is the main file of the project.
this file contains all the external and global variables,opens the files.
and launches the main functions.
*/
#include <stdio.h>
#include <stdlib.h>
#include "Error.h"
#include "Assembler.h"
#include "firstpass.h"
#include "secondpass.h"
#include "Functions.h"
#include "structs.h"

int error;
int ic;
int dc;
unsigned int data[MACHINE_RAM]; 
unsigned int instructions[MACHINE_RAM];
int EntryExist;
int ExternExist;
int ErrorsFound=FALSE;/*A flag if there was atleast one error in the code.*/
Label *SymbolTable; /*Head pointer to the beggining of the Symbol Table */
Ext *extList;
const char* DIR[]={".data",".string",".entry",".extern"};
const char* COMMANDS[] = {"mov", "cmp", "add", "sub", "not", "clr", "lea", "inc", "dec",
 "jmp", "bne","red", "prn", "jsr", "rts", "stop"};
const char* REGISTERS_NAMES[]={"r0","r1","r2","r3","r4","r5","r6","r7","PSW"};
const char base4[4] = {'*','#','%','!'};

/*Main Function*/
int main(int argc,char * argv[]) {
	char* InputFileName;
    int i;
    FILE *fp; /*File Pointer*/
    if(argc<2){ /*if there is no filename parameter for the program. */
        ErrorsFound=TRUE;
        error=FILE_NOT_FOUND;
        Errors(NO_LINE);
        return 1;
    }
    for(i=1;i<argc;i++){
		InputFileName = createFileExtantion(argv[i], FILE_INPUT);
        if(!(fp=fopen(InputFileName,"r"))){/*In case open failed. */ 
            ErrorsFound=TRUE;
            error=FOPEN_FAILED;
            Errors(NO_LINE); /*Sending fopen failed error.*/
            continue;
        }
        printf("~~~~~~~~~Assembling Process started for %s file~~~~~~~~~\n", InputFileName);
        resetGlobalVars();
        FirstPass(fp); /*Pass 1 of the assembling */
        if(!ErrorsFound){ /*Procceding if there were no errors in the first pass. */
            rewind(fp); /*Resets the pointer on the file. */
            SecondPass(fp,argv[i]); /*Pass 2 of the assembling */
        }
        FreeLabel(&SymbolTable); /*Frees the symboltable linked list. */
		free(InputFileName); /*Frees the input file name.*/
        printf("\n\t~~~~~~~~~~Finished assembling process~~~~~~~~~\n\n");
    }
    return 0;
}
