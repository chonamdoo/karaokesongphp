package kr.ds.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class BaseFragment extends Fragment {

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
    protected int getScreenHeight() {
        Activity activity = getActivity();
        if (activity == null) {
            return 0;
        }
        return activity.findViewById(android.R.id.content).getHeight();
    }

    public static <T extends Fragment> T newInstance(Class<T> clazz) {
        return newInstance(clazz, new Bundle());
    }

    public static <T extends Fragment> T newInstance(Class<T> clazz, Bundle data) {
        try {
            T fragment = clazz.newInstance();
            fragment.setRetainInstance(true);
            // args are always present(not null),
            // even if they are not necessary
            if(data != null) {
                fragment.setArguments(data);
            }
            return fragment;

        } catch (InstantiationException | java.lang.InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void Tab(int tab);


}
