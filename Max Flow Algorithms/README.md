# Instructions:

## Run Locally
  ### 1. Ford-Fulkerson Algorithm
  
   #### a. How to compile:
            
            $ javac fordFulkerson.java
   
   #### b. How to run: 
             
            $ java fordFulkerson
   
   #### c. Input/Output: Standard stdin/stdout
  
  INPUT FORMAT: The first line contains k, the number of problems. The the description of the problem as follow:
  + The next line contains n (the number of vertices) and m (the number of edges).
  + The next line contains s and t with 1 <= s,t <= n (the source and the sink). 
  + Then m lines follow. Each line contains three integers u, v, c, where 1 <= u, v <= n and 1 ≤ c ≤ 1,000,000 (this means that there is edge (u; v) with capacity c in the graph). We have 2 ≤ n ≤ 1; 000 and 1 ≤ m ≤ 10; 000.
  
  OUTPUT FORMAT: The output contains one line for each problem - the value of the maximum s-t flow.
      
  EXAMPLE INPUT:
  2  
  2 1  
  1 2  
  1 2 10  
  2 1  
  2 1  
  1 2 10  
  EXAMPLE OUTPUT:
  10  
  0  
  
  ### 2. Push Relabel Algortihm
  
  #### a. How to compile: 
    
    $ javac pushRelabel.java
    
  #### b. How to run: 
    
    $ java pushRelabel
  
  #### c. Input/Output: Standard stdin/stdout
