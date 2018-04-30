package com.nanodegree.nahla.cupbake.models.recipeListing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by NAHLA on 3/25/2018.
 */


public class ResponseRecipeListing implements Parcelable{

    private long id;
    private String name;
    private ArrayList<Ingredient> ingredients = null;
    private ArrayList<Step> steps = null;
    private long servings;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public long getServings() {
        return servings;
    }

    public void setServings(long servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeLong(this.servings);
        dest.writeString(this.image);
    }

    public ResponseRecipeListing() {
    }

    protected ResponseRecipeListing(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = in.createTypedArrayList(Step.CREATOR);
        this.servings = in.readLong();
        this.image = in.readString();
    }

    public static final Creator<ResponseRecipeListing> CREATOR = new Creator<ResponseRecipeListing>() {
        @Override
        public ResponseRecipeListing createFromParcel(Parcel source) {
            return new ResponseRecipeListing(source);
        }

        @Override
        public ResponseRecipeListing[] newArray(int size) {
            return new ResponseRecipeListing[size];
        }
    };
}
