#ifndef FIRSTPASS_H
#define FIRSTPASS_H

/*Function that deals with the first pass of the file*/
void FirstPass(FILE* fp);

/*readline, reads a line and handles with all the tokens.*/
void readline(char* );

/*this function parses .data parameters. handles all the errors and encode the parameters to memory. */
int HandleDirData(char* );

/*this function parses .string dirs. handles all the errors and encode the string chars to memory.  */
int HandleDirString(char *);

/*this function parses .extern dirs, and handles it's error.*/
int HandleDirExtern(char* );

/*The huge function that handles all the dir types.
uses the HandleDirString,HandleDirExtern and HandleDirData functions. */
int HandleDir(int,char*);

/*The function handles a command type, it detects the type of command and it's parameters,
checks the validity of them, and encodes the first word of the command to the memory.*/
int HandleCommand(int ,char *);

/* This function offsets the addresses of a certain group of labels (data/instruction labels)
 * by a given delta (num).
 */
void finalAdress(Label** , int , int );

/*This function gets a Token and handles with everything that is connected to macro memory*/
int HandleMacro(char*,char*);

/*checks if macro name is valid.*/
int CheckValidMacro(char* );

/*Checks if a line is a macro line, if there is .define in the line*/
int CheckMacro(char* );

#endif // FIRSTPASS_H
