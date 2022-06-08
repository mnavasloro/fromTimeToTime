/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.annotationHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.ft3.time.tictag.MAP;

/**
 *
 * @author mnavas
 */
public class EventsMatterAnnotation implements AnnotationType {

    public String tid;
    public String type;
    public String typeEv;
    public String lemma;
    public String prov;
    public TIMEX3 infotimex3;

    public EventsMatterAnnotation() {

    }
    
    public EventsMatterAnnotation(String input) {
        if (input.contains("Event_when")) {
            
            TIMEX3 res = new TIMEX3(input);

            this.type = "Event_when";
            this.infotimex3 = res;
            this.tid = res.tid;
        } else if (input.contains("Event_who")) {
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                this.tid = m.group(1);
            }
            this.type = "Event_who";
        } else if (input.contains("Event_what")) {
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                this.tid = m.group(1);
            }
            
            
            
            p = Pattern.compile("lemma=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                this.lemma = m.group(1);
            }

            p = Pattern.compile("type=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                this.typeEv = m.group(1);
            }

            p = Pattern.compile("prov=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                this.prov = m.group(1);
            }
            this.type = "Event_what";
        } else{
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                this.tid = m.group(1);
            }
            
            p = Pattern.compile("type=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                this.typeEv = m.group(1);
            }
            this.type = "Event";
        }
            
    }

    public EventsMatterAnnotation(MAP mape) {

        HashMap<String, String> map = mape.map;

        if (mape.meta.equalsIgnoreCase("EVENT CONTEXT")) {
            this.type = "Event";
            if (map.containsKey("TYPE")) {
                this.typeEv = map.get("TYPE");
            }
            if (map.containsKey("ID")) {
                this.tid = map.get("ID");
            }
        } else if (mape.meta.equalsIgnoreCase("WHO ANNOTATION")) {
            this.type = "Event_who";
            if (map.containsKey("TYPE")) {
                this.typeEv = map.get("TYPE");
            }
            if (map.containsKey("ID")) {
                this.tid = map.get("ID");
            }
        } else if (mape.meta.equalsIgnoreCase("EVENT ANNOTATION")) {
            this.type = "Event_what";
            if (map.containsKey("TYPE")) {
                this.typeEv = map.get("TYPE");
            }
            if (map.containsKey("PROV")) {
                this.prov = map.get("PROV");
            }
            if (map.containsKey("ID")) {
                this.tid = map.get("ID");
            }
            if (map.containsKey("LEMMA")) {
                this.lemma = map.get("LEMMA");
            }
        } else if (mape.meta.equalsIgnoreCase("TEMPORAL ANNOTATION")) {
            this.type = "Event_when";
            this.infotimex3 = new TIMEX3();
            if (map.containsKey("ID")) {
                this.tid = map.get("ID");
            }
            if (map.containsKey("TYPE")) {
                this.typeEv = map.get("TYPE");
            }
            if (map.containsKey("TYPE")) {
                this.infotimex3.type = map.get("TYPE");
            }

            if (map.containsKey("ID")) {
                this.infotimex3.tid = map.get("ID");
            }
            if (map.containsKey("VALUE")) {
                this.infotimex3.value = map.get("VALUE");
            }
            if (map.containsKey("SENTID")) {
                this.infotimex3.sentid = map.get("SENTID");
            }
            if (map.containsKey("FUNCTIONINDOCUMENT")) {
                this.infotimex3.functionInDocumentP = map.get("FUNCTIONINDOCUMENT");
            }
            if (map.containsKey("TEMPORALFUNCTION")) {
                this.infotimex3.temporalFunction = map.get("TEMPORALFUNCTION");
            }
            if (map.containsKey("VALUEFROMFUNCTION")) {
                this.infotimex3.valueFromFunction = map.get("VALUEFROMFUNCTION");
            }
            if (map.containsKey("MOD")) {
                this.infotimex3.mod = map.get("MOD");
            }
            if (map.containsKey("ANCHORTIMEID")) {
                this.infotimex3.anchorTimeID = map.get("ANCHORTIMEID");
            }
            if (map.containsKey("BEGINPOINT")) {
                this.infotimex3.beginPoint = map.get("BEGINPOINT");
            }
            if (map.containsKey("ENDPOINT")) {
                this.infotimex3.endPoint = map.get("ENDPOINT");
            }
            if (map.containsKey("QUANT")) {
                this.infotimex3.quant = map.get("QUANT");
            }
            if (map.containsKey("FREQ")) {
                this.infotimex3.freq = map.get("FREQ");
            }
        }
    }

    @Override
    public String toString() {
        if (type.equalsIgnoreCase("Event_who")) {
            return "<Event_who argument=\"who\" tid=\"" + tid + "\">";
        } else if (type.equalsIgnoreCase("Event_what")) {
            String aux;
            aux = "<Event_what argument=\"what\" tid=\"" + tid;
            if(type!=null){
                aux = aux + "\" type=\"" + type;
            }
            if(prov!=null){
                aux = aux + "\" prov=\"" + prov;
            }
            return aux + "\">";
        } else if (type.equalsIgnoreCase("Event")) {
            String aux;
            aux = "<Event_what argument=\"what\" tid=\"" + tid;
            if(type!=null){
                aux = aux + "\" type=\"" + type + "\" prov=\"" + prov + "\">";
            }
            return aux + "\">";
        } else if (type.equalsIgnoreCase("Event_when")) {
            return infotimex3.toString().replaceFirst("TIMEX3", "Event_when");
        }

        return "";
    }

    @Override
    public HashMap<String, String> process(String input) {
        return null;
        
    }

    @Override
    public EventsMatterAnnotation read(String input) {
        if (input.contains("Event_when")) {
            return readEvent_When(input);
        } else if (input.contains("Event_who")) {
            return readEvent_Who(input);
        } else if (input.contains("Event_what")) {
            return readEvent_What(input);
        }

        return readEvent(input);

    }

    public EventsMatterAnnotation readEvent_When(String input) {
        HashMap<String, Pattern> patternsTIMEX3 = new HashMap<String, Pattern>();

        patternsTIMEX3.put("tid", Pattern.compile("tid=\"([^\"]+)\""));
        patternsTIMEX3.put("sentid", Pattern.compile("sentID=\"([^\"]+)\""));
        patternsTIMEX3.put("functionInDocumentP", Pattern.compile("functionInDocument=\"([^\"]+)\""));
        patternsTIMEX3.put("temporalFunction", Pattern.compile("temporalFunction=\"([^\"]+)\""));
        patternsTIMEX3.put("valueFromFunction", Pattern.compile("valueFromFunction=\"([^\"]+)\""));
        patternsTIMEX3.put("mod", Pattern.compile("mod=\"([^\"]+)\""));
        patternsTIMEX3.put("anchorTimeID", Pattern.compile("anchorTimeID=\"([^\"]+)\""));
        patternsTIMEX3.put("beginPoint", Pattern.compile("beginPoint=\"([^\"]+)\""));
        patternsTIMEX3.put("endPoint", Pattern.compile("endPoint=\"([^\"]+)\""));
        patternsTIMEX3.put("quant", Pattern.compile("quant=\"([^\"]+)\""));
        patternsTIMEX3.put("freq", Pattern.compile("freq=\"([^\"]+)\""));
        patternsTIMEX3.put("value", Pattern.compile("value=\"([^\"]+)\""));
        patternsTIMEX3.put("type", Pattern.compile("type=\"([^\"]+)\""));
        Set<Map.Entry<String, Pattern>> entrySet = patternsTIMEX3.entrySet();

        EventsMatterAnnotation ev = new EventsMatterAnnotation();
        TIMEX3 res = new TIMEX3();

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    TIMEX3.class.getField(e.getKey()).set(res, m.group(1));
                }

            }
            if (res.type == null || res.value == null || res.tid == null) {
                return null; // A REQUIRED FIELD WAS NO PROVIDED
            }
        } catch (Exception ex) {
            Logger.getLogger(EventsMatterAnnotation.class.getName()).log(Level.SEVERE, null, ex);
        }

        ev.type = "Event_when";
        ev.infotimex3 = res;
        ev.tid = res.tid;

        return ev;
    }

    public EventsMatterAnnotation readEvent_Who(String input) {
        EventsMatterAnnotation ev = new EventsMatterAnnotation();
        try {
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                ev.tid = m.group(1);
            } else {
                return null; // A REQUIRED FIELD WAS NO PROVIDED
            }
        } catch (Exception ex) {
            Logger.getLogger(EventsMatterAnnotation.class.getName()).log(Level.SEVERE, null, ex);
        }

        ev.type = "Event_who";

        return ev;
    }

    public EventsMatterAnnotation readEvent_What(String input) {
        EventsMatterAnnotation ev = new EventsMatterAnnotation();
        try {
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                ev.tid = m.group(1);
            } else {
                return null; // A REQUIRED FIELD WAS NO PROVIDED
            }

            p = Pattern.compile("type=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                ev.typeEv = m.group(1);
            }

            p = Pattern.compile("prov=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                ev.prov = m.group(1);
            }

            p = Pattern.compile("lemma=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                ev.lemma = m.group(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(EventsMatterAnnotation.class.getName()).log(Level.SEVERE, null, ex);
        }

        ev.type = "Event_what";

        return ev;
    }

    public EventsMatterAnnotation readEvent(String input) {
        EventsMatterAnnotation ev = new EventsMatterAnnotation();
        try {
            Pattern p = Pattern.compile("tid=\"([^\"]+)\"");
            Matcher m = p.matcher(input);
            if (m.find()) {
                ev.tid = m.group(1);
            } else {
                return null; // A REQUIRED FIELD WAS NO PROVIDED
            }

            p = Pattern.compile("type=\"([^\"]+)\"");
            m = p.matcher(input);
            if (m.find()) {
                ev.typeEv = m.group(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(EventsMatterAnnotation.class.getName()).log(Level.SEVERE, null, ex);
        }

        ev.type = "Event";

        return ev;
    }

}
