/*
 * Abstract representation of what an event is
 */
package oeg.ft3.time.tictag;

import java.util.ArrayList;
import oeg.ft3.time.annotationHandler.Annotation;

/**
 *
 * @author mnavas
 */
public class AbstractEvent {
    
    ArrayList<Annotation> what = new ArrayList<Annotation>();
    ArrayList<Annotation> when = new ArrayList<Annotation>();
    ArrayList<Annotation> who = new ArrayList<Annotation>();
    
    String type;
    String id;    
    String lemma;    
    
    ArrayList<String> whatF;
    ArrayList<TIMEMAP> whenF;
    ArrayList<MAP> whoF;
    
    
    public AbstractEvent(){
        
    }
    
}
