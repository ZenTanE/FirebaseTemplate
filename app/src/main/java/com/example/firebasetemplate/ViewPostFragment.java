package com.example.firebasetemplate;

import static com.google.firebase.firestore.FirebaseFirestore.getInstance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.firebasetemplate.databinding.FragmentSignOutBinding;
import com.example.firebasetemplate.databinding.FragmentViewPostBinding;
import com.example.firebasetemplate.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;


public class ViewPostFragment extends AppFragment {


    private FragmentViewPostBinding binding;

    public ViewPostFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentViewPostBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String postid = ViewPostFragmentArgs.fromBundle(getArguments()).getPostid();

        // todo obtener de firebase el post con ese id y mostrarlo en la pantalla

        DocumentReference docRef = getInstance().collection("posts").document(postid);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Post post = documentSnapshot.toObject(Post.class);

            binding.autor.setText(post.authorName);
            binding.contenido.setText(post.content);
            Glide.with(requireContext()).load(post.imageUrl).into(binding.imagen);
            binding.favorito.setChecked(post.likes.containsKey(auth.getUid()));
            Glide.with(requireContext()).load(post.authorImageUrl).into(binding.autorFoto);

        });



    }

}