package com.example.shubham.augmento10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder>{

    private Context mCtx;
    private List<Model> modelList;
    private onItemClickListener mListener;

    public interface onItemClickListener {
         void onItemClick(View v, int position);
    }

    public void setItemClickListener(onItemClickListener clickListener) {
        this.mListener = clickListener;
    }

    public ModelAdapter(Context mCtx, List<Model> modelList) {
        this.mCtx = mCtx;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout,null);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {

            Model model = modelList.get(position);
            holder.textViewTitle.setText(model.getTitle());
            holder.textViewDesc.setText(model.getDescription());
            ImageView img = holder.imageView;
            Picasso.get().load(model.getImage()).into(img);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ModelViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle, textViewDesc;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,getAdapterPosition());
                }
            });

        }



    }

}
