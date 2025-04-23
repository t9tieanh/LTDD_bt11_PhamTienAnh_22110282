package com.example.firebase.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;
import com.example.firebase.models.Video;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context context;
    private List<Video> videoList;
    private boolean isFav = false;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_youtube, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Video video = videoList.get(position);

        holder.textVideoTitle.setText(video.getTitle());
        holder.textVideoDescription.setText(video.getDesc());
        holder.progressBar.setVisibility(View.VISIBLE);

        holder.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                holder.progressBar.setVisibility(View.GONE);
                String videoId = Uri.parse(video.getUrl()).getQueryParameter("v");
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }
        });

        holder.favorites.setOnClickListener(v -> {
            if (!isFav) {
                holder.favorites.setImageResource(R.drawable.baseline_favorite_24);
                isFav = true;
            } else {
                holder.favorites.setImageResource(R.drawable.baseline_favorite_border_24);
                isFav = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView youtubePlayerView;
        ProgressBar progressBar;
        TextView textVideoTitle, textVideoDescription;
        ImageView imPerson, favorites, imShare, imMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            youtubePlayerView = itemView.findViewById(R.id.youtubePlayerView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
        }
    }
}
