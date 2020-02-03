package com.attafakkur.myfavoritemovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfavoritemovie.R;
import com.attafakkur.myfavoritemovie.entity.MyMovies;
import com.bumptech.glide.Glide;

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movies_tvshow_content,parent,false);

        return new MovieViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewAdapter holder, int position) {
        final MyMovies myMovies = mData.get(position);
        String urlImage = "https://image.tmdb.org/t/p/w185" + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .into(holder.itemImg);

        holder.title.setText(myMovies.getTitle());
        holder.date.setText(myMovies.getDate());
        holder.description.setText(myMovies.getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewAdapter extends RecyclerView.ViewHolder {

        final ImageView itemImg;
        final TextView title;
        final TextView description;
        final TextView date;
        final Button moreInfo;

        private MovieViewAdapter(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.img_item_photo);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            date = itemView.findViewById(R.id.tv_date);
            moreInfo = itemView.findViewById(R.id.btn_moreInfo);

        }

    }
}