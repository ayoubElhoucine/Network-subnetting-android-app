package com.tofaha.vlsmtofaha.ui.cidrFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.converter.Converter;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;

public class IPv4Fragment extends MainFragment implements CidrView {
    private int CurrentBits;
    private String CurrentIP;
    @BindView(R.id.bitlength)
    Spinner bitlength_spinner;
    @BindView(R.id.calculate)
    Button calculate;
    private OnItemSelectedListener mBitlengthSelectedListener = new C03882();
    private OnClickListener mCalculateListener = new C03904();
    private OnClickListener mResetListener = new C03915();
    private OnItemSelectedListener mSubnetMaskSelectedListener = new C03893();
    @BindView(R.id.address_range)
    TextView msgAddressRange;
    @BindView(R.id.ipaddress)
    AutoCompleteTextView msgIPAddress;
    @BindView(R.id.ip_binary_host)
    TextView msgIPBinaryHost;
    @BindView(R.id.ip_binary_netmask)
    TextView msgIPBinaryNetmask;
    @BindView(R.id.ip_binary_network)
    TextView msgIPBinaryNetwork;
    @BindView(R.id.maximum_addresses)
    TextView msgMaximumAddresses;
    @BindView(R.id.wildcard)
    TextView msgWildcard;
    @BindView(R.id.main_outer_layout)
    RelativeLayout outerLayout;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.ipv4_result)
    View result;
    @BindView(R.id.subnetmask)
    Spinner subnetmask_spinner;

    class C03871 implements OnEditorActionListener {
        C03871() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            IPv4Fragment.this.doCalculate();
            IPv4Fragment.this.calculate.requestFocus();
            ((InputMethodManager) IPv4Fragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(IPv4Fragment.this.calculate.getWindowToken(), 2);
            return true;
        }
    }

    class C03882 implements OnItemSelectedListener {
        C03882() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
            IPv4Fragment.this.UpdateSubnetmaskFromBitlength();
            IPv4Fragment.this.updateResults(true);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            IPv4Fragment.this.UpdateSubnetmaskFromBitlength();
        }
    }

    class C03893 implements OnItemSelectedListener {
        C03893() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
            IPv4Fragment.this.UpdateBitlengthFromSubnetmask();
            IPv4Fragment.this.updateResults(true);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            IPv4Fragment.this.UpdateBitlengthFromSubnetmask();
        }
    }

    class C03904 implements OnClickListener {
        C03904() {
        }

        public void onClick(View v) {
            IPv4Fragment.this.doCalculate();
        }
    }

    class C03915 implements OnClickListener {
        C03915() {
        }

        public void onClick(View v) {
            IPv4Fragment.this.CurrentIP = "";
            IPv4Fragment.this.CurrentBits = 24;
            IPv4Fragment.this.updateFields();
            IPv4Fragment.this.ClearResults();
            IPv4Fragment.this.hideResult();
        }
    }

    class C03926 implements Runnable {
        C03926() {
        }

        public void run() {
            IPv4Fragment.this.result.setVisibility(View.GONE);
        }
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (MyData.cidr == null || !MyData.cidr.getType().equals(Constant.IPV4)) {
            this.subnetmask_spinner.setSelection(this.pref.getIPv4());
            this.bitlength_spinner.setSelection(this.pref.getIPv4());
        } else {
            this.subnetmask_spinner.setSelection(MyData.cidr.getMaskIp4());
            this.bitlength_spinner.setSelection(MyData.cidr.getMaskIp4());
            this.msgIPAddress.setText(MyData.cidr.getIpAddress());
        }
        if (this.pref.isAutoComplete()) {
            this.msgIPAddress.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1 , cidrIPlist()));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ipv4, container, false);
        ButterKnife.bind((Object) this, view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.bitlengths, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.bitlength_spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> subnetmask_adapter = ArrayAdapter.createFromResource(getContext(), R.array.subnets, android.R.layout.simple_spinner_item);
        subnetmask_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.subnetmask_spinner.setAdapter(subnetmask_adapter);
        this.bitlength_spinner.setOnItemSelectedListener(this.mBitlengthSelectedListener);
        this.subnetmask_spinner.setOnItemSelectedListener(this.mSubnetMaskSelectedListener);
        this.calculate.setOnClickListener(this.mCalculateListener);
        this.reset.setOnClickListener(this.mResetListener);
        this.msgIPAddress.setOnEditorActionListener(new C03871());
        return view;
    }

    private String IntIPToString(int in) {
        int quad1 = ((ViewCompat.MEASURED_STATE_MASK & in) >> 24) & 255;
        int quad2 = (16711680 & in) >> 16;
        int quad3 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & in) >> 8;
        int quad4 = in & 255;
        return String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(quad1), Integer.valueOf(quad2), Integer.valueOf(quad3), Integer.valueOf(quad4)});
    }

    private void UpdateSubnetmaskFromBitlength() {
        this.subnetmask_spinner.setSelection(this.bitlength_spinner.getSelectedItemPosition());
    }

    private void UpdateBitlengthFromSubnetmask() {
        this.bitlength_spinner.setSelection(this.subnetmask_spinner.getSelectedItemPosition());
    }

    private void ClearResults() {
        this.msgAddressRange.setText("");
        this.msgMaximumAddresses.setText("");
        this.msgWildcard.setText("");
        this.msgIPBinaryNetwork.setText("");
        this.msgIPBinaryHost.setText("");
        this.msgIPBinaryNetmask.setText("");
    }

    private boolean updateResults(boolean updateView) {
        CharSequence ipAddressText = this.msgIPAddress.getText();
        if (ipAddressText == null) {
            return false;
        }
        String ip = ipAddressText.toString();
        try {
            int ip32bit = Converter.stringIPtoInt(ip);
            String selectedItem = (String) this.bitlength_spinner.getSelectedItem();
            if (selectedItem == null) {
                return false;
            }
            int maximumAddresses;
            int bitlength = Integer.parseInt(selectedItem.substring(1));
            int ip32bitmask = (1 << (32 - bitlength)) - 1;
            int firstip = ip32bit & (ip32bitmask ^ -1);
            int lastip = firstip | ip32bitmask;
            String ipFirst = IntIPToString(firstip);
            String ipLast = IntIPToString(lastip);
            if (ip32bitmask > 0) {
                maximumAddresses = ip32bitmask - 1;
            } else {
                maximumAddresses = 0;
            }
            String wildcard = IntIPToString(ip32bitmask);
            String binary = Converter.convertIPIntDec2StringBinary(ip32bit);
            String binaryNetmask = Converter.convertIPIntDec2StringBinary(ip32bitmask ^ -1);
            this.CurrentIP = ip;
            this.CurrentBits = bitlength;
            if (updateView) {
                int networkHostCutoff;
                this.msgAddressRange.setText(ipFirst + " - " + ipLast);
                this.msgMaximumAddresses.setText(String.format("%d", new Object[]{Integer.valueOf(maximumAddresses)}));
                this.msgWildcard.setText(wildcard);
                if (bitlength >= 24) {
                    networkHostCutoff = bitlength + 3;
                } else if (bitlength >= 16) {
                    networkHostCutoff = bitlength + 2;
                } else if (bitlength >= 8) {
                    networkHostCutoff = bitlength + 1;
                } else {
                    networkHostCutoff = bitlength;
                }
                String binary_network = binary.substring(0, networkHostCutoff);
                String binary_host = binary.substring(networkHostCutoff);
                this.msgIPBinaryNetwork.setText(binary_network);
                this.msgIPBinaryHost.setText(binary_host);
                this.msgIPBinaryNetmask.setText(binaryNetmask);
            }
            return true;
        } catch (Exception e) {
            ClearResults();
            return false;
        }
    }

    private void doCalculate() {
        if (updateResults(true)) {
            showResult();
            saveInHistory();
            return;
        }
        Toast.makeText(getContext(), "Enter valid IP address", Toast.LENGTH_SHORT).show();
    }

    private void updateFields() {
        this.msgIPAddress.setText(this.CurrentIP);
        this.bitlength_spinner.setSelection(this.pref.getIPv4());
    }

    public void showResult() {
        this.result.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp).duration(1000).playOn(this.result);
    }

    public void hideResult() {
        Handler handler = new Handler();
        YoYo.with(Techniques.SlideOutDown).duration(1000).playOn(this.result);
        handler.postDelayed(new C03926(), 1000);
    }

    public void saveInHistory() {
        if (this.realm.where(CIDR.class).findAll().size() < this.pref.getHistoryEntries()) {
            CIDR cidr = new CIDR();
            cidr.setIpAddress(this.msgIPAddress.getText().toString());
            cidr.setType(Constant.IPV4);
            cidr.setId(((long) this.realm.where(CIDR.class).findAll().size()) + System.currentTimeMillis());
            cidr.setTime(time());
            cidr.setMaskIp4(this.subnetmask_spinner.getSelectedItemPosition());
            this.realm.beginTransaction();
            this.realm.copyToRealm(cidr);
            this.realm.commitTransaction();
        }
    }
}
