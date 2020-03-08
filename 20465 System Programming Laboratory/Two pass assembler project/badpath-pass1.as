; This file is intended to check the bad-path of the assembler.

; Each line (except a few) contains an error in the assembly language code.
; A comment preceding each line explains the error.

; All errors should be detectable in the first pass of the assembler.

; Run the assembler on this file, and verify that it catches all the errors.
; Your assembler messages need not be identical to the comments in this file.

; Disclaimer: this list of errors is not exhaustive; 
;             you are encouraged to identify additional errors.

; 1. this line is ok (label DATA4 is declared later)
Start:  dec DATA4

; 2. missing operand
        sub #5

; 3. missing operand
        sub #5,

; 4. missing operand
        red

; 5. this line is ok (label declared as external)
       .extern DATA4

; 6. invalid target operand (immediate)
        add  #5,#6

; 7. this line is ok (immediate target operand allowed for cmp)
Next:   cmp  #5,#6

; 8. invalid operand (immediate)
        inc  #50

; 9. undefined instruction
       and  r1,r2

; 10. undefined instruction (case sensitivity)
        jSr  Start

; 11. invalid characters (,r2)
        cmp  r1,,r2

; 12. invalid characters (,r3)
        add  #5,r1,r3

; 13. invalid characters (blabla)
        prn r1 blabla

; 14. invalid operand (undefined addressing mode)
        prn  48

; 15. label was already declared earlier
Next:   clr  r2

; 16. label does not start in first column (optional error! label is ignored)
	label1:   sub r1,r7

; 17. invalid source operand (register)   
        lea  r3,r1

; 18. invalid source operand (immediate)   
        lea  #3,r5

; 19. invalid characters (Start)
        stop Start

; 20. this line is ok (.data directive can be used without a label)
        .data 400

; 21. this line is ok (.string directive can be used without a label)
        .string "maman14"

; 22. invalid characters (400) 
        .data   200 400

; 23. this line is ok (arbitrary whitespace between fields and at end of line)
DATA1:  .data   100,    	200 ,  -500, 300   				   

; 24. invalid characters (,3)
        .data   1, ,3

; 25. invalid character (,) 
        .data   4,

; 26. invalid characters (#123)
        .data   #123

; 27. invalid characters (.4)
        .data   12.4

; 28. invalid characters (-5)
        .data   --5

; 29. invalid label (cannot be an instruction name)
mov:    .data   5

; 30. invalid label (cannot be a register name)
r6:     .data   200

; 31. label was already declared earlier
DATA1:  .data   300

; 32. this line is ok (new label, as labels are case sensitive)
Data1:  .data   100, +200, -300

; 33. invalid label(non-alphabetic first character)
1DATA:  .data   300

; 34. this line is ok (label X declaration ignored - warning may be issued)
X:      .entry  DATA1

; 35. this line is ok (it is ok to declare the same external more than once)
        .extern DATA4 

; 36. local label cannot be declared as external
        .extern Start
		
; 37. this line is ok (label Y declaration ignored - warning may be issued)
Y:      .extern DATA8

; 38. this line is ok (label STOP is declared later)
        .entry  STOP

; 39. label already designated as external (cannot be both entry and exteral) 
        .entry  DATA4

; 40. invalid symbol (DATA2 is not an instruction name and not a valid label)
DATA2   .data   4

; 41. undefined directive (case sensitivity of directive name)
        .DATA   5

; 42. This line is ok (it is ok to designate the same entry more than once)
        .entry  DATA1

; 43. invalid characters (blabla is not a string)
        .string blabla

; 44. invalid characters (blabla)
        .string "abcdefg" blabla

; 45. this line is ok (comma within string is not a separator)
STR1:   .string "abc, ,defg"

; 46. invalid label (too long)
SuperCalifragilisticExpiAlidocious: .data	4
          
; 47. missing argument in directive 
        .data

; 48. missing argument in directive 
        .entry

; 49. undefined directive
        .invalid 85,90

; 50. this line is ok
        rts

; 51. invalid characters (Next)
        rts Next

; 52. this line is ok (case sensitivity)
STOP:   stop
