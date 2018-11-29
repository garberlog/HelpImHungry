package marshmallowboom.com.helpimhungry;

public class Recipe {

    private String Id;
    private String recipeName;
    private String recipeInstructions;



    public Recipe(String id, String recipeName, String recipeInstructions) {
        this.Id = id;
        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
    }

    public String getId() {
        return Id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    @Override
    public String toString(){

        return ( "Id: "+this.Id+" - Name: "+this.recipeName+" - Instructions: "+this.recipeInstructions);
    }


}
