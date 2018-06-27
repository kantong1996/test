package com.example.kanto.projects;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

public class ItemCell extends SimpleCell<Item,ItemCell.ViewHolder>{

    public ItemCell(@NonNull Item item) {
        super(item);
    }
    protected int getLayoutRes() {
        return R.layout.model;
    }
    /*
   - Return a ViewHolder instance
    */
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new ViewHolder(cellView);
    }
    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Context context, Object o) {

        viewHolder.FoodName.setText(getItem().getFoodName());
        viewHolder.Kcal.setText("พลังงาน : "+getItem().getKcal());
//        viewHolder.Protein.setText(getItem().getKcal());
    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */
    static class ViewHolder extends SimpleViewHolder {
        TextView FoodName,Kcal;


        ViewHolder(View itemView) {
            super(itemView);
            FoodName=itemView.findViewById(R.id.foodname);
            Kcal=itemView.findViewById(R.id.kcal);
//            Protein = itemView.findViewById(R.id.protein);
        }
    }
}
