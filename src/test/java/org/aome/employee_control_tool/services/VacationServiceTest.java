package org.aome.employee_control_tool.services;

import org.aome.employee_control_tool.dtos.VacationEmployeeDTO;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.VacationEntity;
import org.aome.employee_control_tool.store.repositories.VacationRepository;
import org.aome.employee_control_tool.store.search_specifications.VacationSearchSpecifications;
import org.aome.employee_control_tool.util.containers.VacationContainer;
import org.aome.employee_control_tool.util.converters.Converters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class VacationServiceTest {
    @InjectMocks
    private VacationService vacationService;

    @Mock
    private VacationRepository vacationRepository;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private VacationSearchSpecifications vacationSearchSpecifications;

    @Test
    void findVacationDTOs() {
        VacationEntity vacation1 = VacationEntity.builder()
                .id(UUID.randomUUID())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .employee(new EmployeeEntity())
                .build();
        VacationEntity vacation2 = VacationEntity.builder()
                .id(UUID.randomUUID())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .employee(new EmployeeEntity())
                .build();
        List<VacationEntity> vacationEntities = List.of(vacation1, vacation2);

        try {
            given(vacationRepository.findAll(any(Specification.class), (Pageable) any()))
                    .willReturn(new PageImpl<>(vacationEntities));

        }catch (ClassCastException e){

        }
        List<VacationEmployeeDTO> action = vacationService.findVacationDTOs(new VacationContainer(), 1,1);
        List<VacationEmployeeDTO> expected = vacationEntities.stream().map(Converters::vacationEntityToVacationEmployeeDTO).toList();

        assertEquals(expected, action);
    }

    @Test
    void saveAndConnectWithEmployee() {

    }
}