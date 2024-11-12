import java.util.Scanner;

public class DisplayTime {
    static class Student {
        private String name;
        private int age;
        private double averageGrade;

        public Student(String name, int age, double averageGrade) {
            this.name = name;
            this.age = age;
            this.averageGrade = averageGrade;
        }

        public double getAverageGrade() {
            return averageGrade;
        }
    }

    static class Group {
        private Student[] students;
        private int size;

        public Group(int capacity) {
            students = new Student[capacity];
            size = 0;
        }

        public void addStudent(Student student) {
            students[size] = student;
            size++;
        }

        public void removeStudent(Student student) {
            for (int i = 0; i < size; i++) {
                if (students[i] == student) {
                    for (int j = i; j < size - 1; j++) {
                        students[j] = students[j + 1];
                    }
                    size--;
                    break;
                }
            }
        }

        public double getAverageGrade() {
            double sum = 0;
            for (int i = 0; i < size; i++) {
                sum += students[i].getAverageGrade();
            }
            return sum / size;
        }
    }
    public static void main(String[] args) {
        Group group = new Group(5);

        Student student1 = new Student("Alice", 20, 4.5);
        Student student2 = new Student("Bob", 22, 3.7);
        Student student3 = new Student("Charlie", 21, 4.2);

        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);

        double averageGrade = group.getAverageGrade();
        System.out.println("Average grade of the group: " + averageGrade);

    }
}


