package app.adapters.utils;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator extends SimpleValidator{
    public String medicationNameValidator(String value) throws Exception {
        return stringValidator(value, "nombre del medicamento");
    }

    public String medicationDosageValidator(String value) throws Exception {
        return stringValidator(value, "dosis del medicamento");
    }
}
