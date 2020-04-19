package com.example.shubham.augmento10;

public class Model {

    private String title, description;
    private String image, modelLink,name;

    public Model() {

    }

    public Model(String title, String description, String image) {

        this.title = title;
        this.description = description;
        this.image = image;

    }

    public Model(String name,String title, String description, String image, String modelLink) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.image = image;
        this.modelLink = modelLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModelLink() {
        return modelLink;
    }

    public void setModelLink(String modelLink) {
        this.modelLink = modelLink;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}