public class Car {
    private String brand;
    private String color;
    private int speed;

    public Car(String brand, String color, int speed) {
        this.brand = brand;
        this.color = color;
        this.speed = speed;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "red", 200);
        Car car2 = new Car("Honda", "blue", 180);

        if(car1.getSpeed() > car2.getSpeed()) {
            System.out.println(car1.getBrand() + " is faster than " + car2.getBrand());
        } else if(car1.getSpeed() < car2.getSpeed()) {
            System.out.println(car2.getBrand() + " is faster than " + car1.getBrand());
        } else {
            System.out.println("Both cars have the same speed");
        }
    }
}