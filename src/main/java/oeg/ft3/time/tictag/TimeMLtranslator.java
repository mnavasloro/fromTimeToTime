/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.tictag;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.EVENT;
import oeg.ft3.time.annotationHandler.MAKEINSTANCE;
import oeg.ft3.time.annotationHandler.SIGNAL;
import oeg.ft3.time.annotationHandler.TIMEX3;

/**
 *
 * @author mnavas
 */
public class TimeMLtranslator {

    public TimeMLtranslator() {
        init();
    }

    public void init() {
        try {

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + ex.toString());
        }

    }

    public ArrayList<Annotation> translateTimeML(String input) {
        ArrayList<Annotation> annstotal = new ArrayList<Annotation>();

        ArrayList<Annotation> anns = fromTXTtoTIMEX(input);
        for (Annotation an : anns) {
            an.standard = "TimeML";
            an.annotInfo = new TIMEX3(an.tag);
            annstotal.add(an);
        }

        anns = fromTXTtoEVENT(input);
        for (Annotation an : anns) {
            an.standard = "TimeML";
            an.annotInfo = new EVENT(an.tag);
            annstotal.add(an);
        }

        anns = fromTXTtoMAKEINSTANCE(input);
        for (Annotation an : anns) {
            an.standard = "TimeML";
            an.annotInfo = new MAKEINSTANCE(an.tag);
//            an.timemap = processMAKEINSTANCE(an.tag);
            annstotal.add(an);
        }

        anns = fromTXTtoSIGNAL(input);
        for (Annotation an : anns) {
            an.standard = "TimeML";
            an.annotInfo = new SIGNAL(an.tag);
//            an.timemap = processSIGNAL(an.tag);
            annstotal.add(an);
        }

//        anns = fromTXTtoALINK(input);        
//        for(Annotation an : anns){
////            an.timemap = processALINK(an.tag);
//            annstotal.add(an);
//        }
//        
//        anns = fromTXTtoTLINK(input);        
//        for(Annotation an : anns){
////            an.timemap = processTLINK(an.tag);
//            annstotal.add(an);
//        }
//        
//        anns = fromTXTtoSLINK(input);        
//        for(Annotation an : anns){
////            an.timemap = processSLINK(an.tag);
//            annstotal.add(an);
//        }
        return annstotal;
    }

    private ArrayList<Annotation> fromTXTtoTIMEX(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?TIMEX[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<TIMEX3 [^>]*>)([^<]*)<\\/TIMEX3>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "TIMEX3";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    TIMEX3 t = new TIMEX3(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    res.add(ann);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO TIMEX");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoEVENT(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?EVENT[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<EVENT [^>]*>)([^<]*)<\\/EVENT>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "EVENT";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    EVENT t = new EVENT(ann.tag);
                    ann.annotInfo = t;
                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO EVENT");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoMAKEINSTANCE(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?MAKEINSTANCE[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<MAKEINSTANCE [^>]*\\/>)";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "MAKEINSTANCE";
                    int end = (m.start() + m.group(1).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = "";
                    res.add(ann);
                    MAKEINSTANCE t = new MAKEINSTANCE(ann.tag);
                    ann.annotInfo = t;
//                    ann.map = new MAP(t);

                    m.appendReplacement(sb, "");
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO MAKEINSTANCE");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoSIGNAL(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?SIGNAL[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<SIGNAL [^>]*>)([^<]*)<\\/SIGNAL>";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "SIGNAL";
                    int end = (m.start() + m.group(2).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = m.group(2);
                    res.add(ann);
                    SIGNAL t = new SIGNAL(ann.tag);
                    ann.annotInfo = t;
//                    ann.map = new MAP(t);

                    m.appendReplacement(sb, m.group(2));
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO SIGNAL");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoTLINK(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?TLINK[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<TLINK [^>]*\\/>)";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "TLINK";
                    int end = (m.start() + m.group(1).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = "";
                    res.add(ann);

                    m.appendReplacement(sb, "");
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO TLINK");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoALINK(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?ALINK[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<ALINK [^>]*\\/>)";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "ALINK";
                    int end = (m.start() + m.group(1).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = "";
                    res.add(ann);

                    m.appendReplacement(sb, "");
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO ALINK");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ArrayList<Annotation> fromTXTtoSLINK(String input) {
        ArrayList<Annotation> res = new ArrayList<Annotation>();
        try {
            String inp2 = input;
            // Clean all other tags
            inp2 = inp2.replaceAll("(?!<\\/?SLINK[^>]*>)<\\/?[^>]*>", "");

            String pattern = "(<SLINK [^>]*\\/>)";
            Pattern p = Pattern.compile(pattern);
            while (!inp2.isEmpty()) {
                Matcher m = p.matcher(inp2);
                StringBuffer sb = new StringBuffer(inp2.length());
                if (m.find()) {
                    Annotation ann = new Annotation();
                    ann.type = "SLINK";
                    int end = (m.start() + m.group(1).length());
                    ann.tag = m.group(1);
                    ann.beginIndex = String.valueOf(m.start());
                    ann.endIndex = String.valueOf(end);
                    ann.isString = "";
                    res.add(ann);

                    m.appendReplacement(sb, "");
                    m.appendTail(sb);
                    inp2 = sb.toString();
                } else {

                    return res;
                }
            }

            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, "ERROR FROM TXT TO SLINK");
            return null;

        } catch (Exception ex) {
            Logger.getLogger(TimeMLtranslator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
