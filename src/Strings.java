import java.util.Arrays;

public class Strings {

    public static void main(String[] args) {

        String s = "          the sky is blue           ";
        s = s.trim();
        String[] worlds = s.split(" ");

        int left = 0;
        int right = worlds.length - 1;

        while (left < right){
            String bufString = worlds[left];
            worlds[left] = worlds[right];
            worlds[right] = bufString;

            left += 1;
            right -= 1;
        }

        System.out.println(Arrays.toString(worlds));
        String outString = String.join(" ", worlds);
        System.out.println(outString);

        StringBuilder answerBuilder = new StringBuilder();

        for (int i = worlds.length - 1; i >= 0; i--) {
            answerBuilder.append(worlds[i] + " ");



        }
        System.out.println(answerBuilder.toString());
    }
}