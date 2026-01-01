package dsa.upc.edu.listapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dsa.upc.edu.listapp.api.Track;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Track> values;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvTitle;
        public TextView tvSinger;

        public ViewHolder(View v) {
            super(v);
            tvId = (TextView) v.findViewById(R.id.tvId);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvSinger = (TextView) v.findViewById(R.id.tvSinger);
        }
    }

    // Constructor vacío
    public MyAdapter() {
        values = new ArrayList<>();
    }

    // Constructor con datos iniciales
    public MyAdapter(List<Track> myDataset) {
        values = myDataset;
    }

    // --- MÉTODOS AÑADIDOS PARA SWIPE REFRESH Y DELETE ---

    // 1. Para SWIPE REFRESH: Actualiza toda la lista de golpe
    public void setData(List<Track> myDataset) {
        values = myDataset;
        notifyDataSetChanged();
    }

    // 2. Para SWIPE TO DELETE: Obtener el objeto Track para saber su ID antes de borrar
    public Track getTrack(int position) {
        return values.get(position);
    }

    // 3. Para SWIPE TO DELETE: Borrar visualmente el item de la lista
    public void removeTrack(int position) {
        values.remove(position);
        // Notifica solo la eliminación de esa posición para que haga la animación
        notifyItemRemoved(position);
    }

    // ----------------------------------------------------

    public void add(int position, Track item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Track c = values.get(position); //
        holder.tvId.setText(c.getId());
        holder.tvTitle.setText(c.getTitle());
        holder.tvSinger.setText(c.getSinger());

        // --- NEW CODE STARTS HERE ---
        // When a row is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateActivity.class);
                // Pass the data to the activity
                intent.putExtra("id", c.getId());
                intent.putExtra("title", c.getTitle());
                intent.putExtra("singer", c.getSinger());
                intent.putExtra("isEdit", true); // Flag to tell CreateActivity this is an edit
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}