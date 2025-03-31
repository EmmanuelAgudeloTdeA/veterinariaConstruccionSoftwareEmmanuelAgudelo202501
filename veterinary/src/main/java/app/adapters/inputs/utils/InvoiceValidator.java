package app.adapters.inputs.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class InvoiceValidator extends SimpleValidator {

    public String productNameValidator(String value) throws Exception {
        return stringValidator(value, "nombre del producto");
    }

    public float priceValidator(float value) throws Exception {
        return floatValidator(value, "precio del producto");
    }

    public int amountValidator(int value) throws Exception {
        return intValidator(value, "cantidad del producto");
    }
}
