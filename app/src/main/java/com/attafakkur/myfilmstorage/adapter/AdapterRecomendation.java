package com.attafakkur.myfilmstorage.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmstorage.BuildConfig;
import com.attafakkur.myfilmstorage.R;
import com.attafakkur.myfilmstorage.entity.MyMovies;
import com.attafakkur.myfilmstorage.ui.DetailMovies;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterRecomendation extends RecyclerView.Adapter<AdapterRecomendation.AdapterViewRecomendation> {

    private final ArrayList<MyMovies> movieRecomend = new ArrayList<>();

    public void setMovieRecomend(ArrayList<MyMovies> itemdata) {
        movieRecomend.clear();
        movieRecomend.addAll(itemdata);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterRecomendation.AdapterViewRecomendation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recomendation, parent, false);
        return new AdapterViewRecomendation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecomendation.AdapterViewRecomendation holder, int position) {
        final MyMovies myMovies = movieRecomend.get(position);
        String imageUrl = BuildConfig.IMAGE;
        String urlImage = imageUrl + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher_error)
                .into(holder.imageReco);

        holder.title.setText(myMovies.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieRecomend.size();
    }

    public class AdapterViewRecomendation extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;
        final ImageView imageReco;

        private AdapterViewRecomendation(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_titlerecomend);
            imageReco = itemView.findViewById(R.id.img_recomend);

            imageReco.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyMovies movie = movieRecomend.get(position);

            Intent intentObject = new Intent(itemView.getContext(), DetailMovies.class);
            intentObject.putExtra(DetailMovies.EXTRA_MOVIES, movie);
            itemView.getContext().startActivity(intentObject);
        }
    }
}
