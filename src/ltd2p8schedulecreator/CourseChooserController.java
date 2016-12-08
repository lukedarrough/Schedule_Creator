/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltd2p8schedulecreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luked_000
 */
public class CourseChooserController extends Switchable implements Initializable, CourseDataInterpretor {

    @FXML
    private TextField courseNameField;
    
    @FXML
    private TextField courseAbbreviationField;
    
    @FXML
    private TextField courseNumberField;
    
    @FXML
    private TextField courseLengthField;
            
    @FXML
    private TextField courseMeetingTimeField;
    
    @FXML
    private TextField courseMeetingDaysField;
    
    @FXML
    private Label nameError;
    
    @FXML
    private Label abbrevError;
    
    @FXML
    private Label numberError;
    
    @FXML
    private Label lengthError;
    
    @FXML
    private Label timeError;
    
    @FXML
    private Label daysError;
    
    @FXML 
    private Label errorText;
        
    private String userCourseName;
    private String userCourseAbbreviation;
    private Integer userCourseNumber;
    private Integer userCourseLength;
    private Integer userCourseHours;
    private Integer userCourseMinutes;
    private Integer userCourseMeetingDays;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void handleCancel(ActionEvent e) {
        
        
        courseNameField.setText("");
        courseAbbreviationField.setText("");
        courseNumberField.setText("");
        courseLengthField.setText("");
        courseMeetingTimeField.setText("");
        courseMeetingDaysField.setText("");
        
        nameError.setText("");
        abbrevError.setText("");
        numberError.setAccessibleHelp("");
        lengthError.setText("");
        timeError.setText("");
        daysError.setText("");
        
        errorText.setText("");       
        
        switchTo("ScheduleView");
    }
    
    @FXML
    private void handleSubmit(ActionEvent e) throws IOException {
             
        errorText.setText("");
                
        CourseInformation ci = null;
        
        try {
            parseUserData(courseNameField.getText(), courseAbbreviationField.getText(), 
                    courseNumberField.getText(), courseLengthField.getText(), 
                    courseMeetingTimeField.getText(), courseMeetingDaysField.getText());
            
            ci = new CourseInformation(userCourseName, userCourseAbbreviation, userCourseNumber, userCourseLength, 
                    userCourseHours, userCourseMinutes, userCourseMeetingDays);
            
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try
                {
                    FileOutputStream fileOut = new FileOutputStream(file.getPath());
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(ci);
                    out.close();
                    fileOut.close();
                } catch(IOException ioex)
            {
                String message = "Exception occurred while saving to " + file.getPath();
                errorText.setText(message);
            } 
        }
            
            
        } catch (Exception ex) {
    
            errorText.setText("Ensure all fields are correctly entered");
        }  
        
        
             
    }
    
    @Override
    public void parseUserData(String name, String abbrev, String number, String length, 
            String time, String meetingDays) throws ParseException, Exception {
        
        nameError.setText("");
        abbrevError.setText("");
        numberError.setText("");
        lengthError.setText("");
        timeError.setText("");
        daysError.setText("");
        
        errorText.setText(""); 
        
        //parse the name
        try {
            this.userCourseName = name;
        } catch (Exception ex) {
            userCourseName = null;
            nameError.setText("Invalid Name");
            nameError.setTextFill(Color.RED);
        }
        
        if (userCourseName.equals("")) {
            userCourseName = null;
            nameError.setText("Invalid Name");
            nameError.setTextFill(Color.RED);
        }
        
        //parse abbreviation
        try {
            this.userCourseAbbreviation = abbrev;
        } catch (Exception ex) {
            userCourseAbbreviation = null;
            abbrevError.setText("Invalid Name");
            abbrevError.setTextFill(Color.RED);
        }
        
        if (userCourseAbbreviation.equals("")) {
            userCourseAbbreviation = null;
            abbrevError.setText("Invalid Name");
            abbrevError.setTextFill(Color.RED);
        }
        
        //parse number
        try {
            this.userCourseNumber = Integer.parseInt(number);
        } catch (Exception ex) {
            userCourseNumber = null;
            numberError.setText("Invalid Number");
            numberError.setTextFill(Color.RED);
        }
            
        if (userCourseNumber < 0) {
            userCourseNumber = null;
            numberError.setText("Invalid Number");
            numberError.setTextFill(Color.RED);
        }
        
        //parse length
        try {
            this.userCourseLength = Integer.parseInt(length);
        } catch (Exception ex) {
            userCourseLength = null;
            lengthError.setText("Invalid Length");
            lengthError.setTextFill(Color.RED);
        }
        
        if (userCourseLength > 600 || userCourseLength < 0) {
            userCourseLength = null;
            lengthError.setText("Invalid length");
            lengthError.setTextFill(Color.RED);
        }        
    
        //parse the course meeting time
        
        try {
            Date date = sdf.parse(time);
            Calendar calendar = GregorianCalendar.getInstance();     
            calendar.setTime(date);
            userCourseHours = calendar.get(Calendar.HOUR);
            userCourseMinutes = calendar.get(Calendar.MINUTE);
        } catch (Exception ex) {
            userCourseHours = null;
            userCourseMinutes = null;
            timeError.setText("Invalid Time");
            timeError.setTextFill(Color.RED);
        }       
        
        if (userCourseHours >= 6 && userCourseHours < 8) {
            userCourseHours = null;
            userCourseMinutes = null;
            timeError.setText("Invalid Time");
            timeError.setTextFill(Color.RED);
        }
            
        //convert course start time to absolute time
        Double absoluteStartTime;
        if (userCourseHours <= 12 && userCourseHours >= 8)
            absoluteStartTime = ( ((double)userCourseHours - 8) + (((double)userCourseMinutes) / 60 ));
        else
            absoluteStartTime = ( ((double)userCourseHours + 4) + (((double)userCourseMinutes) / 60 ));
        
        //convert length to hours
        Double courseLengthInHours = ( ((double)userCourseLength )/ 60);
        
        
        if ( (absoluteStartTime + courseLengthInHours) > 10 ) {
            userCourseHours = null;
            userCourseMinutes = null;
            lengthError.setText("Length Exceeds Day Length");
            lengthError.setTextFill(Color.RED);
        }    
        
        try {
            this.userCourseMeetingDays = Integer.parseInt(meetingDays);
        } catch (Exception ex) {
            userCourseMeetingDays = null;
            daysError.setText("Invalid Days");
            daysError.setTextFill(Color.RED);
        }   
        
        if (userCourseMeetingDays < 1 || userCourseMeetingDays > 7) {
            userCourseMeetingDays = null;
            daysError.setText("Invalid Days");
            daysError.setTextFill(Color.RED);
        }
        
        //make sure all fields are valid
        if ( userCourseName == null || userCourseAbbreviation == null || userCourseNumber == null
                || userCourseLength == null || userCourseHours == null || userCourseLength == null
                || userCourseMeetingDays == null) 
            throw new Exception();
        
    }
    
}
