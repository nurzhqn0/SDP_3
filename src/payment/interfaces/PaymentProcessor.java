package payment.interfaces;

import payment.PaymentRequest;
import payment.PaymentResult;

public interface PaymentProcessor {
    PaymentResult processPayment(PaymentRequest request);
    boolean isPaymentMethodSupported(String paymentMethod);
    String getProcessorName();
}

