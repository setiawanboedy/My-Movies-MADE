package com.attafakkur.myfilmapi.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmapi.ui.DetailMoviesActivity;
import com.attafakkur.myfilmapi.model.MyMovies;
import com.attafakkur.myfilmapi.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterMyMovies extends RecyclerView.Adapter<AdapterMyMovies.filmViewAdapter> {

    private ArrayList<MyMovies> mData = new ArrayList<>();
    public void setData(ArrayList<MyMovies> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMyMovies.filmViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movies_tvshow_content,parent,false);

        return new filmViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyMovies.filmViewAdapter holder, int position) {
        final MyMovies myMovies = mData.get(position);
        String url_image = "https://image.tmdb.org/t/p/w185" + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .apply(new RequestOptions().override(136,186))
                .into(holder.item_img);

        holder.title.setText(myMovies.getTitle());
        holder.date.setText(myMovies.getDate());
        holder.description.setText(myMovies.getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class filmViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView item_img;
        TextView title, description, date;
        Button moreinfo;

        private filmViewAdapter(@NonNull View itemView) {
            super(itemView);

            item_img = itemView.findViewById(R.id.img_item_photo);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            date = itemView.findViewById(R.id.tv_date);
            moreinfo = itemView.findViewById(R.id.btn_moreinfo);

            moreinfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyMovies movie = mData.get(position);

            Intent intentObject = new Intent(itemView.getContext(), DetailMoviesActivity.class);
            intentObject.putExtra(DetailMoviesActivity.extra_movies, movie);
            itemView.getContext().startActivity(intentObject);
        }
    }
}