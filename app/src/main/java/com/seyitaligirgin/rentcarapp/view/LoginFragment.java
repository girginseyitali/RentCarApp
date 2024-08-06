package com.seyitaligirgin.rentcarapp.view;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.seyitaligirgin.rentcarapp.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private FragmentLoginBinding binding;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        /*
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToFirstPageFragment();
            Navigation.findNavController(getView()).navigate(action);
            //Load Main Page Fragment
        }
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

    }

    public void login(View view){

        String email = binding.loginEmailTxt.getText().toString();
        String password = binding.loginPasswordTxt.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(getContext(), "Email or Password field can not be empty", Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //Load Main Page Fragment
                    NavDirections action = LoginFragmentDirections.actionLoginFragmentToFirstPageFragment();
                    Navigation.findNavController(view).navigate(action);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void signUp(View view){
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
        Navigation.findNavController(view).navigate(action);
    }
}