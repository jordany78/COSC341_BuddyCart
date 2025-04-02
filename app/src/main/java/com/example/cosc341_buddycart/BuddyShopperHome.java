package com.example.cosc341_buddycart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuddyShopperHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuddyShopperHome extends Fragment {

    private Button approveButton;
    private SharingViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuddyShopperHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuddyShopperHome.
     */
    // TODO: Rename and change types and number of parameters
    public static BuddyShopperHome newInstance(String param1, String param2) {
        BuddyShopperHome fragment = new BuddyShopperHome();
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
        View view = inflater.inflate(R.layout.fragment_buddy_shopper_home, container, false);

        // View Model for passing data -> copy this to share data between fragments!

        viewModel = new ViewModelProvider(requireActivity()).get(SharingViewModel.class);

        approveButton = view.findViewById(R.id.approveButton);

        approveButton.setOnClickListener(new View.OnClickListener() { // THIS IS JUST A DEMO
            @Override
            public void onClick(View v) {
                Boolean isSubmitted = (Boolean) viewModel.getData("isSubmitted").getValue(); // Gets it from RemoteShopperHome

                if(isSubmitted != null && isSubmitted) {

                    // Toast at top of screen
                    Toast.makeText(view.getContext(), "Your list has been approved!", Toast.LENGTH_SHORT).show();

                    if ((MainActivity) getActivity() != null) { // Call funct. in main activity
                        ((MainActivity) getActivity()).listApproved();
                    }

                }
            }
        });

        return view;
    }
}