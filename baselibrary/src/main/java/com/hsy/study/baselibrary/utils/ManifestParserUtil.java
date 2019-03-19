package com.hsy.study.baselibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hsy.study.baselibrary.config.IConfigModule;

import java.util.ArrayList;
import java.util.List;

/**
 * AndroidManifest 解析解析其中{@link IConfigModule}
 * @author haosiyuan
 * @date 2019/2/16 8:08 PM
 */
public class ManifestParserUtil {

    private static final String MODULE_NAME = "ConfigModule";
    private final Context mContext;

    public ManifestParserUtil(Context context) {
        this.mContext = context;
    }

    public List<IConfigModule> parse() {
        List<IConfigModule> configModuleList = new ArrayList<>();

        try {
            ApplicationInfo applicationInfo = mContext.getPackageManager().
                    getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);

            if (applicationInfo.metaData != null) {
                for (String key : applicationInfo.metaData.keySet()) {
                    if (MODULE_NAME.equals(applicationInfo.metaData.get(key))) {
                        configModuleList.add(parserModule(key));
                    }
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("no AndroidManifest");
        }
        return configModuleList;
    }

    public static IConfigModule parserModule(String className){
        Class clazz;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class is not find");
        }

        Object module = null;
        try {
            module = clazz.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("AndroidManifest parser Exception" + e.getMessage());
        } catch (InstantiationException e) {
            throw new RuntimeException("AndroidManifest parser Exception" + e.getMessage());
        }

        if (!(module instanceof IConfigModule)) {
            throw new RuntimeException("Module is not instanceof ConfigModule");
        }

        return (IConfigModule) module;
    }
}
