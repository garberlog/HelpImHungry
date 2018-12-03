package marshmallowboom.com.helpimhungry;


import java.util.ArrayList;

public class Recipe {

    private String Id;
    private String recipeName;
    private ArrayList<String> recipeInstructions;
    private ArrayList<String> recipeIngredients;



    public Recipe(String id, String recipeName, ArrayList<String> recipeInstructions, ArrayList<String> recipeIngredients) {
        this.Id = id;
        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
        this.recipeIngredients = recipeIngredients;
    }

    public String getId() {
        return Id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<String> getRecipeInstructions() {
        return recipeInstructions;
    }

    public ArrayList<String> getRecipeIngredients(){
        return recipeIngredients;
    }

    @Override
    public String toString(){

        return ( "Id: "+this.Id+" - Name: "+this.recipeName+" - Instructions: "+this.recipeInstructions);
    }


}
