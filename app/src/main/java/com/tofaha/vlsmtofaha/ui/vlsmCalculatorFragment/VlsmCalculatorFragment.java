package com.tofaha.vlsmtofaha.ui.vlsmCalculatorFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.HostNumber;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.model.Vlsm;
import com.tofaha.vlsmtofaha.realm.RealmController;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;
import com.tofaha.vlsmtofaha.ui.adapter.HostNumberAdapter;
import com.tofaha.vlsmtofaha.ui.vlsmResult.VlsmResult;
import com.tofaha.vlsmtofaha.util.Util;

import io.realm.Realm;
import io.realm.RealmList;

public class VlsmCalculatorFragment extends MainFragment implements VlsmView {
    public static RecyclerView recyclerView;
    @BindView(R.id.vlsm_back)
    ImageView back;
    @BindView(R.id.vlsm_cardView1)
    CardView cardView1;
    @BindView(R.id.vlsm_cardView2)
    CardView cardView2;
    private HostNumberAdapter hostNumberAdapter;
    @BindView(R.id.vlsm_ip_address)
    AutoCompleteTextView ipAddress;
    OnClickListener listener = new C04041();
    @BindView(R.id.vlsm_next1)
    Button next1;
    @BindView(R.id.vlsm_next2)
    Button next2;
    private Realm realm;
    @BindView(R.id.vlsm_subnet_number)
    EditText subnetNumber;

    class C04041 implements OnClickListener {
        C04041() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vlsm_next1:
                    String ip = VlsmCalculatorFragment.this.ipAddress.getText().toString();
                    if (!Util.isValidIp(ip)) {
                        Toast.makeText(VlsmCalculatorFragment.this.getContext(), "Enter valid IP address", Toast.LENGTH_LONG).show();
                        return;
                    }else if (VlsmCalculatorFragment.this.subnetNumber.getText().toString().equals("") ){
                        Toast.makeText(VlsmCalculatorFragment.this.getContext(), "Enter the number of networks", Toast.LENGTH_LONG).show();
                        return;
                    }
                    VlsmCalculatorFragment.this.handleNext1Click(Integer.parseInt(VlsmCalculatorFragment.this.subnetNumber.getText().toString()), ip);
                    VlsmCalculatorFragment.this.startAnimation();
                    return;
                case R.id.vlsm_back:
                    VlsmCalculatorFragment.this.startBackAnimation();
                    return;
                case R.id.vlsm_next2:
                    if (VlsmCalculatorFragment.this.realm.where(Vlsm.class).findAll().size() < VlsmCalculatorFragment.this.pref.getHistoryEntries()) {
                        MyData.vlsm.setId(((long) VlsmCalculatorFragment.this.realm.where(Vlsm.class).findAll().size()) + System.currentTimeMillis());
                        MyData.vlsm.setTime(VlsmCalculatorFragment.this.time());
                        VlsmCalculatorFragment.this.saveInRealm(MyData.vlsm);
                    }
                    VlsmCalculatorFragment.this.resultActivity();
                    return;
                default:
                    return;
            }
        }
    }

    class C04052 implements Runnable {
        C04052() {
        }

        public void run() {
            VlsmCalculatorFragment.this.cardView1.setVisibility(View.GONE);
        }
    }

    class C04063 implements Runnable {
        C04063() {
        }

        public void run() {
            VlsmCalculatorFragment.this.cardView2.setVisibility(View.GONE);
        }
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.pref.isAutoComplete()) {
            this.ipAddress.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1 , vlsmIPlist()));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vlsm_calculator, container, false);
        ButterKnife.bind((Object) this, view);
        this.realm = RealmController.with(getActivity()).getRealm();
        this.next1.setOnClickListener(this.listener);
        this.next2.setOnClickListener(this.listener);
        this.back.setOnClickListener(this.listener);
        recyclerView = (RecyclerView) view.findViewById(R.id.vlsm_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }

    public void hideVlsmCard1() {
        YoYo.with(Techniques.SlideOutLeft).duration(700).playOn(this.cardView1);
        new Handler().postDelayed(new C04052(), 700);
    }

    public void showVlsmCard1() {
        this.cardView1.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInLeft).duration(700).playOn(this.cardView1);
    }

    public void hideVlsmNext1() {
        this.next1.setVisibility(View.GONE);
    }

    public void showVlsmNext1() {
        this.next1.setVisibility(View.VISIBLE);
    }

    public void hideVlsmCard2() {
        YoYo.with(Techniques.SlideOutRight).duration(700).playOn(this.cardView2);
        new Handler().postDelayed(new C04063(), 700);
    }

    public void showVlsmCard2() {
        this.cardView2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight).duration(1000).playOn(this.cardView2);
    }

    public void hideVlsmNext2() {
        this.next2.setVisibility(View.GONE);
    }

    public void showVlsmNext2() {
        this.next2.setVisibility(View.VISIBLE);
    }

    public void handleNext1Click(int subnetNumber, String ipAddress) {
        RealmList<HostNumber> intList = new RealmList();
        for (int i = 0; i < subnetNumber; i++) {
            HostNumber host = new HostNumber();
            host.setHostNumber(0);
            intList.add(host);
        }
        MyData.vlsm.setIpAddress(ipAddress);
        MyData.vlsm.setHostNumberList(intList);
        this.hostNumberAdapter = new HostNumberAdapter(getContext(), MyData.vlsm.getHostNumberList());
        recyclerView.setAdapter(this.hostNumberAdapter);
    }

    public void startAnimation() {
        hideVlsmCard1();
        hideVlsmNext1();
        showVlsmCard2();
        showVlsmNext2();
    }

    public void startBackAnimation() {
        hideVlsmCard2();
        hideVlsmNext2();
        showVlsmCard1();
        showVlsmNext1();
    }

    public void resultActivity() {
        Handler handler = new Handler();
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "wait please .........");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                VlsmCalculatorFragment.this.startActivity(new Intent(VlsmCalculatorFragment.this.getActivity(), VlsmResult.class));
                progressDialog.dismiss();
            }
        }, 1000);
    }
}
