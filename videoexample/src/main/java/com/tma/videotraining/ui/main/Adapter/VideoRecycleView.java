package com.tma.videotraining.ui.main.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.Utils.DownloadVideoOfListVideo;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;
import com.tma.videotraining.ui.view.interDrawer.ClickListener;

import java.util.List;

/**
 * Created by nbhung on 7/13/2017.
 */

public class VideoRecycleView extends RecyclerView.Adapter<VideoRecycleView.VideoViewHolder> {
    private List<ListVideo> mListVideos;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ClickListener clickListener;

    public VideoRecycleView(Context mContext, List<ListVideo> mListVideos, ClickListener clickListener) {
        this.mListVideos = mListVideos;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.clickListener = clickListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mLayoutInflater.inflate(R.layout.list_video_row, parent, false);
        final VideoViewHolder viewHolder = new VideoViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        ListVideo ss = mListVideos.get(position);
        holder.mImageView.setImageResource(R.drawable.icon);
        holder.tvName.setText(ss.getName());
        holder.tvTime.setText(ss.getTime());
    }

    @Override
    public int getItemCount() {
        return mListVideos.size();
    }


    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView tvName, tvTime, tvCounfile;
        private Button btnDown, btnPause, btnResume, btnCancel;
        private ProgressBar mProgressBar;
        private DownloadVideoOfListVideo download;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image);
            tvName = (TextView) itemView.findViewById(R.id.name_ofvideo);
            tvTime = (TextView) itemView.findViewById(R.id.time_ofvideo);
            btnDown = (Button) itemView.findViewById(R.id.btn_downVideo);
            btnPause = (Button) itemView.findViewById(R.id.btn_pauseVideo);
            btnResume = (Button) itemView.findViewById(R.id.btn_resumeVideo);
            btnCancel = (Button) itemView.findViewById(R.id.btn_CancelVideo);
            tvCounfile = (TextView) itemView.findViewById(R.id.tvcountfile);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.mprogessbar);
            btnResume.setOnClickListener(this);
            btnPause.setOnClickListener(this);
            btnDown.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnCancel.setEnabled(false);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_downVideo:
                    btnCancel.setEnabled(true);
                    tvCounfile.setVisibility(View.VISIBLE);
                    tvCounfile.setTextColor(ContextCompat.getColor(mContext, R.color.textBlack));
                    tvCounfile.setText(R.string.wait);
                    mProgressBar.setVisibility(View.VISIBLE);
                    btnDown.setVisibility(View.INVISIBLE);
                    btnPause.setVisibility(View.VISIBLE);
                    download = new DownloadVideoOfListVideo(mContext, mProgressBar, tvCounfile, btnDown, btnPause, btnResume, btnCancel);
                    download.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mListVideos.get(getLayoutPosition()).getUrl());
                    break;
                case R.id.btn_pauseVideo:
                    btnPause.setVisibility(View.INVISIBLE);
                    btnResume.setVisibility(View.VISIBLE);
                    download.pauseVideo();
                    break;
                case R.id.btn_resumeVideo:
                    btnResume.setVisibility(View.INVISIBLE);
                    btnPause.setVisibility(View.VISIBLE);
                    download.resumeVideo();
                    break;
                case R.id.btn_CancelVideo:
                    btnDown.setVisibility(View.VISIBLE);
                    btnPause.setVisibility(View.INVISIBLE);
                    btnResume.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    download.cancel(true);
                    mProgressBar.setProgress(0);
                    btnCancel.setEnabled(false);
                    tvCounfile.setVisibility(View.INVISIBLE);
                   // download.deleteFile();
                    Toast.makeText(mContext, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}
