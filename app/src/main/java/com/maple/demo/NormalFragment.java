package com.maple.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maple.pagestate.PageStatusManager;


/**
 * Created by zhy on 15/8/27.
 */
public class NormalFragment extends Fragment {
    PageStatusManager pageStatusManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pageStatusManager = new PageStatusManager(this);
        View retryView = pageStatusManager.getRetryView();
        View view1 = retryView.findViewById(R.id.id_btn_retry);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "retry event invoked", Toast.LENGTH_SHORT).show();
                pageStatusManager.showLoading();
                loadData();
            }
        });

        pageStatusManager.showLoading();
    }


    public void setRetryEvent(View retryView) {

    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.random() > 0.6) {
                    pageStatusManager.showContent();
                } else {
                    pageStatusManager.showRetry();
                }
            }
        }.start();
    }
}

