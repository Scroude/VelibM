/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import velib.velib.Main;
import velib.velib.bd.JDBC;

/**
 *
 * @author slam
 */
public class CreerUnCompteFXML {
    @FXML
    private TextField nom_utilisateur;
    @FXML
    private PasswordField mot_de_passe;
    @FXML
    private PasswordField mot_de_passe1;
    @FXML
    private Button connexion;
    @FXML
    private Button quitter;
    @FXML
    private Button creerUnCompte;
    @FXML
    private Label erreurCreation;
    
    private Main main;
    
    private final JDBC jdbc = new JDBC();
    
    public CreerUnCompteFXML() {
    }
    
    @FXML
    private void initialize() {
        erreurCreation.setText(null);
    }
    
    @FXML
    private void handleButton1ActionConnexion(ActionEvent event) throws Exception {
        main.showConnexion();
    }
    
    @FXML
    private void handleButton1ActionCreerUnCompte(ActionEvent event) throws Exception {
        System.out.println(mot_de_passe.getText());
        if (nom_utilisateur.getText() != "" & mot_de_passe.getText() != "" & mot_de_passe1.getText() != "") {
            if (mot_de_passe.getText().equals(mot_de_passe1.getText())) {
                String confirmAjout = jdbc.CreationUti(nom_utilisateur.getText(), mot_de_passe.getText());
                if (confirmAjout != null) {
                    main.showConnexion();
                } else {
                }
            } else {
                erreurCreation.setText("Les mots de passe ne sont pas identique");
            }
            
        } else {
            erreurCreation.setText("Des champs sont vides ou incorrects");
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
    }
    
    @FXML
    private void handleButton1ActionQuitter(ActionEvent event) throws Exception {
        System.exit(0);
    }
}
