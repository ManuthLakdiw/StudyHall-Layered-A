package lk.ijse.gdse.instritutefirstsemfinal.dto;

import lombok.*;

import java.time.LocalDate;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
    private String id; // Student ID
    private LocalDate birthday; // Date of Birth
    private String name; // Student Name
    private double admissionFee; // Admission Fee
    private String parentName; // Parent Name
    private String email; // Email Address
    private String phoneNumber; // Phone Number
    private String address; // Address
    private String addedBy; // Admin/Teacher who added the student
    private String grade; // Grade ID
    private String[] subjects;


    public StudentDto(String id , String name , String birthDay,double admissionFee, String grade , String [] subjects, String addedBy) {
        this.id = id;
        this.name = name;
        this.birthday = LocalDate.parse(birthDay);
        this.admissionFee = admissionFee;
        this.grade = grade;
        this.subjects = subjects;
    }

}
