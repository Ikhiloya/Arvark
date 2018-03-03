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
 * Created by user on 12/5/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Category> products;
    private Context context;
    private ListItemClickListener listItemClickListener;
    /**
     * an interface to handle click events on a card
     */
    public interface ListItemClickListener {
        void onListItemClick();
    }
    public ProductAdapter(ArrayList<Category> products, Context context, ListItemClickListener listItemClickListener) {
        this.products = products;
        this.context = context;
        this.listItemClickListener= listItemClickListener;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Category product = products.get(position);
        holder.productImageView.setImageResource(product.getImageResourceId());
        holder.productDescription.setText(product.getCategoryText());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView productImageView;
        private TextView productDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.product_image);
            productDescription = itemView.findViewById(R.id.product_item_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClick();
        }
    }
}
