package lk.ijse.gdse.instritutefirstsemfinal.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamDto {

    private String examId;
    private String grade;
    private String subject;
    private LocalDate examDate;
    private String examType;
    private String examDescription;


}
