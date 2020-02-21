import java.util.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileWriter;
import java.io.IOException;


public class fordFulkerson {
    static int[][] graph;
    static int[][] flow;
    static List<List<Integer>> resEdges;
    static int vertices, edge;
    static boolean[] marked;
    public static void main (String[] args) throws IOException{

        //read input
        // File file = new File(inputFile);
        // FileWriter writer = new FileWriter(outputFile);
        Scanner sc = new Scanner(System.in);
        // sc.useDelimiter("\\Z");

        String prob = sc.nextLine();
        int problems = Integer.parseInt(prob);
        int[] results = new int[problems];
        int index = 0;

        //read each problem and initialize the input
        for (int count = 0; count < problems; count++){
            //initialize graph
            String[] getGraph = sc.nextLine().split(" ");
            vertices = Integer.parseInt(getGraph[0]);
            edge = Integer.parseInt(getGraph[1]);

            getGraph = sc.nextLine().split(" ");
            int s = Integer.parseInt(getGraph[0]) - 1;
            int t = Integer.parseInt(getGraph[1]) - 1;

            graph = new int[vertices][vertices];
            flow = new int[vertices][vertices];

            for (int i = 0; i < edge; i++){
                //get input
                getGraph = sc.nextLine().split(" ");
                int from = Integer.parseInt(getGraph[0]) - 1;
                int to = Integer.parseInt(getGraph[1]) - 1;
                int weight = Integer.parseInt(getGraph[2]);

                //update graph
                graph[from][to] = weight;            
            }
            
            // long start = System.currentTimeMillis();

            //run Ford-Fulkerson algorithm
            fordFulkerson(s, t);

            // long finish = System.currentTimeMillis();
            // long timeElapsed = finish - start;
            //System.out.println("The run-time is: " + timeElapsed);
            

            //get result
            int result = 0;
            for (int i = 0; i < vertices; i++){
                result += flow[s][i];
            }

            results[index] = result;
            index++;
        }
        sc.close();

        for (int i = 0; i < problems; i++){
            System.out.println(results[i]);
        }
    }

    //run Ford-Fulkerson algorithm
    public static void fordFulkerson(int s, int t){
        //mark vertices in explore function
        marked = new boolean[vertices];

        //initialize residual graph and edges
        int[][] resGraph = new int[vertices][vertices];
        resEdges = new ArrayList<>();
        for (int i = 0; i< vertices; i++){
            List<Integer> dummy = new ArrayList<>();
            resEdges.add(dummy);
        }
        for (int i = 0; i < vertices; i++){
            for (int j = 0; j < vertices; j++){
                resGraph[i][j] = graph[i][j] - flow[i][j];
                if (resGraph[i][j] > 0) resEdges.get(i).add(j);
            }
        }

        //store s-t path in residual graph
        ArrayList<Integer> path  = new ArrayList<>();

        //get the max flow
        marked[s] = true;
        while (explore(resGraph,path,s,t)) { //until no s-t path in residual graph
            int min = Integer.MAX_VALUE;
            path.add(s);

            //get bottle-neck of path flow
            for (int i = 0; i < path.size() -1; i++){
                min = Math.min(min,resGraph[path.get(i+1)][path.get(i)]);
            }
            
            //modify flow and residual graph
            for (int i = 0; i < path.size() -1; i++){
                int x = path.get(i+1);
                int y = path.get(i);
                flow[x][y] += min;
                flow[y][x] -= min;
                resGraph[x][y] -= min;
                Integer rm = Integer.valueOf(y);
                if (resGraph[x][y] == 0) {
                    resEdges.get(x).remove(rm);
                }
                if (resGraph[y][x] == 0) resEdges.get(y).add(x);
                resGraph[y][x] += min;
            }

            //reinitialize path and marked array to find s-t path in residual graph again
            path  = new ArrayList<>();
            marked = new boolean[vertices];
            marked[s] = true;
        }
    }

    //find path in residual graph and store the path in ArrayList<Integer> path 
    public static boolean explore(int[][] resGraph, ArrayList<Integer> path, int s, int t){
        for (int num : resEdges.get(s)){
            if (num == t) {
                path.add(t);
                return true;
            }
            else {
                if (!marked[num]){
                    marked[num] = true;
                    if (explore(resGraph,path,num,t)) {
                        path.add(num);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}