package com.models;

public class FactoryMethod {
    public static Figure factoryMethod(String str) {
        Figure figure = switch (str) {
            case "Line" -> new Line();
            case "Rectangle" -> new Rectangle();
            case "Ellipse" -> new Ellipse();
            case "Pen" -> new Pen();
            default -> new Line();
        };

        return figure;
    }
}
