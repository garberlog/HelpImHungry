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

    String[] recipeNames;
    List<String> adaptorNames;
    ListAdaptor listAdaptor;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Search bar
        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setSubmitButtonEnabled(true);
        searchBar.setOnQueryTextListener(this);
        //NOTE:
        //Remove after display works!
        recipeNames = new String[]{"Chicken Noodle Soup",
                "Pancakes",
                "Waffles",
                "Scrambled Eggs",
                "French Toast",
                "Stir-Fry Vegetables",
                "General Tso's Chicken",
                "Steamed Flounder with Black Beans"};
        adaptorNames = new ArrayList<String>();
        //add values into list
        for(int i = 0; i < recipeNames.length; i++){
            adaptorNames.add(recipeNames[i]);
        }
        //Setup adaptor List
        listView = (ListView) this.findViewById(R.id.resultList);
        listAdaptor = new ListAdaptor(this ,adaptorNames, R.id.recipeItems, R.layout.result_list_items, "recipe");
        listView.setAdapter(listAdaptor);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            listAdaptor.filter(query);
        }else{//if not empty
            //turns comma separated string into array
            int loc = query.indexOf(',');
            while(loc != -1){
                queryList.add(query.substring(0, loc));
                query = query.substring(loc+1);
                loc = query.indexOf(',');
            }
            String[] queryArray = new String[queryList.size()];
            ArrayList<Recipe> result = null;
            //gets recipes from database
            try {
                result = task.execute(queryList.toArray(queryArray)).get();
                //listAdaptor.filter(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //Handle text changes
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listAdaptor.filter(newText);
        return false;
    }
}
