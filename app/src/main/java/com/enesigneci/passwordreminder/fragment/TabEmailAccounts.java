package com.enesigneci.passwordreminder.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import androidx.room.Room;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.passwordreminder.MainActivity;
import com.enesigneci.passwordreminder.R;
import com.enesigneci.passwordreminder.adapter.PasswordAdapter;
import com.enesigneci.passwordreminder.db.AppDb;
import com.enesigneci.passwordreminder.listener.DataRefreshListener;
import com.enesigneci.passwordreminder.listener.RecyclerClickListener;
import com.enesigneci.passwordreminder.model.Passwords;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


public class TabEmailAccounts extends Fragment implements DataRefreshListener {

    private View emptyView;
    private View listFilled;
    private AppDb db;
    PasswordAdapter passwordAdapter;

    public TabEmailAccounts() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setEmailRefreshListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_email_accounts, container, false);
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        RecyclerView rvEmailAccountPasswords = (RecyclerView) view.findViewById(R.id.recycler_email_accounts);
        listFilled = view.findViewById(R.id.list_filled);
        emptyView=view.findViewById(R.id.empty_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        db= Room.databaseBuilder(getContext(),AppDb.class,"Passwords").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<Passwords> passwordsList = new ArrayList<Passwords>();
        passwordsList.addAll(db.passwordsDao().getByCategory("EmailPassword"));
        rvEmailAccountPasswords.setLayoutManager(layoutManager);
        if (db.passwordsDao().getByCategory("EmailPassword").isEmpty()){
            listFilled.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        passwordAdapter = new PasswordAdapter(passwordsList, new RecyclerClickListener() {
            @Override
            public void onItemClick(final View v, int position) {
                final TextView passwordText = v.findViewById(R.id.password_text);
                v.findViewById(R.id.password_text).setVisibility(View.VISIBLE);
                ValueAnimator fadeInValueAnimator = ValueAnimator.ofFloat(0f, 1f);
                fadeInValueAnimator.setDuration(500);
                fadeInValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float alpha = (float) animation.getAnimatedValue();
                        v.findViewById(R.id.password_text).setAlpha(alpha);
                    }
                });
                fadeInValueAnimator.start();
                fadeInValueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Handler fadeOutHandler=new Handler();
                        fadeOutHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.findViewById(R.id.password_text).setAlpha(0);
                                v.findViewById(R.id.password_text).setVisibility(View.GONE);
                            }
                        },4000);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("passwordReminder", passwordText.getText().toString());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), R.string.password_copied_to_your_clipboard,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View v, final int position) {
                final MaterialDialog deletePasswordDialog=new MaterialDialog.Builder(getContext()).customView(R.layout.delete_password_dialog,true).build();
                deletePasswordDialog.getCustomView().setPadding(0,0,0,0);
                deletePasswordDialog.getCustomView().setTop(0);
                deletePasswordDialog.getCustomView().setBottom(0);
                deletePasswordDialog.getCustomView().setLeft(0);
                deletePasswordDialog.getCustomView().setRight(0);
                deletePasswordDialog.show();
                View deletePasswordDialogView=deletePasswordDialog.getCustomView();
                Button cancelToDeleteButton=(Button)deletePasswordDialogView.findViewById(R.id.cancel_delete_password_button);
                cancelToDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePasswordDialog.dismiss();
                    }
                });
                Button deleteButton=deletePasswordDialogView.findViewById(R.id.delete_password_button);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Passwords passwordToDelete=passwordAdapter.getItem(position);
                        db.passwordsDao().deletePassword(passwordToDelete);
                        deletePasswordDialog.dismiss();
                        passwordAdapter.setPasswordsList(db.passwordsDao().getByCategory("EmailPassword"));
                        if (db.passwordsDao().getByCategory("EmailPassword").isEmpty()){
                            listFilled.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        },getContext());
        rvEmailAccountPasswords.setHasFixedSize(true);

        rvEmailAccountPasswords.setAdapter(passwordAdapter);

        rvEmailAccountPasswords.setItemAnimator(new DefaultItemAnimator());
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRefresh() {
        passwordAdapter.setPasswordsList(db.passwordsDao().getByCategory("EmailPassword"));
        if (db.passwordsDao().getByCategory("EmailPassword").isEmpty()){
            listFilled.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            listFilled.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
