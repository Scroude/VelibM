/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author slam
 */
public class RootLayoutFXML {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu;
    @FXML
    private MenuItem deconnexion;
    @FXML
    private MenuItem parametres;
    @FXML
    private MenuItem quitter;
    
    private Main main;
    
    public RootLayoutFXML(){
    }
    
    public void setMainApp(Main main) {
        this.main = main;
    }
    
    
    @FXML
    private void handleButton1ActionDeconnexion(ActionEvent event) throws Exception {
        main.clearStationData();
        main.clearFavorisData();
        main.setId_user(-1);
        main.showConnexion();
    }
    
    @FXML
    private void handleButton1ActionQuitter(ActionEvent event) throws Exception {
        System.exit(0);
    }
    
    @FXML
    private void handleButton1ActionParametres(ActionEvent event) throws Exception {
        if (main.getId_user() != -1) {
            main.showParametres();   
        }
    }
    
}
