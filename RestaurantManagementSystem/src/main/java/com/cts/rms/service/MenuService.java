package com.cts.rms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.rms.model.Menu;
import com.cts.rms.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    public Menu getById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }
    
    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Boolean deleteMenu(Long id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Menu updateMenu(Menu updateMenu, Long id) {
        List<Menu> menuList = menuRepository.findAll();
        for (Menu m : menuList) {
            if (m.getMenuId().equals(id)) {
                m.setCategory(updateMenu.getCategory());
                m.setItemName(updateMenu.getItemName());
                m.setDescription(updateMenu.getDescription());
                m.setPrice(updateMenu.getPrice());
                return menuRepository.save(m);
            }
        }
        return null;
    }
}
