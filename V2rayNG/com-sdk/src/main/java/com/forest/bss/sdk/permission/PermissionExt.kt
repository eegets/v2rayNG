package com.forest.bss.sdk.permission

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.forest.bss.sdk.ext.wasNotDestroyed
import com.forest.bss.sdk.log.logger
import com.tbruyelle.rxpermissions3.RxPermissions

/**
 * Created by wangkai on 2021/03/03 15:59

 * Desc 申请相机权限
 */

@SuppressLint("CheckResult")
fun captureCameraPermission(activity: FragmentActivity, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (activity.wasNotDestroyed()) {
        PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.CAMERA)
                .subscribe {
                    logger {
                        "Premission  captureCameraPremission $it"
                    }
                    permissionCallBack?.invoke(it)
                }
    }
}

@SuppressLint("CheckResult")
fun captureCameraPermission(fragment: Fragment, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (fragment.activity != null) {
        PermissionUtil.requireMultiplePermissions(fragment, Manifest.permission.CAMERA)
                .subscribe {
                    logger {
                        "Premission  captureCameraPremission $it"
                    }
                    permissionCallBack?.invoke(it)
                }
    }
}

/**
 * 检查文件写入权限
 */
fun checkFileWritePermission(activity: FragmentActivity, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (activity.wasNotDestroyed()) {
        PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    logger {
                        "Premission  checkFileWritePermission $it"
                    }
                    permissionCallBack?.invoke(it)
                }
    }
}

/**
 * 检查定位权限
 */
fun checkLocationPermission(activity: FragmentActivity, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (activity.wasNotDestroyed()) {
        // 判断编译版本,如果大于等于29,就用permissionsQ
//        if (Build.VERSION.SDK_INT >= 29) {
//            PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                    .subscribe {
//                        logger {
//                            "Premission  checkLocationPermission $it"
//                        }
//                        permissionCallBack?.invoke(it)
//                    }
//
//        } else {
            PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {
                        logger {
                            "Premission  checkLocationPermission $it"
                        }
                        permissionCallBack?.invoke(it)
                    }
//        }
    }
}


/**
 * 检查电话,文件存储权限
 */
fun checkReadPhoneAndFilePermission(activity: FragmentActivity, permissionCallBack: ((Boolean) -> Unit)? = null) {
    PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {
                logger {
                    "Premission  checkLocationPermission $it"
                }
                permissionCallBack?.invoke(it)
            }
}
/**
 * 检查定位权限
 */
fun checkLocationPermission(rxPermissions: RxPermissions, permissionCallBack: ((Boolean) -> Unit)? = null) {
        PermissionUtil.requireMultiplePermissions(rxPermissions, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe {
                    logger {
                        "Premission  checkLocationPermission $it"
                    }
                    permissionCallBack?.invoke(it)
                }
}

/**
 * 检查定位权限
 */
fun checkLocationPermission(fragment: Fragment, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (fragment.activity != null) {
        // 判断编译版本,如果大于等于29,就用permissionsQ
//        if (Build.VERSION.SDK_INT >= 29) {
//            PermissionUtil.requireMultiplePermissions(fragment, Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                    .subscribe {
//                        logger {
//                            "Premission  checkLocationPermission $it"
//                        }
//                        permissionCallBack?.invoke(it)
//                    }
//
//        } else {
            PermissionUtil.requireMultiplePermissions(fragment, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {
                        logger {
                            "Premission  checkLocationPermission $it"
                        }
                        permissionCallBack?.invoke(it)
                    }
//        }
    }
}

/**
 * 检查巡店必要权限
 */
fun checkTourPermission(activity: FragmentActivity, permissionCallBack: ((Boolean) -> Unit)? = null) {
    if (activity.wasNotDestroyed()) {
        PermissionUtil.requireMultiplePermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe {
                    logger {
                        "Premission  checkTourPermission $it"
                    }
                    permissionCallBack?.invoke(it)
                }
    }

}

