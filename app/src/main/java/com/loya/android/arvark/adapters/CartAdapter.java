package com.loya.android.arvark.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loya.android.arvark.R;
import com.loya.android.arvark.models.CartModel;

import java.util.ArrayList;

/**
 * Created by user on 12/5/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<CartModel> cartModels;
    private Context context;

    public CartAdapter(ArrayList<CartModel> cartModels, Context context) {
        this.cartModels = cartModels;
        this.context = context;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        CartModel cartModel = cartModels.get(position);
        holder.desc.setText(cartModel.getDesc());
        holder.cartImage.setImageResource(cartModel.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView desc;
        private ImageView cartImage;

        public ViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.cart_item_desc);
            cartImage = itemView.findViewById(R.id.cart_item_image);

        }
    }
}
