package lk.ijse.gdse.instritutefirstsemfinal.entity;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Exam {
    private String examId;
    private String subject;
    private String examType;
    private LocalDate examDate;
    private String description;
    private String grade;

}
