/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.EventsMatterAnnotation;
import oeg.ft3.time.annotationHandler.TIMEX3;

/**
 *
 * @author mnavas
 */
public class EventsMattertranslator {

    public EventsMattertranslator() {
        init();
    }

    public void init() {
        try {

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + ex.toString());
        }

    }

    public ArrayList<Annotation> translateEventsMatter(String input) {
        ArrayList<Annotation> annstotal = new ArrayList<Annotation>(); 
        
        ArrayList<Annotation> anns = fromTXTtoEvent_When(input);        
        for(Annotation an : anns){
            an.standard="EventsMatter";
            annstotal.add(an);
        }
        
        anns = fromTXTtoEvent_What(input);        
        for(Annotation an : anns){
            an.standard="EventsMatter";
            annstotal.add(an);
        }
        
        
        anns = fromTXTtoEvent_Who(input);        
        for(Annotation an : anns){
            an.standard="EventsMatter";
            annstotal.add(an);
        }
        
        anns = fromTXTtoEvent(input);        
        for(Annotation an : anns){
            an.standard="EventsMatter";
            annstotal.add(an);
        }
        
        
        return annstotal;
    }

    public HashMap<String, String> processTIMEX(String input) {
        TIMEX3 timex = new TIMEX3();
        timex = timex.read(input);
        HashMap<String, String> timeMap = new HashMap<String, String>();

        try {
            if (timex.type.equalsIgnoreCase("time")) {
                timeMap = time(timex);
                timeMap.put("TYPE", "TIME");
            } else if (timex.type.equalsIgnoreCase("date")) {
                timeMap = date(timex);
                timeMap.put("TYPE", "DATE");
            } else if (timex.type.equalsIgnoreCase("set")) {
                timeMap = set(timex);
            } else if (timex.type.equalsIgnoreCase("duration")) {
                timeMap = duration(timex);
                timeMap.put("TYPE", "DURATION");
            }

            return timeMap;
        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    private HashMap<String, String> set(TIMEX3 timex) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        // value tipo date
        timeMap = date(timex);
        if (timeMap != null) {
            timeMap.put("TYPE", "SET-DATE");
        } else {
            // value tipo duration
            timeMap = duration(timex);
            if (timeMap != null) {
                timeMap.put("TYPE", "SET-DURATION");
            }
        }

        //quant y freq
        return timeMap;
    }

    private HashMap<String, String> duration(TIMEX3 timex) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        String value = timex.value;
        String xspattern = "P((((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})(M|ML))?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})(C|CE))?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})(DE))?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})Y)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})T)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})Q)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})M)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})D)?(T((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})H)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})M)?((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})S)?(NI)?)?)|((\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2}))(W|L|E|C|Q)|XX)";
        try {
            if (value.matches(xspattern)) {
//                value=value.substring(1);

                String partP = "";
                String partT = "";

                if (value.indexOf("T") == -1) { //no Ts, solo P
                    partP = value;
                } else if (value.lastIndexOf("T") != value.indexOf("T")) { // HAY LOS DOS
                    String[] parts = value.split(value);
                    partP = parts[0] + "T" + parts[1];
                    partT = parts[2];
                } else if (value.lastIndexOf("T") == value.indexOf("T") && Character.isAlphabetic(value.charAt(value.indexOf("T") - 1))) { // es la separacion
                    String[] parts = value.split(value);
                    partP = parts[0];
                    partT = parts[1];
                } else { // ES TRIMESTER
                    partP = value;
                }

                if (!partP.equalsIgnoreCase("P")) {
                    timeMap = processP(partP, timeMap);
                }
                if (!partT.isEmpty()) {
                    timeMap = processT(partT, timeMap);
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    private HashMap<String, String> time(TIMEX3 timex) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        String value = timex.value;
        String xspattern = "([0-9X]{1,4}-[0-9X]{1,2}-[0-9X]{1,2})?T(([0-9X]{2}(:[0-9X]{2}(:[0-9X]{2})?)?)(Z)?|(MO|MI|AF|EV|NI|DT))";
        String xspattern2 = "[0-9X]{1,4}-W[0-9X]{1,2}-[1-7X]T(([0-9]{2}(:[0-9]{2}(:[0-9]{2})?)?)(Z)?|(MO|MI|AF|EV|NI|DT))";
        try {
            // (([0-9X]{2}(:[0-9X]{2}(:[0-9X]{2})?)?)(Z)?|(MO|MI|AF|EV|NI|DT))
            if (value.matches(xspattern)) {

                // Quitamos la T
                String[] tparts = value.split("T", 2);
                String timepart = "";
                String datepart = "";
                if (tparts.length == 2) {
                    datepart = tparts[0]; // ([0-9X]{1,4}-[0-9X]{1,2}-[0-9X]{1,2})
                    timepart = tparts[1];
                } else {
                    timepart = tparts[0];
                }

                if (!datepart.isEmpty()) {
                    //PROCESA FECHA
                    timeMap = DateXXXX_XX_XX(timeMap, datepart);
                }

                if (timepart.matches("[A-Z][A-Z]")) {
                    timeMap.put("PARTDAY", timepart);
                } else {
                    timeMap = TimeXX_XX_XX(timeMap, timepart);
                }
            } else if (!value.matches(xspattern2)) {
// Quitamos la T
                String[] tparts = value.split("T", 2);
                String timepart = "";
                String datepart = "";
                if (tparts.length == 2) {
                    datepart = tparts[0]; //[0-9X]{1,4}-W[0-9X]{1,2}-[1-7X]
                    timepart = tparts[1];
                }

                if (!datepart.isEmpty()) { //[0-9X]{1,4}-W[0-9X]{1,2}-[1-7X]
                    //PROCESA FECHA
                    timeMap = DateXXXX_WXX_X(timeMap, datepart);
                }

                if (timepart.matches("[A-Z][A-Z]")) {
                    timeMap.put("PARTDAY", timepart);
                } else {
                    timeMap = TimeXX_XX_XX(timeMap, timepart);
                }
            } else {
                return null;//"NO CORRECT";
            }

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    private HashMap<String, String> date(TIMEX3 timex) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        String value = timex.value;
        String xspattern = "(BC|-)?[0-9X]{1,4}(-[0-9X]{1,2}(-[0-9X]{1,2})?)?";
        String xspattern2 = "[0-9X]{1,4}-W[0-9X]{1,2}(-([1-7X]|WE))?";
        String xspattern4 = "[0-9X]{1,4}-(SP|SU|WI|FA)";
        String xspattern5 = "[0-9X]{1,4}-(H[1-2X]|Q[1-4X]|T[1-4X])";

        if (value.equalsIgnoreCase("PAST_REF")) {
            timeMap.put("REF", "PAST");
            return timeMap;
        } else if (value.equalsIgnoreCase("PRESENT_REF")) {
            timeMap.put("REF", "PRESENT");
            return timeMap;
        } else if (value.equalsIgnoreCase("FUTURE_REF")) {
            timeMap.put("REF", "FUTURE");
            return timeMap;
        }

        try {
            // [0-9X]{1,4}-(SP|SU|WI|FA)
            if (value.matches(xspattern4)) {
                String[] tparts = value.split("-", 2);
                timeMap.put("YEAR", tparts[0]);
                timeMap.put("SEASON", tparts[1]);
            } else if (value.matches(xspattern2)) { //[0-9X]{1,4}-W[0-9X]{1,2}(-([1-7X]|WE))?
                String[] tparts = value.split("-", 3);
                timeMap.put("YEAR", tparts[0]);
                timeMap.put("WEEK", tparts[1].substring(1));

                if (tparts.length == 3) {
                    timeMap.put("WEEKDAY", tparts[2]);  //ENTRA WEEKEND!
                }

            } else if (value.matches(xspattern5)) {
                String[] tparts = value.split("-", 2);
                timeMap.put("YEAR", tparts[0]);
                if (tparts[1].startsWith("H")) {
                    timeMap.put("HALFYEAR", tparts[1].substring(1));
                } else if (tparts[1].startsWith("T")) {
                    timeMap.put("TRIMESTER", tparts[1].substring(1));
                } else if (tparts[1].startsWith("Q")) {
                    timeMap.put("QUARTER", tparts[1].substring(1));
                }
            } else if (value.matches(xspattern)) { //"(BC|-)?[0-9X]{1,4}(-[0-9X]{1,2}(-[0-9X]{1,2})?)?"

                if (value.startsWith("BC")) {
                    timeMap.put("ERA", "BC");
                    value = value.substring(2);
                } else if (value.startsWith("-")) {
                    timeMap.put("ERA", "BC");
                    value = value.substring(1);
                }

                String[] parts = value.split("-", 3);

                switch (parts.length) {
                    case 3:
                        timeMap.put("DAY", parts[2]);
                    case 2:
                        timeMap.put("MONTH", parts[1]);
                    case 1:
                        timeMap.put("YEAR", parts[0]);
                        break;
                    default:
                        break;
                }

            } else {
                return null;//"NO CORRECT";
            }

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    private HashMap DateXXXX_WXX_X(HashMap timeMap, String value) { //[0-9X]{1,4}-W[0-9X]{1,2}-[1-7X]

        String[] parts = value.split("-");
        timeMap.put("DAY", parts[2]);
        timeMap.put("WEEK", parts[1].substring(1));
        timeMap.put("WEEKDAY", parts[0]);

        return timeMap;
    }

    private HashMap DateXXXX_XX_XX(HashMap timeMap, String value) {

        String[] parts = value.split("-");
        switch (parts.length) {
            case 3:
                timeMap.put("DAY", parts[2]);
            case 2:
                timeMap.put("MONTH", parts[1]);
            case 1:
                timeMap.put("YEAR", parts[0]);
                break;
            default:
                break;
        }

        return timeMap;
    }

    private HashMap TimeXX_XX_XX(HashMap timeMap, String value) {

        String[] parts = value.split(":");
        switch (parts.length) {
            case 3:
                timeMap.put("SECOND", parts[2].substring(0, 2));
            case 2:
                timeMap.put("MINUTE", parts[1]);
            case 1:
                timeMap.put("HOUR", parts[0]);
                break;
            default:
                break;
        }

        return timeMap;
    }

    private HashMap<String, String> processP(String partP, HashMap<String, String> timeMap) {

        Pattern p = Pattern.compile("(\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})([A-Z]{1,2})");
        Matcher m = p.matcher(partP);
        while (m.find()) {
            String amount = m.group(2);
            String unit = m.group(3);

            switch (unit) {
                case "ML":
                    timeMap.put("MILLENIUM", amount);
                    break;
                case "C":
                case "CE":
                    timeMap.put("CENTURY", amount);
                    break;
                case "DE":
                    timeMap.put("DECADE", amount);
                    break;
                case "Y":
                    timeMap.put("YEAR", amount);
                    break;
                case "T":
                    timeMap.put("TRIMESTER", amount);
                    break;
                case "Q":
                    timeMap.put("QUARTER", amount);
                    break;
                case "H":
                    timeMap.put("HALFYEAR", amount);
                    break;
                case "M":
                    timeMap.put("MONTH", amount);
                    break;
                case "D":
                    timeMap.put("DAY", amount);
                    break;
                default:
                    break;
            }

        }

        return timeMap;
    }

    private HashMap<String, String> processT(String partT, HashMap<String, String> timeMap) {

        Pattern p = Pattern.compile("(\\p{Nd}+(\\.\\p{Nd}+)?|X{1,2})([A-Z]{1,2})");
        Matcher m = p.matcher(partT);
        while (m.find()) {
            String amount = m.group(2);
            String unit = m.group(3);

            switch (unit) {
                case "H":
                    timeMap.put("HOUR", amount);
                    break;
                case "M":
                    timeMap.put("MINUTE", amount);
                    break;
                case "S":
                    timeMap.put("SECOND", amount);
                    break;
                default:
                    break;
            }

        }

        return timeMap;
    }
 
    private ArrayList<Annotation> fromTXTtoEvent_When(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?Event_when[^>]*>)<\\/?[^>]*>", "");
            
            String pattern = "(<Event_when [^>]*>)([^<]*)<\\/Event_when>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "Event_when";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    EventsMatterAnnotation t = new EventsMatterAnnotation(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO EVENT_WHEN");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    private ArrayList<Annotation> fromTXTtoEvent_Who(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?Event_who[^>]*>)<\\/?[^>]*>", "");
            
            String pattern = "(<Event_who [^>]*>)([^<]*)<\\/Event_who>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "Event_who";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    EventsMatterAnnotation t = new EventsMatterAnnotation(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO EVENT_WHO");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    private ArrayList<Annotation> fromTXTtoEvent_What(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?Event_what[^>]*>)<\\/?[^>]*>", "");
            
            String pattern = "(<Event_what [^>]*>)([^<]*)<\\/Event_what>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "Event_what";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    EventsMatterAnnotation t = new EventsMatterAnnotation(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO EVENT_WHAT");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    private ArrayList<Annotation> fromTXTtoEvent(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(<\\/?Event_[^>]*>)", "");
            
            String pattern = "(<Event [^>]*>)([^<]*)<\\/Event>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "Event";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    EventsMatterAnnotation t = new EventsMatterAnnotation(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO EVENT");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(EventsMattertranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
