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
import com.cts.rms.repository.MenuRepository;
import com.cts.rms.service.MenuService;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu(101L, "Drinks", "Coffee", "Milk", 120.0);
    }

    @Test
    void testGetAllMenu() {
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));

        List<Menu> result = menuService.getAllMenu();

        assertEquals(1, result.size());
        assertEquals("Coffee", result.get(0).getItemName());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        when(menuRepository.findById(101L)).thenReturn(Optional.of(menu));

        Menu result = menuService.getById(101L);

        assertNotNull(result);
        assertEquals(101, result.getMenuId());
        assertEquals("Coffee", result.getItemName());
        verify(menuRepository, times(1)).findById(101L);
    }

    @Test
    void testGetById_NotFound() {
        when(menuRepository.findById(999L)).thenReturn(Optional.empty());

        Menu result = menuService.getById(999L);

        assertNull(result);
        verify(menuRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveMenu() {
        when(menuRepository.save(menu)).thenReturn(menu);

        Menu result = menuService.saveMenu(menu);

        assertNotNull(result);
        assertEquals("Drinks", result.getCategory());
        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    void testDeleteMenu_Success() {
        when(menuRepository.existsById(101L)).thenReturn(true);
        doNothing().when(menuRepository).deleteById(101L);

        boolean result = menuService.deleteMenu(101L);

        assertTrue(result);
        verify(menuRepository, times(1)).existsById(101L);
        verify(menuRepository, times(1)).deleteById(101L);
    }

    @Test
    void testDeleteMenu_Failure() {
        when(menuRepository.existsById(999L)).thenReturn(false);

        boolean result = menuService.deleteMenu(999L);

        assertFalse(result);
        verify(menuRepository, times(1)).existsById(999L);
        verify(menuRepository, never()).deleteById(999L);
    }

    @Test
    void testUpdateMenu_Success() {
        Menu updatedMenu = new Menu(101L, "Snacks", "Burger", "Veggie Burger", 150.0);

        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));
        when(menuRepository.save(any(Menu.class))).thenReturn(updatedMenu);

        Menu result = menuService.updateMenu(updatedMenu, 101L);

        assertNotNull(result);
        assertEquals("Burger", result.getItemName());
        verify(menuRepository, times(1)).findAll();
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void testUpdateMenu_NotFound() {
        Menu updatedMenu = new Menu(999L, "Snacks", "Pizza", "Cheese Pizza", 200.0);

        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));

        Menu result = menuService.updateMenu(updatedMenu, 999L);

        assertNull(result);
        verify(menuRepository, times(1)).findAll();
        verify(menuRepository, never()).save(any(Menu.class));
    }
}
