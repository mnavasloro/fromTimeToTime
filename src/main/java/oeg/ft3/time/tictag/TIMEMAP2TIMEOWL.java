/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.FT3Doc;
import oeg.ft3.time.annotationHandler.TIMEX3;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import static org.apache.jena.ontology.OntModelSpec.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author mnavas
 */
public class TIMEMAP2TIMEOWL {

    public String map2time(Annotation an) {
        
            if (an.map != null && an.map.meta.equalsIgnoreCase("TEMPORAL ANNOTATION")) {

                return "ft3:alternativeValue [" + processAnnotation(an) + "]; \r\n" + "\t";
//            anns = anns + "[" + processAnnotation(a) + "],\r\n";    
            }
                return "";
    }

    public String processAnnotation(Annotation a) {
        TIMEMAP aux = new TIMEMAP(a.map);
        if(aux.map!=null){
        a.timemap = aux;
        
        String type = a.timemap.map.get("TYPE");
        
        String id = a.map.map.get("ID");
        
        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/Time_" + id + "> [\r\n" + "\t"
                + " a sem:Time, ";        
        
        if (type.equalsIgnoreCase("DATE")) {
            ini = ini +  "time:GeneralDateTimeDescription ;\r\n" + date(a) + "]; \r\n" + "\t";
            return ini;
        } else if (type.equals("TIME")) {
            ini = ini +  "time:GeneralDateTimeDescription ;\r\n" + time(a) + "]; \r\n" + "\t";
            return ini;
        } else if (type.equalsIgnoreCase("DURATION")) {
            ini = ini +  "time:GeneralDurationDescription ;\r\n" + duration(a) + "]; \r\n" + "\t";
            return ini;
        } else if (type.equalsIgnoreCase("SET-DATE")) {
            ini = ini +  "ft3:RepetitiveTime ;\r\n\t";
            //TIMES
            ini = ini + "ft3:repetitionFrequency [\r\n" + date(a) + "]; \r\n" + "\t";
            if(a.timemap.map.containsKey("TIMEUNIT") && a.timemap.map.containsKey("TIMEAMOUNT")){
            // REPTIMES
            ini = ini + "ft3:repetitionTimes [\r\n" + times(a) + "]; \r\n" + "\t";      
            }
            return ini + "]; \r\n" + "\t";
        } else if (type.equalsIgnoreCase("SET-DURATION")) {
            ini = ini +  "ft3:RepetitiveTime ;\r\n\t";
            //TIMES
            ini = ini + "ft3:repetitionFrequency [\r\n" + duration(a) + "]; \r\n" + "\t";
            if(a.timemap.map.containsKey("TIMEUNIT") && a.timemap.map.containsKey("TIMEAMOUNT")){
            // REPTIMES
            ini = ini + "ft3:repetitionTimes [\r\n" + times(a) + "]; \r\n" + "\t";      
            }
            return ini + "]; \r\n" + "\t";
        }
        }
        return "";

    }

    private String times(Annotation a) {
        
        HashMap<String, String> timeMap = a.timemap.map;
        String outp = "";
        
        if (!timeMap.get("TIMEUNIT").equals("TIMES")) {
            outp = outp + "\t" + " ft3:hasTimeUnit    ft3:" + timeMap.get("TIMEUNIT") + " ;\r\n";
                        
        } else{
            outp = outp + "\t" + " ft3:hasTimeUnit    time:" + timeMap.get("TIMEUNIT") + " ;\r\n";
            
        }
        if(timeMap.get("TIMEAMOUNT").isEmpty()){
            outp = outp + "\t" + " rdf:value         \"1" + "\"^^xsd:nonNegativeInteger ;\r\n";
        } else{
            outp = outp + "\t" + " rdf:value         \"" + timeMap.get("TIMEAMOUNT") + "\"^^xsd:nonNegativeInteger ;\r\n";
        }
        
        
        return outp;
    }
    
    private String duration(Annotation a) {
        
        HashMap<String, String> timeMap = a.timemap.map;
        String outp = "";
        
        double years = 0.0;
        
        // HALFYEAR
        if(timeMap.containsKey("HALFYEAR")){
            years += Double.valueOf(timeMap.get("HALFYEAR"))*0.5;           
        }
        
        // TRIMESTER
        if(timeMap.containsKey("TRIMESTER")){
            years += Double.valueOf(timeMap.get("TRIMESTER"))*0.25;           
        }
        
        // QUARTER
        if(timeMap.containsKey("QUARTER")){
            years += Double.valueOf(timeMap.get("QUARTER"))*0.25;           
        }
        
        // YEAR
        if(timeMap.containsKey("YEAR")){
            years += Double.valueOf(timeMap.get("YEAR"));           
        }
        
        // MILLENIUM
        if(timeMap.containsKey("MILLENIUM")){
            years += Double.valueOf(timeMap.get("MILLENIUM"))*1000;           
        }
        
        // CENTURY
        if(timeMap.containsKey("CENTURY")){
            years += Double.valueOf(timeMap.get("CENTURY"))*100;           
        }
        
        // DECADE
        if(timeMap.containsKey("DECADE")){
            years += Double.valueOf(timeMap.get("DECADE"))*10;           
        }
        
        // MONTH
        if(timeMap.containsKey("MONTH")){
            outp = outp + "\t" + " time:months         \"" + timeMap.get("MONTH") + "\"^^xsd:decimal ;\r\n";
        }
        
        // DAY
        if(timeMap.containsKey("DAY")){
            outp = outp + "\t" + " time:days         \"" + timeMap.get("DAY") + "\"^^xsd:decimal ;\r\n";
        }
        
        // WEEK
        if(timeMap.containsKey("WEEK")){
            outp = outp + "\t" + " time:weeks         \"" + timeMap.get("WEEK") + "\"^^xsd:decimal ;\r\n";
        }
        
        // WEEKDAY
        if(timeMap.containsKey("WEEKDAY")){
                    outp = outp + "\t" + " time:dayOfWeek         time:" + getWeekday(timeMap.get("WEEKDAY")) + " ;\r\n";
                
        }
        
        
        
        // MIN
        if(timeMap.containsKey("MINUTE")){
                    outp = outp + "\t" + " time:minute         " + timeMap.get("MINUTE") + " ;\r\n";
                
        }
        
        // SEC
        if(timeMap.containsKey("SECOND")){
                    outp = outp + "\t" + " time:second         " + timeMap.get("SECOND") + " ;\r\n";
                
        }
        
        
        // HOUR
        if(timeMap.containsKey("HOUR")){
                    outp = outp + "\t" + " time:hour         " + timeMap.get("HOUR") + " ;\r\n";
                
        }
        
        // PARTDAY
        if(timeMap.containsKey("PARTDAY")){//Instant of the Day
                    outp = outp + "\t" + " teo:TEO_0000190         " + getPartDay(timeMap.get("PARTDAY")) + " ;\r\n";
                
        }
        
        if(years!=0.0){ //SUM YEARS
         outp = outp + "\t" + " time:years         \"" + years + "\"^^xsd:decimal ;\r\n";
        }
        
        return outp;
    }

    private String time(Annotation a) {
        
        HashMap<String, String> timeMap = a.timemap.map;
        String value = a.timemap.map.get("VALUE");
        String outp = "";
        
        if (timeMap.containsKey("REF")) {
            outp = outp + "\t" + " ft3:hasTimeRef    ft3:" + timeMap.get("REF") + " ;\r\n";
            return outp;
        }
        
        
        // YEAR
        if(timeMap.containsKey("ERA") && timeMap.get("ERA").equalsIgnoreCase("BC")){
                if(timeMap.containsKey("YEAR")){
                    int year = (Integer.valueOf(timeMap.get("YEAR"))-1)*-1;
                    outp = outp + "\t" + " time:year         \"" + year + "\"^^xsd:gYear ;\r\n";
                }
        } else if(timeMap.containsKey("YEAR")){
                    outp = outp + "\t" + " time:year         \"" + timeMap.get("YEAR") + "\"^^xsd:gYear ;\r\n";
                
        }
        
        // SEASON
        if(timeMap.containsKey("SEASON")){
                    outp = outp + "\t" + " teo:Season         teo:" + getSeason(timeMap.get("SEASON")) + " ;\r\n";
                
        }
        
        // WEEK
        if(timeMap.containsKey("WEEK")){
                    outp = outp + "\t" + " time:week         \"" + timeMap.get("WEEK") + "\"^^xsd:nonNegativeInteger ;\r\n";
                
        }
        
        // WEEKDAY
        if(timeMap.containsKey("WEEKDAY")){
                    outp = outp + "\t" + " time:dayOfWeek         time:" + getWeekday(timeMap.get("WEEKDAY")) + " ;\r\n";
                
        }
                
        // MONTH
        if(timeMap.containsKey("MONTH")){
                    String month = getMonth(timeMap.get("MONTH"));
                    outp = outp + "\t" + " time:monthOfYear         greg:" + month + " ;\r\n"
                    + "\t" + " time:month         \"--" + timeMap.get("MONTH") + "\"^^xsd:gMonth ;\r\n";
                
        }
        
        // DAY
        if(timeMap.containsKey("DAY")){
                    outp = outp + "\t" + " time:day         \"---" + timeMap.get("DAY") + "\"^^xsd:gDay ;\r\n";
                
        }
        
        
        // MIN
        if(timeMap.containsKey("MINUTE")){
                    outp = outp + "\t" + " time:minute         " + timeMap.get("MINUTE") + " ;\r\n";
                
        }
        
        // SEC
        if(timeMap.containsKey("SECOND")){
                    outp = outp + "\t" + " time:second         " + timeMap.get("SECOND") + " ;\r\n";
                
        }
        
        
        // HOUR
        if(timeMap.containsKey("HOUR")){
                    outp = outp + "\t" + " time:hour         " + timeMap.get("HOUR") + " ;\r\n";
                
        }
        
        // PARTDAY
        if(timeMap.containsKey("PARTDAY")){//Instant of the Day
                    outp = outp + "\t" + " teo:TEO_0000190         " + getPartDay(timeMap.get("PARTDAY")) + " ;\r\n";
                
        }
        
        return outp;
        
        
    }

    private String date(Annotation a) {
        HashMap<String, String> timeMap = a.timemap.map;
        String value = a.timemap.map.get("VALUE");
        String outp = "";
        
        if (timeMap.containsKey("REF")) {
            outp = outp + "\t" + " ft3:hasTimeRef    ft3:" + timeMap.get("REF") + " ;\r\n";
            return outp;
        }
        
        
        // YEAR
        if(timeMap.containsKey("ERA") && timeMap.get("ERA").equalsIgnoreCase("BC")){
                if(timeMap.containsKey("YEAR")){
                    int year = (Integer.valueOf(timeMap.get("YEAR"))-1)*-1;
                    outp = outp + "\t" + " time:year         \"" + year + "\"^^xsd:gYear ;\r\n";
                }
        } else if(timeMap.containsKey("YEAR")){
                    outp = outp + "\t" + " time:year         \"" + timeMap.get("YEAR") + "\"^^xsd:gYear ;\r\n";
                
        }
        
        // SEASON
        if(timeMap.containsKey("SEASON")){
                    outp = outp + "\t" + " teo:Season         teo:" + getSeason(timeMap.get("SEASON")) + " ;\r\n";
                
        }
        
        // WEEK
        if(timeMap.containsKey("WEEK")){
                    outp = outp + "\t" + " time:week         \"" + timeMap.get("WEEK") + "\"^^xsd:nonNegativeInteger ;\r\n";
                
        }
        
        // WEEKDAY
        if(timeMap.containsKey("WEEKDAY")){
                    outp = outp + "\t" + " time:dayOfWeek         time:" + getWeekday(timeMap.get("WEEKDAY")) + " ;\r\n";
                
        }
                
        // MONTH
        if(timeMap.containsKey("MONTH")){
                    String month = getMonth(timeMap.get("MONTH"));
                    outp = outp + "\t" + " time:monthOfYear         greg:" + month + " ;\r\n"
                    + "\t" + " time:month         \"--" + timeMap.get("MONTH") + "\"^^xsd:gMonth ;\r\n";
                
        }
        
        // DAY
        if(timeMap.containsKey("DAY")){
                    outp = outp + "\t" + " time:day         \"---" + timeMap.get("DAY") + "\"^^xsd:gDay ;\r\n";
                
        }
        
        //HALFYEAR, TRIMESTER, QUARTER - TODO
        if(timeMap.containsKey("QUARTER")){
                    outp = outp + "\t" + " time:hasDurationDescription          intervals:Q" + timeMap.get("QUARTER") + " ;\r\n";
                
        }
        
        if(timeMap.containsKey("TRIMESTER")){
                    outp = outp + "\t" + " time:hasDurationDescription          intervals:T" + timeMap.get("TRIMESTER") + " ;\r\n";
                
        }
        
        if(timeMap.containsKey("HALFYEAR")){
                    outp = outp + "\t" + " time:hasDurationDescription          intervals:T" + timeMap.get("HALFYEAR") + " ;\r\n";
                
        }
        
        return outp;

    }    

    private String getMonth(String get) {
        switch(get){
            case "01":
            case "1":                
                return "January";
            case "02":
            case "2":                
                return "February";
            case "03":
            case "3":                
                return "March";
            case "04":
            case "4":                
                return "April";
            case "05":
            case "5":                
                return "May";
            case "06":
            case "6":                
                return "June";
            case "11":            
                return "November";
            case "12":                
                return "December";
            case "07":
            case "7":                
                return "July";
            case "08":
            case "8":                
                return "August";
            case "09":
            case "9":                
                return "September";
            case "10":            
                return "October";
        }
        
        return "";
    }

    private String getWeekday(String get) {
        switch(get){
            case "01":
            case "1":                
                return "Monday";
            case "02":
            case "2":                
                return "Tuesday";
            case "03":
            case "3":                
                return "Wednesday";
            case "04":
            case "4":                
                return "Thursday";
            case "05":
            case "5":                
                return "Friday";
            case "06":
            case "6":                
                return "Saturday";
            case "07":
            case "7":                
                return "Sunday";
        }
        
        return "";
    }

    private String getSeason(String get) {
        switch(get){
            case "SU":                
                return "TEO_0000209";
            case "WI":                
                return "TEO_0000210";
            case "SP":                
                return "TEO_0000207";
            case "FA":                
                return "TEO_0000208";
        }
        
        return "";
    }
    
    
    private String getPartDay(String get) {
        switch(get){
            case "MO":                
                return "teo:TEO_0000194";
            case "NO":                
                return "teo:TEO_0000195";
            case "EV":                
                return "teo:TEO_0000196";
            case "AF":                
                return "teo:Afternoon";
            case "NI":                
                return "ft3:NIGHT";
        }
        
        return "";
    }

}
