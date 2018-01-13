package com.tofaha.vlsmtofaha.ui.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.Vlsm;
import com.tofaha.vlsmtofaha.realm.RealmCIDRAdapter;
import com.tofaha.vlsmtofaha.realm.RealmController;
import com.tofaha.vlsmtofaha.realm.RealmVlsmAdapter;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;
import com.tofaha.vlsmtofaha.ui.adapter.HistoryCIDRAdapter;
import com.tofaha.vlsmtofaha.ui.adapter.HistoryVlsmAdapter;
import io.realm.RealmResults;

public class HistoryViewPagerFragment extends MainFragment implements HistoryFragmentView {
    public static HistoryViewPagerFragment mainFragment1;
    public static HistoryViewPagerFragment mainFragment2;
    private HistoryCIDRAdapter historyCIDRAdapter;
    private HistoryVlsmAdapter historyVlsmAdapter;
    @BindView(R.id.vlsm_history_recycler)
    RecyclerView recyclerView;

    public static HistoryViewPagerFragment newInstance(int position) {
        HistoryViewPagerFragment historyViewPagerFragment = new HistoryViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.POSITION, position);
        historyViewPagerFragment.setArguments(bundle);
        return historyViewPagerFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_view_pager, container, false);
        ButterKnife.bind((Object) this, view);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        setRecyclerViewContent(getArguments().getInt(Constant.POSITION));
        return view;
    }

    public void setRecyclerViewContent(int position) {
        switch (position) {
            case 0:
                this.historyVlsmAdapter = new HistoryVlsmAdapter(getContext());
                this.recyclerView.setAdapter(this.historyVlsmAdapter);
                RealmController.with((Fragment) this).refresh();
                setRealmAdapterVlsm(RealmController.with((Fragment) this).getVlsms());
                mainFragment1 = this;
                return;
            case 1:
                this.historyCIDRAdapter = new HistoryCIDRAdapter(getContext());
                this.recyclerView.setAdapter(this.historyCIDRAdapter);
                RealmController.with((Fragment) this).refresh();
                setRealmAdapterCidr(RealmController.with((Fragment) this).getCidrs());
                mainFragment2 = this;
                return;
            default:
                return;
        }
    }

    public void setRealmAdapterVlsm(RealmResults<Vlsm> vlsms) {
        this.historyVlsmAdapter.setRealmAdapter(new RealmVlsmAdapter(getActivity().getApplicationContext(), vlsms, true));
        this.historyVlsmAdapter.notifyDataSetChanged();
    }

    public void setRealmAdapterCidr(RealmResults<CIDR> cidrs) {
        this.historyCIDRAdapter.setRealmAdapter(new RealmCIDRAdapter(getActivity().getApplicationContext(), cidrs, true));
        this.historyCIDRAdapter.notifyDataSetChanged();
    }
}
