/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author slam
 */
public class Station {
    private final StringProperty idStation;
    private final StringProperty nomStation;
    private final StringProperty latStation;
    private final StringProperty lonStation;
    private final StringProperty codeStation;
    private final StringProperty capacityStation;
    private final StringProperty nbBikeA;
    private final StringProperty nbDocksA;
    private BooleanProperty favoris;
    private BooleanProperty ouvert;
    
    public Station() {
        idStation= new SimpleStringProperty("test");
        nomStation= new SimpleStringProperty("test");
        latStation= new SimpleStringProperty("test");
        lonStation= new SimpleStringProperty("test");
        codeStation= new SimpleStringProperty("test");
        capacityStation= new SimpleStringProperty("test");
        nbBikeA = new SimpleStringProperty("test");
        nbDocksA= new SimpleStringProperty("test");
        favoris = new SimpleBooleanProperty(false);
    }
    
    public Station(String idStation, String nomStation, String latStation, String lonStation, String codeStation, String capacityStation, String nbBikeA, String nbDocksA, boolean favoris, boolean ouvert) {
        this.idStation = new SimpleStringProperty(idStation);
        this.nomStation = new SimpleStringProperty(nomStation);
        this.latStation = new SimpleStringProperty(latStation);
        this.lonStation = new SimpleStringProperty(lonStation);
        this.codeStation = new SimpleStringProperty(codeStation);
        this.capacityStation = new SimpleStringProperty(capacityStation);
        this.nbBikeA = new SimpleStringProperty(nbBikeA);
        this.nbDocksA = new SimpleStringProperty(nbDocksA);
        this.favoris = new SimpleBooleanProperty(favoris);
        this.ouvert = new SimpleBooleanProperty(ouvert);

    }

    public String getIdStation() {
        return idStation.get();
    }

    public String getNomStation() {
        return nomStation.get();
    }
    public StringProperty getNomStationColumn() {
        return nomStation;
    }
    public boolean getFavoris() {
        return favoris.get();
    }
    public boolean getOuvert() {
        return ouvert.get();
    }
    
    public BooleanProperty getFavorisColumn() {
        return favoris;
    }

    public String getLatStation() {
        return latStation.get();
    }

    public String getLonStation() {
        return lonStation.get();
    }

    public String getCodeStation() {
        return codeStation.get();
    }

    public String getCapacityStation() {
        return capacityStation.get();
    }

    public String getNbBikeA() {
        return nbBikeA.get();
    }

    public String getNbDocksA() {
        return nbDocksA.get();
    }
    
    public void setFavoris(boolean favoris) {
        this.favoris = new SimpleBooleanProperty(favoris);
    }

    


    
    
    
    
}
