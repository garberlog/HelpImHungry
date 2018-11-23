package marshmallowboom.com.helpimhungry;

import android.content.Context;
import android.util.Log;
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
    Button itemName;
}


public class ListAdaptor extends BaseAdapter {

    Context mContext;
    private List<String> displayItems = null;
    private ArrayList<String> allItems;
    private LayoutInflater inflater;
    private int listID;

    public ListAdaptor(Context context, List<String> recipes, int id){
        mContext = context;
        displayItems = recipes;
        allItems = new ArrayList<String>();
        allItems.addAll(recipes);
        inflater = LayoutInflater.from(mContext);
        displayItems.clear();
        listID = id;
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
    public View getView(int position, View view, final ViewGroup parent){
        final ViewHolder holder;
        if(inflater == null){
            Log.d("Adaptor_inflater", "IN-IF INFLATOR NULL");
            inflater = LayoutInflater.from(mContext);
        }
        if(view == null){
            Log.d("Adaptor_view", "IN-IF VIEW NULL");
            view = inflater.inflate(R.layout.result_list_items,null);
            holder = new ViewHolder();
            holder.itemName = (Button) view.findViewById(listID);
            view.setTag(holder);
        }else{
            Log.d("Adaptor_view", "IN-IF VIEW NOT NULL");
            holder = (ViewHolder) view.getTag();
        }
        //Results
        holder.itemName.setText(displayItems.get(position));
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


}
