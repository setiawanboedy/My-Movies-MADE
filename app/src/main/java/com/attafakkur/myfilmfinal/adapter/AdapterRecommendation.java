package com.attafakkur.myfilmfinal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.entity.MyMovies;
import com.attafakkur.myfilmfinal.ui.DetailMovies;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * @author geek-dev
 */
public class AdapterRecommendation extends RecyclerView.Adapter<AdapterRecommendation.AdapterViewRecommendation> {

    private final ArrayList<MyMovies> movieRecommend = new ArrayList<>();

    public void setMovieRecommend(ArrayList<MyMovies> itemData) {
        movieRecommend.clear();
        movieRecommend.addAll(itemData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewRecommendation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recomendation, parent, false);
        return new AdapterViewRecommendation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewRecommendation holder, int position) {
        final MyMovies myMovies = movieRecommend.get(position);
        String imageUrl = BuildConfig.IMAGE;
        String urlImage = imageUrl + myMovies.getImage();

        Glide.with(holder.itemView.getContext())
                .load(urlImage)
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher_error)
                .into(holder.imageRec);

        holder.title.setText(myMovies.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieRecommend.size();
    }

    public class AdapterViewRecommendation extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;
        final ImageView imageRec;

        private AdapterViewRecommendation(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_titlerecomend);
            imageRec = itemView.findViewById(R.id.img_recomend);

            imageRec.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyMovies movie = movieRecommend.get(position);

            Intent intentObject = new Intent(itemView.getContext(), DetailMovies.class);
            intentObject.putExtra(DetailMovies.EXTRA_MOVIES, movie);
            itemView.getContext().startActivity(intentObject);
        }
    }
}
