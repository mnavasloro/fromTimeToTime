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
import oeg.ft3.time.tictag.TimeMLtranslator;


/**
 *
 * @author mnavas
 */
public class TIMEX3 implements AnnotationType{

    public String tid;
    public String sentid;
    public String functionInDocumentP;
    public String temporalFunction;
    public String valueFromFunction;
    public String mod;
    public String anchorTimeID;
    public String beginPoint;
    public String endPoint;
    public String quant;
    public String freq;
    public String value;
    public String type;
    
    
    
    @Override
    public String toString() {
        String ret = "<TIMEX3 tid=\"" + tid + "\" type=\"" + type + "\" value=\"" + value + "\"";
        if(sentid!=null){
            ret = ret + " sentid=\"" + sentid + "\"";
        }
        if(functionInDocumentP!=null){
            ret = ret + " functionInDocument=\"" + functionInDocumentP + "\"";
        }
        if(temporalFunction!=null){
            ret = ret + " temporalFunction=\"" + temporalFunction + "\"";
        }
        if(valueFromFunction!=null){
            ret = ret + " valueFromFunction=\"" + valueFromFunction + "\"";
        }
        if(mod!=null){
            ret = ret + " mod=\"" + mod + "\"";
        }
        if(anchorTimeID!=null){
            ret = ret + " anchorTimeID=\"" + anchorTimeID + "\"";
        }
        if(beginPoint!=null){
            ret = ret + " beginPoint=\"" + beginPoint + "\"";
        }
        
        if(endPoint!=null){
            ret = ret + " endPoint=\"" + endPoint + "\"";
        }
        if(quant!=null){
            ret = ret + " quant=\"" + quant + "\"";
        }
        if(freq!=null){
            ret = ret + " freq=\"" + freq + "\"";
        }
        
        return ret + ">";
    }
    
    public TIMEX3(){
    }
    
    public TIMEX3(MAP mape){
        HashMap<String, String> map = mape.map;
        
        if(map.containsKey("TYPE")){
            this.type = map.get("TYPE");
        }
        if(map.containsKey("ID")){
            this.tid = map.get("ID");
        }
        if(map.containsKey("VALUE")){
            this.value = map.get("VALUE");
        }
        if(map.containsKey("SENTID")){
            this.sentid = map.get("SENTID");
        }
        if(map.containsKey("FUNCTIONINDOCUMENT")){
            this.functionInDocumentP = map.get("FUNCTIONINDOCUMENT");
        }
        if(map.containsKey("TEMPORALFUNCTION")){
            this.temporalFunction = map.get("TEMPORALFUNCTION");
        }
        if(map.containsKey("VALUEFROMFUNCTION")){
            this.valueFromFunction = map.get("VALUEFROMFUNCTION");
        }
        if(map.containsKey("MOD")){
            this.mod = map.get("MOD");
        }
        if(map.containsKey("ANCHORTIMEID")){
            this.anchorTimeID = map.get("ANCHORTIMEID");
        }
        if(map.containsKey("BEGINPOINT")){
            this.beginPoint = map.get("BEGINPOINT");
        }
        if(map.containsKey("ENDPOINT")){
            this.endPoint = map.get("ENDPOINT");
        }
        if(map.containsKey("QUANT")){
            this.quant = map.get("QUANT");
        }
        if(map.containsKey("FREQ")){
            this.freq = map.get("FREQ");
        }
        
    }
    
    public TIMEX3(String input){
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

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    TIMEX3.class.getField(e.getKey()).set(this, m.group(1));
                }
                
                
            }
        } catch (Exception ex) {
            Logger.getLogger(TIMEX3.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public HashMap<String, String> process(String input) {
        TIMEX3 timex = read(input);
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
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }
    
    
    

    static private HashMap<String, String> set(TIMEX3 timex) {
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

    static private HashMap<String, String> duration(TIMEX3 timex) {
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
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    static private HashMap<String, String> time(TIMEX3 timex) {
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
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    static private HashMap<String, String> date(TIMEX3 timex) {
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
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return timeMap;
    }

    static private HashMap DateXXXX_WXX_X(HashMap timeMap, String value) { //[0-9X]{1,4}-W[0-9X]{1,2}-[1-7X]

        String[] parts = value.split("-");
        timeMap.put("DAY", parts[2]);
        timeMap.put("WEEK", parts[1].substring(1));
        timeMap.put("WEEKDAY", parts[0]);

        return timeMap;
    }

    static private HashMap DateXXXX_XX_XX(HashMap timeMap, String value) {

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

    static private HashMap TimeXX_XX_XX(HashMap timeMap, String value) {

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

    static private HashMap<String, String> processP(String partP, HashMap<String, String> timeMap) {

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

    static private HashMap<String, String> processT(String partT, HashMap<String, String> timeMap) {

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

    @Override
    public TIMEX3 read(String input) {
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

        
            TIMEX3 res = new TIMEX3();

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    TIMEX3.class.getField(e.getKey()).set(res, m.group(1));
                }
                
                
            }
                if(res.type==null || res.value==null || res.tid==null){
                    return null; // A REQUIRED FIELD WAS NO PROVIDED
                }
        } catch (Exception ex) {
            Logger.getLogger(TIMEX3.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    
}
