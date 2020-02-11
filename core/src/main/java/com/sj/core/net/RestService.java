package com.sj.core.net;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * retrofit的所有功能
 *
 * @author shenjian
 * @date 2018/8/10
 */


public interface RestService {
    @GET
    Call<String> get(@Url String ur);

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    //下载是直接到内存,所以需要 @Streaming
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    //上传
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @HeaderMap Map<String, Object> header, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @HeaderMap Map<String, Object> header, @PartMap Map<String, RequestBody> params, @Part() List<MultipartBody.Part> files);

    //原始数据 非表单数据提交的
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

}










