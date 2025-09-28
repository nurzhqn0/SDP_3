package stripe;

import payment.PaymentRequest;
import payment.PaymentResult;
import payment.interfaces.PaymentProcessor;

import java.math.BigDecimal;

public class StripePaymentAdapter implements PaymentProcessor {
    private static final String PROCESSOR_NAME = "Stripe";
    private static final BigDecimal CENTS_MULTIPLIER = new BigDecimal("100");

    private final StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = validateNotNull(stripeService, "Stripe service cannot be null");
    }

    /**
     * Adapts our processPayment method to Stripe's createCharge method.
     * This is where the translation happens.
     */
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        validateRequest(request);

        try {
            // Translate our data format to Stripe's expected format
            String amountInCents = convertAmountToCents(request.getAmount());

            System.out.println("Adapter: Converting payment request to Stripe format");
            System.out.println("Adapter: Amount " + request.getAmount() + " -> " + amountInCents + " cents");

            // Call the adaptee's method with translated parameters
            StripeChargeResult stripeResult = stripeService.createCharge(
                    amountInCents,
                    request.getCurrency().toLowerCase(),
                    request.getCustomerEmail(),
                    request.getPaymentMethod()
            );

            return convertStripeResultToPaymentResult(stripeResult);

        } catch (Exception e) {
            return PaymentResult.failure("Stripe processing error: " + e.getMessage());
        }
    }

    @Override
    public boolean isPaymentMethodSupported(String paymentMethod) {
        return stripeService.supportsPaymentMethod(paymentMethod);
    }

    @Override
    public String getProcessorName() {
        return PROCESSOR_NAME;
    }

    private String convertAmountToCents(BigDecimal amount) {
        return amount.multiply(CENTS_MULTIPLIER).toBigInteger().toString();
    }

    private PaymentResult convertStripeResultToPaymentResult(StripeChargeResult stripeResult) {
        System.out.println("Adapter: Converting Stripe result: " + stripeResult);

        if ("succeeded".equals(stripeResult.getStatus())) {
            return PaymentResult.success(stripeResult.getId());
        } else {
            return PaymentResult.failure("Stripe charge failed: " + stripeResult.getDescription());
        }
    }

    private void validateRequest(PaymentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Payment request cannot be null");
        }
    }

    private <T> T validateNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}