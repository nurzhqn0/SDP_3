package stripe;

import java.util.UUID;

public class StripePaymentService {
    public StripeChargeResult createCharge(String amountInCents, String currency,
                                           String customerEmail, String paymentSource) {
        try {
            Thread.sleep(100);

            String chargeId = "ch_" + UUID.randomUUID().toString().substring(0, 8);
            System.out.println("Stripe API: Creating charge for " + amountInCents + " cents");

            if (Integer.parseInt(amountInCents) > 0) {
                return new StripeChargeResult(chargeId, "succeeded",
                        "Charge for " + amountInCents + " cents " + currency);
            } else {
                return new StripeChargeResult(null, "failed", "Invalid amount");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new StripeChargeResult(null, "failed", "Network error");
        } catch (NumberFormatException e) {
            return new StripeChargeResult(null, "failed", "Invalid amount format");
        }
    }

    public boolean supportsPaymentMethod(String method) {
        return "credit_card".equals(method) ||
                "debit_card".equals(method) ||
                "visa".equals(method) ||
                "mastercard".equals(method);
    }

    public int calculateFee(String amountInCents) {
        try {
            int amount = Integer.parseInt(amountInCents);
            return (int) (amount * 0.029) + 30; // 2.9% + $0.30
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
