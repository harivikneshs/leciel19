package com.barebrains.leciel19;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class notifications extends Fragment {

    private ListView notificationList;
    private DatabaseReference ref;
    private notificationItem item1;
    private notificationAdapter madapter;
    private ArrayList<notificationItem> arrListItem;

    public notifications() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root= inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationList = root.findViewById(R.id.notificationListView);
        ref = FirebaseDatabase.getInstance().getReference().child("notifications");
        Log.d("qwer",ref.toString());
        arrListItem = new ArrayList<notificationItem>();

        madapter = new notificationAdapter(getContext(), arrListItem, R.layout.notitem);

        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("asd",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrListItem.clear();
                Log.d("qwer",dataSnapshot.toString());
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    try {
                        item1 = new notificationItem(snapshot.child("sender").getValue().toString(), snapshot.child("time").getValue().toString(), snapshot.child("text").getValue().toString());
                        arrListItem.add(0, item1);
                        Log.d("qwer", "noti");
                    }
                    catch(Exception e){}
                }
                madapter.notifyDataSetChanged();
                ((ProgressBar)root.findViewById(R.id.notload)).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        notificationList.setAdapter(madapter);
        FloatingActionButton floatingActionButton = root.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder b=new AlertDialog.Builder(getContext());
                b.setView(R.layout.notadd);
                b.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        View v1=getLayoutInflater().inflate(R.layout.notadd,null);
                        EditText e=(v1.findViewById(R.id.Id)),d=v1.findViewById(R.id.Noti);

                        String key = ref.push().getKey(),ee=e.getText().toString(),dd=d.getText().toString();
                        String time = new SimpleDateFormat("EEE h:m a").format(new Date());
                        ref.child(key).child("sender").setValue(ee);
                        ref.child(key).child("text").setValue(dd);
                        ref.child(key).child("time").setValue(time.toString());
                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                b.show();
            }
        });
        return root;
    }
}
