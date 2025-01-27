package lk.ijse.gdse.instritutefirstsemfinal.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectDto {

    private String subjectId;
    private String subjectName;
    private String[] subjectGrades;  // Array for subject grades
    private String subjectDescription;

    // Constructor for subjectId, subjectName, and subjectDescription
    public SubjectDto(String subjectId, String subjectName, String subjectDescription) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
    }

    // Add the getGradeIds method to return subjectGrades as a List<String>
    public List<String> getGradeIds() {
        return subjectGrades != null ? Arrays.asList(subjectGrades) : new ArrayList<>();
    }
}
