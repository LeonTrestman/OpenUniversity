#include <stdio.h>
#include "Error.h"
#include "Assembler.h"


/*Prints the line of the ERROR and which error it is*/
void Errors(int line) {

    if(line!=NO_LINE)
        fprintf(stderr,"ERROR\tLine:(%d):\t",line);

    switch(error) {

    case FILE_NOT_FOUND:
        fprintf(stderr,"File not found\n");
        break;
    case FOPEN_FAILED:
        fprintf(stderr,"fopen failed.\n");
        break;
    case BEGGINING_SYNTAX_ERROR:
        fprintf(stderr,"This line must start on a letter or '.'\n");
        break;
    case DYNAMIC_ALLOCATION_ERROR:
        fprintf(stderr,"Malloc Failed.\n");
        break;
    case LABEL_TOO_BIG:
        fprintf(stderr,"The label is too big.\n");
        break;
    case BEGGINING_LABEL_ERROR:
        fprintf(stderr,"The label should start with an alphabetical letter.\n");
        break;
    case NOTHING_AFTER_LABEL:
        fprintf(stderr,"Must have something after label.\n");
        break;
    case NOTHING_AFTER_DIR:
        fprintf(stderr,"Must have something after dir.\n");
        break;
    case DATA_MISSING_NUMBER_PARAM:
        fprintf(stderr,".data missing valid Integer number (or macro) parameter.\n");
        break;
    case DATA_EXPECTED_COMMA:
        fprintf(stderr,".data Missing comma\n");
        break;
    case DATA_CONSECUTIVE_COMMA:
        fprintf(stderr,".data consecutive commas\n");
        break;
    case DATA_ENDING_COMMA:
        fprintf(stderr,".data cannot end on comma\n");
        break;
    case STRING_TOO_MANY_OPERANDS:
        fprintf(stderr,"After Dir .string there should only be one parameter.\n");
        break;
    case STRING_ISNT_VALID:
        fprintf(stderr,"String isn't valid.\n");
        break;
    case EMPTY_ENTRY:
        fprintf(stderr,"Nothing after entry.\n");
        break;
    case NOTHING_AFTER_EXTERN:
        fprintf(stderr,"Nothing after extern.\n");
        break;
    case EXTERN_NO_LABEL:
        fprintf(stderr,"Not a label after extern.\n");
        break;
    case EXTERN_INVALID_LABEL:
        fprintf(stderr,"The label after the extern is invalid.\n");
        break;
    case LABEL_SYNTAX_ERROR:
        fprintf(stderr,"Label should only contain Alphabetic letters and numbers.\n");
        break;
    case LABEL_IS_COMMAND:
        fprintf(stderr,"Label used keyword of command.\n");
        break;
    case LABEL_IS_REGISTER:
        fprintf(stderr,"Label used keyword of register.\n");
        break;
    case MACRO_SYNTAX_ERROR:
        fprintf(stderr,"Macro should only contain Alphabetic letters and numbers.\n");
        break;
    case MACRO_IS_COMMAND:
        fprintf(stderr,"Macro used keyword of command.\n");
        break;
    case MACRO_IS_REGISTER:
        fprintf(stderr,"Macro used keyword of register.\n");
        break;
    case MACRO_ALREADY_EXISTS:
        fprintf(stderr,"Macro by the same name already exists.\n");
        break;
    case NOTHING_AFTER_MACRO:
        fprintf(stderr,"Must have something after macro.\n");
        break;
    case MACRO_MISSING_EQUALS:
        fprintf(stderr,"Macro missing equals sign (=).\n");
        break;
    case MACRO_DEFINITION_IS_NOT_NUMBER:
        fprintf(stderr,"You cannot define something other the INTs.\n");
        break;
    case MACRO_TOO_MANY_PARAMETERS:
        fprintf(stderr,"Macro has too many parameters.\n");
        break;
    case LABEL_IS_DIR:
        fprintf(stderr,"Label used keyword of dir.\n");
        break;
    case EXTERN_TOO_MANY_PARAMETERS:
        fprintf(stderr,"Extern has too many parameters.\n");
        break;
    case TOKEN_UNIDENTIFIED:
        fprintf(stderr,"The line must start with a valid Label OR valid Dir/ Macro/ Command type.\n"); 
        break;
    case COMMAND_UNEXPECTED_CHAR:
        fprintf(stderr,"Invalid syntax in the command operands.\n"); 
        break;
    case COMMAND_MISSING_PARAMETER:
        fprintf(stderr,"Command missing 2nd parameter.\n"); 
        break;
    case COMMAND_TOO_MANY_OPERANDS:
        fprintf(stderr,"Command has too many operands.\n"); 
        break;
    case COMMAND_INVALID_METHOD:
        fprintf(stderr,"Command parameter has invalid addressing method.\n"); 
        break;
    case COMMAND_INVALID_PARAMETER_METHODS:
        fprintf(stderr,"The parameters addressing method do not match the commands.\n"); 
        break;
    case COMMAND_INVALID_NUMBER_PARAM:
        fprintf(stderr,"The number of parameters does not match the command.\n"); 
        break;
    case ENTRY_CANT_BE_EXTERN:
        fprintf(stderr,".entry can't apply to a label that was defined as external.\n\n"); 
        break;
    case ENTRY_LABEL_DOESNT_EXIST:
        fprintf(stderr,"label entry does not exist.\n"); 
        break;
    case LABEL_DOESNT_EXIST:
        fprintf(stderr,"label does not exist.\n"); 
        break;
    case INDEX_LABEL_ISNT_VALID:
        fprintf(stderr,"The label in the index method isn't valid label.\n"); 
        break;
    case INDEX_NOT_A_NUMBER:
        fprintf(stderr,"The index is not a number (or a defined macro).\n"); 
        break;
    case INDEX_EXTRA_TEXT:
        fprintf(stderr,"There is additional text after the closing bracket of the index method.\n"); 
        break;
    case INDEX_LABEL_DOESNT_EXIST:
        fprintf(stderr,"The label that is used in the Index method doesn't exist.\n"); 
        break;
    case LABEL_ALREADY_EXISTS:
        fprintf(stderr,"Label by the same name already exists.\n"); 
        break; 
	case LABEL_BEFORE_MACRO:
		fprintf(stderr, "Cannot declare a macro after label.\n");
		break;
	case NEGATIVE_INDEX:
		fprintf(stderr, "Index must be non-negative integer.\n");
		break;
	case MISSING_HASHTAG_MACRO:
		fprintf(stderr, "Macro missing # in the operand.\n");
		break;
	case NUMBER_LARGER_THAN_12_BITS:
		fprintf(stderr, "Invalid number, larger than 12 bits.\n");
		break;
	case NUMBER_LARGER_THAN_14_BITS:
		fprintf(stderr, "Invalid number, larger than 14 bits.\n");
		break;
    }
}

