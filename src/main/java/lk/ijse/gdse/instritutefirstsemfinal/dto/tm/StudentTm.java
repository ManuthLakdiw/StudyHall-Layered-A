package lk.ijse.gdse.instritutefirstsemfinal.dto.tm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentTm {
    private String id;
    private String name;
    private LocalDate birthday;
    private double admissionFee;

    private String parentName;
    private String email;
    private String phone;
    private String address;
    private String grade;

    private String subjects;
    private String addedBy;



    public StudentTm(String id, String name, LocalDate birthday, double admissionFee,
                    String grade, String subjects, String addedBy) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.admissionFee = admissionFee;
        this.grade = grade;
        this.subjects = subjects;
        this.addedBy = addedBy;
    }

    public StudentTm(String id, String name, String parentName, String email,
                   String phone, String address) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

}
