package stripe;

public class StripeChargeResult {
    private final String id;
    private final String status;
    private final String description;

    public StripeChargeResult(String id, String status, String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("StripeChargeResult{id='%s', status='%s', description='%s'}",
                id, status, description);
    }
}