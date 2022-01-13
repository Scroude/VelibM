/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import velib.velib.Lecture;
import velib.velib.Main;
import velib.velib.Station;
import velib.velib.bd.JDBC;

/**
 *
 * @author slam
 */
public class ConnexionFXML {
    @FXML
    private TextField nom_utilisateur;
    @FXML
    private PasswordField mot_de_passe;
    @FXML
    private Button connexion;
    @FXML
    private Button quitter;
    @FXML
    private Button creerUnCompte;
    @FXML
    private Label erreurConnexion;
    
    private Main main;
    
    private final JDBC jdbc = new JDBC();
    
    public ConnexionFXML() {
    }
    
    @FXML
    private void initialize() {
        erreurConnexion.setText(null);

        connexion.setDefaultButton(true);
        quitter.setCancelButton(true);
    }
    
    @FXML
    private void handleButton1ActionConnexion(ActionEvent event) throws IOException, InterruptedException, NoSuchAlgorithmException {
        Connexion();
    }
    
    @FXML
    private void handleButton1ActionConnexionFromFieldPassword(KeyEvent keyEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (nom_utilisateur.getText() != "" && mot_de_passe.getText() != "")
            Connexion();
        }
    }
    
    @FXML
    private void handleButton1ActionQuitter(ActionEvent event) throws Exception {
        System.exit(0);
    }
    
    @FXML
    private void handleButton1ActionCreerUnCompte(ActionEvent event) throws Exception {
        main.showCreerUnCompte();
    }

    public void setMainApp(Main main) {
        this.main = main;
    }
    
    private void Connexion()throws IOException, InterruptedException, NoSuchAlgorithmException{
        String fichier1 = main.getFichier1();
        String fichier2 = main.getFichier2();
        ObservableList<Station> stationData = main.getStationData();
        ArrayList<String> favorisData = main.getFavorisData();
        if (nom_utilisateur.getText() != "" & mot_de_passe.getText() != "") {
            String id = jdbc.ConnexionUti(nom_utilisateur.getText(), mot_de_passe.getText());
            erreurConnexion.setText("Chargement des stations...");
            if (id != null) {
                int id_user = Integer.parseInt(id);
                main.setId_user(id_user);
                main.setNomUtilisateur(nom_utilisateur.getText());
                main.chargerFavoris();
                Lecture lecture = new Lecture(fichier1, fichier2);
                lecture.lectureFichier1(stationData, favorisData);
                main.showStationOverview();
            } else {
                erreurConnexion.setText("Identifiant ou mot de passe incorrect");
            }
        } else {
            erreurConnexion.setText("Des champs sont vides ou incorrects");
        }
    }
}
