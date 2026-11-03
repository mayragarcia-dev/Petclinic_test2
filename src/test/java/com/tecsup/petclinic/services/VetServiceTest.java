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

import java.util.Optional;

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

    @Test
    public void testUpdateVet() {
        // Preparar un veterinario existente y datos nuevos, sin crear registros reales.
        Vet existingVet = new Vet(7, "Ana", "Torres",
                "ana.torres@petclinic.com", "987654321", true);
        VetDTO input = new VetDTO(7, "Maria", "Lopez",
                "maria.lopez@petclinic.com", "912345678", false);
        Vet savedVet = new Vet(7, "Maria", "Lopez",
                "maria.lopez@petclinic.com", "912345678", false);
        VetDTO savedDTO = new VetDTO(7, "Maria", "Lopez",
                "maria.lopez@petclinic.com", "912345678", false);

        when(vetRepository.findById(7)).thenReturn(Optional.of(existingVet));
        when(vetRepository.save(existingVet)).thenReturn(savedVet);
        when(vetMapper.mapToDto(savedVet)).thenReturn(savedDTO);

        // Ejecutar la actualizacion real del servicio.
        VetDTO result = vetService.update(input);

        // Verificar que el registro enviado a guardar conserve el ID y cambie sus datos.
        verify(vetRepository).findById(7);
        verify(vetRepository).save(same(existingVet));
        assertEquals(7, existingVet.getId());
        assertEquals(input.getFirstName(), existingVet.getFirstName());
        assertEquals(input.getLastName(), existingVet.getLastName());
        assertEquals(input.getEmail(), existingVet.getEmail());
        assertEquals(input.getPhone(), existingVet.getPhone());
        assertEquals(input.getActive(), existingVet.isActive());

        // El resultado debe provenir del registro devuelto por el repositorio.
        assertNotNull(result);
        assertEquals(input, result);
        verify(vetMapper).mapToDto(savedVet);
        verifyNoMoreInteractions(vetRepository, vetMapper);
    }
}
