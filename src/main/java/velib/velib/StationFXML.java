/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import velib.velib.bd.JDBC;

/**
 *
 * @author slam
 */
public class StationFXML {
    @FXML
    private TableView<Station> tableStation;
    @FXML
    private TableColumn<Station, String> nomStationColumn;
    @FXML
    private TableColumn<Station, Boolean> favorisStationColumn;
    @FXML
    private Label idStationLabel;
    @FXML
    private Label nomStationLabel;
    @FXML
    private Label latStationLabel;
    @FXML
    private Label lonStationLabel;
    @FXML
    private Label codeStationLabel;
    @FXML
    private Label capacityStationLabel;
    @FXML
    private Label nbBikeALabel;
    @FXML
    private Label nbDocksALabel;
    @FXML
    private CheckBox favoris = new CheckBox();
    @FXML
    private Button buttonRechercher;
    @FXML
    private TextField tFRechercher;
    @FXML
    private Button buttonFavoris;
    @FXML
    private Button buttonAnnuler;
    @FXML
    private CheckBox ouvert = new CheckBox();
    @FXML
    private WebView vueCarte;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem ajouterFavorisContextMenu;
    
    private Main main;
    
    private JDBC jdbc = new JDBC();

    private Boolean recherche = false;

    private Boolean rFavoris = false;
    
    public StationFXML() {
    }
    
    @FXML
    private void initialize() throws MalformedURLException, UnsupportedEncodingException {
        nomStationColumn.setCellValueFactory(cellData -> cellData.getValue().getNomStationColumn());
        favorisStationColumn.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().getFavoris())); 
        favorisStationColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        buttonAnnuler.setCancelButton(true);

        showStationDetails(null);
        showWebView(null);
    }

    @FXML
    private void handleButton1ActionRechercher(ActionEvent event) throws Exception {
        if (main.getId_user() != -1 && !tFRechercher.getText().equals("")) {
            if (recherche == false) {
                main.Rechercher(tFRechercher.getText()); 
                recherche = true;
            }
        }
    }
    
    @FXML
    private void handleButton1ActionFavoris(ActionEvent event) throws Exception {
        if (main.getId_user() != -1) {
            if (rFavoris == false) {
                main.RechercherFavoris();
                rFavoris = true;
            }
        }
    }

    @FXML
    private void handleButton1ActionRechercherFromField(KeyEvent keyEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!tFRechercher.getText().equals(""))
            main.Rechercher(tFRechercher.getText()); 
        }
    }

    @FXML
    private void handleButton1ActionAnnuler(ActionEvent event) throws Exception {
        if (main.getId_user() != -1 && !main.stationDataR.isEmpty()) {
            main.stationDataR.clear();
            main.showStationOverview();
        }
    }
    
    @FXML
    private void handleTableClicks(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            //tableStation.getSelectionModel().selectedItemProperty(observable, oldValue, newValue) -> showStationDetails(newValue);
            showStationDetails(tableStation.getSelectionModel().getSelectedItem());
            try {
                showWebView(tableStation.getSelectionModel().getSelectedItem());
            } catch (MalformedURLException ex) {
                Logger.getLogger(StationFXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(StationFXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            tableStation.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(tableStation, event.getScreenX(), event.getScreenY());
            }
        });
        }
    }
    
    @FXML
    private void handleFavoris(ActionEvent event) {
        ArrayList<String> favorisData = main.getFavorisData();
        setFavoris(tableStation.getSelectionModel().selectedItemProperty().getValue(), favorisData);
        RefreshTable();
    }
    
    @FXML
    private void RefreshTable(ActionEvent event) {
        RefreshTable();
    }
    
    private void RefreshTable(){
        tableStation.refresh();
    }

    
    public void setMainApp(Main main) {
        this.main = main;

        // Add observable list data to the table
        tableStation.setItems(main.getStationData());
    }
    
    private void showStationDetails(Station station) {
    if (station != null) {
        favoris.setIndeterminate(false);
        ouvert.setIndeterminate(false);
        // Fill the labels with info from the person object.
        idStationLabel.setText(station.getIdStation());
        nomStationLabel.setText(station.getNomStation());
        latStationLabel.setText(station.getLatStation());
        lonStationLabel.setText(station.getLonStation());
        codeStationLabel.setText(station.getCodeStation());
        capacityStationLabel.setText(station.getCapacityStation());
        nbBikeALabel.setText(station.getNbBikeA());
        nbDocksALabel.setText(station.getNbDocksA());
        favoris.setSelected(station.getFavoris());
        ouvert.setSelected(station.getOuvert());
    } else {
        // Person is null, remove all the text.
        idStationLabel.setText("");
        nomStationLabel.setText("");
        latStationLabel.setText("");
        lonStationLabel.setText("");
        codeStationLabel.setText("");
        capacityStationLabel.setText("");
        nbBikeALabel.setText("");
        nbDocksALabel.setText("");
        favoris.setSelected(false);
        ouvert.setSelected(false);
    }
     
}
    private void showWebView(Station station) throws MalformedURLException, UnsupportedEncodingException{
        WebEngine engine = vueCarte.getEngine();
        if (station != null){
            //System.out.println("carte changée");
            String Lat = station.getLatStation();
            String Lon = station.getLonStation();
            File file = new File(main.nomSession + "\\Documents\\GitHub\\Velib\\src\\velib\\carte.html?lat="+Lat+"&lon="+Lon);
            URL url = file.toURI().toURL();
            //System.out.println(URLDecoder.decode(url.toString(), "UTF-8"));
            engine.load(URLDecoder.decode(url.toString(), "UTF-8"));
        }
    }
    
    public void setFavoris(Station station, ArrayList<String> favorisData) {
        if (station.getFavoris() == false) {
            station.setFavoris(true);
            favoris.setSelected(true);
            favorisData.add(station.getIdStation());
            int id_station = Integer.parseInt(station.getIdStation());
            jdbc.ajouterFavoris(main.getId_user(), id_station);
        } else if (station.getFavoris() == true){
            station.setFavoris(false);
            favoris.setSelected(false);
            favorisData.remove(station.getIdStation());
            String id_station = station.getIdStation();
            jdbc.supprimerFavoris(main.getId_user(), id_station);
        }    
    }
   
}
