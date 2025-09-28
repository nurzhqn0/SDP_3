package payment;

import payment.interfaces.PaymentProcessor;

public class PaymentService {
    private final PaymentProcessor paymentProcessor;

    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = validateNotNull(paymentProcessor, "Payment processor cannot be null");
    }

    public PaymentResult processOrder(PaymentRequest request) {
        System.out.println("PaymentService: Processing order " + request.getOrderId());

        if (!paymentProcessor.isPaymentMethodSupported(request.getPaymentMethod())) {
            String message = String.format(
                    "Payment method '%s' is not supported by %s",
                    request.getPaymentMethod(),
                    paymentProcessor.getProcessorName()
            );
            return PaymentResult.failure(message);
        }

        System.out.println("PaymentService: Using " + paymentProcessor.getProcessorName() + " processor");
        PaymentResult result = paymentProcessor.processPayment(request);

        System.out.println("PaymentService: Payment result - " +
                (result.isSuccess() ? "SUCCESS" : "FAILED"));

        return result;
    }

    public String getProcessorInfo() {
        return "Using processor: " + paymentProcessor.getProcessorName();
    }

    private <T> T validateNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
