import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;
import java.text.*;

public class Simulator {
    /**
     * These are the default minimum and maximum bounds set. Simulator
     * draws random values between these values to construct vital signs.
     */
    private static final int HRMIN = 50;     //heart rate min
    private static final int HRMAX = 100;    //heart rate max
    private static final int BPMIN = 60;     //blood pressure min
    private static final int BPMAX = 139;    //blood pressure max
    private static final int TPMIN = 35;     //temperature min
    private static final int TPMAX = 38;     //temperature max
    private static final int BOMIN = 90;     //blood oxygen min
    private static final int BOMAX = 100;    //blood oxygen max
    private Timer timer = new Timer();
    public Simulator() {
        /**
         * This ticker will update patient.xml every 1 second. It can be
         * set lower if desired. The ticker in StorageHandler must be set
         * at a greater interval or the StorageHandler will draw identical values.
         */
        class BuildPatient extends TimerTask{
            public void run(){
                buildPatient();
            }
        }
        timer.schedule(new BuildPatient(),0,500);
    }
    /**
     * buildPatient
     *  Constructs a patient, who's name, age, and room are taken from predefined array,
     *  (can add and remove).
     * @param 
     *  name    - name of the patient
     *  age     - age of the patient
     *  room    - room the patient resides
     */
    private synchronized void buildPatient() {
        DecimalFormat df = new DecimalFormat("#.00");
        Random rand = new Random();
        DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbF.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement
                    = doc.createElement("Patients");
            //append root element to document
            doc.appendChild(rootElement);
            //create 5 patients
            for (int i = 0; i < MonitorSystem.patientList.size(); i++) {
                //Blood pressure "X BPM"
                String heartRate = Integer.toString(rand.nextInt((HRMAX - HRMIN) + 1) + HRMIN); 
                //Heart rate "X °C"
                String temperature = Integer.toString(rand.nextInt((TPMAX - TPMIN)) + TPMIN); 
                //Blood pressure "X/Y mmHg"
                String systolic = Integer.toString(rand.nextInt((BPMAX - BPMIN) + 1) + BPMIN);
                String diastolic = Integer.toString(rand.nextInt((BPMAX - BPMIN) + 1) + BPMIN);
                //Blood Oxy "X%"
                String bloodOxy = Integer.toString((rand.nextInt((BOMAX - BOMIN) + 1) + BOMIN));
                //Create an XML element of patient
                rootElement.appendChild(getPatient(doc, i, heartRate, temperature, systolic, diastolic, bloodOxy));
            }
            //for output to file
            TransformerFactory transformerF = TransformerFactory.newInstance();
            Transformer transformer = transformerF.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            //write to file
            StreamResult file = new StreamResult(new File("patient.xml"));
            //write data
            transformer.transform(source, file);
            //System.out.println("XML File has been created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getPatient
     *  Takes randomized patient attributes and creates an XML element to add to
     *  and XML document. 
     * @param
     *  doc
     *  id          - patient ID
     *  name        - patient name
     *  room        - patient room
     *  age         - patient age
     *  heart       - heart rate
     *  temp        - temperature status
     *  bloodPsr    - blood pressure reading   
     *  bloodOxy    - blood oxygen reading
     * @return 
     *  patient     - the fully constructed XML element to be added to the file
     */
    private Node getPatient(
            Document doc,
            int id,
            String heart,
            String temp,
            String systolicBP,
            String diastolicBP,
            String bloodOxy) {
        Patient pInfo = MonitorSystem.patientList.get(id);
        Element patient = doc.createElement("Patient");
        patient.setAttribute("number", String.valueOf(id + 1));
        patient.appendChild(getPatientElements(doc, "name", pInfo.getName()));    //set age
        patient.appendChild(getPatientElements(doc, "room", Integer.toString(pInfo.getRoom())));    //set room
        patient.appendChild(getPatientElements(doc, "age", Integer.toString(pInfo.getAge())));     //set age
        patient.appendChild(getPatientElements(doc, "HeartRate", heart));  //ste heart rate
        patient.appendChild(getPatientElements(doc, "Temperature", temp)); //set temp
        patient.appendChild(getPatientElements(doc, "SystolicBP", systolicBP)); //set blood pressue
        patient.appendChild(getPatientElements(doc, "DiastolicBP", diastolicBP)); 
        patient.appendChild(getPatientElements(doc, "BloodOxygen", bloodOxy)); //set blood oxygen
        return patient;
    }

    // create text node
    private Node getPatientElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
