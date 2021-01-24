package com.barclayshack.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.barclayshack.backend.beans.Book;
import com.barclayshack.backend.beans.PaymentInfo;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

@Service
public class PaymentService {

	public PaymentOrderResponse createPaymentOrder(PaymentInfo paymentInfo) {
		/*
		 * Get a reference to the instamojo api
		 */
		ApiContext context = ApiContext.create("test_Q9MJitOf3dVQ54HoDPJugC6EyhYttMuT604",
				"test_HzOuF9XX5fNgETOfuhet0aZLJIIfHw48t3VMLcE2MGXLbf6eW3ug5SzbaKGCAp2gbaNHyvCcXrGpPCZjJZ33QGUM92lcP1h8jKBMdX4hZF51kxgc8JlD9znz9vj",
				ApiContext.Mode.TEST);
		Instamojo api = new InstamojoImpl(context);

		/*
		 * Create a new payment order
		 */
		PaymentOrder order = new PaymentOrder();
		order.setName(paymentInfo.getName());
		order.setEmail(paymentInfo.getEmail());
		order.setPhone(paymentInfo.getPhone());
		order.setCurrency("INR");

		int amount = 0;

		for (Book book : paymentInfo.getItems()) {
			amount += (book.getPrice() * paymentInfo.getCount().get(book.getId()));
		}

		order.setAmount(Double.valueOf(amount));
		order.setDescription("This is a test transaction.");
		order.setRedirectUrl("http://localhost:4200/barclays-hack-app/order");
		// order.setWebhookUrl("http://www.someurl.com/");
		order.setTransactionId(UUID.randomUUID().toString());

		PaymentOrderResponse paymentOrderResponse = null;

		try {

			paymentOrderResponse = api.createPaymentOrder(order);
			System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());

		} catch (HTTPException e) {
			System.out.println(e.getStatusCode());
			System.out.println(e.getMessage());
			System.out.println(e.getJsonPayload());

		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}

		return paymentOrderResponse;
	}

	public PaymentOrder getOrderDetails(String paymentId) {
		PaymentOrder paymentOrder = null;
		try {

			ApiContext context = ApiContext.create("test_Q9MJitOf3dVQ54HoDPJugC6EyhYttMuT604",
					"test_HzOuF9XX5fNgETOfuhet0aZLJIIfHw48t3VMLcE2MGXLbf6eW3ug5SzbaKGCAp2gbaNHyvCcXrGpPCZjJZ33QGUM92lcP1h8jKBMdX4hZF51kxgc8JlD9znz9vj",
					ApiContext.Mode.TEST);
			Instamojo api = new InstamojoImpl(context);

			paymentOrder = api.getPaymentOrder(paymentId);
			System.out.println(paymentOrder.getId());
			System.out.println(paymentOrder.getStatus());

		} catch (HTTPException e) {
			System.out.println(e.getStatusCode());
			System.out.println(e.getMessage());
			System.out.println(e.getJsonPayload());

		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
		return paymentOrder;
	}

}
