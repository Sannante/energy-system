module com.example.energyfxgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.example.energyfxgui to javafx.fxml;
    exports com.example.energyfxgui;
}