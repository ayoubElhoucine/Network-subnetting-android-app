package com.tofaha.vlsmtofaha.ui.binaryCalculatorFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.converter.Converter;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;

public class BinaryCalculatorFragment extends MainFragment {
    @BindView(R.id.convertbin)
    Button convertBinButton;
    @BindView(R.id.convertdec)
    Button convertDecButton;
    @BindView(R.id.converthex)
    Button convertHexButton;
    private String currentIP;
    @BindView(R.id.converter_ipaddress)
    AutoCompleteTextView ipAddress;
    @BindView(R.id.ipbinary)
    EditText ipBinary;
    @BindView(R.id.iphex)
    EditText ipHex;
    Animation pulseAnim = null;

    class C03841 implements OnClickListener {
        C03841() {
        }

        public void onClick(View arg0) {
            if (BinaryCalculatorFragment.this.ipAddress.getText().toString().trim().length() == 0) {
                Toast.makeText(BinaryCalculatorFragment.this.getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
                return;
            }
            BinaryCalculatorFragment.this.convertDecimal();
            BinaryCalculatorFragment.this.ipBinary.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
            BinaryCalculatorFragment.this.ipHex.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
        }
    }

    class C03852 implements OnClickListener {
        C03852() {
        }

        public void onClick(View arg0) {
            if (BinaryCalculatorFragment.this.ipBinary.getText().toString().trim().length() == 0) {
                Toast.makeText(BinaryCalculatorFragment.this.getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
                return;
            }
            BinaryCalculatorFragment.this.convertBinary();
            BinaryCalculatorFragment.this.ipAddress.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
            BinaryCalculatorFragment.this.ipHex.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
        }
    }

    class C03863 implements OnClickListener {
        C03863() {
        }

        public void onClick(View arg0) {
            if (BinaryCalculatorFragment.this.ipHex.getText().toString().trim().length() == 0) {
                Toast.makeText(BinaryCalculatorFragment.this.getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
                return;
            }
            BinaryCalculatorFragment.this.convertHex();
            BinaryCalculatorFragment.this.ipAddress.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
            BinaryCalculatorFragment.this.ipBinary.startAnimation(BinaryCalculatorFragment.this.pulseAnim);
        }
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.pref.isAutoComplete()) {
            this.ipAddress.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, vlsmIPlist()));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_binary_calculator, container, false);
        ButterKnife.bind((Object) this, view);
        this.pulseAnim = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        this.convertDecButton.setOnClickListener(new C03841());
        this.convertBinButton.setOnClickListener(new C03852());
        this.convertHexButton.setOnClickListener(new C03863());
        return view;
    }

    private void convertDecimal() {
        String decimalIP = this.ipAddress.getText().toString().trim();
        try {
            int ip32bit = Converter.stringIPtoInt(decimalIP);
            this.currentIP = decimalIP;
            this.ipBinary.setText(convertIPIntDec2StringBinary(ip32bit));
            this.ipHex.setText(convertIPIntDec2StringHex(ip32bit));
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        }
    }

    private void convertBinary() {
        String currentBinary = this.ipBinary.getText().toString().trim();
        if (currentBinary.length() < 32) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        }
        try {
            String octet1b = currentBinary.substring(0, 8);
            String octet2b = currentBinary.substring(9, 17);
            String octet3b = currentBinary.substring(18, 26);
            long octet2i = (long) Integer.parseInt(octet2b, 2);
            long octet3i = (long) Integer.parseInt(octet3b, 2);
            long octet4i = (long) Integer.parseInt(currentBinary.substring(27, 35), 2);
            this.currentIP = ((long) Integer.parseInt(octet1b, 2)) + "." + octet2i + "." + octet3i + "." + octet4i;
            this.ipAddress.setText(this.currentIP);
            this.ipHex.setText(convertIPIntDec2StringHex(Converter.stringIPtoInt(this.currentIP)));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        } catch (StringIndexOutOfBoundsException e2) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        } catch (Exception e3) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        }
    }

    private void convertHex() {
        String hexIP = this.ipHex.getText().toString().trim();
        if (hexIP.length() < 11) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        }
        try {
            String octet1b = hexIP.substring(0, 2);
            String octet2b = hexIP.substring(3, 5);
            String octet3b = hexIP.substring(6, 8);
            long octet2i = (long) Integer.parseInt(octet2b, 16);
            long octet3i = (long) Integer.parseInt(octet3b, 16);
            long octet4i = (long) Integer.parseInt(hexIP.substring(9, 11), 16);
            this.currentIP = ((long) Integer.parseInt(octet1b, 16)) + "." + octet2i + "." + octet3i + "." + octet4i;
            this.ipAddress.setText(this.currentIP);
            this.ipBinary.setText(convertIPIntDec2StringBinary(Converter.stringIPtoInt(this.currentIP)));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        } catch (StringIndexOutOfBoundsException e2) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        } catch (Exception e3) {
            Toast.makeText(getContext(), R.string.err_bad_ip, Toast.LENGTH_SHORT).show();
        }
    }

    public static String convertIPIntDec2StringBinary(int intIP) {
        String stringIP = Integer.toBinaryString(intIP);
        int length = stringIP.length();
        if (length < 32) {
            int prependZeros = 32 - length;
            for (int i = 0; i < prependZeros; i++) {
                stringIP = "0" + stringIP;
            }
        }
        String octet1 = stringIP.substring(0, 8);
        String octet2 = stringIP.substring(8, 16);
        String octet3 = stringIP.substring(16, 24);
        return octet1 + "." + octet2 + "." + octet3 + "." + stringIP.substring(24, 32);
    }

    public static String convertIPIntDec2StringHex(int intIP) {
        String stringIP = Integer.toHexString(intIP);
        int length = stringIP.length();
        if (length < 8) {
            int prependZeros = 8 - length;
            for (int i = 0; i < prependZeros; i++) {
                stringIP = "0" + stringIP;
            }
        }
        String octet1 = stringIP.substring(0, 2);
        String octet2 = stringIP.substring(2, 4);
        String octet3 = stringIP.substring(4, 6);
        return octet1 + "." + octet2 + "." + octet3 + "." + stringIP.substring(6, 8);
    }
}
