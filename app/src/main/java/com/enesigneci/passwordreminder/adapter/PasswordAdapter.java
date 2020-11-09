package com.enesigneci.passwordreminder.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enesigneci.passwordreminder.R;
import com.enesigneci.passwordreminder.SecurityManager;
import com.enesigneci.passwordreminder.listener.RecyclerClickListener;
import com.enesigneci.passwordreminder.model.Passwords;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView passwordName;
        public TextView passwordUsername;
        public TextView passwordText;
        public ImageView passwordIcon;
        public CardView cardView;
        public View cardContainer;


        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            cardContainer= (View) view.findViewById(R.id.card_container);
            passwordName = (TextView) view.findViewById(R.id.password_name);
            passwordUsername = (TextView) view.findViewById(R.id.password_username);
            passwordText = (TextView) view.findViewById(R.id.password_text);
            passwordIcon = (ImageView) view.findViewById(R.id.password_icon);

        }
    }

    List<Passwords> passwordsList;
    RecyclerClickListener listener;
    Context context;

    public PasswordAdapter(List<Passwords> passwordsList, RecyclerClickListener listener,Context context) {

        this.passwordsList = passwordsList;
        this.listener = listener;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v,viewHolder.getPosition());
                return true;
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.passwordUsername.setText(passwordsList.get(position).getPasswordUsername());
        holder.passwordName.setText(passwordsList.get(position).getPasswordName());
        holder.passwordText.setText(SecurityManager.fromBase64(passwordsList.get(position).getPasswordText()));
        holder.passwordIcon.setImageResource(passwordsList.get(position).getPasswordType());
        switch (position % 6){
            case 0:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_1));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_1));
                break;
            case 1:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_2));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_2));

                break;
            case 2:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_3));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_3));
                break;
            case 3:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_4));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_4));
                break;
            case 4:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_5));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_5));

                break;
            case 5:
                holder.cardContainer.setBackgroundColor(context.getResources().getColor(R.color.rainbow_6));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.rainbow_6));

                break;
        }

    }

    @Override
    public int getItemCount() {
        return passwordsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void setPasswordsList(List<Passwords> passwordsList){
        this.passwordsList=new ArrayList<>();
        this.passwordsList=passwordsList;
        notifyDataSetChanged();
    }
    public Passwords getItem(int position){
        return passwordsList.get(position);
    }
}