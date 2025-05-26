package app.adapters.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class PetValidator extends SimpleValidator {

    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la mascota");
    }

    public Date ageValidator(Date value) throws Exception {
        return dateValidator(value, "fecha de nacimiento de la mascota");
    }

    public String breedValidator(String value) throws Exception {
        return stringValidator(value, "raza de la mascota");
    }

    public String characteristicsValidator(String value) throws Exception {
        return stringValidator(value, "caracteristicas de la mascota");
    }

    public float weightValidator(float value) throws Exception {
        return floatValidator(value, "peso de la mascota");
    }
}
