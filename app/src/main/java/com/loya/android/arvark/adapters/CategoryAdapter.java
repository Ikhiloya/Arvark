package com.loya.android.arvark.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loya.android.arvark.R;
import com.loya.android.arvark.models.Category;

import java.util.ArrayList;

/**
 * Created by user on 12/1/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> categories;
    private Context context;

    private ListItemClickListener listItemClickListener;
    /**
     * an interface to handle click events on a card
     */
    public interface ListItemClickListener {
        void onListItemClick();
    }
    public CategoryAdapter(ArrayList<Category> categories, Context context, ListItemClickListener listItemClickListener) {
        this.categories = categories;
        this.context = context;
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.imageView.setImageResource(category.getImageResourceId());
        holder.categoryText.setText(category.getCategoryText());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView categoryText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            categoryText = itemView.findViewById(R.id.category_text);

            //set onClick listener on this view @link{itemView}
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClick();
        }
    }
}
