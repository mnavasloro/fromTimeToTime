/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.AnnotationType;
import oeg.ft3.time.annotationHandler.EVENT;
import oeg.ft3.time.annotationHandler.EventsMatterAnnotation;
import oeg.ft3.time.annotationHandler.TIMEX3;

/**
 *
 * @author mnavas
 */
public class MAP2AnnotationType {
    
    public Annotation toTimeML(Annotation an){
        Annotation newan = an;
        newan.standard="TimeML";
        
        if(an.map.meta.equalsIgnoreCase("TEMPORAL ANNOTATION")){
            newan.type = "TIMEX3";
            newan.annotInfo = new TIMEX3(an.map);
            return newan;
        }
        else if(an.map.meta.equalsIgnoreCase("EVENT ANNOTATION")){
            newan.type = "EVENT";
            newan.annotInfo = new EVENT(an.map);
            return newan;
        }
        
        return null;
    }
    
    public Annotation toEventsMatter(Annotation an){
        Annotation newan = an;
        newan.standard="EventsMatter";
        
        if(an.map.meta.equalsIgnoreCase("TEMPORAL ANNOTATION")){
            newan.type = "Event_when";
            newan.annotInfo = new EventsMatterAnnotation(an.map);
            return newan;
        }
        else if(an.map.meta.equalsIgnoreCase("EVENT ANNOTATION")){
            newan.type = "Event_what";
            newan.annotInfo = new EventsMatterAnnotation(an.map);
            return newan;
        }
        else if(an.map.meta.equalsIgnoreCase("WHO ANNOTATION")){
            newan.type = "Event_who";
            newan.annotInfo = new EventsMatterAnnotation(an.map);
            return newan;
        }
        else if(an.map.meta.equalsIgnoreCase("EVENT CONTEXT")){
            newan.type = "Event";
            newan.annotInfo = new EventsMatterAnnotation(an.map);
            return newan;
        }
        
        return null;
    }
    
    
}
