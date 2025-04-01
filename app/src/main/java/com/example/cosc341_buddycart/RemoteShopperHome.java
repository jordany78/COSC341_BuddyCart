package com.example.cosc341_buddycart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RemoteShopperHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoteShopperHome extends Fragment {

    private TextView waitingForBuddyText;
    private Button submitButton;
    private SharingViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RemoteShopperHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemoteShopperHome.
     */
    // TODO: Rename and change types and number of parameters
    public static RemoteShopperHome newInstance(String param1, String param2) {
        RemoteShopperHome fragment = new RemoteShopperHome();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remote_shopper_home, container, false);

        // View Model for passing data -> copy this to share data between fragments!

        viewModel = new ViewModelProvider(requireActivity()).get(SharingViewModel.class);


        waitingForBuddyText = view.findViewById(R.id.waitingForBuddyText);
        submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waitingForBuddyText.setText("Your list is waiting to be accepted by a buddy. "+
                        "You'll be notified shortly once connected.");

                viewModel.setData("isSubmitted", true);
            }
        });

        return view;
    }
}