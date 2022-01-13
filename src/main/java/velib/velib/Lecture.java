package velib.velib;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.collections.ObservableList;


public class Lecture {
    private  final String NOM_FICHIER1;
    private final String NOM_FICHIER2;

    public Lecture(String NOM_FICHIER1, String NOM_FICHIER2) {
        this.NOM_FICHIER1 = NOM_FICHIER1;
        this.NOM_FICHIER2 = NOM_FICHIER2;
    }
    
    public Lecture() {
        this.NOM_FICHIER1 = null;
        this.NOM_FICHIER2 = null;
    }
    
    ObservableList lectureFichier1(ObservableList<Station> stationData, ArrayList<String> favorisData) {
        try {            
                Path cheminFichier = Paths.get(NOM_FICHIER1);
        
                try (Reader lecteur = Files.newBufferedReader(cheminFichier, StandardCharsets.UTF_8)) {
                    //JsonElement elementRacine = JsonParser.parseReader(lecteur);
                    JsonElement elementRacine = JsonParser.parseReader(lecteur);
                 
                    JsonObject objetRacine = elementRacine.getAsJsonObject();
                    com.google.gson.JsonElement elementData = objetRacine.get("data");
                    JsonObject objetStations = elementData.getAsJsonObject();
                
                    com.google.gson.JsonElement elementStations = objetStations.get("stations");
                    JsonArray tabStations = elementStations.getAsJsonArray();
                    
                    for(com.google.gson.JsonElement elementStation : tabStations) {
                        JsonObject objetStation = elementStation.getAsJsonObject();
                        com.google.gson.JsonElement elementId = objetStation.get("station_id");
                        com.google.gson.JsonElement elementNom = objetStation.get("name");
                        com.google.gson.JsonElement elementLat = objetStation.get("lat");
                        com.google.gson.JsonElement elementLon = objetStation.get("lon");
                        com.google.gson.JsonElement elementCode = objetStation.get("stationCode");
                        com.google.gson.JsonElement elementCapacite = objetStation.get("capacity");
                        String id = elementId.getAsString();
                        try{            
                            Path cheminFichier2 = Paths.get(NOM_FICHIER2);
        
                            try (Reader lecteur2 = Files.newBufferedReader(cheminFichier2, StandardCharsets.UTF_8)) {
                                com.google.gson.JsonElement elementRacine2 = JsonParser.parseReader(lecteur2);
                 
                                JsonObject objetRacine2 = elementRacine2.getAsJsonObject();
                                com.google.gson.JsonElement elementData2 = objetRacine2.get("data");
                                JsonObject objetStations2 = elementData2.getAsJsonObject();
                
                                com.google.gson.JsonElement elementStations2 = objetStations2.get("stations");
                                JsonArray tabStations2 = elementStations2.getAsJsonArray();
                    
                                for(com.google.gson.JsonElement elementStation2 : tabStations2) {
                                    JsonObject objetStation2 = elementStation2.getAsJsonObject();
                                    com.google.gson.JsonElement elementId2 = objetStation2.get("station_id");
                                    com.google.gson.JsonElement elementNbBA = objetStation2.get("numBikesAvailable");
                                    com.google.gson.JsonElement elementNbDA = objetStation2.get("numDocksAvailable");
                                    com.google.gson.JsonElement elementIsR = objetStation2.get("is_renting");
                                    boolean elementIsRB = false;
                                    if (elementIsR.getAsInt() == 1) {
                                        elementIsRB = true;
                                    } else if (elementIsR.getAsInt() == 0) {
                                        elementIsRB = false;
                                    }
                                    String id2 = elementId2.getAsString();
                                    if (id.equals(id2)) {
                                        boolean fav = false;
                                        for(String n : favorisData) {
                                            if (n.equals(id)){
                                                fav = true;
                                            }
                                        }
                                        stationData.add(new Station(elementId.getAsString(), elementNom.getAsString(), elementLat.getAsString(), elementLon.getAsString(), elementCode.getAsString(), elementCapacite.getAsString(), elementNbBA.getAsString(), elementNbDA.getAsString(), fav, elementIsRB));
                                        //System.out.println("new station added");
                                        break;
                                    }
                                }
                            }
                        } catch(FileNotFoundException ex) {
                            System.err.println("erreur : fichier non trouvée (" + NOM_FICHIER2 + ")");
                        } catch(IOException ex) {
                            System.err.println("erreur : problème d'entrées/sorties (" + NOM_FICHIER2 + ")");        
                        }                        
                    }
                }
        } catch(FileNotFoundException ex) {
            System.err.println("erreur : fichier non trouvée (" + NOM_FICHIER1 + ")");
        } catch(IOException ex) {
            System.err.println(ex.getCause());
            ex.getMessage();
        }        
        return stationData;
    }
}

