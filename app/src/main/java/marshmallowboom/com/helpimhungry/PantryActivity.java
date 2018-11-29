package marshmallowboom.com.helpimhungry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                     SearchView.OnQueryTextListener{

    ListAdaptor listAdaptor;
    SearchView ingredientInput;
    ArrayList<String> pantryList;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ingredientInput = (SearchView) findViewById(R.id.pantryInput);
        ingredientInput.setSubmitButtonEnabled(true);
        ingredientInput.setOnQueryTextListener(this);

        pantryList = new ArrayList<String>();


        listView = (ListView) findViewById(R.id.pantryList);
        listAdaptor = new ListAdaptor(this, pantryList, R.id.pantryItem, R.layout.pantry_item, "pantry");
        listView.setAdapter(listAdaptor);
        //inputIngredient


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
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pantry) {

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

    //When submit text
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Handles search query
        //Debugging line:
        Log.d("QUERY", "word: " + query);
        //Implement Search Function here;
        listAdaptor.addIngredient(query);
        listAdaptor.displayAll();
        query = "";
        return false;
    }
    //Handle text changes
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listAdaptor.filter(text);
        if(text.isEmpty()){
            listAdaptor.displayAll();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ingredientInput.setQuery("", false);
    }


}
