package com.leon.filepicker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.leon.filepicker.R;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "FilePickerLeon";
    private RadioGroup mRgIconType;
    private RadioGroup mRgBackArrawType;
    private int mIconType;
    private int mBackArrawType;
    private USBStatesReceiver usbStatesReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        usbStatesReceiver = new USBStatesReceiver();
        usbStatesReceiver.registerReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        usbStatesReceiver.unregisterReceiver(this);
    }

    private void initView() {
        mRgIconType = (RadioGroup) findViewById(R.id.rg_iconstyle);
        mRgBackArrawType = (RadioGroup) findViewById(R.id.rg_backarrawstyle);
    }

    private void initListener() {
        mRgIconType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_yellow:
                        mIconType = Constant.ICON_STYLE_YELLOW;
                        break;
                    case R.id.radio_green:
                        mIconType = Constant.ICON_STYLE_GREEN;
                        break;
                    case R.id.radio_blue:
                        mIconType = Constant.ICON_STYLE_BLUE;
                        break;
                    default:
                }
            }
        });
        mRgBackArrawType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.arrawback_styleone:
                        mBackArrawType = Constant.BACKICON_STYLEONE;
                        break;
                    case R.id.arrawback_styletwo:
                        mBackArrawType = Constant.BACKICON_STYLETWO;
                        break;
                    case R.id.arrawback_stylethree:
                        mBackArrawType = Constant.BACKICON_STYLETHREE;
                        break;
                    default:
                }
            }
        });
    }

    public void openFromActivity(View view) {

        new LFilePicker()
                .withActivity(this)
                .withRequestCode(Consant.REQUESTCODE_FROM_ACTIVITY)
                .withTitle("文件选择")
                .withIconStyle(mIconType)
                .withBackIcon(mBackArrawType)
                .withMutilyMode(false)
                .withMaxNum(2)
                .withStartPath(getSharedPreferences(Consant.TAG_KV_CACHE, Context.MODE_PRIVATE).getString(Consant.USB_PATH, "/"))//指定初始显示路径
                .withNotFoundBooks("至少选择一个文件")
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(500 * 1024)//指定文件大小为500K
                .withChooseMode(false)//文件夹选择模式
                .withFileFilter(new String[]{"txt", "png", "docx"})
                .start();
    }

    public void openFragmentActivity(View view) {
        startActivity(new Intent(this, FragmengActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Consant.REQUESTCODE_FROM_ACTIVITY) {
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                //for (String s : list) {
                //    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                //}
//                Toast.makeText(getApplicationContext(), "选中了" + list.size() + "个文件", Toast.LENGTH_SHORT).show();
                String path = data.getStringExtra("path");
                Toast.makeText(getApplicationContext(), "选中的路径为" + path, Toast.LENGTH_SHORT).show();
                Log.i("LeonFilePicker", path);
            }
        }
    }
}
