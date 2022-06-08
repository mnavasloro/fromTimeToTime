/*
 * Annotation to Event
 */
package oeg.ft3.time.tictag;

import java.util.ArrayList;
import oeg.ft3.time.annotationHandler.Annotation;

/**
 *
 * @author This is currently done for events, eventually it should be done with instances!
 */
public class ANNOT2EVENT {
    
    TIMEMAP2TIMEOWL t2t = new TIMEMAP2TIMEOWL();

    public String map2eventEventsMatter(Annotation ev, ArrayList<Annotation> args) {
        
            if (ev.map != null && ev.map.meta.equalsIgnoreCase("EVENT ANNOTATION")) {

                return "ft3:hasEvent [" + processEventAnnotation(ev,args) + "]; \r\n" + "\t";  
            }            
            
            return "";
    }

    public String processEventAnnotation(Annotation an, ArrayList<Annotation> args) {
        AbstractEvent ev = new AbstractEvent();
        ev.what.add(an);
        String id = an.map.map.get("ID");
        String aux = "";
        if(an.map.map.containsKey("LEMMA")){
            aux += " sem:EventType    \"" + an.map.map.get("LEMMA") + "\" ;\r\n\t";
        }
        if(an.map.map.containsKey("TYPE")){
            aux += " ft3:hasType    ft3:" + an.map.map.get("TYPE") + " ;\r\n\t";
        }
        
        String outp = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/EVENT_" + id + "> [\r\n" + "\t"
                + " a sem:Event ;\r\n" + "\t" + aux
                + " ft3:hasID    \"\"\"" + id + "\"\"\" ;\r\n";
        
        for(Annotation a : args){
            if(id.equalsIgnoreCase(a.map.map.get("ID"))){
                outp = outp + processArg(a);
            }
        }

        return outp + " ]\r\n" + "\t";

    }

    private String processArg(Annotation a) {
        String outp = "";
        String type = a.map.meta;
        if(type.equalsIgnoreCase("WHO ANNOTATION")){
            outp = outp + "\t" + " sem:hasActor    \"\"\"" + a.isString + "\"\"\"^^xsd:String ;\r\n";            
        } else if(type.equalsIgnoreCase("TEMPORAL ANNOTATION")){
            outp = outp + "\t" + " sem:hasTime    [" + t2t.processAnnotation(a) + "] ;\r\n";            
        }  
        
        return outp;
    }

}
