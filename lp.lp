\* Problem: myProblem *\

Minimize
 z: + x1 + x2 + x3

Subject To
 c1: + x3 + x1 - ~r_1 = 0.1
 c2: + x3 + x2 >= 0.1

Bounds
 0 <= ~r_1 <= 0.1
 0 <= x1 <= 0.5
 0 <= x2 <= 0.5
 0 <= x3 <= 0.5

End
