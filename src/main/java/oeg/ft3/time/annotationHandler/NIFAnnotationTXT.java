package oeg.ft3.time.annotationHandler;

/**
 * Class that stores and outputs as String the information needed for a NIF
 * annotation
 *
 * @author mnavas
 */
public class NIFAnnotationTXT {

    public String header = "";
    public String a = "";
    public String beginIndex = "";
    public String endIndex = "";
    public String isString = "";
    public String referenceContext = "";
    public String value = "";
    public String type = "";

    public String toString() {
        return header + a + beginIndex + endIndex + isString + referenceContext + "\n\n";
    }

}
