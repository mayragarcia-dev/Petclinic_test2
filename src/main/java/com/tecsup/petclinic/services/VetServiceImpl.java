package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public void deactivateVet(Integer id) {
        Vet vet = findVet(id);
        vet.setActive(false);
        vetRepository.save(vet);
    }

    @Override
    public void reactivateVet(Integer id) {
        Vet vet = findVet(id);
        vet.setActive(true);
        vetRepository.save(vet);
    }

    @Override
    public List<Vet> findActiveVets() {
        return vetRepository.findByActiveTrue();
    }

    private Vet findVet(Integer id) {
        return vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Veterinarian not found: " + id));
    }
}
