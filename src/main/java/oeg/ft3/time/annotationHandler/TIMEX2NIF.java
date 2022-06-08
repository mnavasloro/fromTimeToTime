package oeg.ft3.time.annotationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.rdf.model.ResourceFactory;
import org.slf4j.LoggerFactory;

/**
 * Class that converts a TIMEX annotation into a NIF annotation
 *
 * @author mnavas
 */
public class TIMEX2NIF {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TIMEX2NIF.class);

    List<Annotation> listAnnotations = new ArrayList<Annotation>();


    /**
     * Converts a sentence @intput in TIMEX format into NIF
     *
     * @param input String in TIMEX format
     * @param lynxDocument LynxDocument with base nif
     * @return
     */
//    public LynxDocument insert2ExistingNIF(String input, LynxDocument lynxDocument) {
//        try {
//            String inp2 = input;
////            if(inp2.contains("\\n")){
////                inp2 = inp2.replaceAll("\\\\[rnt]", "n");
////            }
//            String pattern = "<TIMEX3 tid=\"([^\"]+)\" type=\"([^\"]+)\" value=\"([^\"]+)\"[^>]*>([^<]*)<\\/TIMEX3>";
//            Pattern p = Pattern.compile(pattern);
//            while (!inp2.isEmpty()) {
//
//                Matcher m = p.matcher(inp2);
//                StringBuffer sb = new StringBuffer(inp2.length());
//                if (m.find()) {
//                    int end = (m.start() + m.group(4).length());
//                    LynxAnnotation ann = new LynxAnnotation();
//                    ann.offset_ini = m.start();
//                    ann.offset_end = end;
//                    ann.referenceContext = lynxDocument.getId();
//                    ann.anchorOf = m.group(4);
//
//                    String timexAnn = ResourceFactory.createResource("http://annotador.oeg-upm.net/").getURI();
//
////	        ann.id = ann.referenceContext + "#offset_"+ ann.offset_ini+"_"+ ann.offset_end;
//                    LynxAnnotationUnit au = new LynxAnnotationUnit();
//                    au.annotationProperties.put(LynxAnnotationUnit.CLASSREF, "lkg:" + m.group(2));
//                    au.annotationProperties.put(LynxAnnotationUnit.ANNOTATORSREF, timexAnn);
//                    au.annotationProperties.put(LynxAnnotationUnit.CONFIDENCE, "0.9");
//                    au.annotationProperties.put("rdf:value", m.group(3));
//
//                    ann.annotationUnit.add(au);
//                    lynxDocument.addAnnotation(ann);
//                    m.appendReplacement(sb, m.group(4));
//                    m.appendTail(sb);
//                    inp2 = sb.toString();
//                } else {
//
//                    return lynxDocument;
//                }
//            }
//
//            Logger.getLogger(TIMEX2NIF.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO TIMEX");
//            return null;
//
//        } catch (Exception ex) {
//            Logger.getLogger(TIMEX2NIF.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

}
