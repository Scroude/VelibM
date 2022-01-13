/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib.bd;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author slam
 */
public class JDBC {

    private String id;
    
    private md5_java hash = new md5_java();
    
    public JDBC() {
        this.id = null;
    }
    
    public void miseAJourMdp(String nom_utilisateur, String mot_de_passe) throws NoSuchAlgorithmException {
        Connection connexion = null;
        String hash_mdp = hash.hash(mot_de_passe);
        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // mise à jour
        PreparedStatement requete = null;
        try {
            requete = connexion.prepareStatement("update utilisateur set mot_de_passe=? where nom=?");
            
            requete.setString(1, hash_mdp);    
            requete.setString(2, nom_utilisateur);
            requete.executeUpdate(); // tentative d'exécution de la requête
        } catch (SQLException e) {
            // la requête n'a pas réussie
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la mise à jour");
            System.exit(1);
        }

        //System.out.println("mise à jour réussie");
    }
    
    public void supprimerFavoris(int uti_id, String id_station) {
        Connection connexion = null;

        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // mise à jour
        PreparedStatement requete = null;
        try {
            requete = connexion.prepareStatement("delete from favoris where uti_id=? and id_station=?");
            
            requete.setInt(1, uti_id);
            requete.setString(2, id_station);    
            requete.executeUpdate(); // tentative d'exécution de la requête
        } catch (SQLException e) {
            // la requête n'a pas réussie
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la mise à jour");
            System.exit(1);
        }

        System.out.println("mise à jour réussie");
    }
    
    public void ajouterFavoris(int uti_id, int id_station) {
        Connection connexion = null;

        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // mise à jour
        PreparedStatement requete = null;
        try {
            requete = connexion.prepareStatement("insert into favoris(uti_id, id_station) values (?, ?)");
            
            requete.setInt(1, uti_id);
            requete.setInt(2, id_station);    
            requete.executeUpdate(); // tentative d'exécution de la requête
        } catch (SQLException e) {
            // la requête n'a pas réussie
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la mise à jour");
            System.exit(1);
        }

        //System.out.println("mise à jour réussie");
    }
    
    public String[] SelectionFavoris(int id_uti) {
        Connection connexion = null;
        String [] favoris = new String[50];
        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // sélection
        PreparedStatement requete = null;
        ResultSet resultat = null; // représente le résultat de la requête
        try {
            requete = connexion.prepareStatement("select id_station from favoris where uti_id= ?");
            requete.setInt(1, id_uti);

            resultat = requete.executeQuery();

            int i = 0;
            while (resultat.next()) {
                int station = resultat.getInt("id_station");
                favoris[i] = Integer.toString(station);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la sélection");
            System.exit(1); // on arrête le programme
        }
        //System.out.println("la sélection a été exécutée");
        return favoris;
    }
    
    public String ConnexionUti(String nom_utilisateur, String mot_de_passe) throws NoSuchAlgorithmException {
        Connection connexion = null;
        String hash_mdp = hash.hash(mot_de_passe);
        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // sélection
        PreparedStatement requete = null;
        ResultSet resultat = null; // représente le résultat de la requête
        try {
            requete = connexion.prepareStatement("select id from utilisateur where nom=? and mot_de_passe=?");
            requete.setString(1, nom_utilisateur);
            requete.setString(2, hash_mdp);

            resultat = requete.executeQuery();        
            while (resultat.next()) {
                this.id = resultat.getString("id");
                //System.out.println(id);
                
            }
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la sélection");
            System.exit(1); // on arrête le programme
        }
        //System.out.println("la sélection a été exécutée");
        return getId();
    
    }
    
    public String CreationUti(String nom, String mot_de_passe) throws NoSuchAlgorithmException {
        Connection connexion = null;
        String hash_mdp = hash.hash(mot_de_passe);
        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // insertion
        PreparedStatement requete = null;
        try {
            requete = connexion.prepareStatement("insert into utilisateur(nom, mot_de_passe) values (?, ?)");
            requete.setString(1, nom); // 2 correspond au 2ème point d'interrogation
            requete.setString(2, hash_mdp); // 3 correspond au 3ème point d'interrogation
            requete.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de l'insertion");
            System.exit(1); // on arrête le programme
        }
        String oui = "ajouté";
        return oui;

    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean checkMdp(String nom_utilisateur, String mot_de_passe) throws NoSuchAlgorithmException {
        Connection connexion = null;
        String hash_mdp = hash.hash(mot_de_passe);
        // tentative de connexion à la base de données
        try {
            Class.forName("org.postgresql.Driver"); // chargement du pilote (driver) permettant d'accéder au SGBDR
            connexion = DriverManager.getConnection("jdbc:postgresql:bd_velib", "uti_velib", "slam"); // chemin de la base, utilisateur, mot de passe
        } catch (ClassNotFoundException e) {
            // la connexion a échoué
            System.err.println("erreur driver non trouvé");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        } catch (SQLException e) {
            // la connexion a échoué
            System.err.println("erreur SQL au moment de la connexion");
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.exit(1); // on arrête le programme
        }

        // sélection
        PreparedStatement requete = null;
        ResultSet resultat = null; // représente le résultat de la requête
        try {
            requete = connexion.prepareStatement("select id from utilisateur where nom=? and mot_de_passe=?");
            requete.setString(1, nom_utilisateur);
            requete.setString(2, hash_mdp);

            resultat = requete.executeQuery();        
            while (resultat.next()) {
                return true;
                
            }
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("erreur lors de la sélection");
            System.exit(1); // on arrête le programme
        }
        //System.out.println("la sélection a été exécutée");
        return false;
    
    }
    
    
}
