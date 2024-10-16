package me.bdats_proja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable
{
    public static AbstrDoubleList<Obec> [] kraje = new AbstrDoubleList[15];


    public void innitKraje()
    {
        for (int i = 0; i < kraje.length; i++)
        {
            kraje[i] = new AbstrDoubleList<>();
        }
    }


    @FXML
    protected void onLoadButtonClick()
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        int successfullyLoaded = Obyvatele.importData(String.valueOf(file));
        if (successfullyLoaded != 0) refreshTabs();
    }


    @FXML
    protected void onRefreshButtonClick()
    {
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

            ObservableList<Obec> observableList = convertToObservableList(kraje[i]);
            tableView.setItems(observableList);

            tab.setContent(tableView);
        }
    }

    private ObservableList<Obec> convertToObservableList(AbstrDoubleList<Obec> list)
    {
        ObservableList<Obec> observableList = FXCollections.observableArrayList();
        for (Obec obec : list)
        {
            observableList.add(obec);
        }
        return observableList;
    }


    private TableView<Obec> createTableView()
    {
        TableView<Obec> tableView = new TableView<>();

        TableColumn<Obec, Integer> pscColumn = new TableColumn<>("PSC");
        pscColumn.setCellValueFactory(new PropertyValueFactory<>("psc"));

        TableColumn<Obec, String> nameColumn = new TableColumn<>("Nazev");
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


    public void refreshTabs()
    {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            Tab tab = tabPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            ObservableList<Obec> observableList = convertToObservableList(kraje[i+1]);
            tableView.setItems(observableList);

            if (kraje[i+1].zpristupniAktualni() != null)
            {
                highlightActiveElement(tableView, kraje[i+1].zpristupniAktualni().data);
            }

            tab.setContent(tableView);
        }
    }


    private void highlightActiveElement(TableView<Obec> tableView, Obec activeElement)
    {
        tableView.setRowFactory(tv -> new TableRow<>()
        {
            @Override
            protected void updateItem(Obec item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setStyle(""); // Reset style for non-data rows
                }
                else
                {
                    if (item.equals(activeElement))
                    {
                        setStyle("-fx-background-color: lightgreen;");
                    }
                    else
                    {
                        setStyle("");
                    }
                }
            }
        });
    }


    public void onNextButtonClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].zpristupniNaslednika();
        refreshTabs();
    }


    public void onPrevButtonClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].zpristupniPredchudce();
        refreshTabs();
    }


    public void onRemNextClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].odeberNaslednika();
        refreshTabs();
    }

    public void onRemActClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].odeberAktualni();
        refreshTabs();
    }

    public void onRemPrevClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].odeberPredchudce();
        refreshTabs();
    }

    public void onRemLastClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].odeberPosledni();
        refreshTabs();
    }

    public void onRemFirstClick()
    {
        int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
        kraje[selectedIndex+1].odeberPrvni();
        refreshTabs();
    }
}