package com.sj.core.app;

import android.content.Context;

/**
 * Created by 申健 on 2018/8/10.
 */

public class ProjectInit {
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT.name());
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }


}
