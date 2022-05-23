package com.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Pen extends Figure{
    ArrayList<Point> points;
    public Pen() {
        super();
        points = new ArrayList<>();
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        super.Draw(graphicsContext);
        points.add(currentPoint.get(1));
        graphicsContext.strokeLine(currentPoint.get(0).getX(), currentPoint.get(0).getY(),
                points.get(0).getX(), points.get(0).getY());

        for(int i = 0; i < points.size() - 1; i++)
            graphicsContext.strokeLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(i+1).getX(), points.get(i+1).getY());

    }
}
