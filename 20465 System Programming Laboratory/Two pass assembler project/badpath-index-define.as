; This file contains errors in indexed addressing mode and in macros.

; Each line (except a few) contains an error in the assembly language code.
; A comment preceding each line explains the error.

; All errors should be detectable in the first pass of the assembler.

; Run the assembler on this file, and verify that it catches all the errors.
; Your assembler messages need not be identical to the comments in this file.

; Disclaimer: this list of errors is not exhaustive; 
;             you are encouraged to identify additional errors.

; 1. this line is ok
array:  .data 0,1,20,300,400

; 2. this line is ok
str:    .string "hello"

; 3. this line is ok 
start:   inc array

; 4. missing operand
         lea array[0]

; 5. this line is ok 
         add array[2],r1

; 6. this line is ok 
         cmp #48,str[3]

; 7. this line is ok 
         mov array[2],array[3]

; 8. invalid index (negative) 
         dec array[-40]  

; 9. missing index
         dec array[]

; 10. invalid index 
         cmp #123,array[r1]

; 11. invalid index 
         cmp #123,array[#11]

; 12. invalid operand (index mode not allowd for this instruction) 
         jmp array[3]

; 13. invalid characters ([4]) (space before index)
         dec array [4]

; 14. invalid characters( 4]) (space within index)
         dec array[ 4]

; 15. invalid characters( ]) (space within index)
         dec array[4 ]

; 16. invalid characters((5)) 
         clr array(5)

; 17. invalid characters([5]) (index with register operand)
         clr r3[5]

; 18. invalid characters([5])
         clr [5]
		  
; 19. this line is ok
        .define three = 3
 
; 20. this line is ok 
        .define minus100=-100
		
; 21. invalid index (not a macro)
         inc array[ZERO]
		
; 22. this line is ok 
        .define ZERO = 0

; 23. this line is ok
         inc array[ZERO]

; 24. symbol already exists
        .define ZERO = 1

; 25. symbol already exists
        .define array = 1

; 26. invalid macro value (ZERO is not a number)
        .define TWO = ZERO

; 27. invalid label declaration (label not allowed on .define)
MAC:    .define ONE = 1
								
; 28. this line is ok
         add  #ZERO,array[three]

; 29. invalid first operand (not a macro - case sensitivity)
         add  #zero,array

; 30. this line is ok
         cmp #minus100,array

; 31. invalid first operand (not a label or immediate)
         cmp  minus100,array	 

; 32. invalid first opetand (not a macro)
         add  #start,array		 

; 33. invalid second operand (negative index)
         add  #100,array[minus100]

; 34. invalid operand (not a label) 
         inc ZERO

; 35. this line is ok 
x:      .data minus100, 5, ZERO

; 36. invalid operand (not a macro) 
y:      .data SIX

; 37. this line is ok (arbitrary whitespace allowed)
        .define SIX  	 =      7      
	  
; 38. this line is ok	  
z:      .data SIX
