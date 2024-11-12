


class Student{
    String name;
    String group;
    public Student(String name, String group){
        this.name = name;
        this.group = group;
    }
    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
class Group {
    Student[] students;
    int size;
    public Group(int capacity){
        students = new Student[capacity];
        size = 0;
    }
    public void addStudent(Student student){
        students[size] = student;
        size++;
    }
    public void printStudents(){
        for (int i = 0; i < size; i++) {
            System.out.println("Student name: " + students[i].getName() + ", Group: " + students[i].getGroup());
        }
    }
}


public class HomeWorkTwo {
    public static void main(String[] args) {
        Group group = new Group(3);

        Student student1 = new Student("Alice", "GroupA");
        Student student2 = new Student("Bob", "GroupA");
        Student student3 = new Student("Charlie", "GroupB");

        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        group.printStudents();

    }
}
