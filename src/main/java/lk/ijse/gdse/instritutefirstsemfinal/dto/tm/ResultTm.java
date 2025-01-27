package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultTm {
    private String resultID;
    private String grade;
    private String subject;
    private String exam;
    private String student;
    private int marks;
    private String gradeArchieved;
    private String status;
}
