# Schedule_Creator
Luke Darrough CS 3330 Final Project Documentation

 ***To run the program, use the .jar file in the dist folder.***

The application's purpose is to create courses and add the created
courses to a weekly schedule view.

The application is comprised of two scenes that each have an associated 
controller. Each scene's controller is a subclass of the Switchable
class written by Dr. Dale Musser. The model for the application is 
the class CourseInformation, which holds all of the course data in 
fields. It also implements the interface java.io.Serializable, which
allows its information to be saved. The two views can be switched 
two from either one by clicking File->Create Course in the Schedule View
and by clicking the Return to Schedule View button in the Course Chooser
View.

The controller for the scene where
the user creates courses implements an interface titled 
CourseDataInterpretor written by me that included a function called
parseUserData. This function is called when the user tries to submit 
course data. It serves the purpose of trying and catching all invalid
inputs, and notifying the user if there is an error with the input.
If all the input is valid, there is an attempt to save the data to a file.
This is done by creating a new instance of the CourseInformation 
class with the input from the user and using the writeObject method
of the ObjectOutputStream class. If there is an error saving, the user
is notified. 

In the Schedule View Conroller's initialize function, a pane of times is
created that is used as a reference for other courses. There is an About
section under the Help menu that makes an alert pop up that contains 
information about the application and how to use it. If File->Load Course
is clicked, a FileChooser allows the user to select a course file to load.
This is done in a try catch block, and a error is printed if there is an 
issue loading the file. If the load is successful, the method placeObject
is called, and it accepts the loaded CourseInformation instance as a 
parameter. An ArrayList of StackPanes is made for the courses. If the 
course meets 3 times, 3 StackPanes are added to the list, each containing
the course information. The same is done if with 2 StackPanes if there are
2 course meeting times, and 1 as the default for the rest of the days. 
The method addBasedOnDays is then called with the parameters of the
ArrayList and the course meeting days field. This method contains
a switch statement with the meeting days field as the parameter. The
StackPanes from the ArrayList are then added to Panes that correspond with
their meeting day information. 
