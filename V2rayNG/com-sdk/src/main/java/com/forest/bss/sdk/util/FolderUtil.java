package com.forest.bss.sdk.util;

import android.os.Environment;

import java.io.File;

/**
 * Desc
 * <p>
 * Created by zhangzhixiang on 4/12/21 12:14 PM
 */
public class FolderUtil {

    public static boolean mkDir() {
        String path = Environment.getExternalStorageDirectory().getPath();
        File folder = new File(path + File.separator + "yqsl");

        if (!folder.exists()) {
            return folder.mkdir();
        } else {
            return true;
        }

    }

}
