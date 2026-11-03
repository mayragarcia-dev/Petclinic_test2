package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet savedVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(savedVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = findVet(vetDTO.getId());
        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        vet.setEmail(vetDTO.getEmail());
        vet.setPhone(vetDTO.getPhone());
        vet.setActive(vetDTO.getActive());

        Vet savedVet = vetRepository.save(vet);
        return vetMapper.mapToDto(savedVet);
    }

    @Override
    public VetDTO findById(Integer id) {
        return vetMapper.mapToDto(findVet(id));
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
