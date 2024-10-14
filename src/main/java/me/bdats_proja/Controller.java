package me.bdats_proja;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        refreshTabs();

    }

    @FXML
    private TabPane tabPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        innitKraje();
        for (int i = 0; i < tabPane.getTabs().size(); i++)
        {
            Tab tab = tabPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            // Convert the custom list to an ObservableList
            ObservableList<Obec> observableList = convertToObservableList(kraje[i]);
            tableView.setItems(observableList);

            tab.setContent(tableView);
        }
    }

    private ObservableList<Obec> convertToObservableList(AbstrDoubleList<Obec> list) {
        ObservableList<Obec> observableList = FXCollections.observableArrayList();
        for (Obec obec : list) {  // Assuming your AbstrDoubleList implements Iterable
            observableList.add(obec);
        }
        return observableList;
    }

    private TableView<Obec> createTableView()
    {
        TableView<Obec> tableView = new TableView<>();

        TableColumn<Obec, Integer> pscColumn = new TableColumn<>("PSC");
        pscColumn.setCellValueFactory(new PropertyValueFactory<>("psc"));

        TableColumn<Obec, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Obec, Integer> muziPocetColumn = new TableColumn<>("Muzi");
        muziPocetColumn.setCellValueFactory(new PropertyValueFactory<>("muziPocet"));

        TableColumn<Obec, Integer> zenyPocetColumn = new TableColumn<>("Zeny");
        zenyPocetColumn.setCellValueFactory(new PropertyValueFactory<>("zenyPocet"));

        TableColumn<Obec, Integer> celkemPocetColumn = new TableColumn<>("Celkem");
        celkemPocetColumn.setCellValueFactory(new PropertyValueFactory<>("celkemPocet"));

        tableView.getColumns().addAll(pscColumn, nameColumn, muziPocetColumn, zenyPocetColumn, celkemPocetColumn);

        return tableView;


    }
    public void refreshTabs() {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            Tab tab = tabPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            // Convert the custom list to an ObservableList
            ObservableList<Obec> observableList = convertToObservableList(kraje[i+1]);
            tableView.setItems(observableList);

            tab.setContent(tableView);
        }
    }
}