# Instructions:

## Run Locally:
  1. Ford-Fulkerson Algorithm
    - How to compile: 
      ```
      
      $ javac fordFulkerson.java
      
      ```
    - How to run: 
    ```
    
    $ java fordFulkerson
    
    ```
    - Input/Output: standard stdin/stdout
    ```
    
    INPUT FORMAT: The first line contains k, the number of problems. Then descriptions of the
    problems follow. The first line contains n (the number of vertices) and m (the number of edges).
    The second line contains s; t 2 f1; : : : ; ng (the source and the sink). Then m lines follow. Each
    line contains three integers u; v; c, where u; v 2 f1; : : : ; ng and 1 ≤ c ≤ 1; 000; 000; this means that
    there is edge (u; v) with capacity c in the graph. We have 2 ≤ n ≤ 1; 000 and 1 ≤ m ≤ 10; 000.
    OUTPUT FORMAT: The output contains one line for each problem|the value of the maximum
    s-t flow.
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
    
    ```
  2. Push Relabel Algortihm
    - How to compile: 
    ```
    
    $ javac pushRelabel.java
    
    ```
    - How to run: 
    ```
    
    $ java pushRelabel
    
    ```
