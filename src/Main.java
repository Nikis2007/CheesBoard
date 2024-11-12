

import java.io.*;
import java.sql.SQLData;

public class Main {
    public static void main(String[] args) {
        Shape square = new Square(5);
        System.out.println("Square area " + square.getArea());
        System.out.println("Square perimeter " + square.getPerimeter());
        Shape rectangle = new Rectangle(12, 34);
        System.out.println("Rectangle area " + rectangle.getArea());
        System.out.println("Rectangle perimeter " + rectangle.getPerimeter());
        Shape circle = new Circle(12);
        System.out.println("Circle area " + circle.getArea());
        System.out.println("Circle perimeter " + circle.getPerimeter());

    }

    public interface Shape {
        double getArea();

        double getPerimeter();

    }

    public static class Square implements Shape {
        private double side;
        public Square(double side){
            this.side = side;
        }
        @Override
        public double getArea() {
            return side * side;
        }
        @Override
        public double getPerimeter() {
            return 4 * side;
        }
    }
    public static class Rectangle implements Shape {
        private double width;
        private double height;
        public Rectangle (double width, double height){
            this.width = width;
            this.height = height;
        }

        @Override
        public double getPerimeter() {
            return (width + height) * 2;
        }

        @Override
        public double getArea() {
            return width * height;
        }
    }
    public static class Circle implements Shape {
        private double radius;
        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double getPerimeter() {
            return 2 * Math.PI * radius;
        }

        @Override
        public double getArea() {
            return Math.PI * radius * radius;
        }
    }
}





