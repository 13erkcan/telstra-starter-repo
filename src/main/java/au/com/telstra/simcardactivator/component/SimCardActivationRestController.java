package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.SimCard;
import au.com.telstra.simcardactivator.foundation.SimCardActivation;
import au.com.telstra.simcardactivator.service.SimCardActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activations")
public class SimCardActivationRestController {

    private final SimCardActuationHandler simCardActuationHandler;
    private final SimCardActivationService simCardActivationService;

    @Autowired
    public SimCardActivationRestController(SimCardActuationHandler simCardActuationHandler, SimCardActivationService simCardActivationService) {
        this.simCardActuationHandler = simCardActuationHandler;
        this.simCardActivationService = simCardActivationService;
    }

    @PostMapping("/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        var actuationResult = simCardActuationHandler.actuate(simCard);
        System.out.println(actuationResult.getSuccess());

        // Save the activation result to the database
        SimCardActivation activation = new SimCardActivation();
        activation.setIccid(simCard.getIccid());
        activation.setCustomerEmail(simCard.getCustomerEmail());
        activation.setActive(actuationResult.getSuccess());
        simCardActivationService.saveActivation(activation);
    }

    @PostMapping
    public ResponseEntity<SimCardActivation> createActivation(@RequestBody SimCardActivation activation) {
        SimCardActivation savedActivation = simCardActivationService.saveActivation(activation);
        return ResponseEntity.ok(savedActivation);
    }

    @GetMapping
    public ResponseEntity<SimCardActivation> getActivation(@RequestParam Long simCardId) {
        SimCardActivation activation = simCardActivationService.getActivationById(simCardId);
        if (activation != null) {
            return ResponseEntity.ok(activation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
