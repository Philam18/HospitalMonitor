import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Patient{

 private String name;
 private int roomNum;
 private float temperature;
 private int heartRate;
 private int systolicBP;
 private int diastolicBP;
 private int bloodOxyLvl;
 private int age;
 private PatientBox displayBox;
 
 public Patient(String name, int roomNum, int age)
 {
     this(name, roomNum, age, 0,0,0,0,0);
 }
 /**
  * 
  * @param name
  * @param roomNum
  * @param temperature
  * @param heartRate
  * @param bloodPreesure
  * @param bloodOxyLvl
  * @param age
  */
 public Patient(String name, int roomNum, int age, float temperature, int heartRate, int systolicBP, int diastolicBP, int bloodOxyLvl){
 
  this.name = name;
  this.roomNum = roomNum;
  this.heartRate = heartRate;
  this.systolicBP = systolicBP;
  this.diastolicBP = diastolicBP;
  this.bloodOxyLvl = bloodOxyLvl;
  this.age = age;
  displayBox = new PatientBox(this);
 }
 public void update()
 {
     displayBox.update(this);
 }
 public void remove()
 {
     MonitorSystem.patientList.remove(this);
     MonitorSystem.mainPanel.remove(displayBox);
 }
 //Setters
 
 public void setName(String name){
  this.name = name;
 }
 
 public void setRoomNum(int room){
  roomNum = room;
 }
 
 /**
  *  Sets new temperature
  * @param temperature
  */
 public void setTemperature(float temperature){
  this.temperature = temperature;
 }
 
 /**
  * Sets new heartRate
  * @param heartRate
  */
 public void setHeartRate(int heartRate){
  this.heartRate = heartRate;
 }
  /**
  * Sets new Systolic Blood Pressure
  * @param bloodPressure
  */
 public void setSystolic(int bloodPressure){
  this.systolicBP = bloodPressure;
 }
 
 /**
  * Sets new Diastolic Blood Pressure
  * @param bloodPressure
  */
 public void setDiastolic(int bloodPressure){
  this.diastolicBP = bloodPressure;
 }
 
 /**
  * Sets new bloodOxyLevel
  * @param bloodOxyLevel
  */
 public void setBloodOxyLvl(int bloodOxyLvl){
  this.bloodOxyLvl = bloodOxyLvl;
 }
 
 public void setAge(int age){
  this.age = age;
 }
 
 
 //Getters
 
 public String getName(){
  return name;
 }
 
 public int getRoom(){
  return roomNum;
 }
 public int getHeartRate()
 {
  return heartRate;
 }
 public float getTemperature(){
  return temperature;
 }
 
 public int getSystolicBP(){
  return systolicBP;
 }
 
  public int getDiastolicBP(){
  return diastolicBP;
 }
 
 public int getBloodOxyLvl(){
  return bloodOxyLvl;
 }
 
 public int getAge(){
  return age;
 }
 public PatientBox getDisplayBox()
 {
     return displayBox;
 }
}
