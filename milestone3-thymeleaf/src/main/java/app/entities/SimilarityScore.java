package app.entities;

public class SimilarityScore {
    private String country;
    private String region;
    private double absoluteSimilarity;
    private double levelOfOverlap;

    public SimilarityScore(String country, String region, double absoluteSimilarity, double levelOfOverlap) {
        this.country = country;
        this.region = region;
        this.absoluteSimilarity = absoluteSimilarity;
        this.levelOfOverlap = levelOfOverlap;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getAbsoluteSimilarity() {
        return absoluteSimilarity;
    }

    public double getLevelOfOverlap() {
        return levelOfOverlap;
    }

    public double getSimilarityScore() {
        return absoluteSimilarity;
    }
}
