/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltd2p8schedulecreator;

/**
 *
 * @author luked_000
 */
public class CourseInformation implements java.io.Serializable{
    
    private String courseName;
    private String courseAbbreviation;
    private Integer courseNumber;
    private Integer courseLength;
    private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer courseMeetingDays;
            
    public CourseInformation(String name, String abbrev, Integer num, Integer length, Integer hour, Integer minute, Integer days) {
        this.courseName = name;
        this.courseAbbreviation = abbrev;
        this.courseNumber = num;
        this.courseLength = length;
        this.startTimeHour = hour;
        this.startTimeMinute = minute;
        this.courseMeetingDays = days;
        
        
    }
        
    public String getCourseName() {
        return courseName;
    }
    
    public String getCourseAbbreviation() {
        return courseAbbreviation;
    }
    
    public Integer getCourseNumber() {
        return courseNumber;
    }
    
    public Integer getCourseLength() {
        return courseLength;
    }
    
    public Integer getHour() {
        return startTimeHour;
    }
        
    public Integer getMinute() {
        return startTimeMinute;
    }
    
    public Integer getDays() {
        return courseMeetingDays;
    }
}
