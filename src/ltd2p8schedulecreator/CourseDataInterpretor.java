/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltd2p8schedulecreator;

import java.text.ParseException;

/**
 *
 * @author luked_000
 */
public interface CourseDataInterpretor {
    void parseUserData(String courseName, String courseAbbreviation, String courseNumber, String courseLength, 
            String courseTime, String courseMeetingDays) throws ParseException, Exception;
}
