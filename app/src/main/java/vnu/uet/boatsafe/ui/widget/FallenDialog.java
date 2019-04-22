package vnu.uet.boatsafe.ui.widget;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;

import vnu.uet.boatsafe.data.prefs.Preference;

public class FallenDialog extends Activity implements DialogFallentDetected.Callback{

    private DialogFallentDetected dialogFallentDetected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogFallentDetected = new DialogFallentDetected(FallenDialog.this);
        dialogFallentDetected.setCallback(this);
        dialogFallentDetected.show();
        dialogFallentDetected.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onButtonPositiveClick(DialogFallentDetected td, CountDownTimer count) {
        count.cancel();
        td.dismiss();

        //Tạm thời không dùng đến chức năng gửi tin nhắn toạ độ về cho người thân.
        /*String messageToSend = "Đây là tọa độ của anh: " + String.valueOf(Preference.buildInstance(getApplicationContext()).getLastLat()) + ", " + Preference.buildInstance(getApplicationContext()).getLastLong();
        String number = "0916662195";

        SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);*/

        //Sử dụng chức năng tự động gọi
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:0916662195"));
        startActivity(intent);

        //finish();
    }



    @Override
    public void onBackClick(DialogFallentDetected td) {
        td.dismiss();
        finish();
    }

    @Override
    public void onBackPressed() {
        if(dialogFallentDetected != null){
            super.onBackPressed();
        }
    }
}
