package lk.ijse.gdse.instritutefirstsemfinal.entity;


import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
}
