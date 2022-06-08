/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.ft3.time.annotationHandler.AnnotationType;

/**
 *
 * @author mnavas
 */
public class TIMEMAP {

    public HashMap<String, String> map = new HashMap<String, String>();
    public String meta;
    public String orig;
    public AnnotationType obj;

    public TIMEMAP(MAP mapa) {
        meta = mapa.meta;
        obj = mapa.obj;
        orig = mapa.orig;

        HashMap<String, String> timeMap = new HashMap<String, String>();

        try {
            if (mapa.map.get("TYPE").equalsIgnoreCase("time")) {
                timeMap = time(mapa);
                timeMap.put("TYPE", "TIME");
            } else if (mapa.map.get("TYPE").equalsIgnoreCase("date")) {
                timeMap = date(mapa);
                if(timeMap!=null){
                    timeMap.put("TYPE", "DATE");
                }
            } else if (mapa.map.get("TYPE").equalsIgnoreCase("set")) {
                timeMap = set(mapa);
            } else if (mapa.map.get("TYPE").equalsIgnoreCase("duration")) {
                timeMap = duration(mapa);
                timeMap.put("TYPE", "DURATION");
            }

            if(timeMap!=null){
                this.map = timeMap;
            }
        } catch (Exception ex) {
            Logger.getLogger(TIMEMAP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }
    }

    
    private HashMap<String, String> set(MAP mapa) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        // value tipo date
        timeMap = date(mapa);
        if (timeMap != null) {
            timeMap.put("TYPE", "SET-DATE");
        } else {
            // value tipo duration
            timeMap = duration(mapa);
            if (timeMap != null) {
                timeMap.put("TYPE", "SET-DURATION");
            }
        }

        //freq
        if(mapa.map.containsKey("FREQ")){
            String freq = mapa.map.get("FREQ");
            
            if(freq.matches("\\d+")){
                timeMap.put("TIMEUNIT", "TIMES");
                timeMap.put("TIMEAMOUNT", freq);
            }
            else if(freq.matches("\\d+X")){
                timeMap.put("TIMEUNIT", "TIMES");
                timeMap.put("TIMEAMOUNT", freq.substring(0, freq.length()-1));
            }
            else if(freq.matches("X")){
                timeMap.put("TIMEUNIT", "TIMES");
                timeMap.put("TIMEAMOUNT", "1");
            }
            else if(freq.matches("\\d+[A-Z]+")){
                String freq1 = freq;
                if(!freq1.startsWith("P")){
                    freq1 = "P" + freq1;
                }
                HashMap<String, String> duration = duration(freq1);
                if(duration!=null && !duration.isEmpty()){
                    Map.Entry<String, String> aux = duration.entrySet().iterator().next();
                    timeMap.put("TIMEUNIT", aux.getKey());
                    timeMap.put("TIMEAMOUNT", aux.getValue());
                }                
            } else{ //else es un date
                HashMap<String, String> date = date(freq);
                if(date!=null && !date.isEmpty()){
                    Map.Entry<String, String> aux = date.entrySet().iterator().next();
                    timeMap.put("TIMEUNIT", aux.getKey());
                    timeMap.put("TIMEAMOUNT", aux.getValue());
                } 
            }
        }
        
        return timeMap;
    }

    
    private HashMap<String, String> duration(String value) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        String xspattern = "P((((\\d+(\\.\\d+)?|X{1,2})(M|ML))?((\\d+(\\.\\d+)?|X{1,2})(C|CE))?((\\d+(\\.\\d+)?|X{1,2})(DE))?((\\d+(\\.\\d+)?|X{1,2})Y)?((\\d+(\\.\\d+)?|X{1,2})T)?((\\d+(\\.\\d+)?|X{1,2})Q)?((\\d+(\\.\\d+)?|X{1,2})M)?((\\d+(\\.\\d+)?|X{1,2})D)?(T((\\d+(\\.\\d+)?|X{1,2})H)?((\\d+(\\.\\d+)?|X{1,2})M)?((\\d+(\\.\\d+)?|X{1,2})S)?(NI)?)?)|((\\d+(\\.\\d+)?|X{1,2}))(W|L|E|C|Q)|XX)";
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
            Logger.getLogger(TIMEMAP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }
    
    
    private HashMap<String, String> duration(MAP mapa) {
        String value = mapa.map.get("VALUE");
        return duration(value);
        
    }

    private HashMap<String, String> time(MAP mapa) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        String value = mapa.map.get("VALUE");
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
            Logger.getLogger(TIMEMAP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    private HashMap<String, String> date(MAP mapa) {
        String value = mapa.map.get("VALUE");
        return date(value);
        
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

        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?|X{1,2})([A-Z]{1,2})");
        Matcher m = p.matcher(partP);
        while (m.find()) {
            String amount = m.group(1);
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
                case "W":
                    timeMap.put("WEEK", amount);
                    break;
                default:
                    break;
            }

        }

        return timeMap;
    }

    private HashMap<String, String> processT(String partT, HashMap<String, String> timeMap) {

        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?|X{1,2})([A-Z]{1,2})");
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

    private HashMap<String, String> date(String value) {
        HashMap<String, String> timeMap = new HashMap<String, String>();
        
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
            Logger.getLogger(TIMEMAP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }
}
