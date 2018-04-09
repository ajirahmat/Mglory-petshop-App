package com.aji.userpc.mglory_petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrudProduk extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EditText eTNama;
    EditText eTHarga;
    EditText eTDeskripsi;
    Spinner spinKategori;
    Button btnAddProduk;
    Button btnGetData;
    Button btnPilih;
    ListView listViewProduk;
    DatabaseReference databaseProduk;
    ImageView imageView;
    private ProgressDialog pbDialog;


    private Uri filePath;
    public static FirebaseStorage storage;
    public static StorageReference storageRef;
    private StorageReference refProdukImage;
    private final int PICK_IMAGE_REQUEST = 71;

    List<Produk> produks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_produk);

        listViewProduk = (ListView) findViewById(R.id.listViewProduks);


        eTNama = (EditText) findViewById(R.id.editTextNama);
        eTHarga = (EditText) findViewById(R.id.editTextHarga);
        eTDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);
        spinKategori = (Spinner) findViewById(R.id.spinnerKategori);
        btnAddProduk = (Button) findViewById(R.id.buttonAddProduk);
        btnGetData = (Button) findViewById(R.id.btnGetdata);
        btnPilih = (Button) findViewById(R.id.pilihGambar);
        imageView = (ImageView) findViewById(R.id.imageView);
        pbDialog = new ProgressDialog(this);

        databaseProduk = FirebaseDatabase.getInstance().getReference("Produk");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        produks = new ArrayList<>();

        btnAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduk();
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        btnPilih.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                chooseImage();


            }
        });
        listViewProduk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Produk produk = produks.get(i);
                showUpdateDeleteDialog(produk.getId(), produk.getNamaProduk(),
                        produk.getHargaProduk(), produk.getKategoriProduk());
                return true;
            }
        });

    }



    //    @Override
//    protected void onStart() {
//        super.onStart();
    private void getData() {
        databaseProduk.child(spinKategori.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                produks.clear();


                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {

                    Produk produk = produkSnapshot.getValue(Produk.class);
                    produks.add(produk);
                }

                ProdukAdapter produkAdapter = new ProdukAdapter(CrudProduk.this, produks);
                listViewProduk.setAdapter( produkAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "eror : .", databaseError.toException());

            }
        });
    }

    private void addProduk() {

        final String nama = eTNama.getText().toString().trim();
        final String harga = eTHarga.getText().toString().trim();
        final String deskripsi = eTDeskripsi.getText().toString().trim();
        final String kategori = spinKategori.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nama)) {

            pbDialog.setMessage("Uploading..");
            pbDialog.setIndeterminate(true);
            pbDialog.show();

            //melakukan proses update foto
            refProdukImage = storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
            StorageReference photoImagesRef = storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg");
            refProdukImage.getName().equals(photoImagesRef.getName());
            refProdukImage.getPath().equals(photoImagesRef.getPath());

            //mengambil gambar dari imageview yang sudah di set menjadi selected image sebelumnya
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap =imageView.getDrawingCache(); //convert imageview ke bitmap
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
                    //push atau insert data ke firebase database
                    String id = databaseProduk.push().getKey();
                    Produk produk = new Produk (id,nama,harga,deskripsi,kategori,img_url);
                    databaseProduk.child(spinKategori.getSelectedItem().toString()).child(id).setValue(produk);
                    eTNama.setText("");
                    pbDialog.dismiss();
                    Toast.makeText(CrudProduk.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "masukan nama ", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateProduk(String id, String nama, String harga, String kategori) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Produk")
                .child(spinKategori.getSelectedItem().toString()).child(id);


        Produk produk = new Produk(id, nama, harga, kategori);
        dR.setValue(produk);
        Toast.makeText(getApplicationContext(), "Produk telah di Update", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteProduk(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Produk")
                .child(spinKategori.getSelectedItem().toString()).child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Produk Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private void showUpdateDeleteDialog(final String Id, String Name, String harga, String kategori) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextHarga =  (EditText) dialogView.findViewById(R.id.editTextHarga);
        final Spinner spinnerKategori = (Spinner) dialogView.findViewById(R.id.spinnerKategori);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduk);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduk);

        dialogBuilder.setTitle(Name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String harga = editTextHarga.getText().toString().trim();
                String kategori = spinnerKategori.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateProduk(Id, name,harga, kategori);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduk(Id);
                b.dismiss();

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
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
