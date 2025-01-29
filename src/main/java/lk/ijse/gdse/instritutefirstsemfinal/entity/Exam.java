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
    private String grade;
    private String subject;
    private LocalDate examDate;
    private String examType;
    private String description;

}
