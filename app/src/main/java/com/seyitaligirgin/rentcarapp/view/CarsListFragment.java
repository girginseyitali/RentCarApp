package com.seyitaligirgin.rentcarapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seyitaligirgin.rentcarapp.model.Car;
import com.seyitaligirgin.rentcarapp.adapter.CarsListAdapter;
import com.seyitaligirgin.rentcarapp.databinding.FragmentCarsListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CarsListFragment extends Fragment {

    private FragmentCarsListBinding binding;
    CarsListAdapter carsListAdapter;
    ArrayList<Car> carArrayList;

    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    public CarsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        carArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.carslistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carsListAdapter = new CarsListAdapter(carArrayList);
        binding.carslistRecyclerView.setAdapter(carsListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.carslistRecyclerView.setLayoutManager(layoutManager);

        getData();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backArrowClicked(view);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void backArrowClicked(View view){
       NavDirections action = CarsListFragmentDirections.actionCarsListFragmentToFirstPageFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void getData(){

        CollectionReference collectionReference = firebaseFirestore.collection("Cars");
        collectionReference.orderBy("brand_name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(getContext(),error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){

                       Map<String, Object> data =  snapshot.getData();

                       String brandName = (String) data.get("brand_name");
                       String modelName = (String) data.get("model_name");
                       String modelYear = (String) data.get("model_year");
                       String logoUrl = (String) data.get("logo_url");
                       String[] modelImages;
                       /*for(String key : dataKeySet){
                           if (key.equals("car_images")){

                           }
                       }*/
                        for (Map.Entry e : data.entrySet()){
                            if (e.getKey().equals("car_images")){
                                ArrayList<String> list = (ArrayList<String>) e.getValue();

                                modelImages = list.toArray(new String[0]);   //list1;
                                Car car = new Car(brandName,modelName,modelYear,logoUrl,modelImages);
                                carArrayList.add(car);
                            }
                        }
                       //String[] modelImages = data.keySet().toArray(new String[0]);

                    }
                    carsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}