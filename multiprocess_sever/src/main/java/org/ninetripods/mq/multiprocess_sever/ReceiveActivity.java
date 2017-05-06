package org.ninetripods.mq.multiprocess_sever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.ninetripods.mq.multiprocess_sever_i.MultiProcess;

public class ReceiveActivity extends AppCompatActivity {
    private TextView tv_text;
    private static final String INTENT_ACTION = "android.mq.intent";
    private static final String STRING_KEY = "string_key";
    private static final String OBJECT_KEY = "object_key";
    private SpannableStringBuilder builder = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("IPC-Intent");
        setContentView(R.layout.activity_recieve);
        tv_text = (TextView) findViewById(R.id.tv_text);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            switch (action) {
                case INTENT_ACTION:
                    String str = intent.getExtras().getString(STRING_KEY);
                    MultiProcess multiProcess = intent.getExtras().getParcelable(OBJECT_KEY);
                    if (!TextUtils.isEmpty(str)) {
                        showMessage(str, R.color.black);
                    }
                    if (multiProcess != null) {
                        showMessage("\n" + multiProcess.getInfo(), R.color.colorAccent);
                    }
                    break;
            }
        }
    }

    /**
     * 显示文字
     *
     * @param info 提示信息
     */
    private void showMessage(String info, int color) {
        int startPos = builder.length();
        builder.append(info);
        tv_text.setText(DisplayUtil.changeTextColor(this, builder, color, startPos));
    }
}
