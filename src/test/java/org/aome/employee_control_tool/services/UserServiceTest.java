package org.aome.employee_control_tool.services;

import org.aome.employee_control_tool.dtos.AuthenticationDTO;
import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.store.entities.EmployeeEntity;
import org.aome.employee_control_tool.store.entities.TestEmployeeEntity;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.aome.employee_control_tool.store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    private static UUID id;
    private static UserEntity user;
    private static EmployeeEntity employee;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        user = UserEntity.builder()
                .id(id)
                .username("Test")
                .password("Test")
                .build();
        employee = EmployeeEntity.builder()
                .id(id)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testings")
                .dateOfHire(LocalDate.now())
                .build();
    }

    @Test
    void findUserEntityByUsername_returnUserEntity() {
        given(userRepository.findUserEntityByUsername(any())).willReturn(Optional.ofNullable(user));

        assertEquals(userService.findUserEntityByUsername("Test"), user);
    }
    @Test
    void findUserEntityBuUsername_throwException(){
        given(userRepository.findUserEntityByUsername(any())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserEntityByUsername("Test"));
    }

    @Test
    void findUserEntityById_returnUserEntity() {
        given(userRepository.findUserEntityById(any())).willReturn(Optional.ofNullable(user));
        assertEquals(userService.findUserEntityById(user.getId()), user);
    }
    @Test
    void findUserEntityByIdBuUsername_throwException(){
        given(userRepository.findUserEntityById(any())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserEntityById(user.getId()));
    }

    @Test
    void isExistByUsername_returnTrue() {
        given(userRepository.existsByUsername(any())).willReturn(true);
        assertTrue(userService.isExistByUsername(user.getUsername()));
    }
    @Test
    void isExistByUsername_returnFalse() {
        given(userRepository.existsByUsername(any())).willReturn(false);
        assertFalse(userService.isExistByUsername(user.getUsername()));
    }

    @Test
    void save() {
        given(userRepository.save(any())).willReturn(user);
        assertEquals(user, userService.save(new AuthenticationDTO("Test", "Test")));
    }

    @Test
    void setEmployeeById_correct() {
        given(userRepository.findUserEntityById(any())).willReturn(Optional.ofNullable(user));
        UserEntity expectedUser = UserEntity.builder()
                .id(id)
                .username("Test")
                .password("Test")
                .role("ROLE_EMPLOYEE")
                .employee(employee)
                .build();
        UserEntity returnedUser = userService.setEmployeeById(user.getId(), employee);
        assertEquals(expectedUser, returnedUser);
        assertEquals(employee, returnedUser.getEmployee());
    }
    @Test
    void setEmployeeById_throwException() {
        given(userRepository.findUserEntityById(any())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.setEmployeeById(id, employee));
    }

    @Test
    void addTestEmployeeById_correctWithSingleTestEmployee() {
        TestEmployeeEntity testEmployeeEntity = TestEmployeeEntity.builder()
                .id(id)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testings")
                .dateOfHire(LocalDate.now())
                .build();
        UserEntity expectedUser = UserEntity.builder()
                .id(id)
                .username("Test")
                .password("Test")
                .testEmployees(Collections.singletonList(testEmployeeEntity))
                .build();

        given(userRepository.findUserEntityById(any())).willReturn(Optional.ofNullable(user));

        UserEntity returnedUser = userService.addTestEmployeeById(user.getId(), testEmployeeEntity);
        assertEquals(expectedUser, returnedUser);
        assertArrayEquals(expectedUser.getTestEmployees().toArray(), returnedUser.getTestEmployees().toArray());
    }
    @Test
    void addTestEmployeeById_correctWithMultipleTestEmployees() {
        TestEmployeeEntity testEmployeeEntity1 = TestEmployeeEntity.builder()
                .id(id)
                .firstName("Test")
                .lastName("Test")
                .position("Testing")
                .department("Testings")
                .dateOfHire(LocalDate.now())
                .build();
        TestEmployeeEntity testEmployeeEntity2 = TestEmployeeEntity.builder()
                .id(id)
                .firstName("Test1")
                .lastName("Test1")
                .position("Testing1")
                .department("Testing1")
                .dateOfHire(LocalDate.now())
                .build();
        UserEntity expectedUser = UserEntity.builder()
                .id(id)
                .username("Test")
                .password("Test")
                .testEmployees(List.of(testEmployeeEntity1, testEmployeeEntity2))
                .build();

        given(userRepository.findUserEntityById(any())).willReturn(Optional.ofNullable(user));

        user.getTestEmployees().add(testEmployeeEntity1);
        UserEntity returnedUser = userService.addTestEmployeeById(user.getId(), testEmployeeEntity2);
        assertEquals(expectedUser, returnedUser);
        assertArrayEquals(expectedUser.getTestEmployees().toArray(), returnedUser.getTestEmployees().toArray());
    }
    @Test
    void addTestEmployeeById_throwException() {
        given(userRepository.findUserEntityById(any())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.addTestEmployeeById(id, new TestEmployeeEntity()));
    }

    @Test
    void private_enrich() {
        UserEntity expectedUser = UserEntity.builder()
                .id(id)
                .username("Test")
                .password("someSecretPassword")
                .role("ROLE_USER")
                .build();
        try {
            Method enrichMethod = userService.getClass().getDeclaredMethod("enrich", UserEntity.class);
            given(passwordEncoder.encode(any())).willReturn("someSecretPassword");
            enrichMethod.setAccessible(true);

            assertEquals(expectedUser, enrichMethod.invoke(userService, user));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}