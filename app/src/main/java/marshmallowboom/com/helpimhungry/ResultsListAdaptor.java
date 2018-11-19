package marshmallowboom.com.helpimhungry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ViewHolder{
    Button recipeName;
}


public class ResultsListAdaptor extends BaseAdapter {

    Context mContext;
    private List<String> recipeNames = null;
    private ArrayList<String> displayNames;
    private LayoutInflater inflater;

    public ResultsListAdaptor(Context context, List<String> recipes){
        mContext = context;
        recipeNames = recipes;
        displayNames = new ArrayList<String>();
        displayNames.addAll(recipes);
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return recipeNames.size();
    }

    @Override
    public String getItem(int position) {
        return recipeNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, final ViewGroup parent){
        final ViewHolder holder;
        if(inflater == null){
            inflater = LayoutInflater.from(mContext);
        }
        if(view == null){
            view = inflater.inflate(R.layout.result_list_items,null);
            holder = new ViewHolder();
            holder.recipeName = (Button) view.findViewById(R.id.recipe_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //Results
        holder.recipeName.setText(recipeNames.get(position));
        return view;
    }

    //Filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        recipeNames.clear();
        if(charText.length() == 0){
            recipeNames.addAll(displayNames);
        }else{
            for(String name : displayNames){
                if(name.toLowerCase(Locale.getDefault()).contains(charText)){
                    recipeNames.add(name);
                }
            }
        }
        notifyDataSetChanged();
    }


}
