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
public class SIGNAL implements AnnotationType {

    public String sid;
    
    public SIGNAL() {

    }

    public SIGNAL(String input) {
        Pattern p = Pattern.compile("sid=\"([^\"]+)\"");
        Matcher m = p.matcher(input);
        if (m.find()) {
            this.sid = m.group(1);
        }

    }

    @Override
    public String toString() {
        return "<SIGNAL " + "sid=\"" + sid + "\">";
    }

    @Override
    public HashMap<String, String> process(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SIGNAL read(String input) {
        try {
            HashMap<String, Pattern> patternsSIGNAL = new HashMap<String, Pattern>();

            patternsSIGNAL.put("sid", Pattern.compile("sid=\"([^\"]+)\""));
            Set<Map.Entry<String, Pattern>> entrySet = patternsSIGNAL.entrySet();

            SIGNAL res = new SIGNAL();

            for (Map.Entry<String, Pattern> e : entrySet) {
                Pattern p = e.getValue();
                Matcher m = p.matcher(input);
                if (m.find()) {
                    SIGNAL.class.getField(e.getKey()).set(res, m.group(1));
                }

            }
            if (res.sid == null) {
                return null; // A REQUIRED FIELD WAS NO PROVIDED
            }

            return res;
        } catch (Exception ex) {
            Logger.getLogger(SIGNAL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
