package lk.ijse.gdse.instritutefirstsemfinal.bo;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.bo.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {}


    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }


    public enum BOType{
        USER , SUBJECT , GRADE , TEACHER , STUDENT
    }

    public SuperBO getBO(BOType boType){
        return switch (boType) {
            case USER -> new UserBOImpl();
            case SUBJECT -> new SubjectBOImpl();
            case GRADE -> new GradeBOImpl();
            case TEACHER -> new TeacherBOImpl();
            case STUDENT -> new StudentBOImpl();

            default -> null;
        };
    }
}
