public class ClassesAndObjects {
    public static void main(String[] args) {
        Person person1 = new Person();
        person1.name = "Гомосек";
        person1.age = 24;
        System.out.println("Меня зовут " +person1.name+ ","+ "мне " + person1.age + " года");
        Person person2 = new Person();
        person2.name = "Уебок";
        person2.age = 34;
        System.out.println("Меня зовут " +person2.name+ ","+ "мне " + person2.age + " года");
    }
}
class Person {
    String name;
    int age;
}