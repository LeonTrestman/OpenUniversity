/*secondpass.c deals with the 2nd pass, it gets a valid data from first pass,
checks the validty of second pass and encodes it.
This file also creates the potential 3 output files. (entry, external and object). */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "secondpass.h"
#include "Functions.h"

void SecondPass(FILE *fp, char *FileName){
    char Buff[MAX_LINE_LENGTH]; /*buffer of the line  */
    int line_num=LINE_START; /*line number starts from 1 */
    ic=0; /*Initialize IC*/
    while (fgets(Buff,MAX_LINE_LENGTH,fp)){
        error=NO_ERROR;
        if(!ignore(Buff))/*ignore line if its blank or comment. */
            readlineP2(Buff);
        if(error){
            ErrorsFound=TRUE;
            Errors(line_num);
        }
        line_num++;
    }
    if(!ErrorsFound)
        writeOutputFiles(FileName); /* Write output files only if there weren't any errors in the program */
    FreeExt(&extList);
}

void readlineP2(char* line){
    int dirtype;
    int commandtype;
    char Token[MAX_LINE_LENGTH];
    line=skipSpaces(line);
    if(endOfLine(line))
        return;/*blank line isn't an error */
    copyToken(line,Token);
    if(CheckIfLabel(Token)){/*in 2nd pass, we just skip labels.
                            no need to check for validity as it's dont in the first pass */
        line=skipToken(line);
        copyToken(line,Token);
    }
    if((dirtype=findDir(Token))!=NOT_FOUND){ /*The only dir we need to handle is .entry */
        line=skipToken(line);
        if(dirtype==ENTRY){
            copyToken(line,Token);
            if(!makeEntry(SymbolTable,Token))/*creating an entry for the SymbolTable. */
                return;
        }
    }
    else
        if((commandtype=findCommand(Token))!=NOT_FOUND){ /*encoding commands additional words. */
            line=skipToken(line);
            handleCommandsP2(commandtype,line);
        }
}

int makeEntry(Label * SymbolTable,char* Token){
    Label* temp=getLabel(SymbolTable,Token);
    if(temp){/*isn't NULL */
        if(temp->isExtern){
            error=ENTRY_CANT_BE_EXTERN;
            return FALSE;
        }
        temp->isEntry=TRUE;
        EntryExist=TRUE;
        return TRUE;
    }
    error=ENTRY_LABEL_DOESNT_EXIST;
    return FALSE;
}




int handleCommandsP2(int commandtype, char *line){
    /*those buffers will hold the 1st and the 2nd operands. */
    char firstOp[MAX_LINE_LENGTH];
    char secondOp[MAX_LINE_LENGTH];
    /*after the check below, source will point to source, and des will point to des */
    char* source=firstOp;
    char* des=secondOp;
    /*Those flags holds the existance of the source and destination operands. */
    int isSource=FALSE;
    int isDes=FALSE;
    /*The adressing method of the operands. */
    int sourceMethod=METHOD_UNKNOWN;
    int desMethod=METHOD_UNKNOWN;

    commandOperandsExist(commandtype,&isSource,&isDes);

    /*Extracting source and destination adressing methods. */
    if(isSource)
        sourceMethod=extractingBits(instructions[ic],SOURECE_METHOD_STARTING_POSITION,SOURECE_METHOD_ENDING_POSITION);
    if(isDes)
        desMethod=extractingBits(instructions[ic],DESTINATION_METHOD_STARTING_POSITION,DESTINATION_METHOD_ENDING_POSITION);

    /*Matching the source and the destination pointers to the correct operands. */
    if(isSource||isDes){
        line=NextListParameter(line,firstOp);
        if(isSource&&isDes){ /* if there are 2 parameters. */
            line=NextListParameter(line,secondOp);/*it would copy the (,) */
            NextListParameter(line,secondOp);/*it would copy the actual parameter. */
        }
        else{
            des=firstOp; /*if there is only 1 operand it must be the destination operand. */
            source=NULL;
        }
    }
    ic++; /* The first word of the command was already encoded in this IC in the first pass */
    return encodeAdditionalWords(source, des, isSource, isDes, sourceMethod, desMethod);
}

/* This function determines if source and destination operands exist by opcode */
void commandOperandsExist(int commandtype, int *isSource, int *isDes){
    switch (commandtype){
    case MOV:
    case CMP:
    case ADD:
    case SUB:
    case LEA:
        *isSource = TRUE;
        *isDes = TRUE;
        break;

    case NOT:
    case CLR:
    case INC:
    case DEC:
    case JMP:
    case BNE:
    case RED:
    case PRN:
    case JSR:
        *isSource = FALSE;
        *isDes = TRUE;
        break;

    case RTS:
    case STOP:
        *isSource = FALSE;
        *isDes = FALSE;
    }
}

unsigned int extractingBits(unsigned int word,int start, int end){
    unsigned int result;
    int length=end-start+1; /*Length of the bit sequence */
    unsigned int mask=(int)pow(2,length)-1; /* Creating a '111...1' mask with above line's length */
    mask<<=start; /*moving mask to the place of extraction */
    result = word & mask; /* The bits are now in their original position, and the rest is 0's */
    result >>= start; /* Moving the sequence to LSB */
    return result;
}


int encodeAdditionalWords(char *source, char *des, int isSource, int isDes, int sourceMethod,int desMethod) {
    /* There's a special case where 2 register operands share the same additional word */
    if(isSource&& isDes && sourceMethod == METHOD_REGISTER && desMethod== METHOD_REGISTER){
        encodeToInstructions(buildRegisterWord(FALSE,source)|buildRegisterWord(TRUE,des));
    }

    else /* It's not the special case */
    {
        if(isSource ) 
            encodeAdditionalWord(FALSE, sourceMethod, source);

        if(isDes) 
            encodeAdditionalWord(TRUE, desMethod, des);
    }
    return TRUE;
}

unsigned int buildRegisterWord(int isDes, char *reg)
{
    unsigned int word = (unsigned int) atoi(reg+1); /* Getting the register's number */
    /* Inserting it to the required bits (by source or destination operand) */
    if(!isDes)
        word <<= BITS_IN_REGISTER;
    word = insertARE(word, ABSOLUTE);
    return word;
}


void encodeAdditionalWord(int isDes, int method, char *operand)
{
    unsigned int word = 0; /* An empty word */
    char *temp;
    char Buff[MAX_LABEL_LENGTH]; /* for METHOD_INDEX only, in order to check if label exist. */
    

    switch (method){
    case METHOD_IMMEDIATE: /* Extracting immediate number */
        if(FindExistMacro(SymbolTable,operand+1)) /* +1 for skipping the hashtag #*/
            word=MacroValue(SymbolTable,operand+1);
        else
            word = (unsigned int) atoi(operand+1); /* +1 for skipping the hashtag #*/
        word = insertARE(word, ABSOLUTE);
        encodeToInstructions(word);
        break;

    case METHOD_DIRECT:
        encodeLabel(operand);
        break;

    case METHOD_INDEX:
        encodeIndex(operand);
        break;

    case METHOD_REGISTER:
        word = buildRegisterWord(isDes, operand);
        encodeToInstructions(word);
    }
}


void extractingLabel(char* Token,char LabelToken[MAX_LABEL_LENGTH]){
    int i;
    for(i=0;*Token!='[';i++){
        LabelToken[i]=*Token; /*copying the Label part from the parameter to the buffer*/
        Token++;
    }
    LabelToken[i]='\0';/*putting '\0' to end the string. */
}

void encodeIndex(char* Token){
    char indexLabel[MAX_LABEL_LENGTH]; /* buffer for the index label name */
    char index[MAX_LINE_LENGTH]; /* the buffer for the actual index. */
    int i=0;
    unsigned int word = 0; /* An empty word */

    extractingLabel(Token,indexLabel);
    /*getting the actual index form the brackets: [] */
    while(*Token!='[')
        Token++;
	Token++; /*skipping open bracket [ */
    while(*Token!=']'){ 
        index[i++]=*Token;
        Token++;
    }
    index[i]='\0';/*putting the end of the string */
    encodeLabel(indexLabel);/*encoding the label part */

    /*encoding the index inside the brackets */
    if(FindExistMacro(SymbolTable,index))
		word=MacroValue(SymbolTable,index);
        else
            word = (unsigned int) atoi(index);
    word = insertARE(word, ABSOLUTE);
    encodeToInstructions(word);
}

void encodeLabel(char* Token){
    Label* temp =getLabel(SymbolTable,Token);
    unsigned int word; /* the word to be encoded */
    if(temp){/*if label exists */
        word=temp->adress;
        if(temp->isExtern){/*if that label is external one */
            if(!addExt(&extList,Token,ic+MEMORY_START))
                return;
            word=insertARE(word,EXTERNAL); 
        }
        else
            word=insertARE(word,RELOCATABLE); /*if it's not external it's relocatable */
        encodeToInstructions(word);
    }
    else{/*label doesn't exist error */
        ic++;
        error = LABEL_DOESNT_EXIST;
    }
}

Ext* addExt(Ext** extList, char *name, unsigned int reference){
    Ext* temp;

    if(!(temp=(Ext*)malloc(sizeof(Ext)))){
        error=DYNAMIC_ALLOCATION_ERROR;
        Errors(NO_LINE);
        return NULL;
    }

    temp -> adress = reference;
    strcpy(temp->name, name);

    if(!(*extList)){ /* If the list is empty */
        temp -> next = temp;
        temp -> prev = temp;
        *extList = temp;
        return *extList;
    }
    /*else, connecting to the end of the list (before head) */
    ((*extList)->prev)->next = temp;
    temp->next = *extList;
    temp->prev = (*extList)->prev;
    (*extList)->prev = temp;
    return temp;
}

void FreeLabel(Label** SymbolTable){
    Label* temp=NULL;
    while(*SymbolTable){
        temp= *SymbolTable;
        *SymbolTable=(*SymbolTable)->next;
        free(temp);
    }
}

void FreeExt(Ext** extList){
    Ext* temp=NULL;
	if (!*extList)/*empty list*/
		return;
	(*extList)->prev->next = NULL;/*disconnecting the circular list in the end*/
    while(*extList){
        temp= *extList;
        *extList=(*extList)->next;
        free(temp);
    }
}
