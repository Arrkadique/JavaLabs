package com.paintfx;

import java.util.ArrayList;

import com.models.FactoryMethod;
import com.models.Figure;
import com.models.Point;
import com.models.UndoRedo;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Controller{
    private Figure figure = new Figure();
    private final ArrayList<Figure> figures = new ArrayList<Figure>();
    private Figure copyFigure;
    private String str;
    private boolean isChosen;
    private double lastX, lastY;
    private final UndoRedo undoRedo = new UndoRedo();


    @FXML
    private Canvas Canvas;

    @FXML
    private ChoiceBox<String> choiсeBox;

    @FXML
    private ColorPicker colorPickerLine;

    @FXML
    private CheckBox filler;

    @FXML
    private TextField lineWidth;

    @FXML
    private Label yLabel;

    @FXML
    private Label xLabel;

    @FXML
    private Button redoButton;

    @FXML
    private Button undoButton;

    private final ArrayList<String> choiceBoxFood = new ArrayList<>();

    @FXML
    void initialize() {
        choiceBoxFood.add("Rectangle");
        choiceBoxFood.add("Line");
        choiceBoxFood.add("Pen");
        choiceBoxFood.add("Ellipse");
        choiсeBox.getItems().addAll(choiceBoxFood);
        str = "Pen";
        choiсeBox.setValue("Pen");
        choiсeBox.setOnAction(event -> {
            str = choiсeBox.getValue();
            System.out.println(str + filler.isSelected());
        });
        undoButton.setOnAction(event -> undoRedo.Undo(figures, Canvas.getGraphicsContext2D()));
        redoButton.setOnAction(event -> undoRedo.Redo(figures, Canvas.getGraphicsContext2D()));
        filler.setOnAction(event -> {
            if(isChosen && filler.isSelected())
                figure.setFill(true);
            else if (isChosen && !filler.isSelected())
                figure.setFill(false);

            undoRedo.Redraw(figures, Canvas.getGraphicsContext2D());
        });
        lineWidth.setOnAction(event -> {
            try {
                Canvas.getGraphicsContext2D().setLineWidth(Double.parseDouble(lineWidth.getText()));

            } catch (NumberFormatException e) {
                Canvas.getGraphicsContext2D().setLineWidth(1.0);
                lineWidth.setText(String.valueOf(1.0));

            }

        });
    }

    @FXML
    public void MouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY  && isChosen) {
            undoRedo.Redraw(figures, Canvas.getGraphicsContext2D());

            for (int i = figures.size() - 1 ; i >= 0; i--) {
                if (figures.get(i).getField().get(0).getX() < mouseEvent.getX() && figures.get(i).getField().get(0).getY() < mouseEvent.getY() &&
                        figures.get(i).getField().get(1).getX() > mouseEvent.getX() && figures.get(i).getField().get(1).getY() > mouseEvent.getY()) {
                    figure = figures.get(i);
                    filler.setSelected(figure.isFill());
                    figure.setChosen(true, Canvas.getGraphicsContext2D());
                    figure.DrawField(Canvas.getGraphicsContext2D());
                    break;

                } else {
                    figures.get(i).setChosen(false, Canvas.getGraphicsContext2D());

                }
            }
        }
    }

    @FXML
    public void MouseDragged(MouseEvent mouseEvent) {
        if(!isChosen) {
            figure.getCurrentPoint().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            figure.getField().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            undoRedo.Undo(figures, Canvas.getGraphicsContext2D());
            undoRedo.setI();
            figure.Draw(Canvas.getGraphicsContext2D());
            figure.getCurrentPoint().remove(1);
            figure.getField().remove(1);

        } else if(mouseEvent.getButton() == MouseButton.PRIMARY &&
                figure.getField().get(0).getX() < mouseEvent.getX() && figure.getField().get(0).getY() < mouseEvent.getY() &&
                figure.getField().get(1).getX() > mouseEvent.getX() && figure.getField().get(1).getY() > mouseEvent.getY()) {

            figure.Move(Canvas.getGraphicsContext2D(), new Point(lastX, lastY), new Point(mouseEvent.getX(), mouseEvent.getY()));
            undoRedo.Redraw(figures, Canvas.getGraphicsContext2D());
            lastY += mouseEvent.getY() - lastY;
            lastX += mouseEvent.getX() - lastX;

        }
    }

    @FXML
    public void MouseMoved(MouseEvent mouseEvent) {
        yLabel.setText("y: " + String.valueOf(mouseEvent.getY()));
        xLabel.setText("x: " + String.valueOf(mouseEvent.getX()));
    }

    @FXML
    public void MouseReleased(MouseEvent mouseEvent) {
        if(!isChosen) {
            figure.getCurrentPoint().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            figure.getField().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            figure.Draw(Canvas.getGraphicsContext2D());
            figure.setField();

        }
    }

    @FXML
    public void MousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY && !isChosen) {
            figure = FactoryMethod.factoryMethod(str);
            figure.setFillColor(colorPickerLine.getValue());
            figure.setStrokeColor(colorPickerLine.getValue());

            try {
                figure.setWidth(Double.parseDouble(lineWidth.getText()));
            } catch (NumberFormatException e) {
                figure.setWidth(1.0);
                lineWidth.setText(String.valueOf(1.0));
            }

            if(filler.isSelected())
                figure.setFill(true);
            else if (!filler.isSelected())
                figure.setFill(false);

            figures.add(figure);
            undoRedo.setI();
            figure.getCurrentPoint().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            figure.getField().add(new Point(mouseEvent.getX(), mouseEvent.getY()));

        } else if(isChosen) {
            lastX = mouseEvent.getX();
            lastY = mouseEvent.getY();

        }
    }
}
