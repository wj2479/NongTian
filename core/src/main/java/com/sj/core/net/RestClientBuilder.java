package com.sj.core.net;


import com.sj.core.net.callback.IError;
import com.sj.core.net.callback.IFailure;
import com.sj.core.net.callback.IRequest;
import com.sj.core.net.callback.ISuccess;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 建造者模式
 *
 * @author shenjian
 * @date 2018/8/10
 */

public class RestClientBuilder {
    private HashMap<String, Object> mParams;
    private HashMap<String, Object> mHeader;
    private String mUrl;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;

    //上传下载
    private File mFile;
    private ArrayList<File> mFiles = new ArrayList<>();

    private String mDownloadDir;
    private String mExtension;
    private String mFilename;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(HashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    public final RestClientBuilder headers(HashMap<String, Object> headers) {
        this.mHeader = headers;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    //上传
    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder files(ArrayList<String> file) {
        for (int i = 0; i < file.size(); i++) {
            this.mFiles.add(new File(file.get(i)));
        }
        return this;
    }

    //下载
    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    //扩展名
    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    //下载的文件名
    public final RestClientBuilder filename(String filename) {
        this.mFilename = filename;
        return this;
    }


    public final RestClient build() {
        return new RestClient(mParams, mHeader, mUrl, mRequest, mSuccess, mFailure, mError, mBody, mFile, mDownloadDir, mExtension, mFilename, mFiles);
    }
}
