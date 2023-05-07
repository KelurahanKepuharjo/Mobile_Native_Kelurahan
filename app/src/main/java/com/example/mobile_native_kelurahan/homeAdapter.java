package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class homeAdapter extends AppCompatActivity {

    private int selectedTab = 1;
//    private Object ScaleAnimation;
//    private ScaleAnimation scaleAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_adapter);

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout suratLayout = findViewById(R.id.suratLayout);
        final LinearLayout statusLayout = findViewById(R.id.statusLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView suratImage = findViewById(R.id.suratImage);
        final ImageView statusImage = findViewById(R.id.statusImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView suratTxt = findViewById(R.id.suratTxt);
        final TextView statusTxt = findViewById(R.id.statusTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);

        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, HomeFragment.class, null)
                        .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 1){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    suratTxt.setVisibility(View.GONE);
                    statusTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    suratImage.setImageResource(R.drawable.surat_icon);
                    statusImage.setImageResource(R.drawable.status_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    suratLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //buat klo ngetab
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.home_selected_icon);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;

                }
            }
        });
        suratLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, SuratFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    statusTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    statusImage.setImageResource(R.drawable.status_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //buat klo ngetab
                    suratTxt.setVisibility(View.VISIBLE);
                    suratImage.setImageResource(R.drawable.surat_selected_icon);
                    suratLayout.setBackgroundResource(R.drawable.round_back_surat_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    suratLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;

                }
            }
        });
        statusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 3){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, StatusFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    suratTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    suratImage.setImageResource(R.drawable.surat_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    suratLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //buat klo ngetab
                    statusTxt.setVisibility(View.VISIBLE);
                    statusImage.setImageResource(R.drawable.status_selected_icon);
                    statusLayout.setBackgroundResource(R.drawable.round_back_status_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    statusLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;

                }
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 4){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();
                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    String token = preferences.getString("token", "");

                    homeTxt.setVisibility(View.GONE);
                    suratTxt.setVisibility(View.GONE);
                    statusTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    suratImage.setImageResource(R.drawable.surat_icon);
                    statusImage.setImageResource(R.drawable.status_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    suratLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    statusLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //buat klo ngetab
                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profile_selected_icon);
                    profileLayout.setBackgroundResource(R.drawable.round_back_status_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;

                }
            }
        });
    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }
}