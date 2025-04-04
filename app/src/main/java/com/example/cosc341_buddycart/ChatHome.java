package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatHome extends Fragment {

    private SharingViewModel viewModel;
    private ListView listView;

    private Fragment realTimeChatFragment;

    //ArrayList<String> conversations; USE AN ARRAY LIST FOR ADDING NEW CHATS ONCE TASK #3 IS DONE

    public String conversations[] = { // REPLACE WITH LIST NAMES
            "Chat 1",
            "Chat 2",
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatHome.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatHome newInstance(String param1, String param2) {
        ChatHome fragment = new ChatHome();
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
        View view = inflater.inflate(R.layout.fragment_chat_home, container, false);

        // View Model for passing data -> copy this to share data between fragments!
        viewModel = new ViewModelProvider(requireActivity()).get(SharingViewModel.class);
        listView = view.findViewById(R.id.listView); // Don't forget to include the view. otherwise findViewById won't work


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, Arrays.asList(conversations));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                String selectedChat = listView.getItemAtPosition(position).toString();

                Intent intent = new Intent(getActivity(), RealTimeChat.class);
                Bundle bundle = new Bundle();

                bundle.putString("chatId", selectedChat);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        return view;
    }
}