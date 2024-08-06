package com.seyitaligirgin.rentcarapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seyitaligirgin.rentcarapp.databinding.FragmentLoginBinding;
import com.seyitaligirgin.rentcarapp.databinding.FragmentSignUpBinding;

import java.util.HashMap;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

        binding.signupCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSignUp(view);
            }
        });

    }

    public void signUp(View view){

        String email = binding.signupEmailTxt.getText().toString();
        String password = binding.signupPasswordTxt.getText().toString();
        String username = binding.signupUsernameTxt.getText().toString();

        if(email.isEmpty()|| password.isEmpty() || username.isEmpty()){
            Toast.makeText(getContext(), "Field can not be empty", Toast.LENGTH_LONG).show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //Sign Up Successfull
                    Toast.makeText(getContext(), "Sign Up Successfull", Toast.LENGTH_SHORT).show();

                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("email", email);

                    firebaseFirestore.collection("Users").document(firebaseAuth.getUid()).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                            Navigation.findNavController(view).navigate(action);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void cancelSignUp(View view){
        //Return back to Login
        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }
}