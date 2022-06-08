/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.eventExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnavas
 */
public class filesDownloader {

    
    static public File pythonDownloader(String id, String path) {

        File f = null;
            try {
                System.out.println("To execute: " + "python " + path + " -i " + id);

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("python " + path + " -i " + id);
        
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        lineReader.lines().forEach(System.out::println);

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        errorReader.lines().forEach(System.out::println);
        
        pr.waitFor();
        System.out.println(pr.exitValue());
        
        f = new File(id + ".html");
        
    }
    catch(Exception e) {
        System.out.println(e.toString());
        return null;
    }
        return f;    
    }
    
        
    static public File htmlDownloader(String id, String urlS) {

        File f = new File(id + ".html");
        
            InputStream in = null;
            try {
                System.out.println("opening connection");
                URL url = new URL(urlS + id);
                in = url.openStream();
                FileOutputStream fos = new FileOutputStream(f);
                System.out.println("reading file...");
                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from
                // connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }       fos.close();
                in.close();
                System.out.println("file was downloaded in:");
                System.out.println(f.getAbsolutePath());
            } catch (Exception ex) {
                Logger.getLogger(filesDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return f;
    }
    
}
