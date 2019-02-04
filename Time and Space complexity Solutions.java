     Solutions for time and space complexity questions from finals of intro to cs and java openU
     Written by Leon Trestman 
    
///////////////////////////////////////////////////////////////////////////
            //2016 2a 83
            //Variation of binary search
    public static boolean findX(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left != right) {

            int mid = (left + right) / 2;

            //check for edge cases ,and not getting out of bound
            if (mid - 1 < 0 && arr[mid] + arr[mid + 1] != target)
                return false;

            if (mid + 1 > arr.length - 1 && arr[mid] + arr[mid - 1] != target)
                return false;

            //check for sum of two nearby without going out of bounds.
            if (mid - 1 > 0 && arr[mid] + arr[mid - 1] == target) {
                return true;
            }
            if (mid + 1 < arr.length && arr[mid] + arr[mid + 1] == target) {
                return true;
            }

            if (arr[mid] + arr[mid - 1] > target) {
                right = mid - 1;

            } else {
                left = mid + 1;
            }

        }
        return false;
    }
///////////////////////////////////////////////////////////////////////////
              //2017b 85 4a
              
public static void printClosest(int[]a , int[]b ,int x){

    int left = 0;
    int right = b.length - 1;
    int sum = a[left] + b[right];
    int closest = Math.abs(x - sum);
    int num1 = 0;
    int num2 = 0;

    while ( left < a.length-1 && right > -1){
        sum = a[left] + b[right];

        if(Math.abs(x-sum) < closest){
            closest =Math.abs(sum-x);
            num1 = a[left];
            num2 = b[right];
        }

        if (sum < x){
            left++;
        }else{
            right--;
        }
    }
    System.out.println(num1 + " and " + num2);
}
///////////////////////////////////////////////////////////////////////////
                //2017 5a 86
    //o(n2) time complexity , one loop that goes through "all" the array and another loop
    //that is a variation of the swing algorithm.

public static int countTriplets (int [] arr, int num){

    int count=0;

    //left
    for (int left = 0 ; left != arr.length-3 ; left++){

        int mid = left +1;
        int right = arr.length-1;

        while (mid <right){

            if ( arr[left]+arr[mid]+arr[right] < num ) {
                right--;
            }else {
                count += right- mid; //count all the triplets in between
                mid++;
            }
        }
        
    }
    return count;
}
///////////////////////////////////////////////////////////////////////////
                //2017 90 1b
                // o(n) time complexity,one loop.
public static int findSmallest(int[] arr){
    // if it doesnt start with the lowest positive number
    if (arr[1] != 1)
        return 1;

    int sum=1;

    for (int i = 0 ; i<arr.length ;i++){

        if (sum < arr[i])
            return sum +1;
        sum +=arr[i];
    }
    return sum;
}
///////////////////////////////////////////////////////////////////////////
              //2015b a5 86 
public static void printPairs(int[] a, int k) {

    int left = 0;
    int right = 1;

    while (right < a.length) {

        if (k == a[right] - a[left]) {
            System.out.println("Pair Found: " + a[left] + "," + a[right]);
            left++;
        }

         if (a[right] - a[left] < k) {
            right++;
        } else {
            left++;
        }
    }
}
///////////////////////////////////////////////////////////////////////////
                 // 2018 a 2a
public static boolean what(int[] a, int sum) {
    int left = 0;
    int right = 0;
    int sum_temp = a[0];
    while (left < a.length) {
        if (sum_temp == sum) {
            return true;
        } else {
            if (sum_temp < sum) {
                if (right < a.length - 1) {
                    right++;
                    sum_temp += a[right];
                } else {
                    return false;
                }
            } else {
                sum_temp -= a[left];
                left++;
            }
        }
    }
    return false;
}
///////////////////////////////////////////////////////////////////////////
            //2015b 5b 94 
public static int smallestSub( int [] a , int k) {
    int left = 0;
    int right = 0;
    int count = 0;
    int min = a.length + 1;
    int sum = 0;

    while (left < a.length ) {

        if (sum > k) {
            if (min > count) {
                min = count;
            }
            sum -= a[left];
            count--;
            left++;
        }

        if (sum <= k ) {
            if (right<a.length) {
                sum += a[right];
                count++;
                right++;
            }else{
                return min;
            }
        }
    }
    return min;
}
///////////////////////////////////////////////////////////////////////////
                //2017a 6a 87
public static boolean isPythagorean(int[] arr) {

    for (int a = 0; a != arr.length - 2; a++) {
        int b = a+1;
        int c = b +1;

        while  ( c != arr.length) {

            if ((arr[a] * arr[a]) + (arr[b] * arr[b]) == (arr[c] * arr[c])) {
                return true;
            }

            if (c < arr.length && (arr[a] * arr[a]) + (arr[b] * arr[b]) > (arr[c] * arr[c])) {
                c++;
            }
            if ( c < arr.length && (arr[a] * arr[a]) + (arr[b] * arr[b]) < (arr[c] * arr[c])) {
                b++;
                if (b == c)
                    c++;
            }
        }
    }
    return false;
}
///////////////////////////////////////////////////////////////////////////
            //2014b 4a
            //o(1) complexity , check if the number is a perfect square
public static boolean what (int n){
    double tmp = Math.sqrt(n);
   return  (int)tmp * (int)tmp == n;

}
///////////////////////////////////////////////////////////////////////////
        //2014a 5a
        //variation on binary search

public static int findMax(int[] arr){
    int left = 0;
    int right = arr.length-1;

    while (arr[left] > arr[right] ){
        int mid = (left + right) /2;

        if (arr[left] > arr[mid]){
            right = mid;
        }
        if (arr[right ] < arr[mid] ){
            right = mid;
        }

    }
    return right;
}
///////////////////////////////////////////////////////////////////////////
             //2013b 5a
             // o(logn) time complexity , a varation on binary search

public static int minAbsSum (int []a){
    int left = 0;
     int right = a.length;

     //finds the min number
     while(left < right || left +1 == right){
         int middle = (left+right)/2;
         
         if (Math.abs(a[left]) > Math.abs(a[middle]) ){
             left=middle;

         }else {
             right = middle;
         }
     }
     //checks the lower number nearby within the boundaries
     if (left-1 >0) {
         if (Math.abs(a[left]) + Math.abs(a[left + 1]) > Math.abs(a[left]) + Math.abs(a[left - 1])) {
             left--;
         } else
             if (right+1 < a.length)
             right++;
     }else
         if (right+1 < a.length)
         right++;

 return Math.abs(a[left]) +Math.abs(a[right]);
}

///////////////////////////////////////////////////////////////////////////

