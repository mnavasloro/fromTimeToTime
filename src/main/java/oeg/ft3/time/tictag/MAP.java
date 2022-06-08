/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import java.util.HashMap;
import oeg.ft3.time.annotationHandler.AnnotationType;
import oeg.ft3.time.annotationHandler.EVENT;
import oeg.ft3.time.annotationHandler.EventsMatterAnnotation;
import oeg.ft3.time.annotationHandler.TIMEX3;

/**
 *
 * @author mnavas
 */
public class MAP {

    public HashMap<String, String> map = new HashMap<String, String>();
    public String meta;
    public String orig;
    public AnnotationType obj;

    public MAP(TIMEX3 input) {
        meta = "TEMPORAL ANNOTATION";
        obj = input;
        orig = "TIMEX3";

        if (input.type != null) {
            map.put("TYPE", input.type);
        }
        if (input.tid != null) {
            map.put("ID", input.tid);
        }
        if (input.value != null) {
            map.put("VALUE", input.value);
        }
        if (input.sentid != null) {
            map.put("SENTID", input.sentid);
        }
        if (input.functionInDocumentP != null) {
            map.put("FUNCTIONINDOCUMENT", input.functionInDocumentP);
        }
        if (input.temporalFunction != null) {
            map.put("TEMPORALFUNCTION", input.temporalFunction);
        }
        if (input.valueFromFunction != null) {
            map.put("VALUEFROMFUNCTION", input.valueFromFunction);
        }
        if (input.mod != null) {
            map.put("MOD", input.mod);
        }
        if (input.anchorTimeID != null) {
            map.put("ANCHORTIMEID", input.anchorTimeID);
        }
        if (input.beginPoint != null) {
            map.put("BEGINPOINT", input.beginPoint);
        }
        if (input.endPoint != null) {
            map.put("ENDPOINT", input.endPoint);
        }
        if (input.quant != null) {
            map.put("QUANT", input.quant);
        }
        if (input.freq != null) {
            map.put("FREQ", input.freq);
        }
    }

    public MAP(EVENT input) {
        meta = "EVENT ANNOTATION";
        obj = input;
        orig = "EVENT";

        if (input.clas != null) {
            map.put("TYPE", input.clas);
        }
        if (input.stem != null) {
            map.put("STEM", input.stem);
        }
        if (input.eid != null) {
            map.put("ID", input.eid);
        }
        
    }
    
    public MAP(EventsMatterAnnotation input) {        
        orig = input.type;
        if (input.type.equalsIgnoreCase("Event_when")) {
            meta = "TEMPORAL ANNOTATION";
            obj = input;

            if (input.infotimex3.type != null) {
                map.put("TYPE", input.infotimex3.type);
            }
            if (input.tid != null) {
                map.put("ID", input.tid);
            }
            if (input.lemma != null) {
                map.put("LEMMA", input.lemma);
            }
            if (input.infotimex3.value != null) {
                map.put("VALUE", input.infotimex3.value);
            }
            if (input.infotimex3.sentid != null) {
                map.put("SENTID", input.infotimex3.sentid);
            }
            if (input.infotimex3.functionInDocumentP != null) {
                map.put("FUNCTIONINDOCUMENT", input.infotimex3.functionInDocumentP);
            }
            if (input.infotimex3.temporalFunction != null) {
                map.put("TEMPORALFUNCTION", input.infotimex3.temporalFunction);
            }
            if (input.infotimex3.valueFromFunction != null) {
                map.put("VALUEFROMFUNCTION", input.infotimex3.valueFromFunction);
            }
            if (input.infotimex3.mod != null) {
                map.put("MOD", input.infotimex3.mod);
            }
            if (input.infotimex3.anchorTimeID != null) {
                map.put("ANCHORTIMEID", input.infotimex3.anchorTimeID);
            }
            if (input.infotimex3.beginPoint != null) {
                map.put("BEGINPOINT", input.infotimex3.beginPoint);
            }
            if (input.infotimex3.endPoint != null) {
                map.put("ENDPOINT", input.infotimex3.endPoint);
            }
            if (input.infotimex3.quant != null) {
                map.put("QUANT", input.infotimex3.quant);
            }
            if (input.infotimex3.freq != null) {
                map.put("FREQ", input.infotimex3.freq);
            }
        } else if (input.type.equalsIgnoreCase("Event_who")) {
            meta = "WHO ANNOTATION";
            obj = input;

            if (input.tid != null) {
                map.put("ID", input.tid);
            }
        } else if (input.type.equalsIgnoreCase("Event_what")) {
            meta = "EVENT ANNOTATION";
            obj = input;

            if (input.type != null) {
                map.put("TYPE", input.typeEv);
            }
            if (input.tid != null) {
                map.put("ID", input.tid);
            }
            if (input.prov != null) {
                map.put("PROV", input.prov);
            }
            if (input.lemma != null) {
                map.put("LEMMA", input.lemma);
            }
        } else if (input.type.equalsIgnoreCase("Event")) {
            meta = "EVENT CONTEXT";
            obj = input;

            if (input.type != null) {
                map.put("TYPE", input.typeEv);
            }
            if (input.tid != null) {
                map.put("ID", input.tid);
            }
        }
    }

}
