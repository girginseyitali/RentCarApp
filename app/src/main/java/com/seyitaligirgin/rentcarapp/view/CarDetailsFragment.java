package com.seyitaligirgin.rentcarapp.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seyitaligirgin.rentcarapp.R;
import com.seyitaligirgin.rentcarapp.databinding.FragmentCarDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class CarDetailsFragment extends Fragment {


    private FragmentCarDetailsBinding binding;

    private int index;
    private String[] carImages;

    public CarDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarDetailsBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            String brandName = CarDetailsFragmentArgs.fromBundle(getArguments()).getBrandName();
            String modeName = CarDetailsFragmentArgs.fromBundle(getArguments()).getModelName();
            String modelYear = CarDetailsFragmentArgs.fromBundle(getArguments()).getModelYear();
            carImages = CarDetailsFragmentArgs.fromBundle(getArguments()).getModelImages();

            System.out.println(CarDetailsFragmentArgs.fromBundle(getArguments()).getModelImages());
            System.out.println("Car Images Length: " + carImages.length);
            System.out.println("Index: " + index);

            binding.detailsBrandNameTxt.setText(brandName);
            binding.detailsModelNameTxt.setText(modeName);
            binding.detailsModelYearTxt.setText(modelYear);
            Picasso.get().load(carImages[index]).into(binding.carImageView);
            //binding.carImageView.setImageResource(R.drawable.alfa);
        }

        binding.nextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if (index == carImages.length){
                    index = 0;
                }
                Picasso.get().load(carImages[index]).into(binding.carImageView);
                System.out.println(index);
            }
        });

        binding.prevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if(index <0){
                    index = carImages.length -1;
                }
                Picasso.get().load(carImages[index]).into(binding.carImageView);
                System.out.println(index);
            }
        });

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = CarDetailsFragmentDirections.actionCarDetailsFragmentToCarsListFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.detailsRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = CarDetailsFragmentDirections.actionCarDetailsFragmentToRentFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

    }
}