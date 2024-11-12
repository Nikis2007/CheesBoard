import java.util.Arrays;


public class Arrays1 {
    public static void main(String[] args) {
        int[] num1 = {1, 2, 3, 4, 5};
        int[] num2 = {2, 5, 6, 8, 15};

        // [1, 2, 2, 3, 4, 5, 5, 6, 8, 15]

        int outArrays[] = new int[num1.length + num2.length];

        int idx1 = 0;
        int idx2 = 0;

        for (int i = 0; i < outArrays.length; i++) {
            if (idx1 >= num1.length) {
                outArrays[i] = num2[idx2++];
            } else if (idx2 >= num2.length) {
                outArrays[i] = num1[idx1++];
            } else if (num1[idx1] < num2[idx2]) {
                outArrays[i] = num1[idx1++];
            } else {
                outArrays[i] = num2[idx2++];
            }

        }
        System.out.println("out Array: " + Arrays.toString(outArrays));

        int[] nums = {1, 2, 2, 4, 5, 6, 7, 8, 2, 10};
        int val = 2;


    }


    }
