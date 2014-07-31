package closestpair;

import java.io.*;


public class ClosestPair {

    private static int[][] pairs;
    private static int size = 0;
    private static int[][] temporary;

	// read the points from the file
    public static void readFile() {

        try {

            File file = new File("points.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String strArr[];

			int[][] temp = new int[100][2];										
            while ((strLine = br.readLine()) != null) {

                if (strLine.trim().length() == 0) {
                    continue;
                }

                strArr = strLine.split(" ");

                try {

                    temp[size][0] = Integer.parseInt(strArr[0]);
                    temp[size++][1] = Integer.parseInt(strArr[1]);
                } catch (NumberFormatException ex) {
                    continue;
                }
            }
            pairs = new int[size][2];
            for (int i = 0; i < size; i++) {
                pairs[i][0] = temp[i][0];
                pairs[i][1] = temp[i][1];
            }
            in.close();


        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

	
	
    public static int[][] Merge_Sort(int numbers[][], int low, int high, int option) {
        int[][] temp = new int[(high - low + 1)][2];
        temporary = new int[(high - low + 1)][2];
        for (int i = 0; i < (high - low + 1); i++) {
            temporary[i][0] = numbers[i][0];
            temporary[i][1] = numbers[i][1];
        }
        return Rec_Merge_Sort(temporary, temp, low, high, option);
    }

    // Sorting -  MergeSort Recursive
    public static int[][] Rec_Merge_Sort(int numbers[][], int temp[][], int low, int high, int option) {
        if (low < high) {
            int mid = (low + high) / 2;
            // recursive calls and merge
            Rec_Merge_Sort(numbers, temp, low, mid, option);
            Rec_Merge_Sort(numbers, temp, mid + 1, high, option);
            return Merge(numbers, temp, low, mid, high, option);
        }
        return null;
    }

    // Merge the halfs of the array, from the pointer low until the pointer high
    public static int[][] Merge(int numbers[][], int temp[][], int low, int mid, int high, int option) {
        int i = low, j = mid + 1, t = low, k = -1, l = -1;
        if (option == 1) {    // for x
            k = 0;
            l = 1;
        } else {
            k = 1;
            l = 0;
        }
        // Merge until one of the two halfs is finished
        while ((i <= mid) && (j <= high)) {
            if (numbers[i][k] <= numbers[j][k]) {
                temp[t][l] = numbers[i][l];
                temp[t++][k] = numbers[i++][k];

            } else {
                temp[t][l] = numbers[j][l];
                temp[t++][k] = numbers[j++][k];
            }
        }
		// Merge the rest
        if (i > mid) {
            for (int r = j; r <= high; r++) {
                temp[t][l] = numbers[r][l];
                temp[t++][k] = numbers[r][k];
            }
        } else {
            for (int r = i; r <= mid; r++) {
                temp[t][l] = numbers[r][l];
                temp[t++][k] = numbers[r][k];
            }
        }
        
        // Copy to the initial array
        for (int r = low; r <= high; r++) {
            numbers[r][0] = temp[r][0];
            numbers[r][1] = temp[r][1];
        }
        return temp;
    }

	// distance of two points
    public static double distance(int[] Point1, int[] Point2) {
        if (Point1 != null && Point2 != null) {
            return Math.sqrt(Math.pow((Point1[0] - Point2[0]), 2.0) + Math.pow((Point1[1] - Point2[1]), 2.0));
        }
        return java.lang.Double.POSITIVE_INFINITY;
    }

	
	
    public static int[][] Closest_Pair(int[][] pairs) {
        int Px[][] = Merge_Sort(pairs, 0, size - 1, 1);
        int Py[][] = Merge_Sort(pairs, 0, size - 1, 2);
        return Closest_Pair_Rec(Px, Py);
    }

	
	// Closest Pair Search Recursive
    public static int[][] Closest_Pair_Rec(int[][] Px, int[][] Py) {
        int length = Px.length;
        if (length == 1) {
            return null;
        }
        if (length == 2) {

            return Px;
        }
        int mid = (length - 1) / 2;
        int[] midPoint = new int[2];
        midPoint = Px[mid];
        int Qx[][] = new int[mid][2];
        int Rx[][] = new int[length - mid - 1][2];

        for (int i = 0; i < mid; i++) //first half of X becomes Xl
        {
            Qx[i] = Px[i];
            System.out.println("Qxx" + Qx[i][0] + "Qxy" + Qx[i][1]);
        }
        for (int i = mid + 1; i <= length - 1; i++) //second half of X becomes Xr
        {
            Rx[i - mid - 1] = Px[i];
            System.out.println("Rxx" + Rx[i - mid - 1][0] + "Rxy" + Rx[i - mid - 1][1]);
        }


        int counter1 = 0, counter2 = 0;
        int[][] Qy = new int[mid][2];    //indices sorted by y-coord for left
        int[][] Ry = new int[length - mid - 1][2];  //indices sorted by y-coord for right
        System.out.println("qy:" + (mid) + "ry:" + (length - mid - 1));
        System.out.println("midx:" + midPoint[0] + " midy:" + midPoint[1]);
        for (int i = 0; i < length; i++) {
            System.out.println("Pxx:" + Px[i][0] + " Pxy:" + Px[i][1]);
        }

        for (int i = 0; i < length; i++) {
            System.out.println("Pyx:" + Py[i][0] + " Pyy: " + Py[i][1]);
        }
        for (int i = 0; i <= length - 1; i++) {           //Go through all of Y
            if (Py[i][0] < midPoint[0]) {            //  If point left of pmid
                Qy[counter1][0] = Py[i][0];
                Qy[counter1++][1] = Py[i][1];
                System.out.println("Qyx:" + Qy[counter1 - 1][0] + " Qyy:" + Qy[counter1 - 1][1]);

            } else if (Py[i][0] > midPoint[0]) {                             //  Otherwise    
                Ry[counter2][0] = Py[i][0];
                Ry[counter2++][1] = Py[i][1];
                System.out.println("ryx:" + Ry[counter2 - 1][0] + " Ryy:" + Ry[counter2 - 1][1]);

            }
        }
        int[][] leftpair = Closest_Pair_Rec(Qx, Qy);
        int[][] rightpair = Closest_Pair_Rec(Rx, Ry);
        int[][] closestpair = null;
        double closestdist = java.lang.Double.POSITIVE_INFINITY;

        if (leftpair != null && rightpair != null) {
            if (distance(leftpair[0], leftpair[1]) > distance(rightpair[0], rightpair[1])) {
                closestpair = rightpair;
                closestdist = distance(closestpair[0], closestpair[1]);
            } else {
                closestpair = leftpair;
                closestdist = distance(closestpair[0], closestpair[1]);
            }
        } else {
            if (leftpair != null) {
                closestpair = leftpair;
            }
            if (rightpair != null) {
                closestpair = rightpair;
            }
            if (rightpair == null && leftpair == null) {
                closestpair = new int[2][2];
            }
        }
        int sizeS = 0;
        for (int i = 0; i <= length - 1; i++) {     //compute sizeYprime
            if ((Py[i][0] >= midPoint[0] - closestdist) && (Py[i][0] <= midPoint[0] + closestdist)) {
                sizeS++;
            }
        }

        int[][] S = new int[sizeS][2];
        int indexs = 0;
        for (int i = 0; i <= length - 1; i++) {
            if ((Py[i][0] >= midPoint[0] - closestdist) && (Py[i][0] <= midPoint[0] + closestdist)) {
                S[indexs++] = Py[i];
            }
        }
        double dist = java.lang.Double.POSITIVE_INFINITY;
        for (int i = 0; i <= sizeS - 2; i++) {
            int j = i + 1;
            while ((j <= sizeS - 1) && (distance(S[i], S[j]) < closestdist)) {
                dist = distance(S[i], S[j]);
                if (dist < closestdist) {
                    closestdist = dist;
                    closestpair[0] = S[i];
                    closestpair[1] = S[j];
                }
                j++;
            }
        }

        return closestpair;
    }

    public static void main(String[] args) {

        readFile();
        int temp[][] = Merge_Sort(pairs, 0, size - 1, 1);
        for (int i = 0; i < size; i++) {
            System.out.println("x:" + temp[i][0] + " y:" + temp[i][1]);
        }

        int temp2[][] = Merge_Sort(pairs, 0, size - 1, 2);
        for (int i = 0; i < size; i++) {
            System.out.println("x:" + temp2[i][0] + " y:" + temp2[i][1]);
        }
        System.out.println(size - 1);
        System.out.println((int) (size / 2));
        System.out.println();
        System.out.println(distance(Closest_Pair(pairs)[0], Closest_Pair(pairs)[1]));

    }
}
