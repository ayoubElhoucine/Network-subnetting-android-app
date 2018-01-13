package com.tofaha.vlsmtofaha.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.superClasses.SuperMainActivity;
import com.tofaha.vlsmtofaha.ui.main.MainActivity;

public class Setting extends SuperMainActivity {
    @BindView(R.id.setting_auto_complete_layout)
    View autoComplete;
    @BindView(R.id.auto_complete_checkbox)
    CheckBox autoCompleteCheckbox;
    private Button cancelDialog;
    @BindView(R.id.setting_history_entries)
    TextView historyEntries;
    @BindView(R.id.setting_history_entries_layout)
    View historyEntriesLayout;
    public OnClickListener listener = new C04002();
    private Button okayDialog;
    private RadioGroup radioGroup;
    @BindView(R.id.spinner_ipv4)
    Spinner spinnerIPv4;
    @BindView(R.id.spinner_ipv6)
    Spinner spinnerIPv6;
    public OnItemSelectedListener spinnerListner = new C04035();
    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    class C03991 implements OnClickListener {
        C03991() {
        }

        public void onClick(View view) {
            Setting.this.startActivity(new Intent(Setting.this, MainActivity.class));
        }
    }

    class C04002 implements OnClickListener {
        C04002() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_auto_complete_layout:
                    Setting.this.pref.setAutoComplete(!Setting.this.pref.isAutoComplete());
                    Setting.this.updateInformation();
                    return;
                case R.id.setting_history_entries_layout:
                    Setting.this.showDialogHistoryEntries();
                    return;
                default:
                    return;
            }
        }
    }

    class C04035 implements OnItemSelectedListener {
        C04035() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Setting.this.pref.setIPv4(Setting.this.spinnerIPv4.getSelectedItemPosition());
            Setting.this.pref.setIPv6(Setting.this.spinnerIPv6.getSelectedItemPosition());
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_setting);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        this.toolbar.setNavigationOnClickListener(new C03991());
        ArrayAdapter<CharSequence> adapterV4 = ArrayAdapter.createFromResource(this, R.array.subnets,android.R.layout.simple_spinner_item);
        adapterV4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerIPv4.setAdapter(adapterV4);
        ArrayAdapter<CharSequence> adapterV6 = ArrayAdapter.createFromResource(this, R.array.ipv6subnetmasks, android.R.layout.simple_spinner_item);
        adapterV6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerIPv6.setAdapter(adapterV6);
        this.spinnerIPv4.setOnItemSelectedListener(this.spinnerListner);
        this.spinnerIPv6.setOnItemSelectedListener(this.spinnerListner);
        this.autoComplete.setOnClickListener(this.listener);
        this.historyEntriesLayout.setOnClickListener(this.listener);
        updateInformation();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void showDialogHistoryEntries() {
        final AlertDialog alertDialogLanguage = new Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.setting_history_dialog, null);
        this.cancelDialog = (Button) view.findViewById(R.id.setting_prf_history_cancel);
        this.okayDialog = (Button) view.findViewById(R.id.setting_history_prf_ok);
        this.radioGroup = (RadioGroup) view.findViewById(R.id.myRadioGroupHistory);
        this.cancelDialog.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                alertDialogLanguage.dismiss();
            }
        });
        this.okayDialog.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int selectedId = Setting.this.radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.first_entry) {
                    Setting.this.pref.setHistoryEntries(20);
                } else if (selectedId == R.id.second_entry) {
                    Setting.this.pref.setHistoryEntries(100);
                } else if (selectedId == R.id.third_entry) {
                    Setting.this.pref.setHistoryEntries(1000);
                }
                alertDialogLanguage.dismiss();
                Setting.this.updateInformation();
            }
        });
        alertDialogLanguage.setView(view);
        alertDialogLanguage.show();
    }

    public void updateInformation() {
        this.autoCompleteCheckbox.setChecked(this.pref.isAutoComplete());
        this.historyEntries.setText(String.valueOf(this.pref.getHistoryEntries()));
        this.spinnerIPv4.setSelection(this.pref.getIPv4());
        this.spinnerIPv6.setSelection(this.pref.getIPv6());
    }
}
