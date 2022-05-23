package com.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Figure implements IDraw, IMove{
    protected ArrayList<Point> currentPoint;
    protected ArrayList<Point> field;
    protected Paint fillColor;
    protected Paint strokeColor;
    protected double width;
    protected boolean chosen;
    protected boolean fill;

    public Figure(Point... point) {
        currentPoint = new ArrayList<>();
        field = new ArrayList<>();
        chosen = false;
    }
    public Figure() {
        currentPoint = new ArrayList<>();
        field = new ArrayList<>();
        chosen = false;
    }

    public ArrayList<Point> getCurrentPoint() {
        return currentPoint;
    }
    public void setCurrentPoint(ArrayList<Point> currentPoint) {
        this.currentPoint = currentPoint;
    }
    public ArrayList<Point> getField() {
        return field;
    }

    public boolean isChosen() {
        return chosen;
    }
    public void setFillColor(Paint fillColor) {
        this.fillColor = fillColor;
    }
    public void setChosen(boolean chosen, GraphicsContext gc) {
        this.chosen = chosen;

    }
    public void setStrokeColor(Paint strokeColor) {
        this.strokeColor = strokeColor;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setFill(boolean fill) {
        this.fill = fill;
    }
    public boolean isFill() {
        return fill;
    }

    public void setField(){

        if(field.get(0).getX() > field.get(1).getX()) {
            double buff = field.get(0).getX();
            field.get(0).setX(field.get(1).getX());
            field.get(1).setX(buff);

        }

        if(field.get(0).getY() > field.get(1).getY()){
            double buff = field.get(0).getY();
            field.get(0).setY(field.get(1).getY());
            field.get(1).setY(buff);

        }
    }

    public void DrawField(GraphicsContext gc) {
        double h, w;
        h = Math.abs(field.get(0).getY() - field.get(1).getY());
        w = Math.abs(field.get(0).getX() - field.get(1).getX());

        gc.strokeRect(field.get(0).getX(), field.get(0).getY(), w, h);

    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.setLineWidth(width);
    }

    @Override
    public void Move(GraphicsContext graphicsContext, Point startPoint, Point endPoint) {

        double X = endPoint.getX() - startPoint.getX(), Y = endPoint.getY() - startPoint.getY();

        currentPoint.get(0).setX(currentPoint.get(0).getX() + X);
        currentPoint.get(1).setX(currentPoint.get(1).getX() + X);
        currentPoint.get(0).setY(currentPoint.get(0).getY() + Y);
        currentPoint.get(1).setY(currentPoint.get(1).getY() + Y);

        field.get(0).setX(field.get(0).getX() + X);
        field.get(1).setX(field.get(1).getX() + X);
        field.get(0).setY(field.get(0).getY() + Y);
        field.get(1).setY(field.get(1).getY() + Y);

        Draw(graphicsContext);

    }

    @Override
    public Figure clone() {
        try {
            Figure clone = (Figure) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
