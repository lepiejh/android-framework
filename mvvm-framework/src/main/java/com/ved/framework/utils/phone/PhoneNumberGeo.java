package com.ved.framework.utils.phone;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class PhoneNumberGeo {

  private static final String[] PHONE_NUMBER_TYPE = {null, "移动", "联通", "电信", "电信虚拟运营商", "联通虚拟运营商", "移动虚拟运营商"};
  private static final int INDEX_SEGMENT_LENGTH = 9;
  private static final int DATA_FILE_LENGTH_HINT = 3747505;

  private static byte[] dataByteArray;
  private static final int indexAreaOffset = 9949;
  private static final int phoneRecordCount = 415284;

  private ByteBuffer byteBuffer;

  private static synchronized void initData(Context context) {
    if (dataByteArray == null) {
      ByteArrayOutputStream byteData = new ByteArrayOutputStream(DATA_FILE_LENGTH_HINT);
      byte[] buffer = new byte[1024];

      int readBytesLength;
      InputStream inputStream  = null;
      try {
        inputStream = context.getResources().getAssets().open("phone.dat", 3);
        while ((readBytesLength = inputStream.read(buffer)) != -1) {
          byteData.write(buffer, 0, readBytesLength);
        }
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }

      dataByteArray = byteData.toByteArray();
    }
  }

  private final static class Inner{
    private static PhoneNumberGeo instance;

    private static synchronized PhoneNumberGeo getInstance(Context context){
      if (instance == null){
        instance = new PhoneNumberGeo(context);
      }
      return instance;
    }
  }

  public static PhoneNumberGeo getInstance(Context context){
    return Inner.getInstance(context.getApplicationContext());
  }

  private PhoneNumberGeo(Context context) {
    initData(context);

    byteBuffer = ByteBuffer.wrap(dataByteArray);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
  }

  /**
   * 获取号码归属地信息
   * @param phoneNumber     号码
   * @param needPhoneType   是否需要运营商信息
   * @return                号码归属地
   */
  public String lookup(String phoneNumber,boolean needPhoneType) {
    if (phoneNumber == null || phoneNumber.length() > 11 || phoneNumber.length() < 7) {
      return null;
    }
    int phoneNumberPrefix;
    try {
      phoneNumberPrefix = Integer.parseInt(phoneNumber.substring(0, 7));
    } catch (Exception e) {
      return null;
    }
    int left = 0;
    int right = phoneRecordCount;
    while (left <= right) {
      int middle = (left + right) >> 1;
      int currentOffset = indexAreaOffset + middle * INDEX_SEGMENT_LENGTH;
      if (currentOffset >= dataByteArray.length) {
        return null;
      }

      byteBuffer.position(currentOffset);
      int currentPrefix = byteBuffer.getInt();
      if (currentPrefix > phoneNumberPrefix) {
        right = middle - 1;
      } else if (currentPrefix < phoneNumberPrefix) {
        left = middle + 1;
      } else {
        int infoBeginOffset = byteBuffer.getInt();
        int phoneType = byteBuffer.get();

        int infoLength = -1;
        for (int i = infoBeginOffset; i < indexAreaOffset; ++i) {
          if (dataByteArray[i] == 0) {
            infoLength = i - infoBeginOffset;
            break;
          }
        }

        String infoString = new String(dataByteArray, infoBeginOffset, infoLength, Charset.forName("UTF-8"));
        String[] infoSegments = infoString.split("\\|");   //ZipCode(infoSegments[2])   AreaCode(infoSegments[3]

        StringBuilder sb = new StringBuilder();
        //省份
        String province = infoSegments[0];
        sb.append(province);
        sb.append(" ");
        //城市
        String city = infoSegments[1];
        sb.append(city);
        if (TextUtils.equals(province,city)){
          sb.append("市");
        }
        sb.append(" ");
        //号码运营商信息
        String type = PHONE_NUMBER_TYPE[phoneType];
        if (needPhoneType){
          sb.append(type);
        }
        return sb.toString();
      }
    }
    return null;
  }
}


