package demo.shu.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

/**
 * @author pukai
 * @date 2016-11-23
 */
public class HomeAddress implements Parcelable {

    //    {"addrId":43,"uid":6195148937236480,"cityId":348,"detailAddr":"贡古鲁克山口","lat":41.48715,"lng":78.6585,"selected":true,"createTime":1479722555000,"updateTime":1479722555000},
    public String addrId;
    public String uid;
    public String cityId;

    public HomeAddress(String addrId, String detailAddr, boolean selected) {
        this.addrId = addrId;
        this.detailAddr = detailAddr;
        this.selected = selected;
    }

    public String detailAddr;
    public double lat;
    public double lng;
    public boolean selected;
    public String createTime;
    public String updateTime;

    public WritableMap getWritableMap(){
        WritableMap writableMap = new WritableNativeMap();
        writableMap.putString("addrId",addrId);
        writableMap.putString("uid",uid);
        writableMap.putString("cityId",cityId);
        writableMap.putString("detailAddr",detailAddr);
        writableMap.putDouble("lat",lat);
        writableMap.putDouble("lng",lng);
        writableMap.putBoolean("selected",selected);
        writableMap.putString("createTime",createTime);
        writableMap.putString("updateTime",updateTime);
        return writableMap;
    }
    @Override
    public int describeContents() { return 0; }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addrId);
        dest.writeString(uid);
        dest.writeString(cityId);
        dest.writeString(detailAddr);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }


    protected HomeAddress(Parcel in) {
        addrId = in.readString();
        uid = in.readString();
        cityId = in.readString();
        detailAddr = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        selected = in.readByte() == 1;

    }



    public static final Creator<HomeAddress> CREATOR = new Creator<HomeAddress>() {
        @Override
        public HomeAddress createFromParcel(Parcel source) {return new HomeAddress(source);}

        @Override
        public HomeAddress[] newArray(int size) {return new HomeAddress[size];}
    };


}


