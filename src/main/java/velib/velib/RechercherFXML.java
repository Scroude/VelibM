/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author utilisateur
 */
public class RechercherFXML {
    @FXML
    private TextField nom_station_recherche;
    @FXML
    private Button buttonRechercher;
    
    private Main main;
    
    public void setMainApp(Main main) {
        this.main = main;
    }
    
    @FXML
    private void handleButton1ActionRechercher(ActionEvent event) throws Exception {
        main.Rechercher(nom_station_recherche.getText());
    }
    
    @FXML
    private void handleButton1ActionAnnuler(ActionEvent event) throws Exception {
        main.initRootLayout();
        main.showStationOverview();
    }
    
}
