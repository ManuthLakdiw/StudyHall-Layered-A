package lk.ijse.gdse.instritutefirstsemfinal.dto;

import lombok.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Lombok annotations at the class level
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    private String teacherId;
    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String[] grades;

    // Additional constructor for a partial object
    public TeacherDto(String teacherId, String name, String phoneNumber, String email, String subject) {
        this.teacherId = teacherId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.subject = subject;
    }




}

