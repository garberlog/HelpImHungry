package marshmallowboom.com.helpimhungry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        TextView recipeView = (TextView)findViewById(R.id.RecipeName);
        recipeView.setText(recipeName);
        String ingredientList = intent.getStringExtra("ingredients");
        TextView ingredientView = (TextView)findViewById(R.id.IngredientsView);
        ingredientView.setText(ingredientList);
        String instructionList = intent.getStringExtra("instructions");
        TextView instructionView = (TextView)findViewById(R.id.InstructionsView);
        instructionView.setText(instructionList);
    }
}
