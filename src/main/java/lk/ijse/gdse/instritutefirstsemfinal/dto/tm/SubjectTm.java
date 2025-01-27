package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectTm {

    private String subjectId;
    private String subjectName;
    private String subjectGrades; // Changed to String for table compatibility
    private String subjectDescription;
}
