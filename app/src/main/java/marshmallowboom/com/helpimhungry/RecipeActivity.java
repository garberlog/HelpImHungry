package marshmallowboom.com.helpimhungry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        TextView recipeView = findViewById(R.id.RecipeName);
        recipeView.setText(recipeName);
        ArrayList<String> ingredientList = intent.getStringArrayListExtra("ingredients");
        TextView ingredientView = findViewById(R.id.IngredientsView);
        String ingredientText = new String();
        if(ingredientList != null && ingredientList.size() > 0) {
            for (int i = 0; i < ingredientList.size(); i++) {
                ingredientText += ingredientList.get(i) + "\n";
            }
        }
        ingredientView.setText(ingredientText);
        ArrayList<String> instructionList = intent.getStringArrayListExtra("instructions");
        TextView instructionView = findViewById(R.id.InstructionsView);
        String instructionText = new String();
        if(instructionList != null && instructionList.size() > 0){
            for(int i = 0; i < instructionList.size(); i ++){
                instructionText += instructionList.get(i) + "\n\n";
            }
        }
        instructionView.setText(instructionText);
    }
}
