#Title : 2016c Q10

################ DATA segment #########################
.data

arr1: .word 777, 33, 35, 35, 33,777 
arr2: .word 1000,1001,1002, 1003, 1004, 1005 
msg1: .asciiz "Y\n" #Y for polindrome
msg2: .asciiz "N\n" #N for polindrome
msgspace: .asciiz "\t" #spacing between numbers

################ Code segment ########################
.text
.globl main

main:
	#testing array 1
	la $a1,arr1 #load arr1 adress into @a1
	addi $a2,$zero,6 # 6 sized array as given
	jal exam_procedure
	
	
	#testing array 2
	la $a1,arr2 #load arr2 adress into @a2
	addi $a2,$zero,6 # 6 sized array as given
	jal exam_procedure

		
exit:
	li $v0 , 10 #end program
	syscall

####################################################
#Procedure: exam_procedure #
# parameter $a1 points to the array address in memory #
# parameter $a2 is the number of integer in array (only even and starting from 2) #
# Given an array address and size , prints one number from the beggining and one from the end#
# continues to print the next one from the beggining and one number from before last one number untill the ned of the array
# also checks if the original array was a polindrome and print Y for yes N for no.
####################################################

 exam_procedure:
 	
	addi $t0,$a1,0 #pointer to the beggining of array
	sub $a2,$a2,1 # last index N-1 for the last number in the array
	mul $a2,$a2,4 #last word in array
	add $t1,$a1,$a2 #pointer to the end of the array
	addi $t2,$zero,0 #assuming polindrome from start, 0 for polindrome , 1 for not polindrome
	 
	
polindrome_check:
	beq $t2,1,print_array_beg #if not polindrome skip
	lw $t3 ,0($t0) #loading beg pointer num
	lw $t4 ,0($t1) #loading end pointer num
	sub $t4,$t4,$t3 
	bnez $t4,not_poli #if not same number sub wont be zero so branch to update it

print_array_beg:

	bgt $t0, $t1 ,polindrome_print #if beggininge index larger than ending index stop pringting
	li $v0,1 #print int
	lw $a0,0($t0) #printing the int of beggining pointer
	syscall
	
	la $a0,msgspace # space
	li $v0,4 
	syscall
	
	addi $t0,$t0,4 # next word in the array
	
	
print_array_end:
	lw $a0,0($t1) #printing the int of end pointer
	li $v0,1 #print int
	syscall
	
	la $a0,msgspace # space
	li $v0,4 
	syscall
	
	addi $t1,$t1,-4 # one word before
	
	j polindrome_check
	
polindrome_print:
	bnez $t2,print_not
	la $a0,msg1 # space
	li $v0,4 
	syscall
	jr $ra 

print_not:
	la $a0,msg2 # space
	li $v0,4 
	syscall
	jr $ra

not_poli:
	addi $t2,$zero,1 #flag for not pilindrome
	j print_array_beg

    ############################################################################################################################
    
    #2017a 2a
################# Data segment #####################
.data
buf: .ascii "xabvfrqwertyqqqwaquu"
buf1: .space 20
msg1: .asciiz "\nThe number of identical char is: "
################# Code segment #####################
.text
.globl main
main:

	li $t0,0 #initialize buff index
	li $t9,0 #intialize equal counter
	
exam_task:

	beq $t0,19,printbuf1
	lb $t1,buf($t0)
	lb $t2,buf+1($t0)
	sub $t3,$t1,$t2 #sub between index ascii and the next one
	beqz $t3,addequal #equal zero add =
	bgtz $t3,addplus # positive means first bigger so add plus
	#else just add minus
	add $t3,$zero,'-'
	sb $t3,buf1($t0)
	addi $t0,$t0,1# index +1
	j exam_task
	
	
addequal:
	add $t3,$zero,'='
	sb $t3,buf1($t0)
	addi $t0,$t0,1# index +1
	addi $t9,$t9,1# equal counter +1
	j exam_task
	
addplus:
	add $t3,$zero,'+'
	sb $t3,buf1($t0)
	addi $t0,$t0,1# index +1
	j exam_task
	
printbuf1:
	add $t3,$zero,0x0 
	sb $t3,buf1($t0)#adding null bite to print string
	la $a0 ,buf1
	li $v0,4
	syscall
	
printequal:
	la $a0 ,msg1
	syscall
	move $a0 ,$t9
	li $v0,1
	syscall
	
exit:
    li      $v0, 10              # terminate program run and
    syscall                      # Exit 
    
    ############################################################################################################################

#2018a b
.data

msg1: .asciiz "\nResult of polindrome : "
msg2: .asciiz "\nThe Total Sum of the bytes in the word (by 2 compliments method): "


.text
.globl main
main:
	
    li $a1,0x1e8f8f1e
    li $v0,4
    la $a0,msg1 #printing msg1 (Result of polindrome)
    syscall
    
    jal byte_palindrome
    
    li $a1,0x02468ace
    li $v0,4
    la $a0,msg1 #printing msg1 (Result of polindrome)
    syscall
    
    jal byte_palindrome
    
exit:
    
      li $v0,10
      syscall 


###
#the procedure check if the byte is palndrome (1 in $v1 if it is otherwise 0 in $v1) and than adds all 
#the bytes in the word (4) in 2 complement methodthan prints the sum of all the bytes
#parameter $a1 point to the given word
#parameter $v1 for polindrome result (1 true 0 false)
###

byte_palindrome:
	li $v1,1
	li $t9,0 #t9, total sum (2 compliment)
	#firs two bits (end and beg)
	sll $t1,$a1,24 #(first 8 bits)
	sra $t1,$t1,24 #sign extend
	sra $t2,$a1,24 #last 8 bits sign extended
	add $t9,$t9,$t1
	add $t9,$t9,$t2
	sub $t3,$t1,$t2
	beqz $t3, continue1 #if polindrome skip
	li $v1,0 
continue1:
	rol $t1,$a1,16 #(bits 8-16)
	sra $t1,$t1,24
	sll $t2,$a1,8 #(bits 16-24)
	sra $t2,$t2,24
	add $t9,$t9,$t1
	add $t9,$t9,$t2
	sub $t3,$t1,$t2
	beqz $t3, printAll #if polindrome skip
	li $v1,0 
	
printAll:
	#printing palindrome result
	move $a0,$v1
	li $v0,1
	syscall
	
	li $v0,4
        la $a0,msg2 #printing msg2 (Total Sum)
        syscall
        
        move $a0,$t9 #printing the sum
        li $v0,1
        syscall
     
        jr $ra

############################################################################################################################
       

#2018a a
.data

space: .asciiz "\n"

.text
.globl main
main:
	#test 1 valid result 3333
	li $a1,255
	li $a2,4
	jal print_base
	li $v0,4
	la $a0,space
	syscall
	#test 2 valid result 414153
	li $a1,33333
	li $a2,6
	jal print_base
	li $v0,4
	la $a0,space
	syscall
	#test 3 valid result 324
	li $a1,89
	li $a2,5
	jal print_base
	li $v0,4
	la $a0,space
	syscall



exit:
    
      li $v0,10
      syscall 


###
#the procedure print_base gets an unsinged word and prints it in different base )(from 2-10) 
#it's assumed the base is valid
#parameter $a1 is unsigned number
#parameter $a2 for is given valid base
#the solution is using stack for printing in the right order
###

print_base:
	move $t0,$a1
	addi $9,$0,0 #intialize stack counter
loop:
	divu $t0,$a2
	mfhi $t1 # $t1 mod $a2
	subi $sp,$sp,4
	sw $t1,0($sp)#push mod
	addi $t9,$t9,1 #stack counter increment
	mflo $t0 # $t0/a0
	bnez $t0,loop
	
printStack: 

	beqz $t9,end # can't pop anymore
	
	li $v0,1
	syscall
	addi $sp,$sp,4
	subi $t9,$t9,1
	j printStack
	
end:
	jr $ra
        
############################################################################################################################