package com.smtgroup.estateapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smtgroup.estateapplication.R;
import com.smtgroup.estateapplication.properties.Category;
import com.smtgroup.estateapplication.properties.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tugay Demirel.
 */

public class AdapterCategory extends ArrayAdapter<Category> {

    private Activity activity;
    private ArrayList<Category> categoryList;
    private static LayoutInflater inflater = null;


    public AdapterCategory(Activity activity, int resource, ArrayList<Category> categoryList) {
        super(activity, resource, categoryList);
        try {
            this.activity = activity;
            this.categoryList = categoryList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){

        }
    }

    public int getCount(){
        return categoryList.size();
    }

    public Category getItem(Category position){
        return  position;
    }

    public long getItemId(int position){
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.activity_homepage_category, null);
                holder = new ViewHolder();

//                holder.display_name = (TextView) vi.findViewById(R.id.display_name);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(categoryList.get(position).getName());


        } catch (Exception e) {


        }
        return vi;
    }

}

/*
*
* public class AdapterPerson extends ArrayAdapter<Person> {
    private Activity activity;
    private ArrayList<Person> lPerson;
    private static LayoutInflater inflater = null;

    public AdapterPerson (Activity activity, int textViewResourceId,ArrayList<Person> _lPerson) {
        super(activity, textViewResourceId, _lProducts);
        try {
            this.activity = activity;
            this.lPerson = _lPerson;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lPerson.size();
    }

    public Product getItem(Product position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_number;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.yourlayout, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                holder.display_number = (TextView) vi.findViewById(R.id.display_number);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(lProducts.get(position).name);
            holder.display_number.setText(lProducts.get(position).number);


        } catch (Exception e) {


        }
        return vi;
    }
}
* */
