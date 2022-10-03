package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickListener implements RecyclerView.OnItemTouchListener {
    public GestureDetector gestureDetector;
    public onItemClickListener mListener;

    public interface onItemClickListener {
        void OnItem(View view, int i);
    }
    public ItemClickListener(Context context, onItemClickListener onitemclicklistener) {
        this.mListener = onitemclicklistener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View findChildViewUnder = rv.findChildViewUnder(e.getX(), e.getY());
        if (!(findChildViewUnder == null || this.mListener == null || !this.gestureDetector.onTouchEvent(e))) {
            this.mListener.OnItem(findChildViewUnder, rv.getChildAdapterPosition(findChildViewUnder));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
