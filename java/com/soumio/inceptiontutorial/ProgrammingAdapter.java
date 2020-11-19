package com.soumio.inceptiontutorial;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ProgrammingViewHolder> {

    private String[] data;
    public ProgrammingAdapter(String[] data)
    {
        this.data = data;
    }
    @NonNull
    @Override

    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclecardview, parent, false);

        ProgrammingViewHolder programmingViewHolder=new ProgrammingViewHolder(view);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {

        String title = data[position];
        holder.text.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.txt);
        }
    }
}
