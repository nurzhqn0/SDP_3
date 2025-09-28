package payment;

import payment.types.PaymentStatus;

public class PaymentResult {
    private final boolean success;
    private final String transactionId;
    private final String message;
    private final PaymentStatus status;

    public PaymentResult(boolean success, String transactionId, String message, PaymentStatus status) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
        this.status = status;
    }

    public static PaymentResult success(String transactionId) {
        return new PaymentResult(true, transactionId, "Payment processed successfully", PaymentStatus.COMPLETED);
    }

    public static PaymentResult failure(String message) {
        return new PaymentResult(false, null, message, PaymentStatus.FAILED);
    }

    public boolean isSuccess() { return success; }
    public String getTransactionId() { return transactionId; }
    public String getMessage() { return message; }
    public PaymentStatus getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("PaymentResult{success=%s, transactionId='%s', message='%s', status=%s}",
                success, transactionId, message, status);
    }
}