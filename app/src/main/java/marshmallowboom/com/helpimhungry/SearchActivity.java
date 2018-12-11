package marshmallowboom.com.helpimhungry;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.SearchView;
import android.widget.ListView;
import android.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                SearchView.OnQueryTextListener{


    ArrayList<String> adaptorNames;
    ListAdaptor listAdaptor;
    ListView listView;
    ArrayList<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Search bar
        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setSubmitButtonEnabled(true);
        searchBar.setOnQueryTextListener(this);
        //NOTE:
        //Remove after display works!
        adaptorNames = new ArrayList<String>();
        //add values into list

        //Setup adaptor List
        listView = this.findViewById(R.id.resultList);
        listAdaptor = new ListAdaptor(this ,adaptorNames, R.id.recipeItems, R.layout.result_list_items, "recipe");
        listView.setAdapter(listAdaptor);

        //initialize the recipes list that is returned from the API
        recipes = new ArrayList<Recipe>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_login){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_pantry) {
            Intent intent = new Intent(this, PantryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_recipes) {
            Intent intent = new Intent(this, MyRecipesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Processes Query after user taps "Submit" button
    @Override
    public boolean onQueryTextSubmit(String query) {

        //Debugging line:
        Log.d("QUERY", "word: " + query);
        //Handles search query
        DownloadTask task = new DownloadTask();
        ArrayList<String> queryList = new ArrayList<>();
        query = query.toLowerCase(Locale.getDefault());

        if(query.length() == 0){
            recipes.clear();
            listAdaptor.clear();
        }else{//if not empty
            //turns comma separated string into array
            int loc = query.indexOf(',');
            while(loc != -1){
                queryList.add(query.substring(0, loc));
                query = query.substring(loc+1);
                loc = query.indexOf(',');
            }
            queryList.add(query);
            String[] queryArray = new String[queryList.size()];
            //gets recipes from database
            listAdaptor.setCurrentIngredient(query);
            try {
                recipes = task.execute(queryList.toArray(queryArray)).get();
                listAdaptor.clear();
                updateUI(recipes);
            } catch (InterruptedException e) {
                e.printStackTrace();
                listAdaptor.clear();
            } catch (ExecutionException e) {
                e.printStackTrace();
                listAdaptor.clear();
            }
        }
        return false;
    }

    public void updateUI(ArrayList<Recipe> recipes) {
        for(Recipe rep : recipes){
            Log.d("UPDATEUI", "ADDED: "+rep.getRecipeName() );
            listAdaptor.addItem(rep.getRecipeName());
        }
        listAdaptor.setRecipes(recipes);
    }

    //Handle text changes
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;

        return false;
    }


}
