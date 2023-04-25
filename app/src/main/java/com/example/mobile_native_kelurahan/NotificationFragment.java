package com.example.mobile_native_kelurahan;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private TextView antrian, proses, selesai;
    private int selectedTabNumber = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        antrian = view.findViewById(R.id.antrian);
        proses = view.findViewById(R.id.proses);
        selesai = view.findViewById(R.id.selesai);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NotificationFragment NotificationFragment = new NotificationFragment();
        fragmentTransaction.add(R.id.fragmentContainer1, NotificationFragment);
        fragmentTransaction.commit();

        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTab(1);
            }
        });

        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTab(2);
            }
        });

        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectTab(3);
            }
        });

        return view;
    }

    private void selectTab(int tabNumber){

        TextView selectedTextView;

        TextView nonSelectedTextView1;
        TextView nonSelectedTextView2;

        if (tabNumber == 1){

            selectedTextView = antrian;

            nonSelectedTextView1 = proses;
            nonSelectedTextView2 = selesai;

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            antrianFragment antrianFragment = new antrianFragment();
            fragmentTransaction.add(R.id.fragmentContainer1, antrianFragment);
            fragmentTransaction.commit();
        }
        else if (tabNumber == 2){
            selectedTextView = proses;

            nonSelectedTextView1 = antrian;
            nonSelectedTextView2 = selesai;

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            prosesFragment prosesFragment = new prosesFragment();
            fragmentTransaction.add(R.id.fragmentContainer1, prosesFragment);
            fragmentTransaction.commit();
        }
        else {
            selectedTextView = selesai;

            nonSelectedTextView1 = antrian;
            nonSelectedTextView2 = proses;

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            selesaiFragment selesaiFragment = new selesaiFragment();
            fragmentTransaction.add(R.id.fragmentContainer1, selesaiFragment);
            fragmentTransaction.commit();
        }

        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0,0);
        translateAnimation.setDuration(100);

        if (selectedTabNumber == 1){
            antrian.startAnimation(translateAnimation);
        }
        else if (selectedTabNumber == 2){
            proses.startAnimation(translateAnimation);
        }
        else {
            selesai.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                selectedTextView.setBackgroundResource(R.drawable.bkg_putih_100);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.BLACK);

                nonSelectedTextView1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView1.setTextColor(Color.parseColor("#FF2A2A72"));
                nonSelectedTextView1.setTypeface(null, Typeface.NORMAL);

                nonSelectedTextView2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView2.setTextColor(Color.parseColor("#FF2A2A72"));
                nonSelectedTextView2.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }

}