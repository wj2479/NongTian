package com.qdhc.ny.bean;

/**
 * 文件上传成功后返回的数据实体
 * Created by 申健 on 2018/9/17.
 */

public class UploadFile {
    //视频
    private String Video;//
    private String Img;//id
    //图片
    private String ImgDa;//
    private String ImgXiao;//
    //多图片上传
     private String ImgDas;//
    private String ImgXiaos;//

    public String getImgDas() {
        return ImgDas == null ? "" : ImgDas;
    }

    public void setImgDas(String imgDas) {
        ImgDas = imgDas;
    }

    public String getImgXiaos() {
        return ImgXiaos == null ? "" : ImgXiaos;
    }

    public void setImgXiaos(String imgXiaos) {
        ImgXiaos = imgXiaos;
    }

    public String getImgDa() {
        return ImgDa == null ? "" : ImgDa;
    }

    public void setImgDa(String imgDa) {
        ImgDa = imgDa;
    }

    public String getImgXiao() {
        return ImgXiao == null ? "" : ImgXiao;
    }

    public void setImgXiao(String imgXiao) {
        ImgXiao = imgXiao;
    }

    public String getVideo() {
        return Video == null ? "" : Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getImg() {
        return Img == null ? "" : Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
