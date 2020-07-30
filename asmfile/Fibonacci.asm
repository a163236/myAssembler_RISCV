li x1,10
li x2,1
blt x2,x1,12
mv x2,x1
j 40
li x2,1
li x3,1
li x4,2
bge x4,x1,24
mv x5,x2
add x2,x2,x3
mv x3,x5
addi x4,x4,1
j -20
mv x6,x2
sw x7, 0(x0)