package com.cts.rms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.rms.model.Menu;
import com.cts.rms.model.Orders;
import com.cts.rms.model.User;
import com.cts.rms.repository.MenuRepository;
import com.cts.rms.repository.OrderRepository;
import com.cts.rms.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	

	@Autowired
	private UserRepository userRepository;
	


	
	public List<Orders> getAllOrder(){
		return orderRepository.findAll();
	}
	

    public Orders getById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public Orders saveOrder(Orders order) {
    	 User user = userRepository.findById(order.getUser().getUserId())
    	            .orElseThrow(() -> new RuntimeException("User not found"));

    	  Menu menu = menuRepository.findById(order.getMenu().getMenuId())
    	            .orElseThrow(() -> new RuntimeException("Menu not found"));

    	    order.setUser(user);
    	    order.setMenu(menu);

      
        return orderRepository.save(order);
    }
	
	public Boolean deleteOrder(Long id) {
		if(orderRepository.existsById(id)) {
			orderRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Orders updateOrder(Orders updateOrder, Long id) {
		List<Orders> orderList =  orderRepository.findAll();
		for(Orders o : orderList) {
			if(o.getOrderId().equals(id)) {
				o.setQuantity(updateOrder.getQuantity());
				return orderRepository.save(o);
			}
		}
		 return null;
	}
}
