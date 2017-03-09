package demo.shu.com.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pukai on 2016-10-27.
 */
public class HomeAddressReactModle extends ReactContextBaseJavaModule {
    private Activity context;
    private ReactApplicationContext reactApplicationContext;

    public HomeAddressReactModle(final ReactApplicationContext reactContext, Activity context) {
        super(reactContext);
        this.context = context;
        reactApplicationContext = reactContext;
        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        gotoMainPage();
                    }
                }, 3000
        );
    }

    @Override
    public String getName() {
        return "NativeMethod";
    }

    public void gotoMainPage() {
        //发送事件
        WritableMap params = Arguments.createMap();
        params.putInt("name", 666);
        reactApplicationContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("test", params);
    }

    /**
     * 在js文件调用该方法的方式 NativeModules.NativeMethod.js2Native
     *
     * @param content 普通类型的参数
     */
    @ReactMethod
    public void showWarnToast(String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    public void showSuccessToast(String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 可以添加多个方法
     */
    @ReactMethod
    public void getAddressList(final Callback successCallback, final Callback failedCallback) {
        List<HomeAddress> list = new ArrayList<>();
        list.add(new HomeAddress("0", "测试地址0", true));
        list.add(new HomeAddress("1", "测试地址1", false));
        list.add(new HomeAddress("2", "测试地址2", false));
        WritableArray writableArray = new WritableNativeArray();
        for (HomeAddress address : list) {
            if (address != null) {
                writableArray.pushMap(address.getWritableMap());
            }
        }
        successCallback.invoke(writableArray);
        //failedCallback.invoke();

    }


    @ReactMethod
    public void setDefaultAddress(final String addrId, final Callback successCallback, final Callback failedCallback) {
        successCallback.invoke(addrId);
        // failedCallback.invoke();
        WritableMap params = Arguments.createMap();
        params.putInt("name", 777);
        reactApplicationContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("test", params);
    }
}
