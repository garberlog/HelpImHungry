package marshmallowboom.com.helpimhungry;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ViewHolder{
    Button itemName;
}


public class ListAdaptor extends BaseAdapter {

    Context mContext;
    private List<String> displayItems = null;
    private ArrayList<String> allItems;
    private LayoutInflater inflater;
    private int listItemID;
    private int layoutID;
    private String buttonType;

    public ListAdaptor(Context context, List<String> content, int itemid, int layoutid, String type){
        mContext = context;
        displayItems = content;
        allItems = new ArrayList<String>();
        allItems.addAll(content);
        inflater = LayoutInflater.from(mContext);
        displayItems.clear();
        listItemID = itemid;
        layoutID = layoutid;
        buttonType = type;
    }
    @Override
    public int getCount() {
        return displayItems.size();
    }

    @Override
    public String getItem(int position) {
        return displayItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup parent){
        final ViewHolder holder;
        if(inflater == null){
            Log.d("Adaptor_inflater", "IN-IF INFLATOR NULL");
            inflater = LayoutInflater.from(mContext);
        }
        if(view == null){
            Log.d("Adaptor_view", "IN-IF VIEW NULL");
            view = inflater.inflate(layoutID,null);
            holder = new ViewHolder();
            //Gets Button
            holder.itemName = view.findViewById(listItemID);
            view.setTag(holder);
        }else{
            Log.d("Adaptor_view", "IN-IF VIEW NOT NULL");
            holder = (ViewHolder) view.getTag();
        }
        //Results
        holder.itemName.setText(displayItems.get(position));
        //SetOnClickListener
        if(buttonType.equals("recipe")){
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //recipe button function
                    Intent intent = new Intent(mContext, RecipeActivity.class);
                    intent.putExtra("recipeName", "penguin soup");
                    intent.putExtra("ingredients", "soup, penguin things");
                    intent.putExtra("instructions", "1. Get a penguin\n2. Soup it up");
                    mContext.startActivity(intent);
                }
            });
        }else if(buttonType.equals("pantry")){
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //pantry button function
                    removeIngredient(position);
                }
            });
        }
        return view;
    }

    //Filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        displayItems.clear();
        if(charText.length() == 0){
            displayItems.clear();
        }else{
            for(String name : allItems){
                if(name.toLowerCase(Locale.getDefault()).contains(charText)){
                    displayItems.add(name);
                }
            }
        }
        notifyDataSetChanged();
    }

    //Add Ingredient:
    //Used by:
    //  Pantry Stuff
    public void addItem(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        if(!displayItems.contains(charText) && !allItems.contains(charText)){
            allItems.add(charText);
            displayItems.add(charText);
        }
        notifyDataSetChanged();
    }
    public void removeIngredient(int pos){
        displayItems.remove(pos);

        notifyDataSetChanged();
    }

    public void clear(){
        displayItems.clear();
        notifyDataSetChanged();
    }

    public void displayAll(){
        displayItems.addAll(allItems);
        notifyDataSetChanged();
    }

    public List<String> getDisplayList(){
        if(displayItems.size() == 0){
            return null;
        }
        else {
            return displayItems;
        }

    }



}
