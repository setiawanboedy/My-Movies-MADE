package com.attafakkur.myfilmfinal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.entity.MyMovies;
import com.attafakkur.myfilmfinal.ui.DetailMovies;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * @author geek-dev
 */
public class AdapterMyMovies extends RecyclerView.Adapter<AdapterMyMovies.MovieViewAdapter> {

    private final ArrayList<MyMovies> mData = new ArrayList<>();

    public void setData(ArrayList<MyMovies> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movies_tvshow_content, parent, false);

        return new MovieViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewAdapter holder, int position) {
        final MyMovies myMovies = mData.get(position);
        String imageUrl = BuildConfig.IMAGE;
        String urlImage = imageUrl + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .apply(new RequestOptions().override(136, 186))
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher_error)
                .into(holder.itemImg);

        holder.title.setText(myMovies.getTitle());
        holder.date.setText(myMovies.getDate());
        holder.description.setText(myMovies.getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView itemImg;
        final TextView title, description, date;
        final Button moreInfo;

        private MovieViewAdapter(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.img_item_photo);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            date = itemView.findViewById(R.id.tv_date);
            moreInfo = itemView.findViewById(R.id.btn_moreinfo);

            moreInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyMovies movie = mData.get(position);

            Intent intentObject = new Intent(itemView.getContext(), DetailMovies.class);
            intentObject.putExtra(DetailMovies.EXTRA_MOVIES, movie);
            itemView.getContext().startActivity(intentObject);
        }
    }
}
