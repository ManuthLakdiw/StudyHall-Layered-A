package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamTm {

    private String examId;
    private String grade;
    private String subject;
    private LocalDate examDate;
    private String examType;
    private String examDescription;
}
