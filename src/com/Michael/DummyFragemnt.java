package com.Michael;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Michael.AccountBook.R;

/**
 * Created with IntelliJ IDEA
 * Created by MaDianYong
 * Date: 2015/4/10
 */

public class DummyFragemnt extends Fragment {
    private TextView textView_dummyfragment_info;
    private int tabindex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tabindex = bundle.getInt("tabindex");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);


        String data = "";
        switch (tabindex) {
            case 0:
                data = "第1个TAB";
                break;
            case 1:
                data = "第2个TAB";
                break;
            default:
                break;
        }
        textView_dummyfragment_info.setText(data);
        return view;
    }
}
