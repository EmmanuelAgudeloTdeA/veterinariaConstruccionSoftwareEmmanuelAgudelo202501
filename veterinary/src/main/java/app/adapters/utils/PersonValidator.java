package app.adapters.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class PersonValidator extends SimpleValidator {

    public long documentValidator(String value) throws Exception {
        return longValidator(value, "numero de documento");
    }

    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la persona");
    }

    public Date dateValidator(Date value) throws Exception {
        return dateValidator(value, "fecha de nacimiento");
    }
}
