package com.barclayshack.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barclayshack.backend.beans.Order;
import com.barclayshack.backend.beans.PaymentInfo;
import com.barclayshack.backend.service.PaymentService;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService service;

	@PostMapping("/payment")
	public PaymentOrderResponse createPaymentOrder(@RequestBody PaymentInfo paymentInfo) {
		return this.service.createPaymentOrder(paymentInfo);
	}

	@GetMapping("/details")
	public PaymentOrder getDetails(@RequestParam String paymentId) {
		return this.service.getOrderDetails(paymentId);
	}

	@GetMapping("/update")
	public void updatePaymentId(@RequestParam String paymentId, @RequestParam String transactionid) {
		this.service.updatePaymentId(paymentId, transactionid);
	}

	@GetMapping("/orders")
	public List<Order> getOrders(@RequestParam String username) {
		return this.service.getOrders(username);
	}

}
