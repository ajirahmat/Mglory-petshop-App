package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SettingProfile extends AppCompatActivity {

    public static FirebaseStorage storage;
    public static StorageReference storageRef;
    private StorageReference refProdukImage;
    TextView namaDisplay;
    private Uri filePath;
    ImageView imageDisplay;
    Button btnUpdateProfile;
    Button btnPhoto;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        imageDisplay = (ImageView) findViewById(R.id.imgDisplay);
        namaDisplay = (TextView) findViewById(R.id.editTextNamaDisplay);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        btnUpdateProfile = (Button) findViewById(R.id.btn_upProfile);
        btnPhoto = (Button) findViewById(R.id.pilihGambar);

        btnPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                chooseImage();


            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfile();
            }
        });


    }

    public void addProfile(){

        refProdukImage = storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
        StorageReference photoImagesRef = storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg");
        refProdukImage.getName().equals(photoImagesRef.getName());
        refProdukImage.getPath().equals(photoImagesRef.getPath());

        //mengambil gambar dari imageview yang sudah di set menjadi selected image sebelumnya
        imageDisplay.setDrawingCacheEnabled(true);
        imageDisplay.buildDrawingCache();
        Bitmap bitmap =imageDisplay.getDrawingCache(); //convert imageview ke bitmap
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //convert bitmap ke bytearray
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = refProdukImage.putBytes(data); //upload image yang sudah dalam bentuk bytearray ke firebase storage
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                filePath = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                String img_url = filePath.toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(namaDisplay.getText().toString().trim())
                        .setPhotoUri(Uri.parse(img_url))
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            String TAG;

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });

                Toast.makeText(SettingProfile.this, "User profile updated.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageDisplay.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
