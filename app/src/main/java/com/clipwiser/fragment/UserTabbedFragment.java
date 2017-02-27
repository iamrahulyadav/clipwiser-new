package com.clipwiser.fragment;

/**
 * Created by sibaprasad on 01/01/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.clipwiser.R;
import com.clipwiser.adapter.ContactAndCallAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by sibaprasad on 22/12/16.
 */

public class UserTabbedFragment extends Fragment {
    public static final String TAG = "UserTabbedFragment";
    List<String> listUser = new ArrayList<>();
    private View rootView;
    private RecyclerView recyclerViewUser;
    private LinearLayout linearLayoutRootUserTab;
    private ContactAndCallAdapter userAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int type = 0;
    List<String> listData = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_tab, container, false);

        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {
        recyclerViewUser = (RecyclerView) rootView.findViewById(R.id.recyclerViewUser);
        linearLayoutRootUserTab = (LinearLayout) rootView.findViewById(R.id.linearLayoutRootUserTab);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        for(int  i = 0;i<20;i++){
            listData.add("asda"+i);
        }
        ContactAndCallAdapter contactAndCallAdapter = new ContactAndCallAdapter(getActivity(),listData);
        recyclerViewUser.setAdapter(contactAndCallAdapter);

    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }
}