package payment;

import java.math.BigDecimal;

public class PaymentRequest {
    private final String orderId;
    private final BigDecimal amount;
    private final String currency;
    private final String customerEmail;
    private final String paymentMethod;

    public PaymentRequest(String orderId, BigDecimal amount, String currency,
                          String customerEmail, String paymentMethod) {
        this.orderId = validateNotEmpty(orderId, "Order ID cannot be empty");
        this.amount = validateAmount(amount);
        this.currency = validateNotEmpty(currency, "Currency cannot be empty");
        this.customerEmail = validateEmail(customerEmail);
        this.paymentMethod = validateNotEmpty(paymentMethod, "Payment method cannot be empty");
    }

    // Getters
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCustomerEmail() { return customerEmail; }
    public String getPaymentMethod() { return paymentMethod; }

    // Private validation methods following clean code principles
    private String validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount;
    }

    private String validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email.trim();
    }

    @Override
    public String toString() {
        return String.format("PaymentRequest{orderId='%s', amount=%s, currency='%s', paymentMethod='%s'}",
                orderId, amount, currency, paymentMethod);
    }
}