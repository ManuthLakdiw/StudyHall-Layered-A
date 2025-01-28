package lk.ijse.gdse.instritutefirstsemfinal.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Teacher {

    private String teacherId;
    private String teacherName;
    private String phoneNumber;
    private String email;
    private String subjectID;
}
