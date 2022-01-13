/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velib.velib.bd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author slam
 */
public class md5_java {
    String hash(String mot_de_passe) throws NoSuchAlgorithmException {
        String password = mot_de_passe;

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convertir le tableau de bits en une format hexadécimal - méthode 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();    
    }
}
