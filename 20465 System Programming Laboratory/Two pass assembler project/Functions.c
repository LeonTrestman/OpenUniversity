/*A collection of general funciton that are being used in multipule files
throughout the project. */
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "Functions.h"

char *createFileExtantion(char *Name, int type){
    char *modified = (char *) malloc(strlen(Name) + MAX_EXTANTION_LENGTH);
    if(modified == NULL){ /*Check if malloc worked*/
        ErrorsFound=TRUE;
        error=DYNAMIC_ALLOCATION_ERROR;
        Errors(NO_LINE);
    }
    strcpy(modified, Name); /* Copying Name filename to the bigger string */
    /* Concatenating the required file extension */
    switch (type){
	case FILE_INPUT:
		strcat(modified, ".as");
		break;
        
    case FILE_OBJECT:
        strcat(modified, ".ob");
        break;

    case FILE_ENTRY:
        strcat(modified, ".ent");
        break;

    case FILE_EXTERN:
        strcat(modified, ".ext");
    }
    return modified;
}


int writeOutputFiles(char *original){
    FILE *file;

    file = openFile(original, FILE_OBJECT);
    writeOutputOb(file);

    if(EntryExist) {
        file = openFile(original, FILE_ENTRY);
        writeOutputEntry(file);
    }

    if(ExternExist){
        file = openFile(original, FILE_EXTERN);
        writeOutputExtern(file);
    }
    return TRUE;
}

FILE *openFile(char *filename, int type){
    FILE *file;
    filename = createFileExtantion(filename, type); /* Creating filename with extension */

    file = fopen(filename, "w"); /* Opening file with permissions */

    if(!file){
        error = FOPEN_FAILED;
        return NULL;
    }
    return file; 
}

void writeOutputOb(FILE *fp){
    unsigned int address = MEMORY_START;
    int i;
    int param1 = ic;
    char *param2;

    fprintf(fp, "%d\t%d\n\n", ic, dc); /* First line */

    for (i = 0; i < ic; address++, i++){ /* Instructions memory */
        param1 = address;
        param2 = convertToBase4(instructions[i]);

        fprintf(fp, "%u\t%s\n", param1, param2);
        free(param2);
    }

    for (i = 0; i < dc; address++, i++){ /* Data memory */
        param1 = address;
        param2 = convertToBase4(data[i]);

        fprintf(fp, "%u\t%s\n", param1, param2);
        free(param2);
    }
    fclose(fp);
}

void writeOutputEntry(FILE *fp){
    Label* temp = SymbolTable;
    /* Go through symbols table and print only symbols that have an entry */
    while(temp)
    {
        if(temp -> isEntry){
            fprintf(fp, "%s\t%u\n", temp -> name, temp->adress);
        }
        temp = temp -> next;
    }
    fclose(fp);
}

void writeOutputExtern(FILE *fp){
    Ext* node = extList;

    /* Going through external circular linked list and pulling out values */
    do{
        fprintf(fp, "%s\t%u\n", node -> name, node->adress); /* Printing to file */
        node = node -> next;
    } while(node != extList);
    fclose(fp);
}

char *convertToBase4(unsigned int num){
    char *base4_seq = (char *) malloc(BASE4_SEQUENCE_LENGTH);

    /* To convert from binary to base 4*/
    base4_seq[0] = base4[extractingBits(num, 12, 13)];
    base4_seq[1] = base4[extractingBits(num, 10, 11)];
    base4_seq[2] = base4[extractingBits(num,8,9)];
    base4_seq[3] = base4[extractingBits(num,6,7)];
    base4_seq[4] = base4[extractingBits(num,4,5)];
    base4_seq[5] = base4[extractingBits(num,2,3)];
    base4_seq[6] = base4[extractingBits(num,0,1)];
    base4_seq[7] = '\0';
    return base4_seq;
}

int ignore(char *line){
    line = skipSpaces(line);
    return *line == ';' || *line == '\0' || *line == '\n';
}

char *skipSpaces(char *ch){
    if(ch == NULL) return NULL;
    while (isspace(*ch)) /* Continue the loop if the character is a whitespace */
        ch++;
    return ch;
}

int CheckIfLabel(char* Token){
    while(*Token!='\0')
        Token++;
    /*Now it's pointed to \0, we need to go one step back */
    if(*(--Token)==':')
        return TRUE;
    return FALSE;
}

void addColon(char* nakedToken){
    while (!endOfLine(nakedToken))
        nakedToken++;
    *nakedToken=':';
    *(++nakedToken)='\0';
}

int CheckValidLabel(char* Token,int colonFlag){
	char* copyptr = Token; /*pointer to the beggining of the Token*/
    /*Label Must begin in an alphabetical letter */
    if(!isalpha(*Token)){
        error=BEGGINING_LABEL_ERROR;
        return FALSE;
    }

    /*The maximum size of the label is 31 */
    if(strlen(Token)>MAX_LABEL_LENGTH){
        error=LABEL_TOO_BIG;
        return FALSE;
    }

	if (!colonFlag)/*adds : in order to check the validty of a label*/
		addColon(Token);

    /*Cheks syntax in the middle of the label*/
    while(isalnum(*copyptr) && *copyptr !=':')
        copyptr++;
    if(*copyptr !=':'){
        error=LABEL_SYNTAX_ERROR;
        return FALSE;
    }

	*copyptr = '\0'; /*earsing the colon*/

    /*Checking if the label is a keyword. We don't have to check for dir as it starts with dot ('.'),
    and label must start with an alphabetical letter. */
    if(findCommand(Token)!=NOT_FOUND){
        error=LABEL_IS_COMMAND;
        return FALSE;
    }
    if(findRegister(Token)!=NOT_FOUND){
        error=LABEL_IS_REGISTER;
        return FALSE;
    }
    return TRUE;
    
}

Label* AddNewLabel(Label** head,char* name,unsigned adress,int isExtern,int isActionStatement,int isEntry,int isMacro){
        /*first if checks if the label already exists in the symbol table, and puts error*/
        Label* temp;
        if(temp=getLabel(SymbolTable,name)){
			if (!temp->isExtern || !isExtern) { /*Extern label with the same name can be entered multiple times*/
				error = LABEL_ALREADY_EXISTS;
				return NULL;
			}
        }

        Label* tail=*head;
        temp = (Label*)malloc(sizeof(Label));
        if(!temp){
            error=DYNAMIC_ALLOCATION_ERROR;
            return NULL;
        }
        strcpy(temp->name,name);
        temp->adress=adress;
        temp->isExtern=isExtern;
        temp->isEntry=isEntry;
        temp->isMacro=isMacro;
        temp->isActionStatement=isActionStatement;
        temp->next=NULL;

        if(isExtern){
            ExternExist=TRUE;
        }

        /*Now adding the temp to the end of the list,
        if the list doesn't exist yet, the function creats it*/
        if(!(*head)){
            *head=temp;
        }
        else{
            while(tail->next!=NULL)
                tail=tail->next;
            tail->next=temp;
        }
        return temp;
}

Label* getLabel(Label * SymbolTable, char* Token) {
	while (SymbolTable) {
		if (!strcmp(SymbolTable->name, Token))
			return SymbolTable;
		SymbolTable = SymbolTable->next;
	}
	return NULL;
}

int deleteLabel(Label** SymbolTable,char* name){
    Label* temp= *SymbolTable;
    Label* prev= *SymbolTable;
    if(!temp)
        return FALSE;
    while(temp){
        if(!strcmp(temp->name,name)){
            if(temp==*SymbolTable ){ /*Edge case, deleting the first label in the structure. */
                *SymbolTable=(*SymbolTable)->next;
                free(temp);
                return TRUE;
            }
            while(prev->next!=temp)
                prev=prev->next;
            prev->next=temp->next;
            temp->next=NULL;
            free(temp);
            return TRUE;
        }
        temp=temp->next;
    }
    return FALSE;
}

int endOfLine(char* line){
    if(line == NULL || *line=='\0'||*line=='\n')
        return TRUE;
    return FALSE;
}

void copyToken(char* line,char* destination){
    int i=0;
    if(line==NULL || destination==NULL)
        return;
    while(i<MAX_LINE_LENGTH && line[i]!='\0' && !isspace(line[i])){
        destination[i]=line[i];
        i++;
    }
    destination[i]='\0';
}

void copyTokenMacro(char* line, char* destination) {
	int i = 0;
	if (line == NULL || destination == NULL)
		return;
	while (i < MAX_LINE_LENGTH && line[i] != '\0' && !isspace(line[i]) && line[i]!= '=') {
		destination[i] = line[i];
		i++;
	}
	if (line[0] == '=') { /*in case '=' is in the beggining of the line.*/
		destination[0] = '=';
		destination[1] = '\0';
		return;
	}
	destination[i] = '\0';
}


char* skipToken(char* line){
    if(line==NULL)
        return NULL;
    /*Skipping the numbers and chars, until we find space or end of line. */
    while(!isspace(*line) && !endOfLine(line)){
        line++;
    }
    /*skipping the spaces until the next Token. */
    line=skipSpaces(line);
    if(endOfLine(line))
        return NULL;
    return line;
}

char* skipTokenMacro(char* line) {
	if (line == NULL)
		return NULL;
	/*Skipping the numbers and chars, until we find space or end of line OR '=' sign. */
	while (!isspace(*line) && !endOfLine(line) && *line != '=') {
		line++;
	}
	
	/*skipping the spaces until the next Token. */
	line = skipSpaces(line);
	if (endOfLine(line))
		return NULL;
	return line;
}

int findDir(char* Token){
    if(!Token || *Token != '.')
        return NOT_FOUND; /* returns -1 in case there is no DIR at all */
    return findIndex(Token,DIR,NUM_OF_DIR);
}

int findCommand(char* Token){
    if(!Token)
        return NOT_FOUND;
    return findIndex(Token,COMMANDS,NUM_OF_COMMANDS);
}
int findRegister(char* Token){
    if(!Token)
        return NOT_FOUND;
    findIndex(Token,REGISTERS_NAMES,NUM_OF_REGISTERS);
}

int findIndex(char* Token, const char* array[],int number){
    int i=0;
    for (; i < number; i++)
    {
        if(!strcmp(Token,array[i])) /* if they are the same. */
            return i;
    }
        return NOT_FOUND;
}

char* NextListParameter(char* origin,char* des){
    char* temp=des;
    if(endOfLine(origin)){/*if the given origin is empty line it copies an empty parameter*/
        des[0]='\0';
        return NULL;
    }
    if(isspace(*origin))
        origin=skipSpaces(origin);/*in case we started on white space */
    if(*origin==','){
        strcpy(des,",");
        return ++origin;
    }
    while(!endOfLine(origin) && *origin!=',' && !isspace(*origin)){
        *temp=*origin;
        temp++;
        origin++;
    }

    *temp='\0';
    return origin;
}

char* NextTokenString(char* origin, char* des){
    char temp[MAX_LINE_LENGTH];
    origin=NextListParameter(origin,des);
    if(*des!='"')
        return origin;
    while(!endOfLine(origin)&& des[strlen(des)-1]!='"'){
        origin=NextListParameter(origin,temp);
        if(origin)
            strcat(des,temp);
    }
    return origin;
}

int isNumber(char* string){
    if(!string || endOfLine(string))
        return FALSE;
    if(*string=='-'||*string=='+'){
        string++;
    }
    while(!endOfLine(string)){
        if(!isdigit(*string++))
            return FALSE;
    }
    return TRUE;
}

int isString(char* string){
    if(!string)
        return FALSE;
    if(*string=='"') /* it should start with: " */
        string++;
    else
        return FALSE;
    while(*string && *string!='"')
        string++;/*goes to the end of the string */
    if(*string!='"') /*The string also must end with: " */
        return FALSE;
    string++;
    if(*string!='\0') /* It should have nothing after: " */
        return FALSE;
    return TRUE;
}

void writeNumToData(int num){
    data[dc++] = (unsigned int) num;
}

void writeMacroToData(Label* SymbolTable,char* Macro){
    int num;
    while(SymbolTable){
        if(!strcmp(SymbolTable->name,Macro)){
            num=SymbolTable->adress;
            break;
        }
        SymbolTable=SymbolTable->next;
    }
    data[dc++] = (unsigned int) num; /*encoding macro to data*/
}

int MacroValue(Label * SymbolTable,char* Token){
    while(SymbolTable){
        if(!strcmp(SymbolTable->name,Token)){
            break;
        }
        SymbolTable=SymbolTable->next;
    }
    return SymbolTable->adress;
}

void writeStringToData(char *str)
{
    while(!endOfLine(str))
    {
        data[dc++] = (unsigned int) *str; /* Inserting a character to data array */
        str++;
    }
    data[dc++] = '\0'; /* Insert a null character to data */
}


int detectMethod(char* parameter){
   
    char temp[MAX_LINE_LENGTH]; /*we need a copy for CheckValidLabel, we don't want permanent
    damage to the parameter. */
    if(endOfLine(parameter)){
        return NOT_FOUND;
    }
    if(*parameter=='#'){
        parameter++;
        /*checks if a number or macro, and returns the immediate method. */
		if (isNumber(parameter) || FindExistMacro(SymbolTable, parameter)) {
			if (isNumber(parameter) && !Valid12BitNumber(atoi(parameter))) {
				error = NUMBER_LARGER_THAN_12_BITS;
				return NOT_FOUND;
			}

            return METHOD_IMMEDIATE;
		}
    }
     else if(findRegister(parameter) !=NOT_FOUND ){/*checks if a register, and returns the register method. */
            return METHOD_REGISTER;
    }

     else if(ifIndexMethod(parameter)){
            if(!ValidIndexMethod(parameter))
                return NOT_FOUND; /*there are errors, so we return NOT_FOUND*/
            return METHOD_INDEX;
	}  
     else{
        strcpy(temp,parameter);
        if(CheckValidLabel(temp,FALSE)){
			if (getLabel(SymbolTable, temp) != NULL && getLabel(SymbolTable, temp)->isMacro) {
				error = MISSING_HASHTAG_MACRO;
				return NOT_FOUND;
			}
            return METHOD_DIRECT;
        }
    }
    error=COMMAND_INVALID_METHOD;
    return NOT_FOUND;
}

int ifIndexMethod(char* Token){
    while(*Token != '\0'){
       if(*Token == '[')
            break;
        Token++;
    }
    while(*Token!='\0'){
        if(*Token == ']')
            break;
        Token++;
    }
    if(*Token == '\0')
        return FALSE;
    return TRUE;
}

int ValidIndexMethod(char* Token){
    char Buff[MAX_LABEL_LENGTH];/*buffer checking the validity*/
    int i;
    for(i=0;*Token!='[';i++){
        Buff[i]=*Token; /*copying the Label part from the parameter to the buffer*/
        Token++;
    }
    Buff[i]='\0'; /*adding end of the string*/
    if(!CheckValidLabel(Buff,FALSE)){ /* checking the label part of the Index Method*/
        return FALSE;
    }
    Token++; /*skipping '[' to the Number/macro part*/
    for(i=0;*Token!=']';i++){
        Buff[i]=*Token;
        Token++;
    }
    Buff[i]='\0'; /*adding end of the string*/
    if(!isNumber(Buff) && !FindExistMacro(SymbolTable,Buff)){
        error=INDEX_NOT_A_NUMBER;
        return FALSE;
    }

	/*check if number fits into 12 bit*/
	if (isNumber(Buff) && !Valid12BitNumber(atoi(Buff))) {
		error = NUMBER_LARGER_THAN_12_BITS;
		return FALSE;
	}
	/*checking if not a negative int index*/
	if (FindExistMacro(SymbolTable, Buff)) { /*if macro we check it's address.*/
		if (getLabel(SymbolTable, Buff)->adress < 0) {
			error = NEGATIVE_INDEX;
			return FALSE;
		}
	}
	else{
		if (atoi(Buff) < 0) { /*if just a number we use atoi.*/
			error = NEGATIVE_INDEX;
			return FALSE;
		}
	}
	
    if(!endOfLine(++Token)){ /*There is something after the closing array bracket*/
        error=INDEX_EXTRA_TEXT;
        return FALSE;
    }
	
    return TRUE;
}

int IfNumberCommand(int type,int first,int second){
    switch (type){
        /*Those are the Opcodes that must receive two operands. */
    case MOV:
    case CMP:
    case ADD:
    case SUB:
    case LEA:
        return first&&second;

        /*Those are the Opcodes that must reveice one operand */
    case NOT:
    case CLR:
    case INC:
    case DEC:
    case JMP:
    case BNE:
    case RED:
    case PRN:
    case JSR:
        return first && !second;

        /* These opcodes can't have any operand */
    case RTS:
    case STOP:
        return !first && !second;
    }
    return FALSE;
}


int commandValidMethods(int type, int firstMethod, int secondMethod){
    switch (type){
        /* These opcodes only accept
         * Source: 0, 1, 2, 3
         * Destination: 1, 2, 3
         */
    case MOV:
    case ADD:
    case SUB:
        return (firstMethod == METHOD_IMMEDIATE ||
                firstMethod == METHOD_DIRECT ||
                firstMethod == METHOD_REGISTER||
                firstMethod==METHOD_INDEX)
                &&
                (secondMethod == METHOD_DIRECT ||
                secondMethod == METHOD_REGISTER||
                secondMethod==METHOD_INDEX);

        /* LEA opcode only accept
         * Source: 1, 2
         * Destination: 1, 2, 3
        */
    case LEA:
        return (firstMethod == METHOD_DIRECT||
                firstMethod==METHOD_INDEX)&&(
                secondMethod == METHOD_DIRECT ||
                secondMethod==METHOD_INDEX||
                secondMethod == METHOD_REGISTER);

        /* These opcodes only accept
         * Source: NONE
         * Destination: 1, 2, 3
        */
    case NOT:
    case CLR:
    case INC:
    case DEC:
    case RED:
        return  firstMethod == METHOD_DIRECT ||
                firstMethod == METHOD_REGISTER||
                firstMethod==METHOD_INDEX;

        /* These opcodes only accept
         * Source: NONE
         * Destination: 1, 3
        */
    case JMP:
    case BNE:
    case JSR:
        return  firstMethod == METHOD_DIRECT||
                firstMethod==METHOD_REGISTER;

        /* These opcodes are always ok because they accept all methods/none of them and
         * number of operands is being verified in another function
        */

    case PRN:
    case CMP:
    case RTS:
    case STOP:
        return TRUE;
    }

    return FALSE;
}

void encodeToInstructions(unsigned int word)
{
    instructions[ic++] = word;
}

unsigned int buildFirstWord(int commandtype,int firstFlag,int secondFlag,int firstMethod,int secondMethod){
    
    /*inserting the Opcode */
    unsigned int word = 0;
    word= commandtype;
    word<<=BITS_IN_METHOD;
    /*if there are 2 operands, starting with the first. */
    if(firstFlag&&secondFlag){
        word |= firstMethod;
    }
    word<<=BITS_IN_METHOD; /*leave space for the 2nd adressing method. */
    if(firstFlag&&secondFlag){
        word|=secondMethod;
    }
    else
        if(firstFlag)
            word |= firstMethod;
    word= insertARE(word,ABSOLUTE);
    return word;
}

unsigned int insertARE(unsigned int info, int are)
{
    return (info << BITS_IN_ARE) | are;
}


int numWordOfCommand(int firstFlag, int secondFlag, int firstMethod, int secondMethod){
    int count = 0;
    if (firstFlag) count += numWords(firstMethod);
    if(secondFlag) count += numWords(secondMethod);

    /* If both methods are register, they will share the same additional word */
    if (firstFlag && secondFlag && firstMethod == METHOD_REGISTER && secondMethod == METHOD_REGISTER) count--;

    return count;
}

int numWords(int method){
    if(method == METHOD_INDEX) /* index addressing method requires two additional words */
        return 2;
    return 1;
}

int Valid12BitNumber(int num) {
	if (num > 2047 || num < -2048)
		return FALSE;
	return TRUE;
}

int Valid14BitNumber(int num) {
	if (num > 8191 || num < -8192)
		return FALSE;
	return TRUE;
}

void resetGlobalVars(){
    SymbolTable=NULL;
    extList=NULL;
    error= NO_ERROR;
	ErrorsFound = NO_ERROR;
    EntryExist=FALSE;
    ExternExist=FALSE;
    line_num=LINE_START;
}