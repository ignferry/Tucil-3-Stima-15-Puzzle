module com.example.tucil3stima15puzzle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app to javafx.fxml;
    exports com.app;
}