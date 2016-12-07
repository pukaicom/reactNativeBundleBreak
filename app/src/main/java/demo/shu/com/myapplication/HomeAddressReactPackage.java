package demo.shu.com.myapplication;

import android.app.Activity;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Provider;

/**
 * Created by pukai on 2016-10-27.
 */
public class HomeAddressReactPackage extends LazyReactPackage {
    private Activity context;

    public HomeAddressReactPackage(Activity homeAddressRNActivity) {
        this.context = homeAddressRNActivity;
    }

    @Override
    public List<ModuleSpec> getNativeModules(final ReactApplicationContext reactContext) {
        return Arrays.asList(new ModuleSpec(HomeAddressReactModle.class, new Provider<NativeModule>() {
            @Override
            public NativeModule get() {
                return new HomeAddressReactModle(reactContext, context);
            }
        }));
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return new ArrayList<>();
    }
}
