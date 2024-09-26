import java.util.Scanner;

public class Input {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Введите слово");
        String string = s.nextLine();
        System.out.println("Вы ввели " + string);
    }
}
