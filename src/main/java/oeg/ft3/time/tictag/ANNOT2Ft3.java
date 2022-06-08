/*
 * Annotation to ft3
 */
package oeg.ft3.time.tictag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.MAKEINSTANCE;
import oeg.ft3.time.annotationHandler.SIGNAL;
import oeg.ft3.time.annotationHandler.TIMEX3;
import oeg.ft3.time.annotationHandler.EVENT;
import oeg.ft3.time.annotationHandler.EventsMatterAnnotation;

/**
 *
 * @author mnavas
 */
public class ANNOT2Ft3 {

    HashMap<String, String> clTIMEX3 = new HashMap<String, String>();
    HashMap<String, String> clFT3 = new HashMap<String, String>();
    HashMap<String, String> clMAKEINSTANCE = new HashMap<String, String>();
    HashMap<String, String> clMAKEFT3 = new HashMap<String, String>();

    int annotationCounter = 0;

    TIMEMAP2TIMEOWL tm2time = new TIMEMAP2TIMEOWL();

    public ANNOT2Ft3() {

        try {

            clTIMEX3.put("tid", "ft3:hasTid");

            clTIMEX3.put("anchorTimeID", "ft3:hasAnchorTimeID");
            clTIMEX3.put("beginPoint", "ft3:hasBeginPoint");
            clTIMEX3.put("endPoint", "ft3:hasEndPoint");
            clTIMEX3.put("quant", "ft3:hasQuant");
            clTIMEX3.put("freq", "ft3:hasFreq");
            clTIMEX3.put("value", "ft3:hasValue");

            clMAKEINSTANCE.put("eiid", "ft3:hasEiid");
            clMAKEINSTANCE.put("eventID", "ft3:hasEventID");
            clMAKEINSTANCE.put("signalID", "ft3:hasSignalID");
            clMAKEINSTANCE.put("cardinality", "ft3:hasCardinality");
            clMAKEINSTANCE.put("modality", "ft3:hasModality");

            clFT3.put("type", "ft3:hasType");
            clFT3.put("functionInDocument", "ft3:hasFunctionInDocument");
            clFT3.put("temporalFunction", "ft3:hasTemporalFunction");
            clFT3.put("valueFromFunction", "ft3:hasValueFromFunction");
            clFT3.put("mod", "ft3:hasMod");

            clMAKEFT3.put("pos", "ft3:hasPos");
            clMAKEFT3.put("tense", "ft3:hasTense");
            clMAKEFT3.put("aspect", "ft3:hasAspect");
            clMAKEFT3.put("polarity", "ft3:hasPolarity");

        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String map2ft3(ArrayList<Annotation> timemap, String input, String time, String id) {
        // We get type
        String ini = "@prefix nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> .\r\n"
                + "@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .\r\n"
                + "@prefix sem:   <http://semanticweb.cs.vu.nl/2009/11/sem/#> .\r\n"
                + "@prefix time:   <http://www.w3.org/2006/time#> .\r\n"
                + "@prefix teo:   <https://sbmi.uth.edu/ontology/TEO.owl#> .\r\n"
                + "@prefix intervals:   <http://reference.data.gov.uk/def/intervals/> .\r\n"
                + "@prefix greg:   <http://www.w3.org/ns/time/gregorian#> .\r\n"
                + "@prefix ft3:   <https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#> .\r\n\r\n\r\n"
                + "<https://fromtimetotime.linkeddata.es/doc/samples/doc002>\r\n" + "\t"
                + " a nif:Context , ft3:Document ;\r\n" + "\t"
                + " nif:beginIndex  \"0\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:title    \"" + id + "\"^^xsd:String ;\r\n" + "\t"
                + " nif:endIndex    \"" + input.length() + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + input + "\"\"\" ;\r\n";

        String anns = "";

        if (!timemap.isEmpty()) {
            anns = anns + " nif:AnnotationUnit    [\r\n\t";

            for (Annotation a : timemap) {
                anns = anns + processAnnotation(a) + "\r\n" + "\t";
                if (time.equalsIgnoreCase("TIME")) {
                    anns = anns + tm2time.map2time(a);
                }
                anns += "];\r\n" + "\t";
            }
            anns = anns + "] ; ";

            // Check event
            if (time.equalsIgnoreCase("EVENT")) {
                anns = anns + "\r\n\t";
                ANNOT2EVENT a2ev = new ANNOT2EVENT();

                for (Annotation a : timemap) {
                    anns = anns + a2ev.map2eventEventsMatter(a, timemap) + "\r\n" + "\t";
                }
            }
            
            anns = anns.substring(0,anns.lastIndexOf(";")) + ".";

            String output = ini + anns;
            return output;
        }

        String output = ini + anns;
        output = output.substring(0, output.lastIndexOf(";")) + '.';
        return output;

    }

    public String processAnnotation(Annotation a) {
        annotationCounter++;
        if (a.type.equalsIgnoreCase("TIMEX3")) {
            return processTIMEXAnnotation(a);
        } else if (a.type.equals("EVENT")) {
            return processEVENTAnnotation(a);
        } else if (a.type.equalsIgnoreCase("SIGNAL")) {
            return processSIGNALAnnotation(a);
//        } else if(a.type.equalsIgnoreCase("TLINK")){
//            return processTLINKAnnotation(a);
//        } else if(a.type.equalsIgnoreCase("SLINK")){
//            return processSLINKAnnotation(a);
//        } else if(a.type.equalsIgnoreCase("ALINK")){
//            return processALINKAnnotation(a);
        } else if (a.type.equalsIgnoreCase("MAKEINSTANCE")) {
            return processMAKEINSTANCEAnnotation(a);
        } else if (a.type.equalsIgnoreCase("Event_who")) {
            return processEvent_WhoAnnotation(a);
        } else if (a.type.equalsIgnoreCase("Event_what")) {
            return processEvent_WhatAnnotation(a);
        } else if (a.type.equalsIgnoreCase("Event_when")) {
            return processEvent_WhenAnnotation(a);
        } else if (a.type.equals("Event")) {
            return processEventAnnotation(a);
        }

        return "";

    }

    public String processTIMEX(TIMEX3 timex) {
        String output = "";
        try {
            Field[] fields = TIMEX3.class.getFields();
            for (Field field : fields) {
                if (field.get(timex) != null) {
                    if (clFT3.containsKey(field.getName())) {
                        output = output + " " + clFT3.get(field.getName()) + " ft3:" + field.get(timex) + " ;\r\n\t";
                    } else if (clTIMEX3.containsKey(field.getName())) {
                        output = output + " " + clTIMEX3.get(field.getName()) + " \"" + field.get(timex) + "\"^^xsd:String;\r\n\t";
                    }
                }
            }

            return output;
        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    private String processEvent_WhenAnnotation(Annotation a) {

        String tag = a.tag;
        EventsMatterAnnotation readTIMEX3 = new EventsMatterAnnotation();
        readTIMEX3 = readTIMEX3.read(tag);
        String processTIMEX = processTIMEX(readTIMEX3.infotimex3);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/EventsMatter/Event_whenannotation_" + readTIMEX3.tid + "_" + annotationCounter + "> [\r\n" + "\t"
                + " a ft3:EventsMatterEvent_when ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " ft3:hasID    \"" + readTIMEX3.tid + "\"^^xsd:String ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processTIMEX;
        return output;

    }

    private String processEvent_WhoAnnotation(Annotation a) {

        String tag = a.tag;
        EventsMatterAnnotation readTIMEX3 = new EventsMatterAnnotation();
        readTIMEX3 = readTIMEX3.read(tag);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/EventsMatter/Event_whoannotation_" + readTIMEX3.tid + "_" + annotationCounter + "> [\r\n" + "\t"
                + " a ft3:EventsMatterEvent_who ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " ft3:hasID    \"" + readTIMEX3.tid + "\"^^xsd:String ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + "\r\n";
        return output;

    }

    private String processTIMEXAnnotation(Annotation a) {

        String tag = a.tag;
        TIMEX3 readTIMEX3 = new TIMEX3();
        readTIMEX3 = readTIMEX3.read(tag);
        String processTIMEX = processTIMEX(readTIMEX3);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/TIMEX3annotation_" + readTIMEX3.tid + "> [\r\n" + "\t"
                + " a ft3:TIMEX3Annotation ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processTIMEX;
        return output;

    }

    public String processMAKEINSTANCE(MAKEINSTANCE makeins) {
        String output = "";
        try {
            Field[] fields = MAKEINSTANCE.class.getFields();
            for (Field field : fields) {
                if (field.get(makeins) != null) {
                    if (clMAKEFT3.containsKey(field.getName())) {
                        output = output + " " + clMAKEFT3.get(field.getName()) + " ft3:" + field.get(makeins) + " ;\r\n\t";
                    } else if (clMAKEINSTANCE.containsKey(field.getName())) {
                        output = output + " " + clMAKEINSTANCE.get(field.getName()) + " \"" + field.get(makeins) + "\"^^xsd:String;\r\n\t";
                    }
                }
            }

            return output;
        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    private String processMAKEINSTANCEAnnotation(Annotation a) {

        String tag = a.tag;
        MAKEINSTANCE readMAKEINSTANCE = new MAKEINSTANCE();
        readMAKEINSTANCE = readMAKEINSTANCE.read(tag);
        String processmakeins = processMAKEINSTANCE(readMAKEINSTANCE);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/MAKEINSTANCEannotation_" + readMAKEINSTANCE.eiid + ">  [\r\n" + "\t"
                + " a ft3:MAKEINSTANCEAnnotation ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t";

        String output = ini + processmakeins;
        return output;

    }

    private String processEVENTAnnotation(Annotation a) {

        String tag = a.tag;
        EVENT readEVENT = new EVENT();
        readEVENT = readEVENT.read(tag);
        String processEVENT = processEVENT(readEVENT);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/EVENTannotation_" + readEVENT.eid + "> [\r\n" + "\t"
                + " a ft3:EVENTAnnotation ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processEVENT;
        return output;

    }

    public String processEVENT(EVENT timemlev) {
        String output = "";
        try {
            if (timemlev.eid != null) {
                output = output + " " + "ft3:hasEid" + " \"" + timemlev.eid + "\"^^xsd:String;\r\n\t";
            }
            if (timemlev.clas != null) {
                output = output + " " + "ft3:hasClass" + " ft3:" + timemlev.clas + " ;\r\n\t";
            }
            if (timemlev.stem != null) {
                output = output + " " + "ft3:hasStem" + " \"" + timemlev.stem + "\"^^xsd:String;\r\n\t";
            }

            return output;
        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    public String processEventsMatter(EventsMatterAnnotation eman) {
        String output = "";
        try {
            if (eman.typeEv != null) {
                output = output + " " + "ft3:hasType" + " ft3:" + eman.typeEv + " ;\r\n\t";
            }
            if (eman.prov != null) {
                output = output + " " + "ft3:hasProv" + " \"" + eman.prov + "\"^^xsd:String;\r\n\t";
            }
            if (eman.lemma != null) {
                output = output + " " + "ft3:hasLemma" + " \"" + eman.lemma + "\"^^xsd:String;\r\n\t";
            }

            return output;
        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    private String processSIGNALAnnotation(Annotation a) {

        String tag = a.tag;
        SIGNAL readSIGNAL = new SIGNAL();
        readSIGNAL = readSIGNAL.read(tag);
        String processSIGNAL = processSIGNAL(readSIGNAL);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/SIGNALannotation_" + readSIGNAL.sid + "> [\r\n" + "\t"
                + " a ft3:SIGNALAnnotation ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processSIGNAL;
        return output;

    }

    public String processSIGNAL(SIGNAL sig) {
        String output = "";
        try {
            if (sig.sid != null) {
                output = output + " " + "ft3:hasSid" + " \"" + sig.sid + "\"^^xsd:String;\r\n\t";
            }

            return output;
        } catch (Exception ex) {
            Logger.getLogger(ANNOT2Ft3.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during translation: " + ex.toString());
        }

        return null;
    }

    private String processEvent_WhatAnnotation(Annotation a) {
        String tag = a.tag;
        EventsMatterAnnotation readTIMEX3 = new EventsMatterAnnotation();
        readTIMEX3 = readTIMEX3.read(tag);
        String processTIMEX = processEventsMatter(readTIMEX3);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/EventsMatter/Event_whatannotation_" + readTIMEX3.tid + "_" + annotationCounter + "> [\r\n" + "\t"
                + " a ft3:EventsMatterEvent_what ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " ft3:hasID    \"" + readTIMEX3.tid + "\"^^xsd:String ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processTIMEX;
        return output;
    }

    private String processEventAnnotation(Annotation a) {
        String tag = a.tag;
        EventsMatterAnnotation readTIMEX3 = new EventsMatterAnnotation();
        readTIMEX3 = readTIMEX3.read(tag);
        String processTIMEX = processEventsMatter(readTIMEX3);

        String ini = "\r\n" + "\t <https://fromtimetotime.linkeddata.es/doc/samples/doc002/EventsMatter/Eventannotation_" + readTIMEX3.tid + "_" + annotationCounter + "> [\r\n" + "\t"
                + " a ft3:EventsMatterEvent ;\r\n" + "\t"
                + " nif:beginIndex  \"" + a.beginIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " nif:endIndex    \"" + a.endIndex + "\"^^xsd:nonNegativeInteger ;\r\n" + "\t"
                + " ft3:hasID    \"" + readTIMEX3.tid + "\"^^xsd:String ;\r\n" + "\t"
                + " nif:isString    \"\"\"" + a.isString + "\"\"\" ;\r\n\t";

        String output = ini + processTIMEX;
        return output;
    }

}
