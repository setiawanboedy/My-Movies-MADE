package com.attafakkur.myfilmapi.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmapi.ui.DetailMoviesActivity;
import com.attafakkur.myfilmapi.model.MyMovies;
import com.attafakkur.myfilmapi.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterRecomendation extends RecyclerView.Adapter<AdapterRecomendation.AdapterViewRecomendation> {

    private final ArrayList<MyMovies> movieRecomend = new ArrayList<>();
    public void setMovieRecomend(ArrayList<MyMovies> itemdata){
        movieRecomend.clear();
        movieRecomend.addAll(itemdata);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterRecomendation.AdapterViewRecomendation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recomendation,parent,false);
        return new AdapterViewRecomendation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecomendation.AdapterViewRecomendation holder, int position) {
        final MyMovies myMovies = movieRecomend.get(position);
        String url_image = "https://image.tmdb.org/t/p/w185" + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .into(holder.image_reco);

        holder.title.setText(myMovies.getTitle());

    }
    @Override
    public int getItemCount() {
        return movieRecomend.size();
    }

    public class AdapterViewRecomendation extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image_reco;

        private AdapterViewRecomendation(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_titlerecomend);
            image_reco = itemView.findViewById(R.id.img_recomend);

            image_reco.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyMovies movie = movieRecomend.get(position);

            Intent intentObject = new Intent(itemView.getContext(), DetailMoviesActivity.class);
            intentObject.putExtra(DetailMoviesActivity.extra_movies, movie);
            itemView.getContext().startActivity(intentObject);
        }
    }
}
