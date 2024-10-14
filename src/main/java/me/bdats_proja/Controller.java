package me.bdats_proja;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    public static AbstrDoubleList<Obec> [] kraje = new AbstrDoubleList[15];

    public void innitKraje()
    {
        for (int i = 0; i < kraje.length; i++)
        {
            kraje[i] = new AbstrDoubleList<Obec>();
        }
    }


    @FXML
    private Label Load;

    @FXML
    protected void onLoadButtonClick()
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        int successfullyLoaded = Obyvatele.importData(String.valueOf(file));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        innitKraje();
    }
}