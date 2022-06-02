package com.example.productapp.view;

import android.content.Context;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;



import com.example.productapp.R;
import com.example.productapp.dto.ProductDTO;
import com.example.productapp.util.StringUtil;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends ListAdapter<ProductDTO, ProductAdapter.ProductViewHolder>  {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 20;
    private int lastPosition = -1;
    private Context context;

    private interface ClickItemAdapter{

    }
    public ProductAdapter(@NonNull DiffUtil.ItemCallback<ProductDTO> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO productDTO = getItem(position);
        if(productDTO ==null) return;
        holder.bind(productDTO);
        setAnimation(holder.itemView, position);


    }
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName, txtQuantitySold, txtPrice, txtUnit;
        RatingBar rb;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuantitySold = itemView.findViewById(R.id.txt_quantity_sold);
            txtName = itemView.findViewById(R.id.text_view_name_product);
            txtPrice = itemView.findViewById(R.id.text_view_price_product);
            rb = itemView.findViewById(R.id.rating_bar_product);
//            txtPrice.setPaintFlags(txtOriPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            img=itemView.findViewById(R.id.image_view_thumbnail_url);
        }

        public void bind(ProductDTO productDTO) {
            txtName.setText(productDTO.getName()+"");
            txtPrice.setText(StringUtil.formatCurrency((double) productDTO.getPrice()));
            int quantitySold = productDTO.getQuanttitySold().getValue();

            String textSold = productDTO.getQuanttitySold().getText();
            txtQuantitySold.setText(textSold==null?("Đã bán " + quantitySold): textSold);
            rb.setRating(productDTO.getRating_average());
            Picasso.get().load(productDTO.getThumbnail_url()).into(img);
//            Picasso.get()
//                    .load(productDTO.getThumbnail_url())
//                    .placeholder(R.drawable.loading)
//                    .error(R.drawable.ic_launcher_background)
//                    .into(img);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


}
