package lk.ijse.gdse.instritutefirstsemfinal.entity.custom;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentCustom {

    private String id;
    private LocalDate birthday;
    private String name;
    private double admissionFee;
    private String parentName;
    private String email;
    private String phoneNumber;
    private String address;
    private String addedBy;
    private String grade;
    private String[] subjects;


}
