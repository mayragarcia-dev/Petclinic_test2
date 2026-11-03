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
        // Preparar los datos y simular las dependencias.
        VetDTO input = new VetDTO(null, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        Vet entity = new Vet(null, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        Vet savedEntity = new Vet(7, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        VetDTO savedDTO = new VetDTO(7, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);

        when(vetMapper.mapToEntity(input)).thenReturn(entity);
        when(vetRepository.save(entity)).thenReturn(savedEntity);
        when(vetMapper.mapToDto(savedEntity)).thenReturn(savedDTO);

        // Ejecutar el servicio real, sin Spring ni base de datos.
        VetDTO result = vetService.create(input);

        // Verificar el resultado y el guardado del veterinario.
        assertNotNull(result);
        assertEquals(7, result.getId());
        assertEquals(input.getFirstName(), result.getFirstName());
        assertEquals(input.getLastName(), result.getLastName());
        assertEquals(input.getEmail(), result.getEmail());
        assertEquals(input.getPhone(), result.getPhone());
        assertEquals(input.getActive(), result.getActive());

        verify(vetMapper).mapToEntity(input);
        verify(vetRepository).save(entity);
        verify(vetMapper).mapToDto(savedEntity);
        verifyNoMoreInteractions(vetRepository, vetMapper);
    }
}
