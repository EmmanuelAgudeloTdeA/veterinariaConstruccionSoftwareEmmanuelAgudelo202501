package app.adapters.rest;

import app.adapters.rest.request.MedicalHistoryRequest;
import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.domain.services.VeterinarianService;
import app.exceptions.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Setter
@Getter
@RestController
@RequestMapping("/veterinarian")
public class VeterinarianController {
    @Autowired
    private VeterinarianService veterinarianService;

    @PostMapping("/clinical-history")
    public ResponseEntity<?> registerClinicalHistory(@RequestBody MedicalHistoryRequest request) {
        try {
            veterinarianService.registerClinicalHistory(request);
            return ResponseEntity.ok("Historia clínica registrada con éxito.");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clinical-history")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistory(
            @RequestParam long ownerDocument,
            @RequestParam int petId
    ) {
        List<MedicalHistory> result = veterinarianService.getMedicalHistories(ownerDocument, petId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order")
    public ResponseEntity<Order> getOrder(@RequestParam long ownerDocument) {
        Order order = veterinarianService.getOrderByOwnerDocument(ownerDocument);
        return ResponseEntity.ok(order);
    }
}
