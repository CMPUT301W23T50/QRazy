package com.example.qrazy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private QRCode qrCode;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRCodeFragment newInstance(String param1, String param2) {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the QRCode from the previous activity
        this.qrCode = requireArguments().getParcelable("QRCode");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);

        // get the views from the layout file
        TextView qr_name = view.findViewById(R.id.qr_code_frag_name);
        TextView qr_score = view.findViewById(R.id.qr_score_frag);
        TextView qr_location = view.findViewById(R.id.qr_location_frag);

        // change the text to match clicked QR code
        qr_name.setText(qrCode.getContent());
        // only change location text if a location exists in the QR code
        qr_location.setText(qrCode.getLocation().toString());

        qr_score.setText(String.valueOf(qrCode.getScore()));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // want to make
    }


}