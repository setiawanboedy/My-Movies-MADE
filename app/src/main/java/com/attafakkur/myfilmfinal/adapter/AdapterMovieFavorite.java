package com.attafakkur.myfilmfinal.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
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

/**
 * @author geek-dev
 */
public class AdapterMovieFavorite extends RecyclerView.Adapter<AdapterMovieFavorite.ViewHolder> {

    private Cursor cursor;

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movies_tvshow_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyMovies movies = getItem(position);

        String imageUrl = BuildConfig.IMAGE;
        String urlImage = imageUrl + movies.getImage();

        holder.title.setText(movies.getTitle());
        Glide.with(holder.itemView.getContext()).load(urlImage)
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher_error)
                .into(holder.itemImg);

        holder.date.setText(movies.getDate());
        holder.description.setText(movies.getDescription());

        Log.d("TITLE", "" + movies.getTitle());
    }

    private MyMovies getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MyMovies(cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView itemImg;
        final TextView title, description, date;
        final Button moreInfo;

        ViewHolder(@NonNull View itemView) {
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
            MyMovies movies = getItem(position);
            Intent intent = new Intent(itemView.getContext(), DetailMovies.class);
            intent.putExtra(DetailMovies.EXTRA_MOVIES, movies);
            itemView.getContext().startActivity(intent);
        }
    }

}
