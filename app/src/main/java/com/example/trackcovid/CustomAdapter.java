package com.example.trackcovid;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter  extends ArrayAdapter<CountryModel> {
    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> filterlist=new ArrayList<>();

    public CustomAdapter(@NonNull Context context, List<CountryModel> countryModelListst) {
        super(context, R.layout.list_custom_items);
        this.context = context;
        this.countryModelList = countryModelList;
        this.filterlist = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_items,null,true);
        ImageView searchimage=view.findViewById(R.id.searchimage);
        TextView searchcountryname=view.findViewById(R.id.searchcountryname);
        searchcountryname.setText(countryModelList.get(position).getCountry());
        Glide.with(context).load(countryModelList.get(position).getFlag()).into(searchimage);

        return  view;
    }

//    @Override
//    public int getCount() {
//        return filterlist .size();
//    }

//    @Nullable
//    @Override
//    public CountryModel getItem(int position) {
//        return filterlist .get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }


//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
//                FilterResults filterResults = new FilterResults();
//                if(constraint == null || constraint.length() == 0){
//                    filterResults.count = countryModelList.size();
//                    filterResults.values = countryModelList;
//
//                }
//                else{
//                    List<CountryModel> resultsModel = new ArrayList<>();
//                    String searchStr = constraint.toString().toLowerCase();
//
//                    for(CountryModel itemsModel:countryModelList){
//                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
//                            resultsModel.add(itemsModel);
//
//                        }
//                        filterResults.count = resultsModel.size();
//                        filterResults.values = resultsModel;
//                    }
//
//
//                }
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//               filterlist = (List<CountryModel>) results.values;
//              effectedcountries.countryModelList = (List<CountryModel>) results.values;
//                notifyDataSetChanged();
//
//            }
//        };
//        return filter;
//    }
}
