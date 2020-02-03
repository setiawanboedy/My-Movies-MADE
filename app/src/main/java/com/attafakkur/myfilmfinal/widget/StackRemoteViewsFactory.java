package com.attafakkur.myfilmfinal.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.entity.MyMovies;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import static com.attafakkur.myfilmfinal.db.DatabaseContract.MovieColumns.CONTENT_URI;


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private final Context context;
    private Cursor cursor;

    StackRemoteViewsFactory(Context mContext) {
        this.context = mContext;
    }

    private void close(){
        if (cursor != null){
            cursor.close();
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        close();
        final long token = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI, null,null,null,null,null);
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {
        close();
    }

    @Override
    public int getCount() {
        if (cursor == null){
            return 0;
        }else{
            return cursor.getCount();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (getCount()>0){
            MyMovies movies = getItem(position);
            String imageUrl = BuildConfig.IMAGE + movies.getImage();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(imageUrl)
                        .submit(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                        .get();
                remoteViews.setImageViewBitmap(R.id.imageWidget, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Bundle extraBundle = new Bundle();
            extraBundle.putInt(ImageWidget.ITEM, position);
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extraBundle);

            remoteViews.setOnClickFillInIntent(R.id.imageWidget,fillIntent);
            return remoteViews;
        }else {
            return new RemoteViews(context.getPackageName(), R.layout.item_widget);
        }
    }

    private MyMovies getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MyMovies(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)){
            cursor.getLong(0);
        }
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}