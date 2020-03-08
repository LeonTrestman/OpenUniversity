; This file is intended to check the bad-path of the assembler.

; Each line (except a few) contains an error in the assembly language code.
; A comment preceding each line explains the error.

; All errors should be detectable in the second pass of the assembler
; or at the end of first pass (during symbol table consistency check).

; Run the assembler on this file, and verify that it catches all the errors.
; Your assembler messages need not be identical to the comments in this file.

; 1. this line is ok (label X is ignored, warning may be issued in 1st pass)
X:     .extern DATA1

; 2. this line is ok (label Y is ignored, warning may be issued in 1st pass)
Y:     .entry START

; 3. this line is ok (DATA1 is an external label)
START:  add  DATA1,r1

; 4. undefined label as first operand
        add  Q,r2

; 5. undefined label as second operand
        add  r3,Q

; 6. undefined label as first operand (label X is ignored)
        add  X,r4

; 7. undefined label as second operand (label Y is ignored)
        add  r5,Y

; 8. undefined label
       .entry Q

; 9. undefined label (label X is ignored)
       .entry X

; 10. undefined label (label Y is ignored)
       .entry Y

; 11. this line is ok
        stop
