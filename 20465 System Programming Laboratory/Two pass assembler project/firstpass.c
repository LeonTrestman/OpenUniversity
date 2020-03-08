/*firstpass.c deals with the 1st pass, it receives a file pointer from main,
starts reading and checking the validty of the lines, prints errors and manages the symboltable.
This file also encodes the first word of each line.*/
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "Functions.h"
#include "firstpass.h"
#include "Assembler.h"

int line_num=LINE_START;


/*Function that deals with the first pass of the file*/
void FirstPass(FILE* fp) {
    char Buff[MAX_LINE_LENGTH]; /*Buffer for string lines*/
    /*Initialized data instruction counters, later will be changed.*/
    ic=0;
    dc=0;
    error=NO_ERROR;

    while(fgets(Buff,MAX_LINE_LENGTH,fp)) {
        if(!ignore(Buff))
            readline(Buff);

        if(error){
            ErrorsFound=TRUE; 
            Errors(line_num); /*Prints the first error found. */
			error = NO_ERROR; /*Reset the errors between each line*/
        }
        line_num++;
    }
    /*when first pass ends and the symboltable is complete, we calculated the real final adresses. */
    finalAdress(&SymbolTable,MEMORY_START,FALSE); /*instruction adresses starting from 100. */
    finalAdress(&SymbolTable,ic+MEMORY_START,TRUE); /*data symbol will start from memory start + ic */
}


/*readline, reads a line and handles with all the tokens.*/
void readline(char* line){
    line=skipSpaces(line);
	char Token[MAX_LINE_LENGTH];
    int LabelFlag=FALSE;
    int dirtype=UNKNOWN_DIR; /*Resets the dir type.*/
    int commandtype=UNKNOWN_COMMAND; /*Resets the command type.*/
    Label* LabelNode =NULL;

    if(*line!='.' && (!isalpha(*line))){
        error=BEGGINING_SYNTAX_ERROR;
        return;
    }
    copyToken(line,Token);
    if(CheckIfLabel(Token)){ /*checking and handling the label*/
        LabelFlag=TRUE;
        if(!CheckValidLabel(Token,TRUE))
            return;
        LabelNode=AddNewLabel(&SymbolTable,Token,0,FALSE,FALSE,FALSE,FALSE);/*Adding label to the SymbolTable. */ 
        if(!LabelNode)
            return;
        line=skipToken(line);
        if(endOfLine(line)){
            error=NOTHING_AFTER_LABEL;/*Meaningless label. */
            return;
        } 
        copyToken(line,Token);/*Copies the next token.*/
    }
   
    if((dirtype=findDir(Token))!=NOT_FOUND){
        if(LabelFlag){
            if(dirtype==EXTERN||dirtype==ENTRY){
                deleteLabel(&SymbolTable,LabelNode->name); /*EXTERN and ENTRY labels should be deleted*/
                LabelFlag=FALSE;
            }
            else{
                LabelNode->adress=dc; 
            }
        }
        line=skipToken(line);
        if(!HandleDir(dirtype,line))/*Handling with out dir, if there are any errros in HandleDir, we skip the line. */
            return;
    }
    else if((commandtype=findCommand(Token))!=NOT_FOUND){ /*detecting the command type. */
            if(LabelFlag){ /* setting fields according to the label. */
                LabelNode->isActionStatement=TRUE;
                LabelNode->adress=ic;
            }
            line=skipToken(line);
            if(!HandleCommand(commandtype,line)) /*Handling with out commands, if there are any errros in HandleCommand, we skip the line. */
                return;
    }
	else if (CheckMacro(Token)) {/*if macro handles it*/
		if (LabelFlag) {
			error = LABEL_BEFORE_MACRO;
			return;
			}
		if (!HandleMacro(Token, line))
			return;
	}
    else{
        error=TOKEN_UNIDENTIFIED; /*line must have a dir or a command or macro. */
        return;
    }
}

void finalAdress(Label** label, int num, int isData){
	Label *temp = *label;
    while(temp)
    {
		/*macro doesn't get its value(address) updated*/
        /* We don't offset external labels (their address is 0). */
        /* is_data and isActionStatement must have different values in order to meet the same criteria
         * and the XOR operator gives us that */
        if(!(temp->isExtern) && (isData ^ temp->isActionStatement) && !(temp->isMacro))
        {
            temp->adress += num;
        }
		temp = temp->next;
    }
}

int CheckMacro(char* line){
    if(!strcmp(line,".define"))
        return TRUE;
    return FALSE;
}

int CheckValidMacro(char* Token){
	
	char* copyptr = Token; /*pointer to the beggining of the Token*/
    /*Label Must begin in an alphabetical letter */
    if(!isalpha(*Token)){
        error=BEGGINING_MACRO_ERROR;
        return FALSE;
    }

    /*The maximum size of the label is 31 */
    if(strlen(Token)>MAX_LABEL_LENGTH){
        error=MACRO_TOO_BIG;
        return FALSE;
    }

    /*Checks syntax in the middle of the label*/
    while(isalnum(*copyptr) && !endOfLine(copyptr))
        copyptr++;
    if(!endOfLine(copyptr)){
        error=MACRO_SYNTAX_ERROR;
        return FALSE;
    }
    

    /*Checking if the macro is a keyword. We don't have to check for dir as it starts with dot ('.'),
    and macro must start with an alphabetical letter. */
    if(findCommand(Token)!=NOT_FOUND){
        error=MACRO_IS_COMMAND;
        return FALSE;
    }
    if(findRegister(Token)!=NOT_FOUND){
        error=MACRO_IS_REGISTER;
        return FALSE;
    }
    if(FindExistMacro(SymbolTable,Token)){
        error = MACRO_ALREADY_EXISTS;
        return FALSE;
    }   

    return TRUE;
}

int FindExistMacro(Label* SymbolTable,char* Token){
    while(SymbolTable){
        if(SymbolTable->isMacro)
            if(!strcmp(Token,SymbolTable->name))
                return TRUE;
		SymbolTable = SymbolTable->next;
    }
    return FALSE;
}


int HandleDir(int dirType,char* line){
	Label* temp=NULL;/*temp label for entry case*/
    if(line==NULL || endOfLine(line)){
        error= NOTHING_AFTER_DIR;
        return FALSE; /* returns false because of error. function failed. */
    }
    switch (dirType){
    case DATA: /*handles .data dir */
        return HandleDirData(line);
    case STRING: /*handles .string dir */
        return HandleDirString(line);
    case ENTRY: /*only checks for valid entry, should only have one parameter. */
		copyToken(line,line);
		if (temp=getLabel(SymbolTable,line)){	
			if (temp->isEntry || temp->isExtern) {
				error = LABEL_ALREADY_EXISTS;
				return FALSE;
			}
		}
        if(!endOfLine(skipToken(line))){
            error=EMPTY_ENTRY;
            return FALSE;
        }
        return TRUE;
    case EXTERN:
        return HandleDirExtern(line);
    }
    return TRUE;
}

int HandleDirData(char* line){
    
    char Token[MAX_LINE_LENGTH];
    int number=FALSE,comma=FALSE; /*Flag if we found a num and a comma (',') */

    while (!endOfLine(line)){
		line=NextListParameter(line,Token);
        if(strlen(Token)>0){
            if(!number){
                if(!isNumber(Token)&& !FindExistMacro(SymbolTable,Token)){/*checks if the parameter 
                is really a number (or a macro)*/
                    error=DATA_MISSING_NUMBER_PARAM;
                    return FALSE;
                }
                else{
                    number=TRUE; /* valid number parameter. */
                    comma=FALSE;
                    if(FindExistMacro(SymbolTable,Token))
                        writeMacroToData(SymbolTable,Token);
                    else
						if(Valid14BitNumber(atoi(Token)))
							writeNumToData(atoi(Token));
						else {
							error = NUMBER_LARGER_THAN_14_BITS;
							return FALSE;
						}
                }
            }
            else if(*Token!=','){
                    error=DATA_EXPECTED_COMMA;
                    return FALSE;
                }
                else{
                    if(comma){
                        error=DATA_CONSECUTIVE_COMMA;
                        return FALSE;
                    }
                    else{
                        comma=TRUE;
                        number=FALSE;
                    }

                }
        }
		
    }

	if (comma) { /*cant end with comma */
		error = DATA_ENDING_COMMA;
		return FALSE;
	}
    return TRUE;
}

int HandleDirString(char *line){
    char Token[MAX_LINE_LENGTH];
    line=NextTokenString(line,Token);
    if(!endOfLine(Token)&&isString(Token)){
        line=skipSpaces(line);
        if(endOfLine(line)){/*No more tokens */
            Token[strlen(Token)-1]='\0'; /*cutting quotation marks and putting \0 */
        writeStringToData(Token+1);/*starting after the quotation mark */
        }
        else{ /* there is more than 1 Token */
            error=STRING_TOO_MANY_OPERANDS;
            return FALSE;
        }
    }
    else{
        error=STRING_ISNT_VALID;
        return FALSE;
    }
    return TRUE;
}

int HandleDirExtern(char* line){
    char Token[MAX_LABEL_LENGTH];
    copyToken(line,Token);
    if(endOfLine(Token)){/*if token is empty, error */
        error=EXTERN_NO_LABEL;
        return FALSE;
    }
    if(!CheckValidLabel(Token,FALSE)){
        error=EXTERN_INVALID_LABEL;
        return FALSE;
    }
    line=skipToken(line);
    if(!endOfLine(line)){
        error=EXTERN_TOO_MANY_PARAMETERS;
        return FALSE;
    }
    /*now adding the label after the extern to the SymbolTable. */
    AddNewLabel(&SymbolTable, Token, EXTERN_DEFAULT_ADRESS, TRUE,FALSE,FALSE,FALSE);
    return TRUE;
}

int HandleCommand(int commandtype,char *line){
    int FirstFlag=FALSE,SecondFlag=FALSE; /*those flags will tell which of the operands we received.*/
    int firstMethod=FALSE,secondMethod=FALSE;/*this will hold the addressing method of the operands.
											 initialized to FALSE (0), methods aren't detected yet.*/
    /*these are the Tokens that will hold the operands. */
    char firstOp[MAX_LINE_LENGTH];
    char secondOp[MAX_LINE_LENGTH];

    line=NextListParameter(line,firstOp);
    if (!endOfLine(firstOp)){/*if the first operand isn't empty */
        FirstFlag=TRUE; /*We managed to find the first operand */
        line=NextListParameter(line,secondOp);
        if(!endOfLine(secondOp)){
            if(*secondOp!=','){ /*should be a coma*/
                error=COMMAND_UNEXPECTED_CHAR;
                return FALSE;
            }
            line=NextListParameter(line,secondOp);/*getting the 2nd parameter*/
            if(endOfLine(secondOp)){
                error=COMMAND_MISSING_PARAMETER;
                return FALSE;
            }
            SecondFlag=TRUE;
        }
    }
    line=skipSpaces(line);
    if(!endOfLine(line)){/*checking too many parameters*/
        error=COMMAND_TOO_MANY_OPERANDS;
        return FALSE;
    }
    if(FirstFlag){
        firstMethod=detectMethod(firstOp); /*detecting the adressing method of the first parameter. */
    }
    if(SecondFlag){
        secondMethod=detectMethod(secondOp); /*detecting the adressing method of the second parameter. */
    }
    if(error)/*if we have found an error during detctMethod we return FALSE*/
        return FALSE;
    else{/*if there was no error while parsing the adressing methods.*/
        if(IfNumberCommand(commandtype,FirstFlag,SecondFlag)){
            if(commandValidMethods(commandtype,firstMethod,secondMethod)){/*if adressing method are valid for the command. */
                encodeToInstructions(buildFirstWord(commandtype,FirstFlag,SecondFlag,firstMethod,secondMethod));
                ic+=numWordOfCommand(FirstFlag,SecondFlag,firstMethod,secondMethod);
            }
            else{
                error=COMMAND_INVALID_PARAMETER_METHODS;
                return FALSE;
            }
        }
        else{
            error=COMMAND_INVALID_NUMBER_PARAM;
            return FALSE;
        }
    }
    return TRUE;
}

int HandleMacro(char* Token,char* line){
        Label* LabelNode=NULL;
        line=skipTokenMacro(line); /*checking if .define*/
		if (endOfLine(line)) {
			error = NOTHING_AFTER_MACRO;/*Meaningless Macro. */
			return FALSE;
		}
        copyTokenMacro(line,Token);/*skipping to the macro declaration*/
        if(!CheckValidMacro(Token))
            return FALSE;
		if (!(LabelNode = AddNewLabel(&SymbolTable, Token, FALSE, FALSE, FALSE, FALSE, TRUE)))/*Initializing macro in the Symbol table*/
			return FALSE;
        line=skipTokenMacro(line);
        copyTokenMacro(line,Token);/*skipping to the equals (=) token*/
        if(*Token!='='){
            error=MACRO_MISSING_EQUALS;
            return FALSE;
        }
		line++;
        line=skipSpaces(line); /*Skipping to the number after the equals token*/
        copyTokenMacro(line,Token); 
        if(!atoi(Token)&& *Token !='0' && !(FindExistMacro(SymbolTable,Token))){
            error= MACRO_DEFINITION_IS_NOT_NUMBER;
            return FALSE;
        }
        line=skipTokenMacro(line);
        if(!endOfLine(line)){
            error=MACRO_TOO_MANY_PARAMETERS; 
            return FALSE;
        }
		
		if (isNumber(Token) && !Valid14BitNumber(atoi(Token))) {/* checking validity of number*/
			error = NUMBER_LARGER_THAN_14_BITS;
			return FALSE;
		}

		if (FindExistMacro(SymbolTable, Token))
			LabelNode->adress = MacroValue(SymbolTable, Token);
		else
			LabelNode->adress=atoi(Token); /*adding the adress of the macro to be his value.*/

        return TRUE;
}