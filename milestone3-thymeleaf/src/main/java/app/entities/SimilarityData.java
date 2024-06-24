package app.entities;

public class SimilarityData {
    private String country;
    private String region;
    private int commonProducts;
    private int totalProducts;
    private double averageLossPercentage;

    public SimilarityData(String country, String region, int commonProducts, int totalProducts, double averageLossPercentage) {
        this.country = country;
        this.region = region;
        this.commonProducts = commonProducts;
        this.totalProducts = totalProducts;
        this.averageLossPercentage = averageLossPercentage;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public int getCommonProducts() {
        return commonProducts;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public double getAverageLossPercentage() {
        return averageLossPercentage;
    }
}
