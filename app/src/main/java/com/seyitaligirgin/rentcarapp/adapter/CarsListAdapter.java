package com.seyitaligirgin.rentcarapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seyitaligirgin.rentcarapp.R;
import com.seyitaligirgin.rentcarapp.model.Car;
import com.seyitaligirgin.rentcarapp.databinding.CarsListRowBinding;
import com.seyitaligirgin.rentcarapp.view.CarsListFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarsListAdapter extends RecyclerView.Adapter<CarsListAdapter.CarsListViewHolder> {

    ArrayList<Car> carsArrayList;

    public CarsListAdapter(ArrayList<Car> carArrayList){
        this.carsArrayList = carArrayList;
    }

    public class CarsListViewHolder extends RecyclerView.ViewHolder {

        private CarsListRowBinding binding;

        public CarsListViewHolder(CarsListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CarsListAdapter.CarsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarsListRowBinding binding = CarsListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CarsListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsListViewHolder holder, int position) {

        //String url = Uri.encode(carsArrayList.get(position).brandLogoUrl);

        Picasso.get().load(carsArrayList.get(position).brandLogoUrl).into(holder.binding.carBrandLogo);

        holder.binding.carBrandText.setText(carsArrayList.get(position).brandName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Araç: " + carsArrayList.get(position).brandName + " Model: " + carsArrayList.get(position).modelName;
                Toast.makeText(view.getContext(), msg, Toast.LENGTH_LONG).show();
                //Go to Cars details page
            }
        });

    }


    @Override
    public int getItemCount() {
        return carsArrayList.size();
    }


}
