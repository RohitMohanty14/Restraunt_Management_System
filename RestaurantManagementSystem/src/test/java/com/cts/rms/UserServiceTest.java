package com.cts.rms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.rms.model.User;
import com.cts.rms.repository.UserRepository;
import com.cts.rms.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "JohnDoe", "pass123", "john@example.com", "Customer");
        user2 = new User(2L, "JaneDoe", "pass456", "jane@example.com", "Admin");
    }

    @Test
    void testListAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.listAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> u.getUserName().equals("JohnDoe")));
        assertTrue(result.stream().anyMatch(u -> u.getUserName().equals("JaneDoe")));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User result = userService.getById(1L);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getUserName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User result = userService.getById(999L);

        assertNull(result);
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testAddUser() {
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User result = userService.addUser(user1);

        assertNotNull(result);
        assertEquals("JohnDoe", result.getUserName());
        verify(passwordEncoder, times(1)).encode("pass123");
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testRemoveUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.removeUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoveUser_Failure() {
        when(userRepository.existsById(999L)).thenReturn(false);

        boolean result = userService.removeUser(999L);

        assertFalse(result);
        verify(userRepository, times(1)).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateUser_Success() {
        User updatedUser = new User(1L, "JohnUpdated", "newPass", "john.new@example.com", "Customer");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser, 1L);

        assertNotNull(result);
        assertEquals("JohnUpdated", result.getUserName());
        assertEquals("john.new@example.com", result.getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newPass");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        User updatedUser = new User(999L, "David", "none", "david@example.com", "Admin");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User result = userService.updateUser(updatedUser, 999L);

        assertNull(result);
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }
}
