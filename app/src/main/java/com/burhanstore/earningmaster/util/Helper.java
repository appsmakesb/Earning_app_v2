package com.burhanstore.earningmaster.util;

import android.content.Context;
import android.provider.Settings.Secure;

public class Helper
{
	private static String keyUserAccount = "USER_ACCOUNT";
	private static String keyHomeAdUnit = "HOME_AD_UNIT";
	private static String keySuccessImpressionCount = "SUCCESS_IMPRESSION";
	
	private static final char[] banglaDigits = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
	private static final char[] englishDigits = {'0','1','2','3','4','5','6','7','8','9'};
	
	public static String getDeviceId(Context ctx) {
		return Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
	}
	
	public static void setUserAccount(Context ctx, String user) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		tinydb.putString(keyUserAccount, user);
	}

	public static String getUserAccount(Context ctx) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		return tinydb.getString(keyUserAccount);
	}

	public static void unsetUserAccount(Context ctx, String user) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		tinydb.remove(keyUserAccount);
	}

	public static void setHomeAdUnit(Context ctx, String unit) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		tinydb.putString(keyHomeAdUnit, unit);
	}

	public static String getHomeAdUnit(Context ctx) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		return tinydb.getString(keyHomeAdUnit);
	}

	public static void setSuccessImpressionCount(Context ctx, int count) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		tinydb.putInt(keySuccessImpressionCount, count);
	}

	public static int getSuccessImpressionCount(Context ctx) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		return tinydb.getInt(keySuccessImpressionCount);
	}

	public static void resetSuccessImpressionCount(Context ctx) {
		com.burhanstore.earningmaster.util.TinyDB tinydb = new com.burhanstore.earningmaster.util.TinyDB(ctx);
		tinydb.putInt(keySuccessImpressionCount, 0);
	}
	
	public  static String  getBangla(String number){
		if(number==null) return new String("");
		StringBuilder builder = new StringBuilder();
		try {
			for(int i =0;i<number.length();i++){
				if(Character.isDigit(number.charAt(i))){
					if(((int)(number.charAt(i))-48)<=9){
						builder.append(banglaDigits[(int)(number.charAt(i))-48]);
					}else{
						builder.append(number.charAt(i));
					}
				}else{
					builder.append(number.charAt(i));
				}
			}
		} catch(Exception e){
			return new String("");
		}
		return builder.toString();
	}
}
