package com.example.firebasetemplate;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.firebasetemplate.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

// THIS CLASS IS UNFINISHED AND CAN CRASH THE PROGRAM, JUST IGNORE IT

public class RegisterFragment extends AppFragment {
    private FragmentRegisterBinding binding;
    private Uri uriImagen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegisterBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.previsualizacion2.setOnClickListener(v -> galeria.launch("image/*"));

        appViewModel.uriImagenSeleccionada.observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {

                Glide.with(this).load(uri).into(binding.previsualizacion2);
                uriImagen = uri;

            }
        });

        binding.createAccountButton.setOnClickListener(v -> {
            if (binding.passwordEditText.getText().toString().isEmpty()) {
                binding.passwordEditText.setError("Required");
                return;
            }
            auth.createUserWithEmailAndPassword(
                            binding.emailEditText.getText().toString(),
                            binding.passwordEditText.getText().toString()
                    ).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            addMisc(); // TODO: add the name and the image to the user

                            navController.navigate(R.id.action_registerFragment_to_postsHomeFragment);

                        } else {

                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        });
    }

    private void addMisc() {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(binding.nameEditText.getText().toString())
                .setPhotoUri(Uri.parse(String.valueOf(appViewModel.uriImagenSeleccionada))) // TODO: MAKE THIS AN URL
                .build();

    }

    private final ActivityResultLauncher<String> galeria = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        appViewModel.setUriImagenSeleccionada(uri);
    });

}