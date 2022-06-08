/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.annotationHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import oeg.ft3.time.tictag.MAP;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author vroddon
 */
public class JENAReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        String ttl = FileUtils.readFileToString(new File("C:\\Users\\mnavas\\Downloads\\do.ttl"), "UTF-8");
        String ttl = "@prefix nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> .\n" +
"@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .\n" +
"@prefix ft3:   <https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#> .\n" +
"\n" +
"\n" +
"<https://fromtimetotime.linkeddata.es/doc/samples/doc002>\n" +
"	 a nif:Context , ft3:Document ;\n" +
"	 nif:beginIndex  \"0\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"145\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"Six of the pediatric deaths were reported in the last week, and it's possible there will be more, said the CDC's Dr. Michael Jhung said Friday.\n" +
"\n" +
"\"\"\" ;\n" +
" nif:AnnotationUnit    [\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/TIMEX3annotation_t2> [\n" +
"	 a ft3:TIMEX3Annotation ;\n" +
"	 nif:beginIndex  \"45\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"58\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"the last week\"\"\" ;\n" +
"	 ft3:hasTid \"t2\"^^xsd:String;\n" +
"	 ft3:hasValue \"2013-W11\"^^xsd:String;\n" +
"	 ft3:hasType ft3:DATE ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/TIMEX3annotation_t3> [\n" +
"	 a ft3:TIMEX3Annotation ;\n" +
"	 nif:beginIndex  \"136\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"142\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"Friday\"\"\" ;\n" +
"	 ft3:hasTid \"t3\"^^xsd:String;\n" +
"	 ft3:hasValue \"2013-03-22\"^^xsd:String;\n" +
"	 ft3:hasType ft3:DATE ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/EVENTannotation_e1000030> [\n" +
"	 a ft3:EVENTAnnotation ;\n" +
"	 nif:beginIndex  \"21\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"27\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"deaths\"\"\" ;\n" +
"	 ft3:hasEid \"e1000030\"^^xsd:String;\n" +
"	 ft3:hasClass ft3:OCCURRENCE ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/EVENTannotation_e8> [\n" +
"	 a ft3:EVENTAnnotation ;\n" +
"	 nif:beginIndex  \"33\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"41\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"reported\"\"\" ;\n" +
"	 ft3:hasEid \"e8\"^^xsd:String;\n" +
"	 ft3:hasClass ft3:REPORTING ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/EVENTannotation_e9> [\n" +
"	 a ft3:EVENTAnnotation ;\n" +
"	 nif:beginIndex  \"98\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"102\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"said\"\"\" ;\n" +
"	 ft3:hasEid \"e9\"^^xsd:String;\n" +
"	 ft3:hasClass ft3:REPORTING ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/EVENTannotation_e10> [\n" +
"	 a ft3:EVENTAnnotation ;\n" +
"	 nif:beginIndex  \"131\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:endIndex    \"135\"^^xsd:nonNegativeInteger ;\n" +
"	 nif:isString    \"\"\"said\"\"\" ;\n" +
"	 ft3:hasEid \"e10\"^^xsd:String;\n" +
"	 ft3:hasClass ft3:REPORTING ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/MAKEINSTANCEannotation_ei2003>  [\n" +
"	 a ft3:MAKEINSTANCEAnnotation ;\n" +
"	 nif:beginIndex  \"144\"^^xsd:nonNegativeInteger ;\n" +
"	 ft3:hasEiid \"ei2003\"^^xsd:String;\n" +
"	 ft3:hasEventID \"e2003\"^^xsd:String;\n" +
"	 ft3:hasPolarity ft3:POS ;\n" +
"	 ft3:hasTense ft3:NONE ;\n" +
"	 ft3:hasAspect ft3:NONE ;\n" +
"	 ft3:hasPos ft3:NOUN ;\n" +
"	];\n" +
"\n" +
"	\n" +
"	 <https://fromtimetotime.linkeddata.es/doc/samples/doc002/TimeML/MAKEINSTANCEannotation_ei2004>  [\n" +
"	 a ft3:MAKEINSTANCEAnnotation ;\n" +
"	 nif:beginIndex  \"145\"^^xsd:nonNegativeInteger ;\n" +
"	 ft3:hasEiid \"ei2004\"^^xsd:String;\n" +
"	 ft3:hasEventID \"e2004\"^^xsd:String;\n" +
"	 ft3:hasPolarity ft3:POS ;\n" +
"	 ft3:hasTense ft3:NONE ;\n" +
"	 ft3:hasAspect ft3:NONE ;\n" +
"	 ft3:hasPos ft3:NOUN ;\n" +
"	];\n" +
"\n" +
"	] .";
        FT3Doc s = readStringft3Document(ttl);
        System.out.println(s);
    }

    public static FT3Doc readStringft3Document(String ttl) {
        FT3Doc doc = new FT3Doc();
        ArrayList<Annotation> listAnnotation = new ArrayList<Annotation>();

        StringReader reader = new StringReader(ttl);
        Model model = ModelFactory.createDefaultModel();
        model.read(reader, null, "TURTLE");
        ResIterator ri = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#Document"));
        Resource res = ri.next();
        if (res != null) {
            // cogemos las cosas del document
            doc.isString = res.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
            doc.beginIndex = res.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
            doc.endIndex = res.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();

            
            // TIMEX3
            ResIterator ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#TIMEX3Annotation"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "TimeML";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{a.type = "TIMEX3";              
                    TIMEX3 t = processTIMEX3(node, model);
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            // SIGNAL
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#SIGNALAnnotation"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "TimeML";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{a.type = "SIGNAL";                
                    a.annotInfo = processSIGNAL(node, model);  
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#MAKEINSTANCEAnnotation"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "TimeML";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{a.type = "MAKEINSTANCE";                
                    a.annotInfo = processMAKEINSTANCE(node, model);  
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#EVENTAnnotation"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "TimeML";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{a.type = "EVENT";                
                    EVENT t = processEVENT(node, model);
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            
            // EventsMatter
        // ft3:EventsMatterEvent_when
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#EventsMatterEvent_when"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "EventsMatter";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.type = "Event_when";              
                    EventsMatterAnnotation t = processEvent_what(node, model);
                    t.type = "Event_when"; 
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            
            
            // ft3:EventsMatterEvent_who
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#EventsMatterEvent_who"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "EventsMatter";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.type = "Event_who";              
                    EventsMatterAnnotation t = processEventsMatter(node, model);
                    t.type = "Event_who"; 
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            
            // ft3:EventsMatterEvent
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#EventsMatterEvent"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "EventsMatter";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.type = "Event";              
                    EventsMatterAnnotation t = processEventsMatter(node, model);
                    t.type = "Event"; 
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }
            
            
            // ft3:EventsMatterEvent_what
            ni = model.listSubjectsWithProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.createResource("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#EventsMatterEvent_what"));
            while (ni.hasNext()) {
                Annotation a = new Annotation();
                a.standard = "EventsMatter";
                Resource node = ni.next();
                
                try{
                    a.isString = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString")).getString();
                } catch(Exception e){
                    
                } try{
                    a.beginIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.endIndex = node.getProperty(model.createProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex")).getString();
                } catch(Exception e){
                    
                } try{
                    a.type = "Event_what";              
                    EventsMatterAnnotation t = processEventsMatter(node, model);
                    t.type = "Event_what"; 
                    a.annotInfo = t; 
                    a.map = new MAP(t);
                } catch(Exception e){
                    
                }
                listAnnotation.add(a);
            }

        }
        
        
                   
        
        doc.ann = listAnnotation;
        return doc;
    }
    
    public static TIMEX3 processTIMEX3(Resource node, Model model){
        TIMEX3 tim = new TIMEX3();
                
                try{
                    tim.tid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasTid")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.anchorTimeID = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasAnchorTimeID")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.beginPoint = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasBeginPoint")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.endPoint = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasEndPoint")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.quant = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasQuant")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.freq = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasFreq")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.value = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasValue")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                
                tim.type = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasType")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.temporalFunction = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasTemporalFunction")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.mod = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasMod")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.valueFromFunction = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasValueFromFunction")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.functionInDocumentP = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasFunctionInDocument")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }
                
                return tim;
    }
    
    
    
    public static EventsMatterAnnotation processEvent_what(Resource node, Model model){
        EventsMatterAnnotation tim = new EventsMatterAnnotation();
                
                try{
                    tim.tid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasTid")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                 
                } try{
                
                tim.type = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasType")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }
                
                tim.infotimex3 = processTIMEX3(node, model);
                
                
                return tim;
    }
    
    
    
    
    public static MAKEINSTANCE processMAKEINSTANCE(Resource node, Model model){
        MAKEINSTANCE tim = new MAKEINSTANCE();

                
                try{
                    tim.eiid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasEiid")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.eventID = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasEventID")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.signalID = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasSignalID")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.cardinality = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasCardinality")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.modality = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasModality")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                }                 
                
                try{
                
                tim.pos = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasPos")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.tense = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasTense")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.aspect = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasAspect")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }try{
                
                tim.polarity = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasPolarity")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }
                
                return tim;
    }
    
    
    
    
    public static EVENT processEVENT(Resource node, Model model){
        EVENT tim = new EVENT();
                
                try{
                    tim.eid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasEid")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                } try{
                tim.stem = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasStem")).getLiteral().getLexicalForm();
                } catch(Exception e){
                    
                }try{
                
                tim.clas = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasClass")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                    
                }
                
                return tim;
    }
    
    
    
    
    
    
    
    public static SIGNAL processSIGNAL(Resource node, Model model){
        SIGNAL tim = new SIGNAL();
                
                try{
                    tim.sid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasSid")).getLiteral().getLexicalForm();
                } catch(Exception e){
                       
                }
                return tim;
    }
    
    
    public static EventsMatterAnnotation processEventsMatter(Resource node, Model model){
        EventsMatterAnnotation tim = new EventsMatterAnnotation();
                
                try{
                    tim.tid = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasID")).getLiteral().getLexicalForm();
                } catch(Exception e){
                       
                }
                
                try{
                    tim.typeEv = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasType")).getObject().toString().replaceFirst("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#", "");
                } catch(Exception e){
                       
                }
                
                try{
                    tim.prov = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasProv")).getLiteral().getLexicalForm();
                } catch(Exception e){
                       
                }
                
                 try{
                    tim.lemma = node.asResource().getProperty(model.createProperty("https://fromtimetotime.linkeddata.es/ontology/fromtimetotime#hasLemma")).getLiteral().getLexicalForm();
                } catch(Exception e){
                       
                }
                 
                return tim;
    }



}
