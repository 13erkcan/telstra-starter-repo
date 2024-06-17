package au.com.telstra.simcardactivator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import au.com.telstra.simcardactivator.foundation.SimCardActivation;
import au.com.telstra.simcardactivator.foundation.SimCardActivationRepository;

@Service
public class SimCardActivationService {
    
    @Autowired
    private SimCardActivationRepository repository;
    
    public SimCardActivation saveActivation(SimCardActivation activation) {
        return repository.save(activation);
    }
    
    public SimCardActivation getActivationById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
