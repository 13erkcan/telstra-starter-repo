package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimActivationController {

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";
        
        // Create the payload for the actuator
        ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());

        // Send the request to the actuator
        ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(actuatorUrl, actuatorRequest, ActuatorResponse.class);
        
        // Process the response
        if (response.getBody().isSuccess()) {
            return ResponseEntity.ok("SIM activation successful");
        } else {
            return ResponseEntity.status(500).body("SIM activation failed");
        }
    }
}

class SimActivationRequest {
    private String iccid;
    private String customerEmail;

    // Getters and setters

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

class ActuatorRequest {
    private String iccid;

    public ActuatorRequest(String iccid) {
        this.iccid = iccid;
    }

    // Getters and setters

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
}

class ActuatorResponse {
    private boolean success;

    // Getters and setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
