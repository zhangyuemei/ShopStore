package com.hswt.shopstore.shopstore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by zhangyuemei on 2019/2/13.
 * Description:
 */
public class ClassificationFragment  extends Fragment {

    public static ClassificationFragment newInstance(String param1) {
        ClassificationFragment fragment = new ClassificationFragment();
        Bundle args = new Bundle();
        //    args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


}
