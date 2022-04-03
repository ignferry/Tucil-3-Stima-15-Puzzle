package com.app.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;

public class NumberTile extends VBox {
    private static final int CELL_SIZE = 75;
    private static final int TILE_SIZE = 60;

    public NumberTile(int number, int row, int col) {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        setMinSize(TILE_SIZE,TILE_SIZE);
        setPrefSize(TILE_SIZE, TILE_SIZE);
        setMaxSize(TILE_SIZE, TILE_SIZE);
        setStyle("-fx-background-color: #C18A54");
        setLayoutX((col + 0.5) * CELL_SIZE - TILE_SIZE / 2);
        setLayoutY((row + 0.5) * CELL_SIZE - TILE_SIZE / 2);
        setAlignment(Pos.CENTER);
        setId(Integer.toString(number));
        setEffect(lighting);

        Label label = new Label();
        label.setText(Integer.toString(number));
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label.setTextFill(Paint.valueOf("#422311"));
        this.getChildren().add(label);
    }
}
