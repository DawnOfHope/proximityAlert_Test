package tw.zora.example.proximityalert_test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class proxAlertActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class MySensorEventListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuilder  sb = new StringBuilder();
            sb.append("sensor:" + event.sensor.getName() + "\n");
            //Android 的 proximity sensor 接近偵測是屬於所謂的 Binary Sensor
            //所以並不會有實際測量距離值的回饋
            //只能測得 0.0 接近，5.0遠離
            //故 Binary Sensor 其精確度一律返回
            sb.append("accuracy:" + getAccuracyName(event.accuracy) + "\n")
            //Android 的 proximity sensor 接近偵測內容只有 values[0] 有意義！
            final float proxValue = event.values[0];
            sb.append("values:" + proxValue + "cm\n");//0.0:靠近;0.5:離開
            sb.append("timestamp:" + event.timestamp + "ns");
            final String msg = sb.toString();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (proxValue < 1){
                        //vib.vibrate(3000);
                        //tv1.setText(msg + "\n" + "靠太近啦" );
                        //imageView1.setImageResource(R.drawable.p2);
                    }else {
                        //vib.cancel();
                        //tv1.setText(msg + "\n" );
                        //imageView1.setImageResource(R.drawable.p1);
                    }
                }
            });
        }

        //對感應器的精度改變做出回應
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }

        //取得 accuracy 精度值的說明
        private String getAccuracyName(int accuracy){
            String name = "";
            switch (accuracy){
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    name = "LOW";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    name = "MEDIUM";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    name = "HIGH";
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    name = "UNRELIABLE";
                    break;
                default:
                    name = "Non";
            }
            return name;

        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        //sensor_manger.unregisterListener(listener);
    }
}
