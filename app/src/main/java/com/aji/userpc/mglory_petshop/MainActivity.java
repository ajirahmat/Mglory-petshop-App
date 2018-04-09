package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView StatusCard, AksesorisCard,FoodCard, CareCard, BuyCard;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    FirebaseAuth mAuth;

    TextView namauser, emailuser;
    ImageView fotouser;

    String nama, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailuser = findViewById(R.id.emailUser);
        namauser = findViewById(R.id.namaUser);
        fotouser = findViewById(R.id.fotoUser);

        StatusCard = findViewById(R.id.status_card);
        AksesorisCard = findViewById(R.id.aksesoris_card);
        FoodCard = findViewById(R.id.food_card);
        CareCard = findViewById(R.id.care_card);
        BuyCard = findViewById(R.id.buy_card);
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation_view);

//        namauser.setText("nama user");
//        emailuser.setText(mAuth.getCurrentUser().getEmail());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        Intent intent;
                        int id = menuItem.getItemId();
                        switch (id){
                            case R.id.setting :
                                Toast.makeText(MainActivity.this ,"Setting",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.signOut:
                                Toast.makeText(MainActivity.this ,"SignOut!",
                                        Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                                break;
                            case R.id.mychart:
                                intent = new Intent(MainActivity.this, ShoppingCart.class);
                                startActivity(intent);

                                break;
                        }


                        return true;
                    }
                });

        StatusCard.setOnClickListener(this);
        AksesorisCard.setOnClickListener(this);
        FoodCard.setOnClickListener(this);
        CareCard.setOnClickListener(this);
        BuyCard.setOnClickListener(this);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.signOut:
//
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                Intent intent = new Intent();
//                startActivity(intent);
//
//                break;
//        }
//
//        return true;
//    }


    public void onClick(View view) {
        Intent i ;
        String kategori;
        switch (view.getId()){
            case R.id.status_card :
                i = new Intent(this,ListProduk.class);
                kategori = "Status";
                i.putExtra("kategori", kategori);
                startActivity(i);
                break;
            case R.id.aksesoris_card :
                i = new Intent(this,ListProduk.class);
                kategori = "Aksesoris";
                i.putExtra("kategori", kategori);
                startActivity(i);
                break;
            case R.id.food_card :
                i = new Intent(this,ListProduk.class);
                kategori = "Makanan";
                i.putExtra("kategori", kategori);
                startActivity(i);
                break;
            case R.id.care_card :
                i = new Intent(this,ListProduk.class);
                kategori = "Perawatan";
                i.putExtra("kategori", kategori);
                startActivity(i);
                break;
            case R.id.buy_card :
                i = new Intent(this,ListProduk.class);
                kategori = "Buy";
                i.putExtra("kategori", kategori);
                startActivity(i);
                break;
        }

    }

}
