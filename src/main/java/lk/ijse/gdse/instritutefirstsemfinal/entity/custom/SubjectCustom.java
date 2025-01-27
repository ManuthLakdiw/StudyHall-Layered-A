package lk.ijse.gdse.instritutefirstsemfinal.entity.custom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectCustom {

    private String subjectId;
    private String subjectName;
    private String[] subjectGrades;
    private String subjectDescription;
}
