module com.example.slideshow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.slideshow to javafx.fxml;
    exports com.example.slideshow;
}