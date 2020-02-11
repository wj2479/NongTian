package com.sj.core.net;


import com.sj.core.net.callback.IError;
import com.sj.core.net.callback.IFailure;
import com.sj.core.net.callback.IRequest;
import com.sj.core.net.callback.ISuccess;
import com.sj.core.net.callback.RequestCallbacks;
import com.sj.core.net.download.DownloadHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * 请求客户端
 *
 * @author shenjian
 * @date 2018/8/10
 */

public class RestClient {
    //参数
    private final HashMap<String, Object> PARAMS;
    private final HashMap<String, Object> HEADER;
    private final String URL;
    //回调接口
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    //上传下载
    //文件上传的路径
    private final File FILE;
    private final ArrayList<File> mFiles;
    //下载目录
    private final String DOWNLOAD_DIR;
    //扩展名
    private final String EXTENSION;
    //文件名
    private final String FILENAME;

    public RestClient(HashMap<String, Object> params,
                      HashMap<String, Object> header,
                      String url,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file, String downloadDir, String extension, String filename, ArrayList<File> mFiles) {
        this.PARAMS = params;
        this.HEADER = header;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;

        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;  ///sdcard/XXXX.ext
        this.EXTENSION = extension;
        this.FILENAME = filename;
        this.mFiles = mFiles;
    }

    public static RestClientBuilder create() {
        return new RestClientBuilder();
    }

    //开始实现真实的网络操作
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        switch (method) {
            case GET:
                if (PARAMS == null || PARAMS.isEmpty()) {
                    call = service.get(URL);
                } else {
                    call = service.get(URL, PARAMS);
                }
                break;
            case POST:
                HashMap<String, Object> empty = new HashMap<String, Object>();
                if (HEADER != null && PARAMS == null) {
                    call = service.post(URL, HEADER, empty);
                } else if (HEADER == null && PARAMS != null) {
                    call = service.post(URL, empty, PARAMS);
                } else if (HEADER != null && PARAMS != null) {
                    call = service.post(URL, HEADER, PARAMS);
                }
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM, FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData(
                        "file", FILE.getName(), requestBody);
                if (HEADER != null) {
                    call = service.upload(URL, HEADER, body);
                } else {
                    call = service.upload(URL, body);
                }
                break;
            case UPLOADS:
                List<MultipartBody.Part> files = new ArrayList<>();
                for (int i = 0; i < mFiles.size(); i++) {
                    MultipartBody.Part bodys = MultipartBody.Part.createFormData(
                            "files", mFiles.get(i).getName(), RequestBody.create(MultipartBody.FORM, mFiles.get(i)));
                    files.add(bodys);
                }

                Map<String ,RequestBody> bodys = new HashMap<>();
                for (Map.Entry<String, Object> entry : PARAMS.entrySet()) {
                    RequestBody mBody = RequestBody.create(MultipartBody.FORM, entry.getValue().toString());
                    bodys.put(entry.getKey(),mBody);
                }

                call = service.upload(URL, HEADER, bodys, files);
                break;
            default:
                break;
        }
        if (call != null) {
            //异步操作
            call.enqueue(getReqeustCallback());
        }
    }

    private Callback<String> getReqeustCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    //各种请求
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void uploads() {
        request(HttpMethod.UPLOADS);
    }

    public final void download() {
        new DownloadHandler(PARAMS, URL, REQUEST,
                SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR,
                EXTENSION, FILENAME)
                .handleDownload();
    }

}







