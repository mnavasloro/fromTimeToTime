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

/**
 *
 * @author mnavas
 */
public class MAKEINSTANCE implements AnnotationType{

    public String eiid;
    public String eventID;
    public String signalID;
    public String cardinality;
    public String modality;
    public String polarity;
    public String tense;
    public String aspect;
    public String pos;

    public MAKEINSTANCE() {
        
    }
    
    public MAKEINSTANCE(String input) {
        HashMap<String, Pattern> patternsMAKEINSTANCE = new HashMap<String, Pattern>();
    
   
            patternsMAKEINSTANCE.put("eiid", Pattern.compile("eiid=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("eventID", Pattern.compile("eventID=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("signalID", Pattern.compile("signalID=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("cardinality", Pattern.compile("cardinality=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("modality", Pattern.compile("modality=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("polarity", Pattern.compile("polarity=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("tense", Pattern.compile("tense=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("aspect", Pattern.compile("aspect=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("pos", Pattern.compile("pos=\"([^\"]+)\""));
            
        Set<Map.Entry<String, Pattern>> entrySet = patternsMAKEINSTANCE.entrySet();
            
        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    MAKEINSTANCE.class.getField(e.getKey()).set(this, m.group(1));
                }
                
                
            }
        } catch (Exception ex) {
            Logger.getLogger(MAKEINSTANCE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @Override
    public String toString() {
        String ret = "<MAKEINSTANCE eiid=\"" + eiid + "\" eventID=\"" + eventID + "\"";
        if(signalID!=null){
            ret = ret + " signalID=\"" + signalID + "\"";
        }
        if(cardinality!=null){
            ret = ret + " cardinality=\"" + cardinality + "\"";
        }
        if(modality!=null){
            ret = ret + " modality=\"" + modality + "\"";
        }
        if(polarity!=null){
            ret = ret + " polarity=\"" + polarity + "\"";
        }
        if(tense!=null){
            ret = ret + " mod=\"" + tense + "\"";
        }
        if(aspect!=null){
            ret = ret + " aspect=\"" + aspect + "\"";
        }
        if(pos!=null){
            ret = ret + " pos=\"" + pos + "\"";
        }
        
        return ret + "/>";
    }

    @Override
    public HashMap<String, String> process(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MAKEINSTANCE read(String input) {
        HashMap<String, Pattern> patternsMAKEINSTANCE = new HashMap<String, Pattern>();
    
   
            patternsMAKEINSTANCE.put("eiid", Pattern.compile("eiid=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("eventID", Pattern.compile("eventID=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("signalID", Pattern.compile("signalID=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("cardinality", Pattern.compile("cardinality=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("modality", Pattern.compile("modality=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("polarity", Pattern.compile("polarity=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("tense", Pattern.compile("tense=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("aspect", Pattern.compile("aspect=\"([^\"]+)\""));
            patternsMAKEINSTANCE.put("pos", Pattern.compile("pos=\"([^\"]+)\""));
            
        Set<Map.Entry<String, Pattern>> entrySet = patternsMAKEINSTANCE.entrySet();
            
        MAKEINSTANCE res = new MAKEINSTANCE();

        try {
            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    MAKEINSTANCE.class.getField(e.getKey()).set(res, m.group(1));
                }
                
                
            }
                if(res.eiid==null || res.eventID==null){
                    return null; // A REQUIRED FIELD WAS NO PROVIDED
                }
        } catch (Exception ex) {
            Logger.getLogger(MAKEINSTANCE.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
    
}
