package com.qdhc.ny.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Poi
import com.amap.api.navi.AmapNaviPage
import com.amap.api.navi.AmapNaviParams
import com.amap.api.navi.AmapNaviTheme
import com.amap.api.navi.AmapNaviType
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jaeger.ninegridimageview.ItemImageClickListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.adapter.ReportCommentAdapter
import com.qdhc.ny.adapter.ReportNineGridImageViewAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.*
import com.qdhc.ny.utils.UserInfoUtils
import com.sj.core.net.RestClient
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.ToastUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_daily_report_details.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


/**
 * 日志详情
 */
class DailyReportDetailsActivity : BaseActivity() {

    val INFOCUS = "已关注"
    val NOFOCUS = "+ 关注"

    lateinit var user: User
    lateinit var report: DailyReport
    lateinit var adapter: ImageAdapter
    lateinit var nineGridImageViewAdapter: ReportNineGridImageViewAdapter

    /**
     * 日报图片 视频列表
     */
    var selectList = ArrayList<Media>()

    /**
     * 评论对象
     */
    var commentList = ArrayList<ReportComment>()

    lateinit var commentAdapter: ReportCommentAdapter
    /**
     * 是不是被关注
     */
    var isReportFocus = false

    override fun intiLayout(): Int {
        return R.layout.activity_daily_report_details
    }

    override fun initView() {
        nineGridImageViewAdapter = ReportNineGridImageViewAdapter()
        nineGridImageView.setAdapter(nineGridImageViewAdapter)
        nineGridImageView.setItemImageClickListener(object : ItemImageClickListener<Media> {
            override fun onItemImageClick(context: Context?, imageView: ImageView?, index: Int, list: MutableList<Media>?) {
                if (list != null) {
                    var url = list.get(index).url
                    if (url.endsWith("mp4", true)) {
                        PictureSelector.create(this@DailyReportDetailsActivity).externalPictureVideo(url);
                    } else {
                        var mediaList = ArrayList<LocalMedia>()

                        list.forEach { media ->
                            var localMedia = LocalMedia()
                            localMedia.path = media.url
                            localMedia.mimeType = PictureMimeType.ofImage()
                            localMedia.pictureType = "image/jpeg"
                            mediaList.add(localMedia)
                        }
                        PictureSelector.create(this@DailyReportDetailsActivity)
                                .themeStyle(R.style.picture_default_style)
                                .openExternalPreview(index, mediaList);
                    }
                }
            }
        })

        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        commentAdapter = ReportCommentAdapter(commentList)
        commentAdapter.setmContext(this)
        rlv.adapter = commentAdapter

        commentAdapter.setOnItemChildClickListener(object : BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (commentList.size > position) {
                    var url = commentList.get(position).mediaList.get(0).url
                    var mediaList = ArrayList<LocalMedia>()

                    var localMedia = LocalMedia()
                    localMedia.path = url
                    localMedia.mimeType = PictureMimeType.ofImage()
                    localMedia.pictureType = "image/jpeg"
                    mediaList.add(localMedia)

                    PictureSelector.create(this@DailyReportDetailsActivity)
                            .themeStyle(R.style.picture_default_style)
                            .openExternalPreview(0, mediaList);
                }
            }
        })
    }

    override fun initClick() {
        backIv.setOnClickListener { finish() }

        attentionTv.setOnClickListener {
            attentionTv.isEnabled = false
            focusReport(user.id, report.id, !isReportFocus)
        }

        sendTv.setOnClickListener {
            var content = inputEt.text.toString().trim()
            if (content.isEmpty()) {
                ToastUtil.show(this, "评论内容不能为空")
                return@setOnClickListener
            }
            uploadComment(report.id, user.id, content)
        }

        picIv.setOnClickListener {
            requestPermissions()
        }

        locationLayout.setOnClickListener {
            if (!TextUtils.isEmpty(report.lnglat)) {
                try {
                    var split = report.lnglat.split(",")
                    /**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
                    if (TextUtils.isEmpty(report.poi)) {
                        var end = Poi(locationTv.text.toString(), LatLng(split[1].toDouble(), split[0].toDouble()), "");
                        AmapNaviPage.getInstance().showRouteActivity(this, AmapNaviParams(null, null, end, AmapNaviType.DRIVER).setTheme(AmapNaviTheme.BLACK), null);
                    } else {
                        //  当不设置起点信息时，会采用用户当前位置作为起点，并显示地点名称为“我的位置”。
                        var end = Poi(report.poi, LatLng(split[1].toDouble(), split[0].toDouble()), "");
                        AmapNaviPage.getInstance().showRouteActivity(this, AmapNaviParams(null, null, end, AmapNaviType.DRIVER).setTheme(AmapNaviTheme.BLACK), null);
                    }
                } catch (e: Exception) {
                }
            } else {
                ToastUtil.show(this, "获取位置坐标失败，无法为您导航")
            }
        }
    }

    override fun initData() {
        user = ProjectData.getInstance().userInfo
        report = intent.getSerializableExtra("report") as DailyReport
        Log.e("TAG", "日志数据--> " + gson.toJson(report))

        // 如果是监理 就不显示 关注按钮
        if (user.role.code == Role.CODE.SUPERVISOR.value) {
            attentionTv.visibility = View.INVISIBLE
        } else {
            picIv.visibility = View.GONE
            if (ProjectData.getInstance().focusReportIdList.contains(report.id)) {
                isReportFocus = true
                attentionTv.text = INFOCUS
            } else {
                isReportFocus = false
                attentionTv.text = NOFOCUS
            }
        }

        nameTv.text = report.title
        if (!TextUtils.isEmpty(report.address)) {
            locationTv.text = report.address.replace("山东省", "")
        } else {
            locationLayout.visibility = View.GONE
        }

        if (TextUtils.isEmpty(report.updateTime)) {
            timeTv.text = report.createTime
        } else {
            timeTv.text = report.updateTime
        }
        contentTv.text = "详情: " + report.content

        when (report.check) {
            0 -> {
                checkTv.text = "合格"
            }
            1 -> {
                checkTv.text = "一般"
                checkTv.setTextColor(getResources().getColor(R.color.text_color_orange))
            }
            2 -> {
                checkTv.text = "不合格"
                checkTv.setTextColor(getResources().getColor(R.color.text_color_red))
            }
        }

        if (user.id == report.uid) {
            personTv.visibility = View.GONE
        } else {
            UserInfoUtils.getInfoByObjectId(report.uid, { user ->
                if (user != null) {
                    personTv.setText(user.getNickName());
                }
            });
        }

        if (report.mediaList.size > 0) {
            selectList.addAll(report.mediaList)
        }
        nineGridImageView.setImagesData(selectList)

        getReportComments(report.id)
    }

    /**
     * 取消/关注 日报
     */
    private fun focusReport(uid: Int, rid: Int, isFocus: Boolean) {
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        params["rid"] = rid
        params["isFocus"] = isFocus

        RxRestClient.create()
                .url("report/focusReport")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                if (isFocus) {
                                    attentionTv.text = INFOCUS
                                    ToastUtil.show(this, "关注成功")
                                } else {
                                    attentionTv.text = NOFOCUS
                                    ToastUtil.show(this, "取消关注成功")
                                }
                                isReportFocus = isFocus
                            }
                            attentionTv.isEnabled = true
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            attentionTv.isEnabled = true
                        })
    }

    private fun uploadComment(rid: Int, uid: Int, content: String) {
        var files = ArrayList<String>()
        uploadComment(rid, uid, content, files)
    }

    /**
     * 上传评论对象
     */
    private fun uploadComment(rid: Int, uid: Int, content: String, files: ArrayList<String>) {
        var url = "report/addReportComment"
        var params: HashMap<String, Any> = HashMap()
        var headers: HashMap<String, Any> = HashMap()
        params["content"] = content
        params["rid"] = rid
        params["sender"] = uid
        RestClient.create()
                .params(params)
                .headers(headers)
                .files(files)
                .url(url)
                .request(object : IRequest {
                    override fun onRequestStart() {
                        showDialog("正在添加评论...")
                    }

                    override fun onRequestEnd() {
                        dismissDialogNow()
                    }
                }).success { result ->
                    ToastUtil.show(mContext, "评论成功")
                    inputEt.setText("")
                    getReportComments(report.id)
                }.error { code, msg ->
                    ToastUtil.show(mContext, "请求错误code:$code$msg")
                }.failure {
                    if (NetWorkUtil.isNetworkConnected(mContext)) {
                        ToastUtil.show(mContext, resources.getString(R.string.net_error))
                    } else {
                        ToastUtil.show(mContext, resources.getString(R.string.net_no_worker))
                    }
                }
                .build()
                .uploads()
    }

    /**
     * 获取评论数据
     */
    private fun getReportComments(rid: Int) {
        var params: HashMap<String, Any> = HashMap()
        params["rid"] = rid

        RxRestClient.create()
                .url("report/getReportComments")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                commentList.clear()
                                var result = json.getJSONArray("result")
                                for (index in 0 until result.length()) {
                                    var jobj = result.getJSONObject(index)
                                    var reportComment = gson.fromJson<ReportComment>(jobj.toString(), ReportComment::class.java)
                                    commentList.add(reportComment)
                                }
                                commentAdapter.notifyDataSetChanged()
                                commentTitleTv.text = "评论 (" + commentList.size + ")"
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })
    }

    /**
     * 检查定位权限
     */
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var rxPermission = RxPermissions(this)

            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            ).subscribe({ permission ->
                if (permission.granted) {
                    // 用户已经同意该权限
                    Log.e("TAG", permission.name + " is granted.")
                    if (permission.name.equals(Manifest.permission.CAMERA)) {
                        startActivityForResult(Intent(this, CameraActivity::class.java), 100);
                    }
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                    ToastUtil.show(this, "拒绝了该权限，没有选中『不再询问』")
                    Log.e("TAG", permission.name + " is denied. More info should be provided.")
                } else {
                    // 用户拒绝了该权限，而且选中『不再询问』
                    ToastUtil.show(this, "拒绝了该权限，而且选中『不再询问』")
                    Log.e("TAG", permission.name + " is denied.")
                }

            })
        } else {
            startActivityForResult(Intent(this, CameraActivity::class.java), 100);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 101) {
            var path = data?.getStringExtra("path").toString()
            showCommentDialog(path)
        } else if (resultCode == 102) {
            var url = data?.getStringExtra("url").toString()

            showCommentDialog(url)
        }
    }

    /**
     * 显示添加对话框
     */
    fun showCommentDialog(path: String) {
        var builder = AlertDialog.Builder(this)
        var view = getLayoutInflater().inflate(R.layout.dialog_comment_pic_input, null)
        var imageIv = view.findViewById<ImageView>(R.id.imageIv)
        Glide.with(this).load(path).into(imageIv)
        var inputEt = view.findViewById<EditText>(R.id.inputEt)
        builder.setView(view)
                .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var files = ArrayList<String>()
                        files.add(path)
                        var content = inputEt.text.toString()
                        uploadComment(report.id, user.id, content, files)
                    }
                })
        ;
        builder.create().show();
    }
}
