package app.adapters.utils;

import org.springframework.stereotype.Component;

@Component
public class MedicalHistoryValidator extends SimpleValidator {
    public String reasonValidator(String value) throws Exception {
        return stringValidator(value, "motivo de la consulta");
    }

    public String symptomsValidator(String value) throws Exception {
        return stringValidator(value, "sintomas");
    }

    public String diagnosisValidator(String value) throws Exception {
        return stringValidator(value, "diagnostico");
    }

    public String treatmentValidator(String value) throws Exception {
        return stringValidator(value, "tratamiento");
    }

    public String prescribedMedicationValidator(String value) throws Exception {
        return stringValidator(value, "medicamento recetado");
    }

    public String medicationDosageValidator(String value) throws Exception {
        return stringValidator(value, "dosis del medicamento");
    }

    public String vaccinationHistoryValidator(String value) throws Exception {
        return stringValidator(value, "historial de vacunacion");
    }

    public String medicationAllergiesValidator(String value) throws Exception {
        return stringValidator(value, "alergias a medicamentos");
    }

    public String treatmentDetailsValidator(String value) throws Exception {
        return stringValidator(value, "detalles del tratamiento");
    }
}
