package com.cts.rms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.rms.model.ReservationTable;
import com.cts.rms.model.User;
import com.cts.rms.repository.TableReservationRepository;
import com.cts.rms.repository.UserRepository;
import com.cts.rms.service.TableReservationService;

@ExtendWith(MockitoExtension.class)
public class TableReservationServiceTest {

    @Mock
    private TableReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TableReservationService reservationService;

    private User user;
    private ReservationTable table;

    @BeforeEach
    void setUp() {
        user = new User(1L, "JaneDoe", "pass456", "jane@gmail.com", "customer");
        table = new ReservationTable(1L, user, LocalDateTime.of(2025, 10, 20, 19, 0), 4);
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(table));

        List<ReservationTable> result = reservationService.getAllReservations();

        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getPeopleNumber());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testSaveReservation() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(reservationRepository.save(table)).thenReturn(table);

        ReservationTable result = reservationService.saveReservation(table);

        assertNotNull(result);
        assertEquals(4, result.getPeopleNumber());
        assertEquals(user, result.getUser());
        verify(userRepository, times(1)).findById(user.getUserId());
        verify(reservationRepository, times(1)).save(table);
    }

    @Test
    void testDeleteReservation_Success() {
        when(reservationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservationRepository).deleteById(1L);

        boolean result = reservationService.deleteReservation(1L);

        assertTrue(result);
        verify(reservationRepository, times(1)).existsById(1L);
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReservation_Failure() {
        when(reservationRepository.existsById(999L)).thenReturn(false);

        boolean result = reservationService.deleteReservation(999L);

        assertFalse(result);
        verify(reservationRepository, times(1)).existsById(999L);
        verify(reservationRepository, never()).deleteById(999L);
    }

    @Test
    void testUpdateReservation_Success() {
        ReservationTable updatedTable = new ReservationTable(1L, user, LocalDateTime.of(2025, 10, 20, 20, 0), 5);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(table));
        when(reservationRepository.save(any(ReservationTable.class))).thenReturn(updatedTable);

        ReservationTable result = reservationService.updateReservation(updatedTable, 1L);

        assertNotNull(result);
        assertEquals(5, result.getPeopleNumber());
        assertEquals(LocalDateTime.of(2025, 10, 20, 20, 0), result.getReservationTime());

        verify(reservationRepository, times(1)).findAll();
        verify(reservationRepository, times(1)).save(table);
    }

    @Test
    void testUpdateReservation_NotFound() {
        ReservationTable updatedTable = new ReservationTable(999L, user, LocalDateTime.now(), 2);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(table));

        ReservationTable result = reservationService.updateReservation(updatedTable, 999L);

        assertNull(result);
        verify(reservationRepository, times(1)).findAll();
        verify(reservationRepository, never()).save(any(ReservationTable.class));
    }
}
