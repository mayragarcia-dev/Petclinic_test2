package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @Mock
    private VetMapper vetMapper;

    @InjectMocks
    private VetServiceImpl vetService;

    @Test
    public void testCreateVet() {
        // Preparar: simular las dependencias del servicio.
        VetDTO newVetDTO = VetDTO.builder()
                .firstName("Ana")
                .lastName("Torres")
                .email("ana.torres@petclinic.com")
                .phone("987654321")
                .active(true)
                .build();
        Vet newVet = new Vet(null, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        Vet savedVet = new Vet(7, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        VetDTO savedVetDTO = VetDTO.builder()
                .id(7)
                .firstName("Ana")
                .lastName("Torres")
                .email("ana.torres@petclinic.com")
                .phone("987654321")
                .active(true)
                .build();

        when(vetMapper.mapToEntity(newVetDTO)).thenReturn(newVet);
        when(vetRepository.save(newVet)).thenReturn(savedVet);
        when(vetMapper.mapToDto(savedVet)).thenReturn(savedVetDTO);

        // Ejecutar: llamar a la implementacion real del servicio.
        VetDTO result = vetService.create(newVetDTO);

        // Verificar: datos devueltos y delegacion al repositorio y al mapper.
        assertNotNull(result);
        assertEquals(7, result.getId());
        assertEquals(newVetDTO.getFirstName(), result.getFirstName());
        assertEquals(newVetDTO.getLastName(), result.getLastName());
        assertEquals(newVetDTO.getEmail(), result.getEmail());
        assertEquals(newVetDTO.getPhone(), result.getPhone());
        assertEquals(newVetDTO.getActive(), result.getActive());

        verify(vetMapper).mapToEntity(newVetDTO);
        verify(vetRepository).save(newVet);
        verify(vetMapper).mapToDto(savedVet);
        verifyNoMoreInteractions(vetRepository, vetMapper);
    }
}
