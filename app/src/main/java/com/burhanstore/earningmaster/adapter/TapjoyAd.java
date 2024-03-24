package com.burhanstore.earningmaster.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.burhanstore.earningmaster.util.Ads_ID_Controller;
//import com.tapjoy.TJActionRequest;
//import com.tapjoy.TJConnectListener;
//import com.tapjoy.TJError;
//import com.tapjoy.TJPlacement;
//import com.tapjoy.TJPlacementListener;
//import com.tapjoy.TJSetUserIDListener;
//import com.tapjoy.Tapjoy;
//import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

public class TapjoyAd {
    private static String TJP, user;
    private static ProgressDialog dialog;
    private static Ads_ID_Controller ads_id_controller;

    public static void init(final Activity context, String uid) {
        ads_id_controller = new Ads_ID_Controller(context);
        dialog = new ProgressDialog(context);
        dialog = ProgressDialog.show(context, "", "Please wait...", true, true);
        dialog.show();
        String TJKEY = ads_id_controller.getTapjoy_SDK_KEY();
        TJP = ads_id_controller.getTapjoy_PLACEMENT_OFFERWALL();
        user = uid;
        Hashtable<String, Object> connectFlags = new Hashtable<>();
//        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
//        Tapjoy.connect(context, TJKEY, connectFlags, new TJConnectListener() {
//            @Override
//            public void onConnectSuccess() {
//                Tapjoy.setUserID(user, new TJSetUserIDListener() {
//                    @Override
//                    public void onSetUserIDSuccess() {
//                        new TJPlacement(context, TJP, new TJPlacementListener() {
//                            @Override
//                            public void onRequestSuccess(TJPlacement tjPlacement) {
//                                if (!tjPlacement.isContentAvailable()) {
//                                    dialog.dismiss();
//                                    uiToast(context, "Offer not available");
//                                }
//                            }
//
//                            @Override
//                            public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
//                                dialog.dismiss();
//                                uiToast(context, tjError.message);
//                            }
//
//                            @Override
//                            public void onContentReady(TJPlacement tjPlacement) {
//                                tjPlacement.showContent();
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void onContentShow(TJPlacement tjPlacement) {
//
//                            }
//
//                            @Override
//                            public void onContentDismiss(TJPlacement tjPlacement) {
//
//                            }
//
//                            @Override
//                            public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {
//
//                            }
//
//                            @Override
//                            public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {
//                                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                            }
//                        }).requestContent();
//                    }
//
//                    @Override
//                    public void onSetUserIDFailure(String s) {
//                        dialog.dismiss();
//                        uiToast(context, "User ID didn't set");
//                    }
//                });
//            }
//
//            @Override
//            public void onConnectFailure() {
//                dialog.dismiss();
//            }
//        });
    }

    private static void uiToast(final Activity context, final String toast) {
        context.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
            }
        });
    }

}
