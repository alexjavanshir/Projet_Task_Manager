module org.example.ex02 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.ex02 to javafx.fxml;
    exports org.example.ex02;
}