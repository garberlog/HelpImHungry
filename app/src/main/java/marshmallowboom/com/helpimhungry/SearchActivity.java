package marshmallowboom.com.helpimhungry;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v7.widget.SearchView;
import android.widget.ListView;
import android.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "What should this do?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        listAdaptor = new ListAdaptor(this ,adaptorNames, R.id.recipeItems);
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
            Intent intent = new Intent(this, RecipesActivity.class);
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
        //Handles search query
        //Debugging line:
        Log.d("QUERY", "word: " + query);
        //Implement Search Function here;
        //listAdaptor.filter(query);
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
