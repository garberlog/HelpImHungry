package marshmallowboom.com.helpimhungry;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Async process: <Type of data sent to download task, name of the method used to show progress
// of task(not implemented yet), type of var to be returned by the download>
public class DownloadTask extends AsyncTask<String, Void, ArrayList<Recipe>> {
    final String API_KEY = "4La0d7DdzBmshTuDWzkODqADfK4up1vuJkljsnpNaMfLWInYch";
    //String... is same as var[] args. Essentially an array of content passed in
    @Override
    protected ArrayList<Recipe> doInBackground(String... ingredients) {
        String searchResult = "";
        //Proper url format below
        //"https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar&limitLicense=false&number=1&ranking=1";
        final String base = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=";
        final String tail = "&limitLicense=false&number=3&ranking=1";
        StringBuilder recStrBldr = new StringBuilder("");
        String seperator = "%2C";
        int numIngredients = ingredients.length;
        int lastIngredient = numIngredients-1;
        //Build the ingredientString based on the above url format using %2C for separators
        for(int k = 0; k < numIngredients; k++ ) {
            String curIng = ingredients[k];
            recStrBldr.append(curIng);
            if(k!=lastIngredient) {
                recStrBldr.append(seperator);
            }
        }
        String stringUrl = base+recStrBldr.toString()+tail;
        URL url;
        HttpURLConnection urlConnection = null;
        String recAsString = "";
        try {
            //Convert the string passed from doInBackground into a URL object
            url = new URL(stringUrl);
            //establish the connection to the url
            urlConnection = (HttpURLConnection)url.openConnection();
            //Inject the API key into the request header
            urlConnection.setRequestProperty("X-Mashape-Key", API_KEY);
            //Stream to hold the input of data from the page we're connecting to
            InputStream in = urlConnection.getInputStream();
            //Used to read the data in the InputStream
            InputStreamReader reader = new InputStreamReader(in);
            //Gonna read the data one character at a time
            int data = reader.read();
            while (data != -1) {
                //Get the current character, append it to result, then continue
                char current = (char) data;
                searchResult += current;
                data = reader.read();
            }
            //Parse the data to get recipe Id, make call to get instructions using the Id, add to recipe ArrayList
            ArrayList<Recipe> MatchedRecipeList = new ArrayList<Recipe>();
            String id = null;
            String recName = null;
            ArrayList<String> instructions = null;
            ArrayList<String> ingredList = null;
            //Create JSON array from search results, then loop through each json object in the array to extract the data
            try {
                JSONArray arr = new JSONArray(searchResult);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    id = jsonPart.getString("id");
                    recName = jsonPart.getString("title");
                    instructions = getInstructions(id);
                    ingredList = getIngredients(id);
                    MatchedRecipeList.add(new Recipe(id, recName, instructions, ingredList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return the result back to the original task
            return MatchedRecipeList;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private ArrayList<String> getInstructions(String recipeId){
        String searchResult = "";
        //Proper url format listed below.
        //"https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/479101/information?includeNutrition=false"
        String base = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";
        String Id = recipeId;
        String tail = "/information?includeNutrition=false";
        String stringUrl = base+Id+tail;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            //Convert the string passed from doInBackground into a URL object
            url = new URL(stringUrl);
            //establish the connection to the url
            urlConnection = (HttpURLConnection)url.openConnection();
            //Inject the API key into the request header
            urlConnection.setRequestProperty("X-Mashape-Key", API_KEY);
            //Stream to hold the input of data from the page we're connecting to
            InputStream in = urlConnection.getInputStream();
            //Used to read the data in the InputStream
            InputStreamReader reader = new InputStreamReader(in);
            //Gonna read the data one character at a time
            int data = reader.read();
            while (data != -1) {
                //Get the current character, append it to result, then continue
                char current = (char) data;
                searchResult += current;
                data = reader.read();
            }
            ArrayList<String> instructions = new ArrayList<>();
            //Create JSON object from the search results
            try {
                JSONObject jObj = new JSONObject(searchResult);
                //Test code to check the keys we have to work with
//                Iterator<String> keys = jObj.keys();
//                Log.i("Keys Available: ",keys.toString());
                JSONArray arr = jObj.getJSONArray("analyzedInstructions");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    JSONArray steps = jsonPart.getJSONArray("steps");
                    for (int j = 0; j < steps.length(); i++) {
                        JSONObject stepObj = steps.getJSONObject(i);
                        String curStep = stepObj.getString("step");
                        instructions.add(curStep);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return instructions;
        }
        catch(Exception e) {
            e.printStackTrace();
            //***We can check for this failed value when we return from the task.
            return null;
        }
    }
    private ArrayList<String> getIngredients(String recipeId){
        String searchResult = "";
        //Proper url format listed below.
        //"https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/479101/information?includeNutrition=false"
        String base = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";
        String Id = recipeId;
        String tail = "/information?includeNutrition=false";
        String stringUrl = base+Id+tail;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            //Convert the string passed from doInBackground into a URL object
            url = new URL(stringUrl);
            //establish the connection to the url
            urlConnection = (HttpURLConnection)url.openConnection();
            //Inject the API key into the request header
            urlConnection.setRequestProperty("X-Mashape-Key", API_KEY);
            //Stream to hold the input of data from the page we're connecting to
            InputStream in = urlConnection.getInputStream();
            //Used to read the data in the InputStream
            InputStreamReader reader = new InputStreamReader(in);
            //Gonna read the data one character at a time
            int data = reader.read();
            while (data != -1) {
                //Get the current character, append it to result, then continue
                char current = (char) data;
                searchResult += current;
                data = reader.read();
            }
            ArrayList<String> ingredients = new ArrayList<>();
            //Create JSON object from the search results
            try {
                JSONObject jObj = new JSONObject(searchResult);
                JSONArray arr = jObj.getJSONArray("extendedIngredients");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String curIng = jsonPart.getString("original");
                    ingredients.add(curIng);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ingredients;
        }
        catch(Exception e) {
            e.printStackTrace();
            //***We can check for this failed value when we return from the task.
            return null;
        }
    }
}