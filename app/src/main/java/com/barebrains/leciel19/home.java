package com.barebrains.leciel19;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {
    private long delay1=0;
    private long delay2=0;

    Button bl,br;
    ImageView slide;

    public home() {
        // Required empty public constructor
    }


    public static home newInstance() {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        slide=(ImageView)root.findViewById(R.id.slide);
        bl=(Button)root.findViewById(R.id.bl);
        br=(Button)root.findViewById(R.id.br);
        slider s=new slider();



        ((CardView)root.findViewById(R.id.w)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Workshop");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.te)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Technical Events");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.nte)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Non Technical Events");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.ps)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Pro Shows");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.ud)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),about.class);
              //  i.putExtra("category","Unnamed");
                startActivity(i);
            }
        });
        ((CardView)root.findViewById(R.id.gl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),event_categories.class);
                i.putExtra("category","Guest Lectures");
                startActivity(i);
            }
        });

        Intent n=new Intent("gyanith.notify");
        getContext().sendBroadcast(n);


        CardView w=(CardView)root.findViewById(R.id.w);
        CardView te=(CardView)root.findViewById(R.id.te);
        CardView nte=(CardView)root.findViewById(R.id.nte);
        nte.setAlpha(0);
        CardView ps=(CardView)root.findViewById(R.id.ps);
       ps.setAlpha(0);
        CardView gl=(CardView)root.findViewById(R.id.gl);
       gl.setAlpha(0);
        CardView un=(CardView)root.findViewById(R.id.ud);
        un.setAlpha(0);

        ObjectAnimator wa=ObjectAnimator.ofFloat(w,"translationX",-300f,0f);
        wa.setInterpolator(new DecelerateInterpolator());
        wa.setStartDelay(delay1);
        wa.setDuration(300);
        wa.start();
        delay1+=150;

        ObjectAnimator nta=ObjectAnimator.ofFloat(nte,"translationX",-300f,0f);
        nta.setInterpolator(new DecelerateInterpolator());
        nta.setStartDelay(delay1);
        nta.setDuration(300);
        nta.start();
        ObjectAnimator ntl=ObjectAnimator.ofFloat(nte,"alpha",0,1);
        ntl.setStartDelay(delay1);
        ntl.start();
        delay1+=150;

        ObjectAnimator ua=ObjectAnimator.ofFloat(un,"translationX",-300f,0f);
        ua.setInterpolator(new DecelerateInterpolator());
        ua.setStartDelay(delay1);
        ua.setDuration(300);
        ua.start();
        ObjectAnimator ul=ObjectAnimator.ofFloat(un,"alpha",0,1);
        ul.setStartDelay(delay1);
        ul.start();
        delay1+=150;

        ObjectAnimator ta=ObjectAnimator.ofFloat(te,"translationX",300f,0f);
        ta.setInterpolator(new DecelerateInterpolator());
        ta.setStartDelay(delay2);
        ta.setDuration(300);
        ta.start();
        delay2+=150;

        ObjectAnimator pa=ObjectAnimator.ofFloat(ps,"translationX",300f,0f);
        pa.setInterpolator(new DecelerateInterpolator());
        pa.setStartDelay(delay2);
        pa.setDuration(300);
        pa.start();
        ObjectAnimator pl=ObjectAnimator.ofFloat(ps,"alpha",0,1);
        pl.setStartDelay(delay2);
        pl.start();
        delay2+=150;

        ObjectAnimator ga=ObjectAnimator.ofFloat(gl,"translationX",300f,0f);
        ga.setInterpolator(new DecelerateInterpolator());
        ga.setStartDelay(delay2);
        ga.setDuration(300);
        ga.start();
        ObjectAnimator gll=ObjectAnimator.ofFloat(gl,"alpha",0,1);
        gll.setStartDelay(delay2);
        gll.start();
        delay2+=150;


        s.run();

        return root;
    }


    class slider extends Thread{

        DatabaseReference dref;
        List<String> Urls=new ArrayList<String>();
        int counter=0,total=0;

        slider(){
            dref= FirebaseDatabase.getInstance().getReference();
            dref.child("url").child("slide").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Log.i("info",snapshot.toString());
                        Urls.add(snapshot.getValue().toString());
                        total++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void run() {

//                counter=counter%total;
            Log.i("ll","kk");
                try {
                    Log.i("url",Urls.get(counter));
                    URL url = new URL(Urls.get(counter));
                    Glide.with(getContext()).load(url).into(slide);
                }
                catch(Exception e){

                }
//                counter++;


        }
    }



}
