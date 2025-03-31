package app.adapters.inputs;

import app.adapters.inputs.utils.MedicalHistoryValidator;
import app.adapters.inputs.utils.OrderValidator;
import app.adapters.inputs.utils.PersonValidator;
import app.adapters.inputs.utils.Utils;
import app.domain.models.*;
import app.ports.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Component
public class VeterinarianInput implements InputPort {
    @Autowired
    private SellerInput sellerInput;
    @Autowired
    @Lazy
    private LoginInput loginInput;
    @Autowired
    private MedicalHistoryValidator medicalHistoryValidator;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private OrderValidator orderValidator;
    @Autowired
    private PersonPort personPort;
    @Autowired
    private OrderPort orderPort;
    @Autowired
    private MedicalHistoryPort medicalHistoryPort;
    @Autowired
    private PetPort petPort;

    private final String MENU = """
            Ingrese la opcion:
            1. Registrar historia clinica.
            2. Consultar historia clinica.
            3. Consultar orden
            4. Salir""";

    @Override
    public void menu() throws Exception {
        boolean sesion = true;
        while (sesion) {
            sesion = options();
        }
    }

    private boolean options() throws Exception {
        try {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();
            switch (option) {
                case "1":
                    this.registerClinicalHistory();
                    return true;
                case "2":
                    this.getMedicalHistory();
                    return true;
                case "3":
                    Order order = sellerInput.consultOrder();
                    System.out.println("------ Orden encontrada -------");
                    System.out.println("Dueño: " + order.getOwner().getName());
                    System.out.println("Veterinario: " + order.getVeterinarian().getName());
                    System.out.println("Diagnóstico: " + order.getMedicationDosage());
                    System.out.println("Medicamento: " + order.getMedicationName());
                    System.out.println("Fecha de creación: " + order.getDate());
                    return true;
                case "4":
                    System.out.println("Saliendo...");
                    return false;
                default:
                    System.out.println("Opcion invalida");
                    return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    public void registerClinicalHistory() throws Exception {
        User user = loginInput.getUserLogged();
        if (user == null) {
            System.out.println("No se ha iniciado sesion");
            return;
        }
        if (!user.getRole().equals("veterinarian")) {
            System.out.println("No tiene permisos para registrar historia clinica");
            return;
        }
        System.out.println("Ingrese el documento del dueño");
        long document = personValidator.documentValidator(Utils.getReader().nextLine());

        Person owner = personPort.findByDocument(document);
        if (owner == null) {
            throw new Exception("No se encontró ninguna persona con ese documento.");
        }

        List<Pet> pets = petPort.getPetsByPerson(owner);
        if (pets.isEmpty()) {
            throw new Exception("Este cliente no tiene mascotas registradas.");
        }
        System.out.println("Seleccione la mascota:");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i).getName() + " - " + pets.get(i).getBreed());
        }
        int selectedIndex = Utils.getReader().nextInt();
        Utils.getReader().nextLine();
        if (selectedIndex < 1 || selectedIndex > pets.size()) {
            throw new Exception("Selección inválida.");
        }
        Pet selectedPet = pets.get(selectedIndex - 1);

        System.out.println("Ingrese la razón de la consulta");
        String reason = medicalHistoryValidator.reasonValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese los sintomas");
        String symptoms = medicalHistoryValidator.symptomsValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese el diagnostico");
        String diagnosis = medicalHistoryValidator.diagnosisValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese el tratamiento");
        String treatment = medicalHistoryValidator.treatmentValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese el medicamento recetado");
        String prescribedMedication = medicalHistoryValidator.prescribedMedicationValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese la dosis del medicamento");
        String medicationDosage = medicalHistoryValidator.medicationDosageValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese el historial de vacunacion");
        String vaccinationHistory = medicalHistoryValidator.vaccinationHistoryValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese las alergias a medicamentos");
        String medicationAllergies = medicalHistoryValidator.medicationAllergiesValidator(Utils.getReader().nextLine());
        System.out.println("Ingrese los detalles del tratamiento");
        String treatmentDetails = medicalHistoryValidator.treatmentDetailsValidator(Utils.getReader().nextLine());
        boolean isRegisterOrder = true;

        while (isRegisterOrder) {
            System.out.println("Desea registrar una orden? (s/n)");
            String registerOrder = Utils.getReader().nextLine();

            if (registerOrder.equals("s")) {
                Order order = new Order();
                order.setOwner(owner);
                order.setVeterinarian(loginInput.getUserLogged());
                order.setMedicationName(prescribedMedication);
                order.setMedicationDosage(medicationDosage);
                order.setDate(Utils.getCurrentDate());
                orderPort.saveOrder(order);
                System.out.println("Orden registrada con exito");
                isRegisterOrder = false;
            } else if (registerOrder.equals("n")) {
                isRegisterOrder = false;
            } else {
                System.out.println("Opcion invalida");
            }
        }

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDate(Utils.getCurrentDate());
        medicalHistory.setVeterinarian(loginInput.getUserLogged());
        medicalHistory.setReasonForConsultation(reason);
        medicalHistory.setSymptoms(symptoms);
        medicalHistory.setDiagnosis(diagnosis);
        medicalHistory.setTreatment(treatment);
        medicalHistory.setPrescribedMedication(prescribedMedication);
        medicalHistory.setMedicationDosage(medicationDosage);
        medicalHistory.setPet(selectedPet);
        medicalHistory.setVaccinationHistory(vaccinationHistory);
        medicalHistory.setMedicationAllergies(medicationAllergies);
        medicalHistory.setTreatmentDetails(treatmentDetails);
        medicalHistory.setOrderCancellation(false);

        medicalHistoryPort.saveMedicalHistory(medicalHistory);
        System.out.println("Historia clinica registrada con exito");
    }

    public void getMedicalHistory() throws Exception {
        System.out.println("Ingrese el documento del dueño");
        long document = personValidator.documentValidator(Utils.getReader().nextLine());

        Person owner = personPort.findByDocument(document);
        if (owner == null) {
            System.out.println("No se encontró ninguna persona con ese documento.");
            return;
        }

        List<Pet> pets = petPort.getPetsByPerson(owner);
        if (pets.isEmpty()) {
            System.out.println("Este cliente no tiene mascotas registradas.");
            return;
        }
        System.out.println("Seleccione la mascota:");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i).getName() + " - " + pets.get(i).getBreed());
        }
        int selectedIndex = Utils.getReader().nextInt();
        Utils.getReader().nextLine();
        if (selectedIndex < 1 || selectedIndex > pets.size()) {
            System.out.println("Selección inválida.");
            return;
        }
        Pet selectedPet = pets.get(selectedIndex - 1);

        List<MedicalHistory> medicalHistories = medicalHistoryPort.findMedicalHistoriesByPet(selectedPet);
        if (medicalHistories == null || medicalHistories.isEmpty()) {
            System.out.println("No se encontraron historias clinicas para esta mascota.");
            return;
        }
        System.out.println("Historias clinicas:");
        for (int i = 0; i < medicalHistories.size(); i++) {
            System.out.println((i + 1) + ". " + medicalHistories.get(i).getDate());
        }
        int selectedHistoryIndex = Utils.getReader().nextInt();
        Utils.getReader().nextLine();
        if (selectedHistoryIndex < 1 || selectedHistoryIndex > medicalHistories.size()) {
            System.out.println("Selección inválida.");
            return;
        }
        MedicalHistory selectedMedicalHistory = medicalHistories.get(selectedHistoryIndex - 1);

        System.out.println("------ Historia clinica encontrada -------");
        System.out.println("Fecha: " + selectedMedicalHistory.getDate());
        System.out.println("Razón de la consulta: " + selectedMedicalHistory.getReasonForConsultation());
        System.out.println("Síntomas: " + selectedMedicalHistory.getSymptoms());
        System.out.println("Diagnóstico: " + selectedMedicalHistory.getDiagnosis());
        System.out.println("Tratamiento: " + selectedMedicalHistory.getTreatment());
        System.out.println("Medicamento recetado: " + selectedMedicalHistory.getPrescribedMedication());
        System.out.println("Dosis del medicamento: " + selectedMedicalHistory.getMedicationDosage());
        System.out.println("Historial de vacunación: " + selectedMedicalHistory.getVaccinationHistory());
        System.out.println("Alergias a medicamentos: " + selectedMedicalHistory.getMedicationAllergies());
        System.out.println("Detalles del tratamiento: " + selectedMedicalHistory.getTreatmentDetails());
        System.out.println("Orden cancelada: " + (selectedMedicalHistory.getOrderCancellation() ? "Si" : "No"));
    }
}
