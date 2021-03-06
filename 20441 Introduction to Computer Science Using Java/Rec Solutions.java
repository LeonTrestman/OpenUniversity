
  Solutions for rec questions from finals of intro to cs and java openU
  Written by Leon Trestman

///////////////////////////////////////////////////////////////////////////
                    //2017 b4 93
    public static int cntTrueReg(boolean[][] mat) {
        return cntTrueReg(mat, 0, 0);
    }

    private static int cntTrueReg(boolean[][] mat, int i, int j) {
        if (j == mat[0].length) {
            j = 0;
            i = i + 1;
        }

        if (i == mat.length)
            return 0;

        if (mat[i][j] == false) {
            return 0 + cntTrueReg(mat, i, j + 1);
        } else {

            detected(mat, i, j);
            return 1 + cntTrueReg(mat, i, j + 1);
        }
    }

    private static void detected(boolean[][] mat, int i, int j) {
        if (mat[i][j] == true) {
            mat[i][j] = false;

            if (j != mat[0].length - 1) {
                detected(mat, i, j + 1);
            }
            if (j != 0) {
                detected(mat, i, j - 1);
            }
            if (i != mat.length - 1) {
                detected(mat, i + 1, j);
            }
            if (i != 0) {
                detected(mat, i - 1, j);
            }

        }
    }
///////////////////////////////////////////////////////////////////////////
                  // 2018 91 a
    public static int cheapestRoute(int[] stations) {
        return cheapestRoute(stations, 0);

    }

    private static int cheapestRoute(int[] stations, int i) {
        int op2;
        if (i >= stations.length)
            return 0;

        if (i == stations.length - 1) {
            return stations[i];
        }

        //op1  jumping 1
        int op1 = stations[i] + cheapestRoute(stations, i + 1);

        //op 2 jumping two
        if (i == stations.length - 2) {
            op2 = stations[i] + cheapestRoute(stations, i + 1);

        } else {

            op2 = stations[i] + cheapestRoute(stations, i + 2);
        }

        return (op1 > op2 ? op2 : op1);
    }

///////////////////////////////////////////////////////////////////////////
                    //2017 b1 90
        public static int KnapSack(Item[] items, int w) {
        return KnapSack(items, w, 0);
    }

    private static int KnapSack(Item[] items, int w, int i) {

        ///pull out item if overloaded.
        if (w < 0) {
            return -(items[i - 1].get_value());
        }
        if (w == 0 || i == items.length) {
            return 0;
        }

        //op1 first used
        int op1 = items[i].get_value() + KnapSack(items, w - items[i].get_weight(), i + 1);

        //op2 first isnt used
        int op2 = KnapSack(items, w, i + 1);
        int max = Math.max(op1, op2);
        return max;
    }

///////////////////////////////////////////////////////////////////////////
                // maman
/**
 *a method that calculates the longest "slope" in 2d matrix, meaning given a num (natural number)
 *  it check if there is -num of the current cell in the nearby cell(up/down/right/left) and continues if it found again.
 * in the end it returns the number of cells of longest found slope in the matrix.
 *
 * @param mat the 2d matrix
 * @param num the num that represents the difference were looking for.
 * @param i i index in matrix
 * @param j j index in matrix
 * @return the number of cells of longest slope found.
 */
private static int longestSlope(int[][] mat, int num, int i, int j) {

    int nextI = i;
    int nextJ = j+1;
    int currentMax =1;

    if (j == mat[0].length){
        nextI = i+1;
        nextJ = 0;
    }

    if (i == mat.length){
        return 0;
    }


    if (j !=mat[0].length) {
        currentMax = slopeCalc(mat, num, i, j);
    }
    int nextMax = longestSlope(mat,num,nextI,nextJ);
    return myMax(currentMax,nextMax);
}

//the checking algorithm.
private static int slopeCalc(int[][] mat, int num, int i, int j) {
    int max = 1;

    //check left
    if (j != 0) {
        if (mat[i][j] - mat[i][j - 1] == num) {
            max = myMax(max,1+ slopeCalc(mat, num, i, j - 1));
        }
    }

    //check right
    if (j != mat[0].length-1) {
        if (mat[i][j] - mat[i][j + 1] == num) {
            max = myMax(max,1+ slopeCalc(mat, num, i, j + 1));
        }
    }
    //check up
    if (i != 0) {
        if (mat[i][j] - mat[i - 1][j] == num) {
            max = myMax(max,1+ slopeCalc(mat, num, i - 1, j));
        }
    }

    //check down
    if (i != mat.length-1) {
        if (mat[i][j] - mat[i + 1][j] == num) {
            max = myMax(max,1+ slopeCalc(mat, num, i + 1, j));
        }
    }

    return max;
}
        // MathMax not allowed in project
private static int myMax(int a, int b) {
    if (a < b) {
        return b;
    } else {
        return a;
    }
}

///////////////////////////////////////////////////////////////////////////

public static int edit(String str1, String str2) {

    return edit(str1, str2, 0, 0);
}

private static int edit(String str1, String str2, int i1, int i2) {

    int op1, op2;
    //reaching the end of the strings;
    if (str1.length() == i1 || str2.length() == i2) {
        return Math.abs((str1.length() - i1) - (str2.length() - i2));
    }

    if (str1.charAt(i1) == str2.charAt(i2)) {
        return edit(str1, str2, i1 + 1, i2 + 1);
    } else {

        //op1 change in the first string
        op1 = 1 + edit(str1, str2, i1 + 1, i2);

        //op2 change in the second string
        op2 = 1 + edit(str1, str2, i1, i2 + 1);
    }
    //returning the minimum
    return (op1 > op2 ? op2 : op1);
}

///////////////////////////////////////////////////////////////////////////
                ///2016 87 6a
public static int count (int sum){

   return  count(sum,1);
 }

 private static int count (int sum , int input){

     //end
     if ( sum == 0) {
         return 1;
     }

     if (sum < 0 || input>sum )
         return 0;

     // op 1 using the the input
     // op 2 not using the input
     return count(sum-input , input+1) + count(sum ,input +1);

 }

///////////////////////////////////////////////////////////////////////////
                  //2017 85 4a
public static int oneFiveSeven(int n) {

    //end
    if (n == 0) {
        return 0;
    }

    if (n < 0) {
        return Integer.MAX_VALUE;
    }

    //op1 using 1
    int op1 = 1 + oneFiveSeven(n - 1);

    //op2 using 5
    int op2 = 1 + oneFiveSeven(n - 5);

    //op3 using 7
    int op3 = 1 + oneFiveSeven(n - 7);

    int min = op1;

    if (min > op2 && op2 > 0) min = op2;
    if (min > op3 && op3 > 0) min = op3;

    return min;
}
///////////////////////////////////////////////////////////////////////////
                    // 2016a 87 6a
public static int minDiff(int[] arr) {
    return minDiff(arr, 0, 0, 0);
}

private static int minDiff(int[] arr, int i, int sum1, int sum2) {

    int op1, op2;
    //end
    if (i == arr.length) {
        return Math.abs(sum1 - sum2);
    }

    //op 1 put the arr[i] in the first "array"
    //op 2 put the arr[i] in the second "array"
    op1 = minDiff(arr, i + 1, sum1 + arr[i], sum2);
    op2 = minDiff(arr, i + 1, sum1, sum2 + arr[i]);

    return Math.min(op1, op2);
}
///////////////////////////////////////////////////////////////////////////
                //2016a 91 2b
public static int minPath(char[][] minChess, int i, int j) {
    //check if valid.
    //if not return a huge number so it won't count with the min and wont effect a sensible sized board;
    if (!(isValid(minChess, i, j)))
        return 999999;

    //end
    if (minChess[i][j] == 'K') {
        return 0;
    }
    
    //marks the place (so it wont go back to it)
    // H for Horse (Knight), because K is for King
    minChess[i][j] = 'H';

    //the knight has 8 options to move
    int op1 = 1 + minPath(minChess, i - 2, j + 1);
    int op2 = 1 + minPath(minChess, i - 2, j - 1);
    int op3 = 1 + minPath(minChess, i - 1, j + 2);
    int op4 = 1 + minPath(minChess, i - 1, j - 2);
    int op5 = 1 + minPath(minChess, i + 1, j + 2);
    int op6 = 1 + minPath(minChess, i + 1, j - 2);
    int op7 = 1 + minPath(minChess, i + 2, j + 1);
    int op8 = 1 + minPath(minChess, i + 2, j - 1);

    //revert the changes for backtracking
    minChess[i][j] = '0';

    //min calc without Math.min
    int min = op1;
    if (min > op2 ) min = op2;
    if (min > op3 ) min = op3;
    if (min > op4 ) min = op4;
    if (min > op5 ) min = op5;
    if (min > op6 ) min = op6;
    if (min > op7 ) min = op7;
    if (min > op8 ) min = op8;

    //if no way found
    if (min >= 999999) {
        min = -1;
    }
    return min;
}

private static boolean isValid(char[][] minChess, int i, int j) {
        
    if (j >= minChess[0].length || j <0 ) {
        return false;
    }
    if (i >= minChess.length || i < 0 ) {
        return false;
    }
    if (minChess[i][j] == 'H') {
        return false;
    }
    return true;
}
///////////////////////////////////////////////////////////////////////////
                  // 2015b 5a 86
public static void printAllSum (int[]a , int sum){
    String ans ="";
    printAllSum(a,sum,0,ans);

}
private static void printAllSum (int[]a , int sum , int i,String ans){

    if(sum == 0 &&  i == a.length){
        System.out.println(ans);
        return;
    }
    if (sum <0 || i == a.length ) {
        return;
    }

    //op 1 the number is part of sum
    //op 2 the number isn't part of the sum
    String ans1 = ans + '1' ;
    String ans2 = ans + "0";
    printAllSum(a,sum-a[i],i+1,ans1);
    printAllSum(a,sum,i+1,ans2);
}
///////////////////////////////////////////////////////////////////////////
            //2015b 83
public static int lcs (String s , String t){
    return lcs(s,t,0,0);
    
 }
  private static int lcs (String s , String t, int index1,int index2){

      if (index1 == s.length() || index2 == t.length() ) {
          return 0;
      }
     if(s.charAt(index1) == t.charAt( index2) )
         return 1 + lcs(s,t,index1+1,index2+1);
     
     //2 op, going either from string one or string 2.
     return  Math.max(lcs(s,t,index1+1,index2),lcs(s,t,index1,index2+1));
  }
 ///////////////////////////////////////////////////////////////////////////
                //2015 91 2bנ
  public static int  makeSum (int[] lengths ,  int k , int num){

    return  makeSum(lengths,k,num,0);
  }
  private static int  makeSum (int[] lengths ,  int k , int num, int i){

      if(num >= 0 && k == 0)
          return 1;

      if( num < 0 || k <= 0 || i > lengths.length-1)
          return 0;

      //2 op index in array participate or doesn't
          return makeSum(lengths,k-lengths[i],num-1,i) + makeSum(lengths,k,num,i+1);
  }
///////////////////////////////////////////////////////////////////////////
                    // 2018 a 2a
public static int howManySorted(int n, int max) {

    return howManySorted(n, max, 1);
}

private static int howManySorted(int n, int max, int input) {

    if (n < 0 || input > max)
        return 0;
    if (n == 0) {
        return 1;
    }
    //2 op:
    //one is using the input
    //second is skipping the input
    return howManySorted(n - 1, max, input) + howManySorted(n, max, input + 1);
}
///////////////////////////////////////////////////////////////////////////
            // 2015 94 5b
public static int longOrdNum (String s){

    return longOrdNum(s,0,0,0,0);
}

private static int longOrdNum (String s,int index,int prev, int max ,int sum){

    if (index == s.length())
        return max;

    if(s.charAt(index) > '9' || s.charAt(index) <= prev) {
            sum = 0;
    }

    if(s.charAt(index) < '9' && s.charAt(index) >= prev) {
        sum++;
    }
        if(sum>max) {
            max = sum;
        }
    return longOrdNum(s,index+1,s.charAt(index),max,sum);
}
///////////////////////////////////////////////////////////////////////////
            // 2015 a4 85
public static int countRopes (int[][] mat){
    return countRopes(mat,0,1,Integer.MAX_VALUE);
    }

private static int countRopes (int[][] mat,int i ,int j , int prev){

        if (j == mat[0].length || j<0 || i == mat.length|| mat[i][j] >= prev)
            return  0;

        if (i == mat.length -1 && mat[i][j] < prev)
            return 1;

        //moving right in the first row
        if ( i == 0){
            countRopes(mat,i,j+1,Integer.MAX_VALUE);
        }
        //3 op.
        //going down
        //going down-right
        //going doqn-left
        return countRopes(mat,i+1,j,mat[i][j]) + countRopes(mat,i+1 ,j+1,mat[i][j]) + countRopes(mat,i+1 ,j-1,mat[i][j]);
    }
///////////////////////////////////////////////////////////////////////////
            //2013b 3b
public static int longestFlat(int[]arr){
    if (arr.length == 0)
        return 0;

   return longestFlat(arr,0,0);
}
private static int longestFlat(int[]arr, int index,int max){

    if(index == arr.length)
        return max;
    
    //plus one because every series starts from one.
    int current = Math.max(samePlus(arr,index,arr[index]) +1,sameMinus(arr,index,arr[index])+1);
            if( current >max)
                max = current;

    return  longestFlat(arr,index+1,max);
}

private static int samePlus(int[]arr,int index,int input){
        if (index +1 < arr.length){
            if(arr[index+1] == input ||arr[index+1] == input+1 ) {
                return 1 + samePlus(arr, index + 1, input);
            } return 0;
        }   return 0;
}

private static int sameMinus(int[]arr,int index,int input){
    if (index +1 < arr.length){
        if(arr[index+1] == input ||arr[index+1] == input-1 ) {
            return 1 + sameMinus(arr, index + 1, input);
        } return 0;
    }   return 0;
}

///////////////////////////////////////////////////////////////////////////
            //2013a 3b
public static boolean match (int[] a , int [] pattern){

    return match(a,pattern,0,0,0);
  }

  private static boolean match (int[] a , int [] pattern,int starti ,int i,int j ){

      if (j == pattern.length || pattern.length ==0 )
          return true;

      if(i == a.length )
          return false;

      if( singleNum(a,i) && (pattern[j] == 1 || pattern[j] == 0  ) ){
          return match(a,pattern,starti,i+1,j+1);
      }
      if( doubleNum(a,i) && (pattern[j] == 2 || pattern[j] == 0 )  ){
          return match(a,pattern,starti,i+1,j+1);
      }
          return match(a,pattern,starti+1,starti+1,0);
  }

  private static boolean singleNum(int[] a , int i) {
      return (-10 < a[i] && a[i] < 10);
  }
  private static boolean doubleNum(int[] a , int i) {
      return ((-100 < a[i] && a[i] <-9  )  ||  ( a[i] > 9 && a[i] < 100));
  }

  ///////////////////////////////////////////////////////////////////////////
                //2015a 2a 83
                //note: it should return 9 and not 10 in the exemple because
                //it returns the path and not the number of nodes.
  public static int shorestPath(int[][] mat) {
    return shorestPath(mat, 0, 0,-1);
}

private static int shorestPath(int[][] mat, int i, int j,int prev) {

    if (i == mat.length - 1 && j == mat[0].length - 1)
        return 0;

    //return a huge number that wont effect the min in a sensible sized mat.
    if( j == -1 || i == mat.length||i == -1|| j == mat[0].length || mat[i][j] <= prev )
            return 999999;

    //going down
     int min1 =  1 + shorestPath(mat, i + 1, j,mat[i][j] );
    //going up
     int min2 = 1 + shorestPath(mat, i - 1, j,mat[i][j]);
    //going right
     int min3 = 1 + shorestPath(mat, i, j + 1,mat[i][j]);
    //going left
     int  min4 = 1 + shorestPath(mat, i, j - 1,mat[i][j]);

     //the min calculation
    int min = min1;
    if (min > min2 ) min = min2;
    if (min > min3 ) min = min3;
    if (min > min4 ) min = min4;

    return min;
}
///////////////////////////////////////////////////////////////////////////
                 //2012a 2a
public static boolean isSumOf (int [] s , int n){
    return isSumOf(s,n,0);
  }

  private static boolean isSumOf (int [] s , int n,int i ){

      if(i == s.length || n < 0  ){
          return false;
      }
      if ( n== 0) {
          return true;
      }
      //2 op using the number in the array or not using
      return (isSumOf(s,n-s[i],i) || isSumOf(s,n,i+1) );
  }

///////////////////////////////////////////////////////////////////////////
             //2014a 5a 2

public static void printPath(int[][] mat) {
    boolean[][] visited = new boolean[mat.length][mat[0].length];

    printPath(mat,0,0,"",Integer.MIN_VALUE,visited);

}
//to stop from printing other ways
static boolean wayfound=false;

private static void printPath(int[][] mat, int i, int j,String path ,int prev ,boolean[][] visited) {

    if(i < 0 || j < 0 || j == mat[0].length || i == mat.length || prev > mat[i][j] || visited[i][j] ||wayfound)
        return;

    path +=  "(" + i + ", " + j + ") ";

    if (isHill(mat,i,j)){
        System.out.println(path);
        wayfound =true;
        return;
    }
    int tmpPrev =mat[i][j];
    visited[i][j] =true;

    printPath(mat,i+1,j,path,tmpPrev,visited);
    printPath(mat,i-1,j,path,tmpPrev,visited);
    printPath(mat,i,j+1,path,tmpPrev,visited);
    printPath(mat,i+1,j-1,path,tmpPrev,visited);

   visited[i][j] =false;
}
private static boolean isHill(int mat[][],int i , int j){

    if(i== 0 || j ==0 || j == mat[0].length-1 || i == mat.length-1 )
        return false;

    //check up
    if ( mat[i][j] <= mat[i-1][j])
        return false;

    //check down
    if ( mat[i][j] <= mat[i+1][j])
        return false;

    //check right
    if ( mat[i][j] <= mat[i][j+1])
        return false;

    //check left
    if ( mat[i][j] <= mat[i][j-1])
        return false;

    return true;
}

///////////////////////////////////////////////////////////////////////////

