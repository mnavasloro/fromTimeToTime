package oeg.core.tagger.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * Servlet that gets a query for the legal graph and transmit it
 * (in order to avoid CORS problems)
 *
 * @author mnavas
 */
public class queryLW extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Takes a text as an input. 
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

        // We get the query
        JSONObject json = new JSONObject(jsonString);
        String input = (String) json.get("query");

        response.setStatus(200);
        response.setContentType("text/plain;charset=UTF-8");

        response.setContentType("text/plain");

        // We send the request to the knowledge graph
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request req = new Request.Builder()
                .url("https://sparqlex.linkeddata.es/sparql?default-graph-uri=&format=json&query=" + URLEncoder.encode(input))
                .method("GET", null)
                .build();
        Response resp = client.newCall(req).execute();
        String response2 = resp.body().string();

        // We send back the response of the knowledge graph
        response.getWriter().println(response2);

    }

    /**
     * Handles the HTTP <code>GET</code> method.
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
