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