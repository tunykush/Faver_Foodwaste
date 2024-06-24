package app.entities;

import java.util.List;

public class FoodLossEvent {
    private List<String> country;
    private String startLoss;
    private String endLoss;
    private String startYear;
    private String endYear;
    private String diffLoss;
    private String commodity;
    private String activity;
    private String foodSupplyStage;
    private String causeOfLoss;

    public FoodLossEvent(List<String> country, String startLoss, String endLoss, String diffLoss, String startYear, String endYear,
                         String commodity, String activity, String foodSupplyStage, String causeOfLoss) {
        this.country = country;
        this.startLoss = startLoss;
        this.endLoss = endLoss;
        this.diffLoss = diffLoss;
        this.startYear = startYear;
        this.endYear = endYear;
        this.commodity = commodity;
        this.activity = activity;
        this.foodSupplyStage = foodSupplyStage;
        this.causeOfLoss = causeOfLoss;
    }

    // Getters and setters for all fields
    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public String getstartLoss() {
        return startLoss;
    }

    public void setstartLoss(String startLoss) {
        this.startLoss = startLoss;
    }

    public String getendLoss() {
        return endLoss;
    }

    public void setendLoss(String endLoss) {
        this.endLoss = endLoss;
    }

    public String getDiffLoss() {
        return diffLoss;
    }

    public void setDiffLoss(String diffLoss) {
        this.diffLoss = diffLoss;
    }

    public String getStartYear() {
        return startYear;
    }


    public String getEndYear() {
        return endYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }


    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getFoodSupplyStage() {
        return foodSupplyStage;
    }

    public void setFoodSupplyStage(String foodSupplyStage) {
        this.foodSupplyStage = foodSupplyStage;
    }

    public String getCauseOfLoss() {
        return causeOfLoss;
    }

    public void setCauseOfLoss(String causeOfLoss) {
        this.causeOfLoss = causeOfLoss;
    }

    @Override
    public String toString() {
        String a = "Name: %s Start Loss: %s End Loss: %s, Diff Loss: %s, Commodity: %s, Activity: %s, Supply Stage: %s, Cause: %s".formatted(country, startLoss, endLoss, diffLoss, commodity, activity, foodSupplyStage, causeOfLoss);
    return a;
    }
}