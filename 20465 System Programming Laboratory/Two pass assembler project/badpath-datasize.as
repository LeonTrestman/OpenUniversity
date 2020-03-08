; This file contains tests for errors in the range of data values.

; Each line (except a few) contains an error in the assembly language code.
; A comment preceding each line explains the error.

; Depending on implementation, these errors are detectable
; either in the first or second pass.

; Run the assembler on this file, and verify that it catches all the errors.
; Your assembler messages need not be identical to the comments in this file.

; 1. this line is ok
X:   cmp r1,r2

; 2. this line is ok (highest positive value that fits in 14 bits)
    .data  8191

; 3. data overflow (positive value too large to fit in 14 bits)
    .data  8192

; 4. this line is ok (lowest negative value that fits in 14 bits)
    .data  -8192

; 5. data overflow (negative value too large to fit in 14 bits)
    .data  -8193

; 6. this line is ok
     stop
