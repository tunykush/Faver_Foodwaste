package app.entities;

import java.util.List;

public class DropDownData {
    private List<String> countries;
    private List<String> years;

    // Getters and Setters
    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
}
