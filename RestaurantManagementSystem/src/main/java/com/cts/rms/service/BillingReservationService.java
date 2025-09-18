package com.cts.rms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.rms.model.Billing;

import com.cts.rms.model.Orders;
import com.cts.rms.repository.BillingRepository;
import com.cts.rms.repository.OrderRepository;

@Service
public class BillingReservationService {

    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private OrderRepository orderRepository;
    
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }
    public Billing getById(Long id) {
        return billingRepository.findById(id).orElse(null);
    }
    
    public Billing saveBill(Billing bill) {
    	   Orders order = orderRepository.findById(bill.getOrder().getOrderId())
    	            .orElseThrow(() -> new RuntimeException("Order not found"));
              
            String itemName = order.getMenu().getItemName();
            int quantity = order.getQuantity();
            double price = order.getMenu().getPrice();

            double total = price * quantity;

            bill.setOrder(order);
            bill.setItemName(itemName);
            bill.setTotalAmount(total);
        return billingRepository.save(bill);
    }


    public Boolean deleteBill(Long id) {
        if (billingRepository.existsById(id)) {
            billingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Billing updateBill(Billing updatedBillData, Long id) {
        List<Billing> billList = billingRepository.findAll();
        for (Billing b : billList) {
            if (b.getBillingId().equals(id)) {
                b.setTotalAmount(updatedBillData.getTotalAmount());
                b.setPaymentType(updatedBillData.getPaymentType());
                b.setBillingDate(updatedBillData.getBillingDate()); 
                return billingRepository.save(b);
            }
        }
        return null;
    }
    
}