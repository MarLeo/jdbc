package com.project.jdbc.config;

/**
 * Created by marti on 23/01/2017.
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConfigConnection.java
 * Initialise une connexion à une base
 * en lisant un fichier de propriétés
 *
 * @version 2 (avec getResource)
 */
public class ConfigConnection {

    //private static final String USER = "postgres";
    //private static final String MDP = "hello";

    private ConfigConnection() {
    }

    /**
     * Obtenir une connexion à partir des infos qui sont
     * dans un fichier de propriétés.
     * Le fichier doit contenir les propriétés driver, url,
     * utilisateur, mdp (mot de passe).
     *
     * @param nomFichierProp nom du fichier de propriétés. Si le nom
     *                       commence par "/", l'emplacement désigne un endroit relatif
     *                       au classpath, sinon il désigne un endroit relatif au
     *                       répertoire qui contient le fichier ConfigConnection.class.
     * @return une connexion à la base
     */
    public static Connection getConnection(String nomFichierProp)
            throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        URL urlFichierProp = ConfigConnection.class.getResource(nomFichierProp);
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(urlFichierProp.openStream());
            props.load(bis);
            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            String utilisateur = props.getProperty("utilisateur");
            String mdp = props.getProperty("mdp");
            Class.forName(driver);
            return DriverManager.getConnection(url, utilisateur, mdp);
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

    /**
     * Obtenir une connexion à partir des infos qui sont
     * dans un fichier de propriétés, du nom d'utilisateur
     * et du mot de passe passés en paramètre.
     * Le fichier doit contenir les propriétés driver, url.
     * Le nom et le mot de passe de l'utilisateur sont passés
     * en paramètre de la méthode.
     *
     //* @param nomFichierProp nom du fichier de propriétés. Si le nom
     *                       commence par "/", l'emplacement désigne un endroit relatif
     *                       au classpath, sinon il désigne un endroit relatif au
     *                       répertoire qui contient le fichier ConfigConnection.class.
     * @param utilisateur    nom de l'utilisateur.
     * @param mdp            mot de passe de l'utilisateur.
     * @return une connexion à la base.
     */
    public static Connection getConnection(/*String nomFichierProp,*/
                                           String utilisateur,
                                           String mdp)
            throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        String nomFichierProp = "C:\\Users\\marti\\Documents\\IdeaProjects\\jdbc\\src\\main\\resources\\config.properties";
        //URL urlFichierProp = ConfigConnection.class.getResource(/*nomFichierProp*/"config.properties");
        //BufferedInputStream bis = null;
        InputStream in = null;
        try {
            //bis = new BufferedInputStream(urlFichierProp);
            in = new FileInputStream(nomFichierProp);
            props.load(in);
            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            //String utilisateur = props.getProperty("utilisateur");
            //String mdp = props.getProperty("mdp");

            Class.forName(driver);

            return DriverManager.getConnection(url, utilisateur, mdp);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

} // Config Connexion
