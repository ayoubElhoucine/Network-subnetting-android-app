package com.tofaha.vlsmtofaha.ui.cidrFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.cidrUtils.InetAddresses;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPv6Fragment extends MainFragment implements CidrView {
    private int CurrentBitsIPv6 = 64;
    private String CurrentIPv6 = "";
    public final int DEFAULT_BITS = 64;
    Animation anim = null;
    @BindView(R.id.ipv6calculate)
    Button calculate;
    @BindView(R.id.ipv6subnetmasks)
    Spinner ipv6subnetmasks_spinner;
    private OnClickListener mCalculateListener = new C03942();
    private OnClickListener mResetListener = new C03953();
    private OnItemSelectedListener mSubnetMaskSelectedListener = new C03931();
    @BindView(R.id.v6address_range)
    TextView msgAddressRange;
    @BindView(R.id.ipv6address)
    AutoCompleteTextView msgIPAddress;
    @BindView(R.id.v6info)
    TextView msgInfo;
    @BindView(R.id.v6maximum_addresses)
    TextView msgMaximumAddresses;
    MulticastAddress[] multicastAddresses = new MulticastAddress[]{new MulticastAddress("ff02::1", R.string.allnodes), new MulticastAddress("ff02::2", R.string.allrouters), new MulticastAddress("ff02::9", R.string.allriprouters), new MulticastAddress("ff05::101", R.string.allntpservers), new MulticastAddress("ff05::1:3", R.string.alldhcpservers)};
    @BindView(R.id.ipv6reset)
    Button reset;
    @BindView(R.id.ipv6_result)
    View result;

    class C03931 implements OnItemSelectedListener {
        C03931() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
            IPv6Fragment.this.updateResults(true);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class C03942 implements OnClickListener {
        C03942() {
        }

        public void onClick(View v) {
            IPv6Fragment.this.doCalculate();
        }
    }

    class C03953 implements OnClickListener {
        C03953() {
        }

        public void onClick(View v) {
            IPv6Fragment.this.CurrentIPv6 = "";
            IPv6Fragment.this.CurrentBitsIPv6 = 64;
            IPv6Fragment.this.updateFields();
            IPv6Fragment.this.ClearResults();
            IPv6Fragment.this.hideResult();
        }
    }

    class C03964 implements Runnable {
        C03964() {
        }

        public void run() {
            IPv6Fragment.this.result.setVisibility(View.GONE);
        }
    }

    class MulticastAddress {
        String address;
        int description;

        public MulticastAddress(String address, int description) {
            this.address = address;
            this.description = description;
        }
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (MyData.cidr == null || !MyData.cidr.getType().equals(Constant.IPV6)) {
            this.ipv6subnetmasks_spinner.setSelection(this.pref.getIPv6());
        } else {
            this.ipv6subnetmasks_spinner.setSelection(MyData.cidr.getMaskIp6());
            this.msgIPAddress.setText(MyData.cidr.getIpAddress());
        }
        if (this.pref.isAutoComplete()) {
            this.msgIPAddress.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, cidrIPlist()));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ipv6, container, false);
        ButterKnife.bind((Object) this, view);
        this.anim = AnimationUtils.loadAnimation(getContext(), R.anim.highlight);
        ArrayAdapter<CharSequence> subnetmask_adapter = ArrayAdapter.createFromResource(getContext(), R.array.ipv6subnetmasks, android.R.layout.simple_spinner_item);
        subnetmask_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.ipv6subnetmasks_spinner.setAdapter(subnetmask_adapter);
        this.ipv6subnetmasks_spinner.setOnItemSelectedListener(this.mSubnetMaskSelectedListener);
        this.calculate.setOnClickListener(this.mCalculateListener);
        this.reset.setOnClickListener(this.mResetListener);
        if (!this.CurrentIPv6.equals("")) {
            this.msgIPAddress.setText(this.CurrentIPv6);
        }
        return view;
    }

    private void ClearResults() {
        this.msgAddressRange.setText("");
        this.msgMaximumAddresses.setText("");
        this.msgInfo.setText("");
    }

    private static InetAddress bigIntToIPv6Address(BigInteger addr) throws UnknownHostException {
        byte[] a = new byte[16];
        byte[] b = addr.toByteArray();
        if (b.length > 16 && (b.length != 17 || b[0] != (byte) 0)) {
            throw new UnknownHostException("invalid IPv6 address (too big)");
        } else if (b.length == 16) {
            return InetAddress.getByAddress(b);
        } else {
            if (b.length == 17) {
                System.arraycopy(b, 1, a, 0, 16);
            } else {
                System.arraycopy(b, 0, a, 16 - b.length, b.length);
            }
            return InetAddress.getByAddress(a);
        }
    }

    private boolean updateResults(boolean updateView) {
        CharSequence ipAddressText = this.msgIPAddress.getText();
        if (ipAddressText == null) {
            return false;
        }
        String ip = ipAddressText.toString();
        try {
            BigInteger maximumAddresses;
            InetAddress hostAddress = InetAddress.getByAddress(InetAddresses.ipStringToBytes(ip));
            BigInteger ip128bit = new BigInteger(hostAddress.getAddress());
            BigInteger ip128bitmask = BigInteger.ONE.shiftLeft(128 - (this.ipv6subnetmasks_spinner.getSelectedItemPosition() + 1)).subtract(BigInteger.ONE);
            BigInteger firstip = ip128bitmask.xor(new BigInteger("ffffffffffffffffffffffffffffffff", 16)).and(ip128bit);
            byte[] firstIPbytes = firstip.toByteArray();
            InetAddress firstIPv6 = bigIntToIPv6Address(firstip);
            InetAddress lastIPv6 = bigIntToIPv6Address(firstip.or(ip128bitmask));
            if (ip128bitmask.equals(BigInteger.ZERO)) {
                maximumAddresses = BigInteger.ZERO;
            } else {
                maximumAddresses = ip128bitmask.subtract(BigInteger.ONE);
            }
            String addressInfo = "";
            if (hostAddress.isAnyLocalAddress()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.any_local));
            }
            if (hostAddress.isLinkLocalAddress()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.link_local));
            }
            if (hostAddress.isLoopbackAddress()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.loopback));
            }
            if (hostAddress.isMCGlobal()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mcglobal));
            }
            if (hostAddress.isMCLinkLocal()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mclink_local));
            }
            if (hostAddress.isMCNodeLocal()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mcnode_local));
            }
            if (hostAddress.isMCOrgLocal()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mcorg_local));
            }
            if (hostAddress.isMCSiteLocal()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mcsite_local));
            }
            if (hostAddress.isMulticastAddress()) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.multicast));
                for (MulticastAddress multicastAddress1 : this.multicastAddresses) {
                    if (hostAddress.getHostAddress().equals(InetAddress.getByAddress(InetAddresses.ipStringToBytes(multicastAddress1.address)).getHostAddress())) {
                        addressInfo = addStringWithSpace(addressInfo, getString(multicastAddress1.description));
                        break;
                    }
                }
            }
            if (InetAddresses.isMappedIPv4Address(ip)) {
                addressInfo = addStringWithSpace(addressInfo, getString(R.string.mappedipv4));
            }
            this.msgAddressRange.setText(firstIPv6.getHostAddress() + " - " + lastIPv6.getHostAddress());
            this.msgMaximumAddresses.setText(maximumAddresses.toString());
            this.msgInfo.setText(addressInfo);
            if (updateView) {
                this.msgAddressRange.startAnimation(this.anim);
            }
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ClearResults();
            return false;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            ClearResults();
            return false;
        }
    }

    private String addStringWithSpace(String str1, String str2) {
        String out = str1;
        if (str1.length() != 0) {
            out = out + ", ";
        }
        return out + str2;
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
        this.msgIPAddress.setText(this.CurrentIPv6);
        this.ipv6subnetmasks_spinner.setSelection(this.pref.getIPv6());
    }

    public void showResult() {
        this.result.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp).duration(1000).playOn(this.result);
    }

    public void hideResult() {
        Handler handler = new Handler();
        YoYo.with(Techniques.SlideOutDown).duration(1000).playOn(this.result);
        handler.postDelayed(new C03964(), 1000);
    }

    public void saveInHistory() {
        if (this.realm.where(CIDR.class).findAll().size() < this.pref.getHistoryEntries()) {
            CIDR cidr = new CIDR();
            cidr.setIpAddress(this.msgIPAddress.getText().toString());
            cidr.setType(Constant.IPV6);
            cidr.setId(((long) this.realm.where(CIDR.class).findAll().size()) + System.currentTimeMillis());
            cidr.setTime(time());
            cidr.setMaskIp6(this.ipv6subnetmasks_spinner.getSelectedItemPosition());
            this.realm.beginTransaction();
            this.realm.copyToRealm(cidr);
            this.realm.commitTransaction();
        }
    }
}
