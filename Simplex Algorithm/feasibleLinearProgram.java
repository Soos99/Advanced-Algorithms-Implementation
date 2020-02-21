import java.util.*;

public class feasibleLinearProgram {
    //marginal error
    public static final double error = Math.pow(10.0,-9.0);
    public static void main (String[] args){
        //Read input
        Scanner sc = new Scanner(System.in);
        int problems = sc.nextInt();
        int index = 0;
        String[] output = new String[problems];
        //long startTime = System.currentTimeMillis();
        while (index < problems) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            double[][] a = new double[m][n];
            double[] b = new double[m];
            for (int i = 0; i < m; i++){
                for (int j = 0; j < n; j++){
                    a[i][j] = (double) sc.nextInt();
                }
                b[i] = (double) sc.nextInt();
            }

            //finding basis
            output[index] = feasibleLinearProgram(a, b);
            index++;
        }
        // long endTime = System.currentTimeMillis();
        // long elapsedTime = endTime - startTime;
        // System.out.println("The run-time is: " + elapsedTime + "ms");
        sc.close();

        //Print output
        for (int i = 0; i < output.length; i++){
            System.out.println(output[i]);
        }
    }

    //find basis
    public static String feasibleLinearProgram(double[][] a, double[] b){
        //Get m and n
        int m = a.length;
        int n = a[0].length;

        //Create A' = [A|I] 
        double[][] concatA = new double[m][m+n];
        int[] basis = new int[m];
        double[] c = new double[n+m];
        double V = 0;

        //mark which one belong to basis
        boolean[] mark = new boolean[n+m];

        //Create A' = [A'|I]
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                concatA[i][j] = a[i][j];
            }
            concatA[i][i+n] = 1;
        }
        
        //Create basis
        for (int i = 0; i < m; i++){
            basis[i] = i+n;
        }

        //Update objective: min y => -sum(b) + max c'*x
        
        //Calculate V
        for (int i = 0; i < m; i++){
            V -= b[i];
        }

        //Create c and mark
        for (int i = 0; i < n; i++){
            double sum = 0.0;
            for (int j = 0; j < m; j++){
                sum += concatA[j][i];
            }
            c[i] = sum;
        }
        for(int i = 0; i < m; i++){
            c[i+n] = 0.0;
            mark[i+n] = true;
        }
        
        //Bland's rule
        //1. pick x_t
        //2. pick k to exit
        //3. update c,V,b,a
        int t = findt(c,mark);
        while (t != -1){
            int k = exitBase(concatA,b,t);
            mark[basis[k]] = false;
            basis[k] = t;
            mark[t] = true;

            //Set A_tk = 1
            
            double divide = concatA[k][t];
            for (int i = 0; i < m+n; i++){
                concatA[k][i] /= divide;
            }
            b[k] /= divide;

            //Update other rows and b
            for (int i = 0; i < m; i++){
                if (i != k) {
                    double times = concatA[i][t];
                    for (int j = 0; j < m+n; j++){
                        concatA[i][j] -= times*concatA[k][j];
                    }
                    b[i] -= times*b[k];
                }
            }

            //Update V
            V += b[k]*c[t];
            
            //update c
            double times = c[t];
            for (int i = 0; i < m+n; i++){
                c[i] -= concatA[k][i]*times;
            }
            
            t = findt(c,mark);
        }
        if(Math.abs(V) > error) {
            return "INFEASIBLE";
        }
        else {
            Arrays.sort(basis);
            String add = "";
            for (int i = 0; i < basis.length; i++){
                add += (basis[i]+1) + " ";
            }
            return add;
        }
    }

    //find k to exit the base
    public static int exitBase(double[][] concatA, double[] b, int t){
        int result = (concatA[0][t] > 0) ? 0 : -1;
        double min = (result == 0) ? b[0]/concatA[0][t] : Double.MAX_VALUE;
        for (int i = 1; i < b.length; i++){
            if (concatA[i][t] > 0){
                double compare = b[i]/concatA[i][t];
                if (min > compare) {
                    min = compare;
                    result = i;
                }
            }
        }
        return result;
    }

    //find t to put in basis
    public static int findt(double[] c, boolean[] mark){
        for (int i = 0; i < c.length; i++){
            if (!mark[i] && c[i] > error) return i;
        }
        return -1;
    }
}