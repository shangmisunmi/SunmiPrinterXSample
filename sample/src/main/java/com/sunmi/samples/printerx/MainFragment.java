package com.sunmi.samples.printerx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunmi.samples.printerx.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.info.setOnClickListener(view1 -> ((MainActivity)getActivity()).switchFragment(InfoFragment.class, true));
        binding.ticket.setOnClickListener(view12 -> ((MainActivity)getActivity()).switchFragment(TicketFragment.class, true));
        binding.label.setOnClickListener(view13 -> ((MainActivity)getActivity()).switchFragment(LabelFragment.class, true));
        binding.file.setOnClickListener(view12 -> ((MainActivity)getActivity()).switchFragment(FileFragment.class, true));
        binding.cmd.setOnClickListener(view12 -> ((MainActivity)getActivity()).switchFragment(CmdFragment.class, true));
        binding.lcd.setOnClickListener(view14 -> ((MainActivity)getActivity()).switchFragment(LcdFragment.class, true));
        binding.cash.setOnClickListener(view14 -> ((MainActivity)getActivity()).switchFragment(CashFragment.class, true));

    }
}
