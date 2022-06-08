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
public class EVENT implements AnnotationType{

    public String eid;
    public String stem;
    public String clas;

    
    public EVENT(){
    }
    
    public EVENT(String input){
        HashMap<String, Pattern> patternsEVENT = new HashMap<String, Pattern>();
    
            patternsEVENT.put("eid", Pattern.compile("eid=\"([^\"]+)\""));
            patternsEVENT.put("stem", Pattern.compile("stem=\"([^\"]+)\""));
            patternsEVENT.put("clas", Pattern.compile("class=\"([^\"]+)\""));
            Set<Map.Entry<String, Pattern>> entrySet = patternsEVENT.entrySet();

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    EVENT.class.getField(e.getKey()).set(this, m.group(1));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EVENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EVENT(MAP mape){
        HashMap<String, String> map = mape.map;
        if(map.containsKey("TYPE")){
            this.clas = map.get("TYPE");
        }
        if(map.containsKey("ID")){
            this.eid = map.get("ID");
        }
        if(map.containsKey("STEM")){
            this.stem = map.get("STEM");
        }
    }
    
    
    @Override
    public String toString() {
        if(stem!=null){
            return "<EVENT " + "eid=\"" + eid + "\" stem=\"" + stem + "\" class=\"" + clas + "\">";
        }
        return "<EVENT " + "eid=\"" + eid + "\" class=\"" + clas + "\">";
    }
    
    
    @Override
    public HashMap<String, String> process(String input) {
        EVENT timex = read(input);
        HashMap<String, String> timeMap = new HashMap<String, String>();

        try {
                timeMap.put("TYPE", timex.clas);
                timeMap.put("ID", timex.eid);
                if(timex.stem!=null && !timex.stem.isEmpty()){
                    timeMap.put("STEM", timex.stem);
                }
                return timeMap;
        } catch (Exception ex) {
            Logger.getLogger(EVENT.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    @Override
    public EVENT read(String input) {
        EVENT res = new EVENT();
        HashMap<String, Pattern> patternsEVENT = new HashMap<String, Pattern>();
    
            patternsEVENT.put("eid", Pattern.compile("eid=\"([^\"]+)\""));
            patternsEVENT.put("stem", Pattern.compile("stem=\"([^\"]+)\""));
            patternsEVENT.put("clas", Pattern.compile("class=\"([^\"]+)\""));
            Set<Map.Entry<String, Pattern>> entrySet = patternsEVENT.entrySet();

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    EVENT.class.getField(e.getKey()).set(res, m.group(1));
                }
            }
                if(res.clas==null || res.eid==null){
                    return null; // A REQUIRED FIELD WAS NO PROVIDED
                }
        } catch (Exception ex) {
            Logger.getLogger(EVENT.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    
    
}
