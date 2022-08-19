/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.forest.bss.sdk.permission;

import android.Manifest;
import android.hardware.Camera;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.forest.bss.sdk.log.LogUtils;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * ================================================
 * 权限请求工具类
 * <p>
 * ================================================
 */
public class PermissionUtil {
    public static final String TAG = "Permission";

    public static boolean STATUS_SHOW_PERMISSION=false;
    private PermissionUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailure(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }

    public interface RequestPermissionEach {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         */
        void onRequestPermissionFailure();
    }


    private static void requestPermission(final RequestPermission requestPermission, final RxPermissions rxPermissions, final String... permissions) {
        if (permissions == null || permissions.length == 0) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            try {
                if (!rxPermissions.isGranted(permission)) {
                    needRequest.add(permission);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过,则开始申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new Observer<List<Permission>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            LogUtils.logger("Request permissions onSubscribe Disposable: " + d);
                        }

                        @Override
                        public void onNext(@NonNull List<Permission> permissions) {
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        LogUtils.logger("Request permissions failure");
                                        requestPermission.onRequestPermissionFailure(Arrays.asList(p.name));
                                        return;
                                    } else {
                                        LogUtils.logger("Request permissions failure with ask never again");
                                        requestPermission.onRequestPermissionFailureWithAskNeverAgain(Arrays.asList(p.name));
                                        return;
                                    }
                                }
                            }
                            LogUtils.logger("Request permissions success");
                            requestPermission.onRequestPermissionSuccess();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            LogUtils.logger("Request permissions onError");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }

    /**
     * 请求摄像头权限（单个询问）
     */
    public static void launchCamera(RequestPermissionEach requestPermission, FragmentActivity activity, boolean... onlyCamera) {
        boolean onlycamera = onlyCamera != null && onlyCamera.length > 0 && onlyCamera[0] ? true : false;

        RxPermissions rxPermissions = new RxPermissions(activity);
        if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !onlycamera) {
            rxPermissions
                    .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (!permission.granted) {
                            // 用户已经同意该权限
                            if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）那么下次再次启动时，还会提示请求权限的对话框
//                                new PermissionDialogUtil(activity).showDialog(permission.name);
                                requestPermission.onRequestPermissionFailure();
                                return;
                            } else {
//                                new PermissionDialogUtil(activity).showDialog(permission.name);
                                requestPermission.onRequestPermissionFailure();
                                return;
                            }
                        }
                        rxPermissions
                                .requestEach(Manifest.permission.CAMERA)
                                .subscribe(camerPpermission -> {
                                    if (!camerPpermission.granted) {
                                        // 用户已经同意该权限
                                        if (camerPpermission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）那么下次再次启动时，还会提示请求权限的对话框
//                                            new PermissionDialogUtil(activity).showDialog(camerPpermission.name);
                                            requestPermission.onRequestPermissionFailure();
                                            return;
                                        } else {
//                                            new PermissionDialogUtil(activity).showDialog(camerPpermission.name);
                                            requestPermission.onRequestPermissionFailure();
                                            return;
                                        }
                                    }
                                    requestPermission.onRequestPermissionSuccess();
                                });
                    });
        } else {
            if (!rxPermissions.isGranted(Manifest.permission.CAMERA)) {
                rxPermissions
                        .requestEach(Manifest.permission.CAMERA)
                        .subscribe(camerPpermission -> {
                            if (!camerPpermission.granted) {
                                // 用户已经同意该权限
                                if (camerPpermission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）那么下次再次启动时，还会提示请求权限的对话框
//                                    new PermissionDialogUtil(activity).showDialog(camerPpermission.name);
                                    requestPermission.onRequestPermissionFailure();
                                    return;
                                } else {
//                                    new PermissionDialogUtil(activity).showDialog(camerPpermission.name);
                                    requestPermission.onRequestPermissionFailure();
                                    return;
                                }
                            }
                            requestPermission.onRequestPermissionSuccess();
                        });
            } else {
                requestPermission.onRequestPermissionSuccess();
            }
        }
    }

    public static boolean checkCameraEnabledForPreM() {
        boolean cameraEnabled = true;
        Camera camera = null;
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            cameraEnabled = false;
        }

        if (camera != null) {
            try {
                camera.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cameraEnabled;

    }

    /**
     * 请求外部存储的权限
     */
    private static void externalStorage(final RequestPermission requestPermission, final RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static Observable<Boolean> requireExternalStorage(FragmentActivity activity) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        emitter.onNext(true);
                        emitter.onComplete();
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {
                        emitter.onNext(false);
                        emitter.onComplete();
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                        emitter.onNext(false);
                        emitter.onComplete();
                    }
                }, new RxPermissions(activity));
            }
        });

    }

    public static void requireReadPhonePermission(FragmentActivity fragmentActivity, Consumer<Boolean> resultConsumer) {
        requireMultiplePermissions(fragmentActivity, Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean accepted) throws Throwable {
                resultConsumer.accept(accepted);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                throwable.printStackTrace();
                resultConsumer.accept(false);
            }
        });
    }


    public static Observable<Boolean> requireMultiplePermissions(FragmentActivity activity, String... permissions) {
        return Observable.create(emitter -> PermissionUtil.requestPermission(new RequestPermission() {
                                             @Override
                                             public void onRequestPermissionSuccess() {
                                                 emitter.onNext(true);
                                                 emitter.onComplete();
                                             }

                                             @Override
                                             public void onRequestPermissionFailure(List<String> permissions1) {
                                                 emitter.onNext(false);
                                                 emitter.onComplete();
                                             }

                                             @Override
                                             public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions1) {
                                                 emitter.onNext(false);
                                                 emitter.onComplete();
                                             }
                                         }, new RxPermissions(activity), permissions));
    }
    public static Observable<Boolean> requireMultiplePermissions(RxPermissions rxPermissions, String... permissions) {
        return Observable.create(emitter -> PermissionUtil.requestPermission(new RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                emitter.onNext(true);
                emitter.onComplete();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions1) {
                emitter.onNext(false);
                emitter.onComplete();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions1) {
                emitter.onNext(false);
                emitter.onComplete();
            }
        }, rxPermissions, permissions));
    }

    public static Observable<Boolean> requireMultiplePermissions(Fragment fragment, String... permissions) {
        return Observable.create(emitter -> PermissionUtil.requestPermission(new RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                emitter.onNext(true);
                emitter.onComplete();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions1) {
                emitter.onNext(false);
                emitter.onComplete();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions1) {
                emitter.onNext(false);
                emitter.onComplete();
            }
        }, new RxPermissions(fragment), permissions));
    }
}

