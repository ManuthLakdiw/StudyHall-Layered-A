package lk.ijse.gdse.instritutefirstsemfinal.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private String resultID;
    private String grade;
    private String exam;
    private String student;
    private int marks;
    private String gradeArchived;
    private String status;
}
