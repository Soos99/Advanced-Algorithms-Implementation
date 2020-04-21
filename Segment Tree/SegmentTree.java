import java.util.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileWriter;
import java.io.IOException;

public class SegmentTree {
    static final int MAX = 1000000 + 1;
    static int size = 1;
    static int[] start;
    static int[] end;
    static int[] count;
    static int[] measure;
    public static void buildTree (int[] arr){
        start = new int[2*size];
        end = new int[2*size];
        count = new int[2*size];
        measure = new int[2*size];
        for (int i = 0; i < arr.length - 1; i++){
            start[size+i] = arr[i];
            end[size+i] = arr[i+1];
        }
        for (int i = size - 1; i > 0; i--){
            if (start[2*i] == 0 && end[2*i] == 0){
                start[i] = start[2*i+1];
            }
            else {
                start[i] = start[2*i];
            }
            if (start[2*i+1] == 0 && end[2*i+1] == 0){
                end[i] = end[2*i];
            }
            else {
                end[i] = end[2*i+1];
            }
        }
    }
    public static void update(int num, int x, int y, int c){
        if (start[num] == x && end[num] == y){
            count[num] += c;
        }
        else {
            int child1 = 2*num;
            int child2 = 2*num + 1;
            int m = end[child1];
            if (x < m && m < y) {
                update(child1,x,m,c);
                update(child2,m,y,c);
            }
            else if (y <= m) {
                update(child1,x,y,c);
            }
            else if (m <= x){
                update(child2,x,y,c);
            }
        }
        if (count[num] == 0){
            if (num >= size) {
                measure[num] = 0;
            }
            else {
                measure[num] = measure[2*num] + measure[2*num+1];
            }
        }
        else {
            measure[num] = end[num] - start[num];
        }
        return;
    }
    public static void main(String[] args) {
        // String inputFile = "10000.in";
        // File file = new File(inputFile);
        Scanner sc = new Scanner(System.in);
        String[] mn = sc.nextLine().split(" ");
        int m = Integer.parseInt(mn[0]);
        int n = Integer.parseInt(mn[1]);
        int[] arr = new int[m];
        for (int i = 0; i < m; i++){
            arr[i] = sc.nextInt();
        }
        while (size < arr.length - 1){
            size *= 2;
        }
        buildTree(arr);
        sc.nextLine();
        int[] result = new int[n];
        for (int i = 0; i < n; i++){
            String[] cmd = sc.nextLine().split(" ");
            int a = Integer.parseInt(cmd[1]);
            int b = Integer.parseInt(cmd[2]);
            if (cmd[0].equals("I")) {
                update(1, a, b, 1);
            }
            else {
                update(1, a, b, -1);
            }
            result[i] = measure[1];
        }
        for (int i = 0; i < n; i++){
            System.out.println(result[i]);
        }
        sc.close();
    }
}