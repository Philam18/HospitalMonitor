(A Jar file is included for testing purposes)
Hospital Patient Monitor
By:
  Alpha Mai, 
  Philip Lam, 
  Manrag Nagra, 
  Brendon Zhang

The hospital patient monitor uses AWT and Swing to create a user-friendly monitor for healthsign vitals. 
It will alert healthcare providers when a user's vitals have entered a danger range, that is also 
adjustable by the user. The user is also able to add as many patients as they would like and all patients
are displayed onto a single frame to allow for easier tracking. 

The vital signs monitored are:
  -Temperature
  -Heart rate
  -Blood pressure (Systolic / Diastolic)
  -Blood oxygen 
In addition to name, age, and room number.

The simulator class acts as a "simulator", generating random information to test out the system in place 
of a real patient. 

The systemw works by first reading patient vitals. The StorageHandler will then format and output data into 
an XML file. An XML file proves its light weight and modularity here. Therein the MonitorSystem will then 
parse the data and work in conjunction with the UI to create a series of JFrames inside a panel to host 
the data. 
























