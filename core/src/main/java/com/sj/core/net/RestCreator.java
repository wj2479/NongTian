package com.sj.core.net;


import com.sj.core.app.ConfigKeys;
import com.sj.core.app.ProjectInit;
import com.sj.core.net.Rx.RxRestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author shenjian
 * @date 2018/8/10
 */


public final class RestCreator {
    /**
     * 产生一个全局的Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = ProjectInit.getConfiguration(ConfigKeys.API_HOST);
        //创建Retrofit实例
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //转换器  如果你有2个ConverterFactory,都可以处理数据,那么,先添加的会生效
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();

    }

    private static final class OKHttpHolder {
        private static final int TIME_OUT = 30;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(new HeaderInterceptor())
                .build();
    }

    //提供接口让调用者得到retrofit对象
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * 获取对象
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    //提供接口让调用者得到retrofit对象
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    /**
     * 获取对象
     */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }

}








