import payment.PaymentRequest;
import payment.PaymentResult;
import payment.PaymentService;
import stripe.StripePaymentAdapter;
import stripe.StripePaymentService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Stripe Adapter Pattern Demo ===\n");

        StripePaymentService stripeService = new StripePaymentService();

        StripePaymentAdapter stripeAdapter = new StripePaymentAdapter(stripeService);

        PaymentService paymentService = new PaymentService(stripeAdapter);

        System.out.println(paymentService.getProcessorInfo());
        System.out.println();

        // Test successful payment
        testSuccessfulPayment(paymentService);

        // Test unsupported payment method
        testUnsupportedPaymentMethod(paymentService);

        // Test different payment amounts
        testDifferentAmounts(paymentService);
    }

    private static void testSuccessfulPayment(PaymentService paymentService) {
        System.out.println("--- Test 1: Successful Credit Card Payment ---");

        PaymentRequest request = new PaymentRequest(
                "ORD-12345",
                new BigDecimal("99.99"),
                "USD",
                "customer@example.com",
                "credit_card"
        );

        System.out.println("Request: " + request);
        PaymentResult result = paymentService.processOrder(request);
        System.out.println("Result: " + result);
        System.out.println();
    }

    private static void testUnsupportedPaymentMethod(PaymentService paymentService) {
        System.out.println("--- Test 2: Unsupported Payment Method ---");

        PaymentRequest request = new PaymentRequest(
                "ORD-12346",
                new BigDecimal("49.99"),
                "USD",
                "customer@example.com",
                "bitcoin"  // Not supported by Stripe
        );

        System.out.println("Request: " + request);
        PaymentResult result = paymentService.processOrder(request);
        System.out.println("Result: " + result);
        System.out.println();
    }

    private static void testDifferentAmounts(PaymentService paymentService) {
        System.out.println("--- Test 3: Different Payment Amounts ---");

        BigDecimal[] amounts = {
                new BigDecimal("10.50"),
                new BigDecimal("150.00"),
                new BigDecimal("0.99")
        };

        for (int i = 0; i < amounts.length; i++) {
            PaymentRequest request = new PaymentRequest(
                    "ORD-1234" + (7 + i),
                    amounts[i],
                    "USD",
                    "customer@example.com",
                    "visa"
            );

            System.out.println("Processing amount: $" + amounts[i]);
            PaymentResult result = paymentService.processOrder(request);
            System.out.println("Result: " + (result.isSuccess() ? "SUCCESS" : "FAILED") +
                    " - " + result.getMessage());

            if (result.getTransactionId() != null) {
                System.out.println("Transaction ID: " + result.getTransactionId());
            }
            System.out.println();
        }
    }
}

