package app.entities;

public class User {
    private String background;
    private String needs;
    private String imageFilePath;

    public User(String background, String needs, String imageFilePath){
        this.background = background;
        this.needs = needs;
        this.imageFilePath = imageFilePath;
    }


    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
