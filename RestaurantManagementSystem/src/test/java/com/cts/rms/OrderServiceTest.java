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

import com.cts.rms.model.Menu;
import com.cts.rms.model.Orders;
import com.cts.rms.model.User;
import com.cts.rms.repository.MenuRepository;
import com.cts.rms.repository.OrderRepository;
import com.cts.rms.repository.UserRepository;
import com.cts.rms.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Orders order;
    private Menu menu;

    @BeforeEach
    void setUp() {
        user = new User(1L, "JaneDoe", "pass456", "jane@gmail.com", "customer");
        menu = new Menu(101L, "Drinks", "Coffee", "Milk", 120.0);
        order = new Orders(1L, user, menu, 2);
    }

    @Test
    void testGetAllOrder() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Orders> result = orderService.getAllOrder();

        assertEquals(1, result.size());
        assertEquals("Coffee", result.get(0).getMenu().getItemName());
        verify(orderRepository, times(1)).findAll();
    }
    @Test
    void testGetById_Found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders result = orderService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(2, result.getQuantity());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        Orders result = orderService.getById(999L);

        assertNull(result);
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveOrder() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(menuRepository.findById(menu.getMenuId())).thenReturn(Optional.of(menu));
        when(orderRepository.save(order)).thenReturn(order);

        Orders result = orderService.saveOrder(order);

        assertNotNull(result);
        assertEquals(2, result.getQuantity());
        verify(userRepository, times(1)).findById(user.getUserId());
        verify(menuRepository, times(1)).findById(menu.getMenuId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);

        boolean result = orderService.deleteOrder(1L);

        assertTrue(result);
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrder_Failure() {
        when(orderRepository.existsById(999L)).thenReturn(false);

        boolean result = orderService.deleteOrder(999L);

        assertFalse(result);
        verify(orderRepository, times(1)).existsById(999L);
        verify(orderRepository, never()).deleteById(999L);
    }

    @Test
    void testUpdateOrder_Success() {
        Orders updatedOrder = new Orders(1L, user, menu, 5);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        Orders result = orderService.updateOrder(updatedOrder, 1L);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(orderRepository, times(1)).findAll();
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrder_NotFound() {
        Orders updatedOrder = new Orders(999L, user, menu, 10);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order)); // no match

        Orders result = orderService.updateOrder(updatedOrder, 999L);

        assertNull(result);
        verify(orderRepository, times(1)).findAll();
        verify(orderRepository, never()).save(any(Orders.class));
    }

}
