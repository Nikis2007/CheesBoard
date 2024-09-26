import java.util.Scanner;

public class TestOut {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Здравствуйте, это простой калькулятор!");
        System.out.println("Введите первое число!");
        double operand1 = scanner.nextDouble();
        System.out.println("Введите необходимую операцию: +, -, *, /");
        char operation = scanner.next().charAt(0);
        System.out.println("Введите второе число!");
        double operand2 = scanner.nextDouble();

        double result = 0;
        switch (operation){
            case '+':
                result = operand1 + operand2;
                System.out.println("Результат: " + result);
                break;
            case '-':
                result = operand1 - operand2;
                System.out.println("Результат: " + result);
                break;
            case '*':
                result = operand1 * operand2;
                System.out.println("Результат: " + result);
                break;
            case '/':
                result = operand1 / operand2;
                if (operand2 == 0){
                    System.out.println("Делить на ноль нельзя!");
                } else
                    System.out.println("Результат: " + result);
                break;
            default:
                System.out.println("Данная операция не поддерживается!");
        }

    }
}
