module lk.ijse.gdse.instritutefirstsemfinal {
    requires javafx.fxml;

    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires java.mail;
    requires mysql.connector.j;
    requires org.controlsfx.controls;
    requires net.sf.jasperreports.core;
    requires com.fasterxml.jackson.annotation;

    opens lk.ijse.gdse.instritutefirstsemfinal.dto.tm to javafx.base;
    opens lk.ijse.gdse.instritutefirstsemfinal.controller to javafx.fxml;
    exports lk.ijse.gdse.instritutefirstsemfinal;
    opens lk.ijse.gdse.instritutefirstsemfinal.dto to javafx.base;
    opens lk.ijse.gdse.instritutefirstsemfinal.dao to javafx.base; // change model to dao
}



