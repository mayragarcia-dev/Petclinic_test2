package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetServiceImpl vetService;

    @Test
    void testDeactivateVet() {
        Vet vet = new Vet(1, "Helen", "Leary", "helen@example.com", "123456789", true);
        when(vetRepository.findById(1)).thenReturn(Optional.of(vet));

        vetService.deactivateVet(1);

        assertFalse(vet.isActive());
        verify(vetRepository).save(vet);
        verify(vetRepository, never()).delete(vet);
        verify(vetRepository, never()).deleteById(1);
    }

    @Test
    void testReactivateVet() {
        Vet vet = new Vet(6, "Sharon", "Jenkins", "sharon@example.com", "987654321", false);
        when(vetRepository.findById(6)).thenReturn(Optional.of(vet));

        vetService.reactivateVet(6);

        assertTrue(vet.isActive());
        verify(vetRepository).save(vet);
    }

    @Test
    void testFindActiveVets() {
        List<Vet> activeVets = List.of(
                new Vet(1, "Helen", "Leary", "helen@example.com", "123456789", true),
                new Vet(2, "Linda", "Douglas", "linda@example.com", "123456780", true));
        when(vetRepository.findByActiveTrue()).thenReturn(activeVets);

        List<Vet> result = vetService.findActiveVets();

        assertEquals(activeVets, result);
        assertTrue(result.stream().allMatch(Vet::isActive));
        verify(vetRepository).findByActiveTrue();
    }
}
