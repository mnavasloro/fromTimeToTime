/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.annotationHandler;

import java.util.ArrayList;
import java.util.Collections;
import oeg.ft3.time.tictag.MAP2AnnotationType;


public class FT3Doc {
    
    public String beginIndex;
    public String id;
    public String endIndex;
    public String isString= "";
    public String taggedString= "";
    public ArrayList<Annotation> ann = new ArrayList<Annotation>();
    public MAP2AnnotationType tr = new MAP2AnnotationType();
  
    
    public String toTimeML(){
        int offset = 0;
        taggedString = isString;
        
        Collections.sort(ann, new AnnotationComparator());  
        
        for(Annotation an : ann){
            
            if(!an.standard.equalsIgnoreCase("TimeML")){
                if(an.map!=null){
                    an = tr.toTimeML(an);
                    if(an==null){ //not compliant annotation
                        continue;
                    }
                }
                else{
                    return null;
                }
            }            
                String tag = an.createTag();
                Integer ini = Integer.valueOf(an.beginIndex);
                Integer end = -1;
                if(!an.endIndex.isEmpty()){
                    end = Integer.valueOf(an.endIndex);
                }
                String add = taggedString.substring(0, offset+ini) + tag + an.isString;  
                if(end!=-1){
                    String aux = "</" + an.type + ">";
                    taggedString = add + aux + taggedString.substring(offset+end);
                    offset+=aux.length();
                } else{
                    taggedString = add + taggedString.substring(offset+ini);
                }    
                offset= offset + tag.length();
            }
        
        
        return "<?xml version=\"1.0\" ?><TimeML xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://timeml.org/timeMLdocs/TimeML_1.2.1.xsd\">" 
 + taggedString + "</TimeML>";
    }

    public String toEventsMatter() {
        int offset = 0;
        taggedString = isString;
        
        Collections.sort(ann, new AnnotationComparator());  
        
        Annotation keependevent = null;
        for(Annotation an : ann){
            if(!an.standard.equalsIgnoreCase("EventsMatter")){
                if(an.map!=null){
                    an = tr.toEventsMatter(an);
                    if(an==null){ //not compliant annotation
                        continue;
                    }
                }
                else{
                    continue;
                }
            }  
            if(an.type.equals("Event") && keependevent!=null){
                String aux = "</Event>";
                Integer end = Integer.valueOf(keependevent.endIndex);
                taggedString = taggedString.substring(0,offset+end) + aux + taggedString.substring(offset+end);
                offset+=aux.length();                
                keependevent = an;
                
            }
                String tag = an.createTag();
                Integer ini = Integer.valueOf(an.beginIndex);
                Integer end = Integer.valueOf(an.endIndex);
                
                
                if(!an.type.equals("Event")){
                    String add = taggedString.substring(0, offset+ini) + tag + an.isString;                  
                    String aux = "</" + an.type + ">";
                    taggedString = add + aux + taggedString.substring(offset+end);
                    offset+=aux.length();
                } else{
                    taggedString = taggedString.substring(0, offset+ini) + tag + taggedString.substring(offset+ini);
                    keependevent = an;
                }
                    
                offset= offset + tag.length();
            }
        if(keependevent!=null){
                String aux = "</Event>";
                Integer end = Integer.valueOf(keependevent.endIndex);
                taggedString = taggedString.substring(0,offset+end) + aux + taggedString.substring(offset+end);
                offset+=aux.length();   
                
            }
        
        
        return taggedString;
    }

}

