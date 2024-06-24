package app.dto;

public class Task2B {

    private String commodity;
    private double start;
    private double end;
    private Double lossPercentage;
    private String activity;
    private String foodSupplyStage;
    private String causeOffLoss;

    public Task2B() {}

    public Task2B( String commodity, double start, double end, Double lossPercentage,
    String activity, String foodSupplyStage,
    String causeOffLoss) {
        this.commodity = commodity;
        this.start = start;
        this.end = end;
        this.lossPercentage = lossPercentage;
        this.activity = activity;
        this.foodSupplyStage = foodSupplyStage;
        this.causeOffLoss = causeOffLoss;
    }

    public String getActivity() {
        return activity;
    }
    public String getCauseOffLoss() {
        return causeOffLoss;
    }
    public String getCommodity() {
        return commodity;
    }
    public String getFoodSupplyStage() {
        return foodSupplyStage;
    }
    public Double getLossPercentage() {
        return lossPercentage;
    }
    public double getStart() {
        return start;
    }
    public double getEnd() {
        return end;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public void setCauseOffLoss(String causeOffLoss) {
        this.causeOffLoss = causeOffLoss;
    }
    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
    public void setFoodSupplyStage(String foodSupplyStage) {
        this.foodSupplyStage = foodSupplyStage;
    }
    public void setLossPercentage(Double lossPercentage) {
        this.lossPercentage = lossPercentage;
    }
    public void setStart(double x) {
        this.start = x;
    }
    public void setEnd(double x) {
        this.end = x;
    }
}