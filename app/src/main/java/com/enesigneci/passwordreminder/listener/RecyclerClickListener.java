package com.enesigneci.passwordreminder.listener;

import android.view.View;

public interface RecyclerClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v,int position);
}