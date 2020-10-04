package com.example.coursemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;

    Button btnUpload;
    Button delete;
    ImageButton btnExit;
    TextView bioCount;
    ListView bioLectList;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageRef = storage.getReference();

        btnUpload = findViewById(R.id.bioupload);
        bioCount = findViewById(R.id.bio_count);
        bioLectList = findViewById(R.id.biolectlist);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        btnExit = findViewById(R.id.btn_exit2);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
                MainActivity2.this.startActivity(myIntent);
            }
        });



        firestore.collection("bio_lectures")
                .orderBy("date")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            bioCount.setText("0");
                            return;
                        }

                        List<String> lects = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("lect_id") != null) {
                                lects.add(doc.get("lect_id")+" - "+doc.get("date").toString());
                            }
                        }
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                (MainActivity2.this, android.R.layout.simple_list_item_1, lects);
                        bioLectList.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        bioCount.setText(""+lects.size());
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    Uri fileUri = data.getData();
                    StorageReference biolectRef = storageRef.child("biolects/"+ UUID.randomUUID().toString());
                    UploadTask uploadTask = biolectRef.putFile(fileUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Map<String, Object> docData = new HashMap<>();
                            docData.put("lect_id", 123);
                            docData.put("lecturer", "Kolia");
                            docData.put("date", new Date().toString());
                            docData.put("file_url", taskSnapshot.getMetadata().getPath());
                            firestore.collection("bio_lectures")
                                    .add(docData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MainActivity2.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity2.this, "Uploaded failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }
}