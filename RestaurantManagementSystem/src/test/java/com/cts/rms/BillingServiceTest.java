package com.cts.rms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cts.rms.model.Billing;
import com.cts.rms.model.Menu;
import com.cts.rms.model.Orders;
import com.cts.rms.repository.BillingRepository;
import com.cts.rms.repository.OrderRepository;
import com.cts.rms.service.BillingReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private BillingReservationService billingService;

    private Billing billing;
    private Orders order;

    @BeforeEach
    void setUp() {
        order = new Orders();
        order.setOrderId(1L);

        
        Menu menu = new Menu();
        menu.setMenuId(10L);
        menu.setItemName("Pizza");
        menu.setPrice(250.00);

        order.setMenu(menu);
        order.setQuantity(2);

        billing = new Billing();
        billing.setBillingId(1L);
        billing.setTotalAmount(Double.valueOf(500.00));
        billing.setPaymentType("Card");
        billing.setBillingDate(LocalDateTime.now());
        billing.setOrder(order);
    }


    @Test
    void testGetAllBills() {
        when(billingRepository.findAll()).thenReturn(Arrays.asList(billing));

        List<Billing> result = billingService.getAllBills();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(billingRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        when(billingRepository.findById(1L)).thenReturn(Optional.of(billing));

        Billing result = billingService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBillingId());
        verify(billingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(billingRepository.findById(2L)).thenReturn(Optional.empty());

        Billing result = billingService.getById(2L);

        assertNull(result);
        verify(billingRepository, times(1)).findById(2L);
    }

    @Test
    void testSaveBill() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(billingRepository.save(any(Billing.class))).thenReturn(billing);

        Billing savedBill = billingService.saveBill(billing);

        assertNotNull(savedBill);
        assertEquals(order, savedBill.getOrder());
        verify(orderRepository, times(1)).findById(1L);
        verify(billingRepository, times(1)).save(billing);
    }

    @Test
    void testDeleteBill_Success() {
        when(billingRepository.existsById(1L)).thenReturn(true);

        Boolean result = billingService.deleteBill(1L);

        assertTrue(result);
        verify(billingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBill_Fail() {
        when(billingRepository.existsById(2L)).thenReturn(false);

        Boolean result = billingService.deleteBill(2L);

        assertFalse(result);
        verify(billingRepository, never()).deleteById(2L);
    }

    @Test
    void testUpdateBill_Success() {
        Billing updatedBill = new Billing();
        updatedBill.setTotalAmount(Double.valueOf(700.00));
        updatedBill.setPaymentType("Cash");
        updatedBill.setBillingDate(LocalDateTime.now());

        when(billingRepository.findAll()).thenReturn(Arrays.asList(billing));
        when(billingRepository.save(any(Billing.class))).thenReturn(billing);

        Billing result = billingService.updateBill(updatedBill, 1L);

        assertNotNull(result);
        assertEquals(700.00, result.getTotalAmount()); 
        assertEquals("Cash", result.getPaymentType());
        verify(billingRepository, times(1)).save(billing);
    }


    @Test
    void testUpdateBill_NotFound() {
        Billing updatedBill = new Billing();
        updatedBill.setTotalAmount(Double.valueOf(700.00));

        when(billingRepository.findAll()).thenReturn(Arrays.asList());

        Billing result = billingService.updateBill(updatedBill, 99L);

        assertNull(result);
        verify(billingRepository, never()).save(any(Billing.class));
    }
}
