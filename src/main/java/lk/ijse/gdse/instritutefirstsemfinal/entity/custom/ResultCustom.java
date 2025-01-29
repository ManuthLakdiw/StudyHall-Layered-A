package lk.ijse.gdse.instritutefirstsemfinal.entity.custom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultCustom {
    private String resultID;
    private String grade;
    private String subject;
    private String exam;
    private String student;
    private int marks;
    private String gradeArchived;
    private String status;
}
