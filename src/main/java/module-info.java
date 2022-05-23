module com.example.paintfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.paintfx to javafx.fxml;
    exports com.paintfx;
}