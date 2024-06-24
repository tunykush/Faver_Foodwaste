package app.entities;

public class Student {
    private String name;
    private String studentID;

    public Student(String name, String studentID){
        this.name = name;
        this.studentID = studentID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
