package com.cts.rms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    @NotNull(message = "A bill must be associated with an order")
    private Orders order;
    
   
    private String itemName;   
    @PastOrPresent(message = "Billing date cannot be in the future")
    @NotNull(message = "Billing date cannot be null")
    private LocalDateTime billingDate;
    
    @NotNull(message = "Invoice number cannot be null")
    private Integer invoiceNumber;

    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    private Double totalAmount;

    @Size(min = 1, max = 1, message = "Payment type")
    private String paymentType;

    public Billing() {
    }
	public Billing(Long billingId,Integer invoiceNumber, Orders order, String itemName, LocalDateTime billingDate, String paymentType,Double totalAmount ) {
		this.billingId = billingId;
		this.order = order;
		this.itemName = itemName;
		this.billingDate = billingDate;
		this.invoiceNumber = invoiceNumber;
		this.totalAmount = totalAmount;
		this.paymentType = paymentType;
	}
	public Long getBillingId() {
		return billingId;
	}

	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public LocalDateTime getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(LocalDateTime billingDate) {
		this.billingDate = billingDate;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}



}