# Instructions

## Description

First phase of simplex algorithm. The algorithm finds if the linear system is feasible. If yes, it returns the initial feasible basis for the linear system.

## Run locally 

### 1. How to compile  

            $ javac feasibleLinearProgram.java

### 2. How to run  

            $ java feasible LinearProgram
            
### 3. Input/Output: Standard stdin/stdout

**INPUT FORMAT:** The first line contains an integer k - the number of problem instances. Then the descriptions of the k problem instances follow:  
+ For each instance the first line contains two numbers n and m, where n is the number of variables and m is the number of constraints. + Then m lines follow, each line containing n+1 numbers. Each line gives a row in A and the corresponding bi ≥ 0. We have 1 ≤ n ≤ 1,000
and 1 ≤ m ≤ 1,000.

**OUTPUT FORMAT:**  
The output contains one or two lines for each problem.
• If the problem is infeasible then there is a line saying INFEASIBLE.
• If the problem is feasible then the line contains a feasible basis; the indices of the basis are in {1,2,... ,n}, listed in increasing order.

**EXAMPLE INPUT:**  
3  
3 2  
1 1 0 2  
1 0 -1 3  
2 2  
1 1 2  
2 2 3  
4 3  
5 -8 -7 6 0  
0 -6 3 -7 10  
-2 -8 2 9 8  

**EXAMPLE OUTPUT:**  
INFEASIBLE  
INFEASIBLE  
1 3 4  
