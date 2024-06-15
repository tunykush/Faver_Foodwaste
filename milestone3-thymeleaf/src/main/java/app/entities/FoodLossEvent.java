package app.entities;

public class FoodLossEvent {

    private String m49Code;
    private String country;
    private String region;
    private Double cpc_code;
    private String commodity;
    private Integer year;
    private Double lossPercentage;
    private String activity;
    private String foodSupplyStage;
    private String causeOffLoss;

    public FoodLossEvent(String m49Code,
            String country, String region,
            Double cpc_code, String commodity,
            Integer year, Double lossPercentage,
            String activity, String foodSupplyStage,
            String causeOffLoss) {

        this.m49Code = m49Code;
        this.country = country;
        this.region = region;
        this.cpc_code = cpc_code;
        this.commodity = commodity;
        this.year = year;
        this.lossPercentage = lossPercentage;
        this.activity = foodSupplyStage;
        this.causeOffLoss = causeOffLoss;
    }

    public FoodLossEvent( String commodity, Integer year, Double lossPercentage,
    String activity, String foodSupplyStage,
    String causeOffLoss) {
        this.commodity = commodity;
        this.year = year;
        this.lossPercentage = lossPercentage;
        this.activity = foodSupplyStage;
        this.causeOffLoss = causeOffLoss;
    }

    public FoodLossEvent() {
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

    public String getCountry() {
        return country;
    }

    public Double getCpc_code() {
        return cpc_code;
    }

    public String getFoodSupplyStage() {
        return foodSupplyStage;
    }

    public Double getLossPercentage() {
        return lossPercentage;
    }

    public String getM49Code() {
        return m49Code;
    }

    public String getRegion() {
        return region;
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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCpc_code(Double cpc_code) {
        this.cpc_code = cpc_code;
    }

    public void setFoodSupplyStage(String foodSupplyStage) {
        this.foodSupplyStage = foodSupplyStage;
    }

    public void setLossPercentage(Double lossPercentage) {
        this.lossPercentage = lossPercentage;
    }

    public void setM49Code(String m49Code) {
        this.m49Code = m49Code;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
