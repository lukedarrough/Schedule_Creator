/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltd2p8schedulecreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author luked_000
 */
public class ScheduleViewController extends Switchable implements Initializable {
    
    @FXML
    private Pane times;
    
    @FXML
    private Pane monday;
    
    @FXML
    private Pane tuesday;
    
    @FXML
    private Pane wednesday;
    
    @FXML
    private Pane thursday;
    
    @FXML
    private Pane friday;
            
    public final static Integer hoursInSchedule = 10;
    public final static Double WIDTH = 126.0;
    public final static Double HEIGHT = 511.0;
    
    private CourseInformation currentCi;
    
    private Stage stage;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        for (int i = 8; i < 12; i++){
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefWidth(times.getPrefWidth());
            stackPane.setPrefHeight(times.getPrefHeight() / hoursInSchedule);
                       
            Rectangle timeBlock = new Rectangle();
            timeBlock.setWidth(times.getPrefWidth());
            timeBlock.setHeight(times.getPrefHeight() / hoursInSchedule);
            timeBlock.setFill(Color.BEIGE);
            timeBlock.setStroke(Color.BLACK);
            
            Label label = new Label( (i) + ":00-" + (i + 1) + ":00");
            
            stackPane.getChildren().addAll(timeBlock, label);
            stackPane.setTranslateY( ( ( (double)i - 8 ) / 10 ) * times.getPrefHeight());
            
            times.getChildren().add(stackPane);
            
        }    
                
        for (int i = 0; i < 6; i++){
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefWidth(times.getPrefWidth());
            stackPane.setPrefHeight(times.getPrefHeight() / hoursInSchedule);
            
            Rectangle timeBlock = new Rectangle();
            timeBlock.setWidth(times.getPrefWidth());
            timeBlock.setHeight(times.getPrefHeight() / hoursInSchedule);
            timeBlock.setFill(Color.BEIGE);
            timeBlock.setStroke(Color.BLACK);
            
            Label label = new Label();
            
            if (i == 0)
                label.setText("12:00-" + (i + 1) + ":00");
            else
                label.setText( (i) + ":00-" + (i + 1) + ":00");
            
            stackPane.getChildren().addAll(timeBlock, label);
            stackPane.setTranslateY( ( ( (double)i + 4 ) / 10 ) * times.getPrefHeight());
            
            times.getChildren().add(stackPane);
                        
        }
        //scheduleView = this;
                        
        
               
        
    }    
    
    @FXML
    private void handleAddCourse(ActionEvent e) {
        switchTo("CourseChooser");
    }
    
    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try
            {
               FileInputStream fileIn = new FileInputStream(file.getPath());
               ObjectInputStream in = new ObjectInputStream(fileIn);
               currentCi = (CourseInformation) in.readObject();
               in.close();
               fileIn.close();
            }catch(IOException ioex)
            {
               String message = "Exception occurred while opening " + file.getPath();
               System.err.println(message + ioex);
            }catch(ClassNotFoundException cnfex)
            {
               String message = "Class not found exception occurred while opening " + file.getPath();
               System.err.println(message + cnfex);
            }
        }
        
        placeObject(currentCi);
    }
    
    @FXML 
    private void handleClear() {
        monday.getChildren().clear();
        tuesday.getChildren().clear();
        wednesday.getChildren().clear();
        thursday.getChildren().clear();
        friday.getChildren().clear();
        
    }
    
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        alert.setTitle("About");
        alert.setHeaderText("Schedule Creator");
        alert.setContentText("This application was created by Luke Darrough for CS 3330 at the University of Missouri.");
        
        TextArea textArea = new TextArea("This application is used to create and save courses, "
                + "and then add saved courses to a weekly schedule view. Courses are created and saved by "
                + "clicking File->Create Course, and saving once the course information has been filled out. "
                + "Courses can be loaded by clicking File->Load Course on the Schedule view.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        
        Pane expContent = new Pane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.getChildren().add(textArea);
        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }
    
    private void placeObject(CourseInformation ci) {
        
        CourseInformation temp = ci;
        
        ArrayList<StackPane> courses = new ArrayList<>();
        
        int numNecessaryInstances;
        
        switch (temp.getDays()) {
            case 1:
                numNecessaryInstances = 3;
                break;
            case 2:
                numNecessaryInstances = 2;        
                break;
            default:
                numNecessaryInstances = 1;
                break;
        }
        
        for (int i = 0; i < numNecessaryInstances; i++) {      
        
            StackPane sPane = new StackPane();

            sPane.setPrefWidth(ScheduleViewController.WIDTH);
            sPane.setPrefHeight( (((double)temp.getCourseLength() / 60.0) / 10.0) * ScheduleViewController.HEIGHT);

            Rectangle block = new Rectangle();

            block.setFill(Color.GOLD);
            block.setWidth(ScheduleViewController.WIDTH);
            block.setHeight( (((double)temp.getCourseLength() / 60.0) / 10.0) * ScheduleViewController.HEIGHT);
            block.setStroke(Color.BLACK);

            Label courseNameLabel = new Label(temp.getCourseName());
            Label courseAbbreviationLabel = new Label(temp.getCourseAbbreviation() + "_" + temp.getCourseNumber().toString());
            
            VBox infoContainer = new VBox();
            infoContainer.setAlignment(Pos.CENTER);
            infoContainer.setPrefSize(ScheduleViewController.WIDTH, (((double)temp.getCourseLength() / 60.0) / 10.0) * ScheduleViewController.HEIGHT );
            infoContainer.getChildren().addAll(courseNameLabel, courseAbbreviationLabel);

            sPane.getChildren().addAll(block, infoContainer);

            sPane.setTranslateX(0.0);

            if (temp.getHour() >= 8 && temp.getHour() <= 12)
                sPane.setTranslateY( ( ( ((double)temp.getHour() - 8.0) + ((double)temp.getMinute() / 60.0) ) / 10.0  )* ScheduleViewController.HEIGHT );
            else
                sPane.setTranslateY( ( ( ((double)temp.getHour() + 4.0) + ((double)temp.getMinute() / 60.0) ) / 10.0  )* ScheduleViewController.HEIGHT );
        
            courses.add(sPane);
            
        }
            
        addBasedOnDays(courses, temp.getDays());
        
    }
    
    private void addBasedOnDays(ArrayList<StackPane> courses, Integer meetingDays) {
        
        switch (meetingDays) {
            
            case 1: monday.getChildren().add(courses.get(0));
                    wednesday.getChildren().add(courses.get(1));
                    friday.getChildren().add(courses.get(2));
                    break;
            case 2: tuesday.getChildren().add(courses.get(0));
                    thursday.getChildren().add(courses.get(1));
                    break;
            case 3: monday.getChildren().add(courses.get(0));
                    break;
            case 4: tuesday.getChildren().add(courses.get(0));
                    break;
            case 5: wednesday.getChildren().add(courses.get(0));
                    break;
            case 6: thursday.getChildren().add(courses.get(0));
                    break;
            case 7: friday.getChildren().add(courses.get(0));
                    break;            
            
        }
        
    }
    
}
