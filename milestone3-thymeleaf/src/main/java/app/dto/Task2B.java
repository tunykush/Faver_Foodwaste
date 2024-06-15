package app.dto;

public class Task2B {

    private String commodity;
    private Integer year;
    private Double lossPercentage;
    private String activity;
    private String foodSupplyStage;
    private String causeOffLoss;

    public Task2B() {}

    public Task2B( String commodity, Integer year, Double lossPercentage,
    String activity, String foodSupplyStage,
    String causeOffLoss) {
        this.commodity = commodity;
        this.year = year;
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
    public Integer getYear() {
        return year;
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
    public void setYear(Integer year) {
        this.year = year;
    }
}