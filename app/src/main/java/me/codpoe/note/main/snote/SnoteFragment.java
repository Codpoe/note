package me.codpoe.note.main.snote;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.main.ViewPagerAdapter;
import me.codpoe.note.main.snote.done.DoneFragment;
import me.codpoe.note.main.snote.todo.TodoFragment;

public class SnoteFragment extends Fragment {

    @BindView(R.id.snote_tab_lay)
    TabLayout mTabLay;
    @BindView(R.id.snote_pager)
    ViewPager mPager;

    private TodoFragment mTodoFragment;
    private DoneFragment mDoneFragment;

    public SnoteFragment() {
    }

    public static SnoteFragment newInstance() {
        return new SnoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_snote, container, false);
        ButterKnife.bind(this, view);

        setupViewPager();
        setupTabs();

        return view;
    }

    private void setupViewPager() {
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        if (fragmentList == null) {
            mTodoFragment = TodoFragment.newInstance();
            mDoneFragment = DoneFragment.newInstance();
        } else {
            mTodoFragment = (TodoFragment) fragmentList.get(0);
            mDoneFragment = (DoneFragment) fragmentList.get(1);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(mTodoFragment, getResources().getString(R.string.todo));
        adapter.addFragment(mDoneFragment, getResources().getString(R.string.done));
        mPager.setAdapter(adapter);
    }

    private void setupTabs() {
        mTabLay.setupWithViewPager(mPager);
        mTabLay.getTabAt(0).setIcon(R.drawable.bitmap_todo);
        mTabLay.getTabAt(1).setIcon(R.drawable.bitmap_done);
    }
}
