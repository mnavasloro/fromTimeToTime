package oeg.ft3.time.annotationHandler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.ft3.time.tictag.MAP;

/**
 * Class that stores and outputs as String the information needed for a NIF
 * annotation
 *
 * @author mnavas
 */
public interface AnnotationType {
    
    
    /**
     *
     * @param input
     * @return
     */
    public HashMap<String, String> process(String input);
    
    /**
     *
     * @param input
     * @return
     */
    public AnnotationType read(String input);

}