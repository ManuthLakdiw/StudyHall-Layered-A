package lk.ijse.gdse.instritutefirstsemfinal.entity.custom;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeacherCustom {
    private String teacherId;
    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String[] grades;
}
