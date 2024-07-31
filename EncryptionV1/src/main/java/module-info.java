module com.example.encryptionv1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.encryptionv1 to javafx.fxml;
    exports com.example.encryptionv1;
}