import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * StorageHandler takes data from the patients.xml updated by Simulator.
 * It then compiles the data into an ArrayList for other classes to use.
 */
public class StorageHandler {
    /**
     * Values we need to read per patient: 
     *  -Name               
     *  -Room               
     *  -Age                
     *  -Heart Rate         BPM
     *  -Temperature        Celsius
     *  -Blood Oxygen       Percent
     *  -Blood Pressure     (systolic/diastolic) mmHg
     */
    private Timer timer = new Timer();
    public StorageHandler(){
        /**
          * A ticker pulses every 2 seconds to retrieve data from patient.xml.
          * The ticker for StorageHandler must be set at a greater interval than
          * the ticker for Simulator, otherwise we risk drawing the same two
          * values (which isn't detrimental by any means). 
        */
        class ReadXML extends TimerTask{
            public void run(){
                readXML();
                //Uncomment if you would like to see real-time values
                //System.out.println(patientList);//****************For debugging****************
            }
        }
        timer.schedule(new ReadXML(),0,1000);
    }
    /**
     * getPatients
     *  Returns an ArrayList of patients taken from patients.xml
     * @return PatientList
     
    public ArrayList getPatients(){
        return patientList;
    }*/
    /**
     * readXML
     *  Peforms a retrieve on patient.xml. It will then create an ArrayList per patient read. 
     *  Afterwards, this patient will be added to the global ArrayList 'patientList'.
     */
    private synchronized void readXML(){
        try{ //throw exception for ParserConfigurationException
            //Create Document Builder
            File fXmlFile = new File("patient.xml"); //read in patient file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize(); 
            //Read in Patient element from XML files
            NodeList nList = doc.getElementsByTagName("Patient");
            for (int i = 0; i < nList.getLength(); i++){
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    Patient patient = MonitorSystem.patientList.get(i);
                    patient.setName(getElement(eElement, "name"));
                    patient.setRoomNum(Integer.parseInt(getElement(eElement, "room")));
                    patient.setAge(Integer.parseInt(getElement(eElement, "age")));
                    patient.setHeartRate(Integer.parseInt(getElement(eElement, "HeartRate")));
                    patient.setTemperature(Integer.parseInt(getElement(eElement,"Temperature")));   
                    patient.setSystolic(Integer.parseInt(getElement(eElement,"SystolicBP")));
                    patient.setDiastolic(Integer.parseInt(getElement(eElement,"DiastolicBP")));
                    patient.setBloodOxyLvl(Integer.parseInt(getElement(eElement,"BloodOxygen"))); 
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getElement(Element element, String tag)
    {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }
}
