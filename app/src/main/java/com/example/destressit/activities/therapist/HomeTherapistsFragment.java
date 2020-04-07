package com.example.destressit.activities.therapist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.destressit.R;
import com.example.destressit.activities.user.ViewReport;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.OnBackPressed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeTherapistsFragment extends Fragment implements OnBackPressed {

    View root;
    LayoutInflater inflater;
    Button contact;
    private String[][] requests= new String[10][4];
    private static final int REQUEST_CALL=1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        DatabaseReference dbref = database.getReference("therapists/" + new DatabaseHelper(getContext()).getTKey() + "/requests");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keyChildren = dataSnapshot.getChildren();
                int i = 0;
                Log.d("TAG", "Check this1");
                for (DataSnapshot key : keyChildren) {
                    requests[i][0] = key.getKey();
                    requests[i][1] = key.child("uname").getValue().toString();
                    requests[i][2] = key.child("uemail").getValue().toString();
                    requests[i][3] = key.child("uphone").getValue().toString();
                    i++;
                }

                if(requests[0][0]!=null)
                    addLayout(0,requests[0][0],requests[0][1],requests[0][2],requests[0][3]);
                if(requests[1][0]!=null)
                    addLayout(1,requests[1][0],requests[1][1],requests[1][2],requests[1][3]);
                if(requests[2][0]!=null)
                    addLayout(2,requests[2][0],requests[2][1],requests[2][2],requests[2][3]);
                if(requests[3][0]!=null)
                    addLayout(3,requests[3][0],requests[3][1],requests[3][2],requests[3][3]);
                if(requests[4][0]!=null)
                    addLayout(4,requests[4][0],requests[4][1],requests[4][2],requests[0][3]);
                if(requests[5][0]!=null)
                    addLayout(5,requests[5][0],requests[5][1],requests[5][2],requests[5][3]);
                if(requests[6][0]!=null)
                    addLayout(6,requests[6][0],requests[6][1],requests[6][2],requests[6][3]);
                if(requests[7][0]!=null)
                    addLayout(7,requests[7][0],requests[7][1],requests[7][2],requests[7][3]);
                if(requests[8][0]!=null)
                    addLayout(8,requests[8][0],requests[8][1],requests[8][2],requests[8][3]);
                if(requests[9][0]!=null)
                    addLayout(9,requests[9][0],requests[9][1],requests[9][2],requests[9][3]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onViewCreated(view, savedInstanceState);

    }

    private void contactBox(final String email, final String phone){
        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setCancelable(false);
        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
        builder.setView(dialogView);
        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
        textView.setText("How do you want to contact?");
        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
        acceptButton.setText("Email");
        final AlertDialog alertDialog = builder.create();
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(email);
                alertDialog.dismiss();
            }
        });
        Button declineButton = (Button) dialogView.findViewById(R.id.decline);
        declineButton.setText("Call");

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makecall(phone);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void makecall(String phone) {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            if(ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                String dial="tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            String dial="tel:" + phone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }
    }

    private void sendMail(String email) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={email};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Reply to report");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    private void viewReport(String key){
        Toast.makeText(getContext(),key,Toast.LENGTH_LONG).show();
        DatabaseReference dbref = database.getReference("users/" + key + "/reports");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long report = (Long) dataSnapshot.child("stressPercent").getValue();
                Intent i = new Intent(getContext(), ViewReport.class);
                i.putExtra("reportValues",report);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addLayout(int no,final String key, final String name, final String email,final String phone) {
        if (no == 0) {
            TextView nameText = (TextView) root.findViewById(R.id.nameText1);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText1);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText1);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view1);
            cardView.setVisibility(View.VISIBLE);
            final Button viewReport = (Button)root.findViewById(R.id.viewReport1);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact1);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        } else if (no == 1) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText2);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText2);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText2);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view2);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport2);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact2);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        } else if (no == 2) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText3);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText3);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText3);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view3);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport3);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact3);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        } else if (no == 3) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText4);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText4);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText4);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view4);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport4);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact4);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 4) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText5);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText5);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText5);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view5);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport5);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact5);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 5) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText6);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText6);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText6);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view6);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport6);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact6);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 6) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText7);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText7);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText7);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view7);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport7);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact7);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 7) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText8);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText8);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText8);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view8);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport8);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact8);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 8) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText9);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText9);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText9);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view9);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport9);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact9);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }else if (no == 9) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText10);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText10);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText10);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view10);
            cardView.setVisibility(View.VISIBLE);
            Button viewReport = (Button)root.findViewById(R.id.viewReport10);
            viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewReport(key);
                }
            });
            Button contact = (Button)root.findViewById(R.id.contact10);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactBox(email,phone);
                }
            });

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission ACCEPTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}