package com.cts.rms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;


@Entity
public class Orders {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long orderId;
 
 @ManyToOne
@JoinColumn(name = "user_id" , referencedColumnName = "userId")
 private User user;
 
 @ManyToOne
@JoinColumn(name = "menu_id" , referencedColumnName = "menuId")
 private Menu menu;
 
 @Min(value = 1, message = "Quantity must be at least 1")
 private Integer quantity;

 
 @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
 private Billing billing;

 
 public Billing getBilling() {
	return billing;
}

 public void setBilling(Billing billing) {
	this.billing = billing;
 }

 public Orders() {
}

 public Orders(Long orderId, User user, Menu menu, Integer quantity) {
	 this.orderId = orderId;
	 this.user=user;
	 this.menu = menu;
	 this.quantity = quantity;
 }

 public Long getOrderId() {
	return orderId;
 }

 public void setOrderId(Long orderId) {
	this.orderId = orderId;
 }

 public User getUser() {
	return user;
 }

 public void setUser(User user) {
	this.user = user;
 }

 public Menu getMenu() {
	return menu;
 }

 public void setMenu(Menu menu) {
	this.menu = menu;
 }

 public Integer getQuantity() {
	return quantity;
 }

 public void setQuantity(Integer quantity) {
	this.quantity = quantity;
 }
 

 
}
