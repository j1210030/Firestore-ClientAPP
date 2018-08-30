package intersoft.com.androidfs.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import intersoft.com.androidfs.Const.ApparelItems;
import intersoft.com.androidfs.R;

import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by user on 8/29/2017.
 */

public class ListAdapter extends FirestoreAdapter<ListAdapter.ViewHolder>  {
    private Context context;


    public ListAdapter(Context context, Query query) {
        super(query);
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.apparel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(context, getSnapshot(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apparel_title)
        TextView name;

        @BindView(R.id.apparel_price)
        TextView price;

        @BindView(R.id.apparel_stock)
        TextView stock;

        @BindView(R.id.apparel_thumbnail)
        ImageView thumbnail;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.apparel_brand)
        ImageView brand;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(Context context, final DocumentSnapshot snapshot) {

            ApparelItems apparelItems = snapshot.toObject(ApparelItems.class);

            StorageReference storageRef;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storageRef = storage.getReferenceFromUrl("gs://firestoredemo1.appspot.com/");
            StorageReference imageRef = storageRef.child("images/apparel/thumbnails/" + apparelItems.getThumbnail());

            // Load the image using Glide
            Glide.with(thumbnail.getContext())
                    .using(new FirebaseImageLoader())
                    .load(imageRef)
                    .into(thumbnail);

            String brandImage = "Brand/gu.png";
            if (apparelItems.getBrand().equalsIgnoreCase("gu")){
                brandImage = "Brand/gu.png";

            } else if(apparelItems.getBrand().equalsIgnoreCase("uniqlo")){
                brandImage = "Brand/uniqlo.png";

            } else if(apparelItems.getBrand().equalsIgnoreCase("hm")){
                brandImage = "Brand/hm.jpg";

            }

            try {
                InputStream brandStream = context.getAssets().open(brandImage);
                Drawable brandDrawable = Drawable.createFromStream(brandStream, null);
                brand.setImageDrawable(brandDrawable);

            } catch (IOException e) {
                Log.d("Assets","Error");

            }
            name.setText(apparelItems.getName());

            Locale locale = Locale.JAPAN;
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String currencyText = fmt.format(apparelItems.getPrice());
            price.setText(currencyText);


            if (apparelItems.getStock().equalsIgnoreCase("0")){
                stock.setTextColor(Color.parseColor("#ff0000"));
                stock.setText("No stock");
            }else{
                stock.setTextColor(Color.parseColor("#6dc066"));
                stock.setText(apparelItems.getStock() + " in stock");
            }

            ratingBar.setRating(Float.parseFloat(apparelItems.getRatings()));

        }
    }

}
