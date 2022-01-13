package velib.velib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import velib.velib.bd.JDBC;
/**
 * @author Théo ROLLIN
 * @version 1.1
 */


public class Main extends Application {
    final String nomSession = System.getProperty("user.home");
    private final String fichier1 = nomSession + "\\Documents\\Velib\\station_information.json";
    private final String fichier2 = nomSession + "\\Documents\\Velib\\station_status.json";
    private final ObservableList<Station> stationData = FXCollections.observableArrayList();
    final ObservableList<Station> stationDataR = FXCollections.observableArrayList();
    private final ArrayList<String> favorisData = new ArrayList<>();
    private BorderPane rootLayout;
    private Stage fenetre;
    private JDBC jdbc = new JDBC();
    int id_user = -1;
    private String nom_utilisateur;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage fenetre) throws IOException {
        //Runtime.getRuntime().exec("curl -o " + nomSession + "\\Documents\\Velib\\station_status.json https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/station_status.json" );
        //Runtime.getRuntime().exec("curl -o " + nomSession + "\\Documents\\Velib\\station_information.json https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/station_information.json" );
        this.fenetre = fenetre;
        fenetre.setTitle("Projet Velib"); // titre de la fenêtre
        fenetre.getIcons().add(new Image(getClass().getResourceAsStream("Paris-Velib-icone-1.png"))); // mise en place d'une icône
        initRootLayout();
        
        showConnexion();
        //showStationOverview();
   
        fenetre.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0); // arrêt de l'application
        });
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("RootLayout.fxml"));
            //loader.setLocation(getClass().getResource("main/velib/velib/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            fenetre.setScene(scene);
            fenetre.show();
            RootLayoutFXML controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ObservableList<Station> getStationData() {
        if (stationDataR.isEmpty()) {
            return stationData;
        } else {
            return stationDataR;
        }

    }
    
    public void showStationOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Velib.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            StationFXML controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showConnexion() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Connexion.fxml"));
            AnchorPane connexionOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(connexionOverview);

            // Give the controller access to the main app.
            ConnexionFXML controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showParametres() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Parametres.fxml"));
            AnchorPane Parametres = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(Parametres);

            // Give the controller access to the main app.
            ParametresFXML controller = loader.getController();
            controller.setMainApp(this);
            controller.setNomUti(nom_utilisateur);

        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showCreerUnCompte(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("CreerUnCompte.fxml"));
            AnchorPane creerCompteOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(creerCompteOverview);

            // Give the controller access to the main app.
            CreerUnCompteFXML controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showRechercher(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Rechercher.fxml"));
            AnchorPane rechercher = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(rechercher);

            // Give the controller access to the main app.
            RechercherFXML controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            System.out.println (ex.toString());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void chargerFavoris() throws FileNotFoundException, IOException {
        //System.out.println(id_user);
        String [] favoris = jdbc.SelectionFavoris(id_user);
        for ( int i=0; i<favoris.length; i++ ) {
            if (favoris[i] != null) {
                favorisData.add(favoris[i]);
                //System.out.println(favoris[i]);
            }
        }
        showStationOverview();
    }
    
    public void Rechercher(String nom_station) {
        stationDataR.clear();
        String regex = ".*"+nom_station.toLowerCase()+".*";
        for (Station station : stationData) {
            if (Pattern.matches(regex, station.getNomStation().toLowerCase())) {
                stationDataR.add(station);
            }
        }
        showStationOverview();
    }
    
    public void RechercherFavoris() {
        stationDataR.clear();
        for (Station station : stationData) {
            if (station.getFavoris()) {
                stationDataR.add(station);
            }
        }
        showStationOverview();
    }
    
    void setId_user(int id_user) {
        this.id_user = id_user;
    }
    
    void setNomUtilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }
    
    String getNomUtilisateur() {
        return nom_utilisateur;
    }
    
    int getId_user() {
        return id_user;
    }
    
    ArrayList<String> getFavorisData() {
        return favorisData;
    }
    
    String getFichier1() {
	return fichier1;
    }
    
    String getFichier2() {
	return fichier2;
    }
    
    void clearStationData(){
        stationData.clear();
    } 
    
    void clearFavorisData() {
        favorisData.clear();
    }
    
}





