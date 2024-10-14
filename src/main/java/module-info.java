module me.bdats_proja {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.bdats_proja to javafx.fxml;
    exports me.bdats_proja;
}