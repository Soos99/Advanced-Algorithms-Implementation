import java.util.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileWriter;
import java.io.IOException;

public class pushRelabel {
    static int[][] graph;
    static int[][] flow;
    static List<List<Integer>> resEdges;
    static int vertices, edge;
    static int[] labelling;
    static int[] excess;
    static boolean[] marked;

    public static void main(String[] args) throws IOException{

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
            labelling = new int[vertices];
            excess = new int[vertices];

            for (int i = 0; i < edge; i++){
                getGraph = sc.nextLine().split(" ");
                int from = Integer.parseInt(getGraph[0]) - 1;
                int to = Integer.parseInt(getGraph[1]) - 1;
                int weight = Integer.parseInt(getGraph[2]);
                graph[from][to] = weight;

                //push flow
                if (from == s){
                    //update flow
                    flow[from][to] = weight;
                    flow[to][from] = -weight;

                    //update excess
                    excess[to] += weight;
                }
            }
            
            // long start = System.currentTimeMillis();

            //run Push-Relabel algorithm
            pushRelabel(s, t);
            
            // long finish = System.currentTimeMillis();
            // long timeElapsed = finish - start;
            //System.out.println("The run-time is: " + timeElapsed);


            //get result
            int result = 0;
            for (int i = 0; i < vertices; i++){
                result += flow[s][i];
                //System.out.println(flow[s][i]);
            }
            results[index] = result;
            index++;
        }
        sc.close();

        //print result
        for (int i = 0; i < problems; i++){
            System.out.println(results[i]);
        }
    }

    public static void pushRelabel(int s, int t){

        //label source as n
        labelling[s] = vertices+5;

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

        //push-relabel operations
        int excessV = findExcess(s,t);
        while (excessV >= 0){
            int pushV = findPush(excessV);
            if (pushV >= 0){
                //push
                push(resGraph,excessV,pushV,s,t);
            }
            else {
                //relabel
                relabel(excessV);
            }
            excessV = findExcess(s,t);
        }
    }
    

    //push operation
    public static void push(int[][] resGraph, int u, int v, int s, int t){

        int min = Math.min(excess[u],resGraph[u][v]);

        //update flow 
        flow[u][v] += min;
        flow[v][u] -= min;

        //update residual graph
        resGraph[u][v] -= min;
        Integer rm = Integer.valueOf(v);
        if (resGraph[u][v] == 0) {
            resEdges.get(u).remove(rm);
        }
        if (resGraph[v][u] == 0) resEdges.get(v).add(u);
        resGraph[v][u] += min;

        //update excess
        excess[u] -= min;
        excess[v] += min;

    }


    //relabel operation
    public static void relabel(int u){
        int min = Integer.MAX_VALUE;
        for (int num : resEdges.get(u)){
            min = Math.min(labelling[num],min);
        }
        labelling[u] = min + 1;
    }


    //find active node if applicable
    public static int findExcess(int s, int t) {
        for (int i = 0; i < vertices; i++){
            if (excess[i] > 0 && i != s && i != t) return i;
        }
        return -1;
    }


    //find node to push if applicable
    public static int findPush(int u){
        for (int v : resEdges.get(u)){
            if (labelling[u] == labelling[v]+1) return v;
        }
        return -1;
    }
}