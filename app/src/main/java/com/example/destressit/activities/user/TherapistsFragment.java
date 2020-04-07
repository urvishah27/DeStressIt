package com.example.destressit.activities.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.destressit.R;
import com.example.destressit.activities.EditProfileActivity;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TherapistsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TherapistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TherapistsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[][] therapists= new String[10][4];
    View root;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private OnFragmentInteractionListener mListener;

    public TherapistsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TherapistsFragment.
     */
    // TODO: Rename and change types and number of parameters
    private static TherapistsFragment newInstance(String param1, String param2) {
        TherapistsFragment fragment = new TherapistsFragment();
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



            DatabaseReference dbref = database.getReference("therapists/");

            dbref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> keyChildren = dataSnapshot.getChildren();
                    int i=0;
                    Log.d("TAG","Check this1");
                    for (DataSnapshot key : keyChildren) {
                        therapists[i][0] = key.getKey();
                        therapists[i][1]=key.child("tname").getValue().toString();
                        therapists[i][2]=key.child("temail").getValue().toString();
                        therapists[i][3]=key.child("tphone").getValue().toString();
                        i++;
                    }

                    if(therapists[0][0]!=null)
                        addLayout(0,therapists[0][0],therapists[0][1],therapists[0][2],therapists[0][3]);
                    if(therapists[1][0]!=null)
                        addLayout(1,therapists[1][0],therapists[1][1],therapists[1][2],therapists[1][3]);
                    if(therapists[2][0]!=null)
                        addLayout(2,therapists[2][0],therapists[2][1],therapists[2][2],therapists[2][3]);
                    if(therapists[3][0]!=null)
                        addLayout(3,therapists[3][0],therapists[3][1],therapists[3][2],therapists[3][3]);
                    if(therapists[4][0]!=null)
                        addLayout(4,therapists[4][0],therapists[4][1],therapists[4][2],therapists[0][3]);
                    if(therapists[5][0]!=null)
                        addLayout(5,therapists[5][0],therapists[5][1],therapists[5][2],therapists[5][3]);
                    if(therapists[6][0]!=null)
                        addLayout(6,therapists[6][0],therapists[6][1],therapists[6][2],therapists[6][3]);
                    if(therapists[7][0]!=null)
                        addLayout(7,therapists[7][0],therapists[7][1],therapists[7][2],therapists[7][3]);
                    if(therapists[8][0]!=null)
                        addLayout(8,therapists[8][0],therapists[8][1],therapists[8][2],therapists[8][3]);
                    if(therapists[9][0]!=null)
                        addLayout(9,therapists[9][0],therapists[9][1],therapists[9][2],therapists[9][3]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_therapists, container, false);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            final Button reqApp = (Button)root.findViewById(R.id.myButton1);
            reqApp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        else if (no == 1) {

            TextView nameText = (TextView) root.findViewById(R.id.nameText2);
            nameText.setText(name);
            TextView emailText = (TextView) root.findViewById(R.id.emailText2);
            emailText.setText("Email: " + email);
            TextView phoneText = (TextView) root.findViewById(R.id.phoneText2);
            phoneText.setText("Phone: " + phone);
            CardView cardView = (CardView) root.findViewById(R.id.card_view2);
            cardView.setVisibility(View.VISIBLE);
            Button reqApp = (Button)root.findViewById(R.id.myButton2);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }

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
            Button reqApp = (Button)root.findViewById(R.id.myButton3);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton4);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton5);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton6);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton7);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton8);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton9);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
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
            Button reqApp = (Button)root.findViewById(R.id.myButton10);
            reqApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString(getContext(), "uname").equals("") || PreferenceUtil.getString(getContext(), "uphone").equals("")) {

                        LayoutInflater inflater;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(false);
                        inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
                        builder.setView(dialogView);

                        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
                        textView.setText("Please fill your information in the Edit Profile section");
                        dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
                        Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
                        acceptButton.setText("Go to Edit Profile");

                        final AlertDialog alertDialog = builder.create();
                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {
                        DatabaseReference dbref = database.getReference("therapists/" + key + "/requests/");
                        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("uname", PreferenceUtil.getString(getContext(), "uname"));
                        result.put("uemail", PreferenceUtil.getString(getContext(), "uemail"));
                        result.put("uphone", PreferenceUtil.getString(getContext(), "uphone"));

                        dbref.child(databaseHelper.getUKey()).updateChildren(result);

                        Toast .makeText(getContext(),"Your request has been send",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }



}
