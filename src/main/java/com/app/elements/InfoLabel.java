package com.app.elements;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class InfoLabel extends Label {
    public InfoLabel(String text) {
        this.setText(text);
        this.setPadding(new Insets(5));
        this.setFont(Font.font("verdana", 12));
        this.autosize();
    }
}
