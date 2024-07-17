package com.example.ultimateiptvplayer.Fragments.ProgressBar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.OnDownloadListener;
import com.example.ultimateiptvplayer.R;


public class ProgressBarFragment extends Fragment implements ProgressCallBack {
    private ProgressBar progressBar;
    private OnDownloadListener downloadListener;
    private TextView progressText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDownloadListener) {
            downloadListener = (OnDownloadListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DownloadCompleteListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progressbar_layout, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        progressText = view.findViewById(R.id.progress_text);
        return view;
    }

    @Override
    public void onProgressUpdate(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
        if (progressText != null) {
            progressText.setText("Progress: " + progress + "%");
        }
    }

    @Override
    public void onDownloadComplete() {
        if (progressText != null) {
            progressText.setText("Download Complete");
            this.downloadListener.onDownloadComplete();
        }
    }

    @Override
    public void onDownloadError(String error) {
        if (progressText != null) {
            progressText.setText("Download Error: " + error);
            this.downloadListener.onDownloadError(error);
        }
    }
}
