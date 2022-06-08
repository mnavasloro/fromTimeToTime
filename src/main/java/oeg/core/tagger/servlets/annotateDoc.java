package oeg.core.tagger.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oeg.ft3.time.annotationHandler.Annotation;
import oeg.ft3.time.annotationHandler.FT3Doc;
import oeg.ft3.time.annotationHandler.JENAReader;
import oeg.ft3.time.tictag.EventsMattertranslator;
import oeg.ft3.time.tictag.ANNOT2Ft3;
import oeg.ft3.time.tictag.TimeMLtranslator;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * Servlet that returns the javascript needed for the BRAT visualization of the
 * webpage
 *
 * @author mnavas
 */
public class annotateDoc extends HttpServlet {
    
    
    static ANNOT2Ft3 m2ft3 = new ANNOT2Ft3();
    static EventsMattertranslator trem = new EventsMattertranslator();
    static TimeMLtranslator tr = new TimeMLtranslator();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Takes a text as an input. Offers the same text: a) either
     * removing the legal references b)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");

        // We get the parameters
        request.setCharacterEncoding("UTF-8");

        String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");


        JSONObject json = new JSONObject(jsonString);
        String input = (String) json.get("inputText");
        String inputF = (String) json.get("inputF");
        String outputF = (String) json.get("outputF");
        
        if(inputF.equalsIgnoreCase(outputF)){
            response.setStatus(201);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("SAME FORMAT");
        }


        response.setStatus(200);
        response.setContentType("text/html;charset=UTF-8");

        // We call the tagger and return its output
        System.out.println("----------\n" + input);
        String salida = transform(input, inputF, outputF);
        response.setContentType("text/plain");
        response.getWriter().println(salida);

    }
    
    
    public String toFT3(String input){
        
        System.out.println("----------\n" + input);
        String salida = transform(input, "EventsMatter", "ft3+events");
        
        return salida;

    }
    
    public static String transform(String txt, String inpF, String outpF) {

        System.out.println("To tag: " + txt);
        String outp2 = "";
        FT3Doc doc = new FT3Doc();
        ArrayList<Annotation> outp = new ArrayList<Annotation>();
        try {
            
            /* INPUT FORMAT */
            if(inpF.equalsIgnoreCase("TimeML")){ //DONE
                doc.ann = tr.translateTimeML(txt);
                doc.isString = txt.replaceAll("<[^>]*>","");
            } else if(inpF.equalsIgnoreCase("EventsMatter")){
                doc.ann = trem.translateEventsMatter(txt);
                doc.isString = txt.replaceAll("<[^>]*>","");
            } else if(inpF.equalsIgnoreCase("ft3")){
                doc = JENAReader.readStringft3Document(txt);
            }
            
            
            
        String id = "X";
        
            /* OUTPUT FORMAT */
            if(outpF.equalsIgnoreCase("ft3")){ //DONE
                outp2 = m2ft3.map2ft3(doc.ann,doc.isString,"FALSE",id);
            } else if(outpF.equalsIgnoreCase("ft3+time")){
                outp2 = m2ft3.map2ft3(doc.ann,doc.isString,"TIME",id);
            } else if(outpF.equalsIgnoreCase("ft3+events")){
                outp2 = m2ft3.map2ft3(doc.ann,doc.isString,"EVENT",id);
            } else if(outpF.equalsIgnoreCase("EventsMatter")){
                outp2 = doc.toEventsMatter();
            } else if(outpF.equalsIgnoreCase("TimeML")){                
                outp2 = doc.toTimeML();
            }
            
            
            return outp2; // We return the javascript with the values to evaluate
        } catch (Exception ex) {
            System.err.print(ex.toString());
            return "";
        }
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
