package oeg.ft3.time.annotationHandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.ft3.time.tictag.MAP;
import oeg.ft3.time.tictag.TIMEMAP;

/**
 * Class that stores and outputs as String the information needed for a NIF
 * annotation
 *
 * @author mnavas
 */
public class Annotation {

    public String type = "";
    public String standard = "";
    public AnnotationType annotInfo;
    public MAP map;
    public String tag = "";
    public String beginIndex = "";
    public String endIndex = "";
    public String isString = "";
    public TIMEMAP timemap;

    @Override
    public String toString() {
        return type  + " - " + tag  + " - " + isString + " - " + beginIndex  + " - " + endIndex  + " - " + timemap + "\n\n";
    }
    
    public String createTag(){
        try {
            tag = annotInfo.toString();            
        } catch (Exception ex) {
            Logger.getLogger(Annotation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tag;
    }

}