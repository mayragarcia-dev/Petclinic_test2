package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.dtos.VetDTO;

import java.util.List;

public interface VetService {

    VetDTO create(VetDTO vetDTO);

    void deactivateVet(Integer id);

    void reactivateVet(Integer id);

    List<Vet> findActiveVets();
}
