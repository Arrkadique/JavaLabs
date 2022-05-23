package com.models;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Figure implements IDraw,IMove{
    public Line(Point... points){
        super(points);
    }
    public Line(){
        super();
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        super.Draw(graphicsContext);

        graphicsContext.strokeLine(currentPoint.get(0).getX(), currentPoint.get(0).getY(),
                currentPoint.get(1).getX(), currentPoint.get(1).getY());

    }

    @Override
    public void Move(GraphicsContext graphicsContext, Point startPoint, Point endPoint) {
        super.Move(graphicsContext, startPoint, endPoint);

    }

}
