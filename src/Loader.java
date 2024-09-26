public class Loader {
    public static void main(String[] args) {
        double[] list = new double[10];
        double min = list[0];
            for (int i = 1; i < list.length; i++) {
                if (min > list[i])
                    min = list[i];
                System.out.println(min);


            }
    }
}
