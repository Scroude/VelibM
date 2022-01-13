package velib.velib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import velib.velib.bd.JDBC;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author slam
 */
public class ParametresFXML {
    @FXML
    private PasswordField mot_de_passe;
    @FXML
    private PasswordField mot_de_passe1;
    @FXML
    private PasswordField ancien_mot_de_passe;
    @FXML
    private Label nom_utilisateur;
    @FXML
    private Button enregistrer;
    @FXML
    private Button retour;
    @FXML
    private Label erreur;
    
    private Main main;
    
    private final JDBC jdbc = new JDBC();
    
    @FXML
    private void initialize() {
        nom_utilisateur.setText("");
        erreur.setText("");
    }
    
    public void setMainApp(Main main) {
        this.main = main;
    }
    
    public void setNomUti(String nom_uti) {
        nom_utilisateur.setText(nom_uti);
    }
    
    @FXML
    private void handleButton1ActionRetour(ActionEvent event) throws Exception {
        main.showStationOverview();
    }
    
    @FXML
    private void handleButton1ActionEnregistrer(ActionEvent event) throws Exception {
        if (mot_de_passe.getText() != "" && mot_de_passe1.getText() != "" && ancien_mot_de_passe.getText() != "") {
            if (jdbc.checkMdp(main.getNomUtilisateur(), ancien_mot_de_passe.getText())) {
                if (mot_de_passe.getText().equals(mot_de_passe1.getText())) {
                    jdbc.miseAJourMdp(nom_utilisateur.getText(), mot_de_passe.getText());
                    main.showConnexion();
                } else {
                    erreur.setText("Les mots de passe ne sont pas identique");
                }
            } else {
                erreur.setText("Mot de passe incorrect");
            }
        } else {
            erreur.setText("Des champs sont vides ou incorrects");
            
        }
    }
}
