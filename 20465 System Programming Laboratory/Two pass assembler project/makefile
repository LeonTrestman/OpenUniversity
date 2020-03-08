# specify filenames in the following lists

EXEC_FILE = assembler
C_FILES = Error.c firstpass.c Functions.c main.c secondpass.c
H_FILES = Error.h structs.h firstpass.h assembler.h secondpass.h functions.h 
# if there are no header files, change the previous line to: H_FILES =

# ==> to flush stdout after each line, insert in main() the statement: FLUSH_STDOUT; 
# ==> to flush stderr after each line, insert in main() the statement: FLUSH_STDERR;

#--------------------------------------------
# no changes need to be made beyond this line

# definitions for flushing after earch line
D_OFLUSH = -DFLUSH_STDOUT='setvbuf(stdout,NULL,_IOLBF,BUFSIZ);'
D_EFLUSH = -DFLUSH_STDERR='setvbuf(stderr,NULL,_IOLBF,BUFSIZ);'
D_FLUSH = $(D_OFLUSH) $(D_EFLUSH)

O_FILES = $(C_FILES:.c=.o)

all: $(EXEC_FILE)

$(EXEC_FILE): $(O_FILES) 
	gcc -Wall -ansi -pedantic $(O_FILES) -o $(EXEC_FILE) -lm

%.o: %.c $(H_FILES)
	gcc -Wall -ansi -pedantic -c $(D_FLUSH) -o $@ $<
	
clean:
	rm -f *.o $(EXEC_FILE)
	
