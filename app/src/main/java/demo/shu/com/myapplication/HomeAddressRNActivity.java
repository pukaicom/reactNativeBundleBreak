package demo.shu.com.myapplication;

import android.os.Bundle;
import android.os.Environment;

import com.facebook.react.ReactPackage;

import java.io.File;

import javax.annotation.Nullable;

/**
 * Created by pukai on 2016-10-27.
 */
public class HomeAddressRNActivity extends BaseReactActivity {

    public static final String JS_MAIN_MODULE_NAME = "mainRNModule";
    public static final String JS_MAIN_BUNDLE_NAME = "mainReact.android";
    public static final String JS_BUNDLE_LOCAL_FILE = "mainReact.android.bundle";
    public static final String JS_BUNDLE_LOCAL_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + JS_BUNDLE_LOCAL_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getJsModuleName() {
        return JS_MAIN_MODULE_NAME;
    }

    @Override
    protected ReactPackage getPackages() {
        return new HomeAddressReactPackage(this);
    }

    @Override
    protected String getMainModuleName() {
        return JS_MAIN_BUNDLE_NAME;
    }

    @Nullable
    @Override
    protected String getJSBundleFile() {
        return JS_BUNDLE_LOCAL_PATH;
    }

    @Nullable
    @Override
    protected String getBundleAssetName() {
        return JS_BUNDLE_LOCAL_FILE;
    }
}