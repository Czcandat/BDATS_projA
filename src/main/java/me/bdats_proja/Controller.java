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

import static me.bdats_proja.Obyvatele.*;

public class Controller implements Initializable
{
    public static IAbstrDoubleList<Obec>[] kraje = new IAbstrDoubleList[15];


    public void innitKraje()
    {
        for (int i = 0; i < kraje.length; i++)
        {
            kraje[i] = new IAbstrDoubleList<>();
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
    private TabPane viewPane;
    @FXML
    private TabPane accessPane;

    @FXML
    private ChoiceBox<Obyvatele.enumKraj> vlozKraj;
    @FXML
    private ChoiceBox<Obyvatele.enumKraj> editKraj;

    @FXML
    private TextField vlozPsc;

    @FXML
    private TextField vlozNazev;

    @FXML
    private TextField vlozMuzi;

    @FXML
    private TextField vlozZeny;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        innitKraje();
        vlozKraj.getItems().addAll(Obyvatele.enumKraj.values());
        editKraj.getItems().addAll(Obyvatele.enumKraj.values());
        for (int i = 0; i < viewPane.getTabs().size(); i++)
        {
            Tab tab = viewPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            ObservableList<Obec> observableList = convertToObservableList(zobrazObce(Obyvatele.enumKraj.values()[i+1]));
            tableView.setItems(observableList);

            tab.setContent(tableView);
        }
        for (int i = 0; i < accessPane.getTabs().size(); i++)
        {
            Tab tab = accessPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            ObservableList<Obec> observableList = convertToObservableList(zobrazObce(Obyvatele.enumKraj.values()[i+1]));
            tableView.setItems(observableList);

            tab.setContent(tableView);
        }

    }

    private ObservableList<Obec> convertToObservableList(IAbstrDoubleList<Obec> list)
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
        for (int i = 0; i < viewPane.getTabs().size(); i++) {
            Tab tab = viewPane.getTabs().get(i);
            TableView<Obec> tableView = createTableView();

            ObservableList<Obec> observableList = convertToObservableList(zobrazObce(Obyvatele.enumKraj.values()[i+1]));
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
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.zpristupniObec(enumPosition.NEXT, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }


    public void onPrevButtonClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.zpristupniObec(enumPosition.PREV, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }

    public void onFirstButtonClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.zpristupniObec(enumPosition.FIRST, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }


    public void onLastButtonClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.zpristupniObec(enumPosition.LAST, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }


    public void onRemNextClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.odeberObec(enumPosition.NEXT, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }


    public void onRemActClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.odeberObec(enumPosition.ACTIVE, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }

    public void onRemPrevClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.odeberObec(enumPosition.PREV, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }

    public void onRemLastClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.odeberObec(enumPosition.LAST, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }

    public void onRemFirstClick()
    {
        if(kraje[viewPane.getSelectionModel().getSelectedIndex()+1].isEmpty()) return;
        Obyvatele.odeberObec(enumPosition.FIRST, enumKraj.values()[viewPane.getSelectionModel().getSelectedIndex()+1]);
        refreshTabs();
    }

    private Obec newObec()
    {
        int psc = Integer.parseInt(vlozPsc.getText());
        int muzi = Integer.parseInt(vlozMuzi.getText());
        int zeny = Integer.parseInt(vlozZeny.getText());
        int celkem = muzi+zeny;
        return new Obec(psc, vlozNazev.getText(), muzi, zeny, celkem);
    }


    public void onAddFirstClick()
    {
        Obyvatele.vlozObec(newObec(), enumPosition.FIRST, vlozKraj.getValue());
        refreshTabs();
    }

    public void onAddLastClick()
    {
        Obyvatele.vlozObec(newObec(), enumPosition.LAST, vlozKraj.getValue());
        refreshTabs();
    }

    public void onAddPrevClick()
    {
        Obyvatele.vlozObec(newObec(), enumPosition.PREV, vlozKraj.getValue());
        refreshTabs();
    }

    public void onAddNextClick()
    {
        Obyvatele.vlozObec(newObec(), enumPosition.NEXT, vlozKraj.getValue());
        refreshTabs();
    }

    public void onAverageClick()
    {

        if (editKraj.getValue() == null) return;
        else if(editKraj.getValue() == Obyvatele.enumKraj.ALL)
        {
            for(enumKraj iter: enumKraj.values())
            {
                if(iter == Obyvatele.enumKraj.ALL)
                {
                    continue;
                }

                Tab tab = accessPane.getTabs().get(iter.ordinal()-1);
                TableView<Obec> tableView = createTableView();

                ObservableList<Obec> observableList = convertToObservableList(Obyvatele.zobrazObceNadPrumer(iter));
                tableView.setItems(observableList);

                tab.setContent(tableView);
            }
            return;
        }
        Tab tab = accessPane.getTabs().get(editKraj.getValue().ordinal()-1);
        TableView<Obec> tableView = createTableView();

        ObservableList<Obec> observableList = convertToObservableList(Obyvatele.zobrazObceNadPrumer(editKraj.getValue()));
        tableView.setItems(observableList);

        tab.setContent(tableView);
    }

    public void onZrusClick()
    {
        Obyvatele.zrus(editKraj.getValue());
        refreshTabs();
    }

    public void onExportClick()
    {
        Obyvatele.exportData();
    }
}