package com.qdhc.ny.activity

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.GridImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.Project
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.SharedPreferencesUtils
import com.sj.core.net.RestClient
import com.sj.core.net.callback.IRequest
import com.sj.core.utils.NetWorkUtil
import com.sj.core.utils.ToastUtil
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.activity_add_project_schedule.*
import kotlinx.android.synthetic.main.activity_add_project_schedule.bt_add
import kotlinx.android.synthetic.main.activity_add_project_schedule.rlv
import kotlinx.android.synthetic.main.activity_update_daily_report.*
import kotlinx.android.synthetic.main.activity_update_daily_report.bubbleSeekbar
import kotlinx.android.synthetic.main.layout_title_theme.*
import java.util.*
import kotlin.collections.ArrayList

class UpdateDailyReportActivity : BaseActivity() {
    val GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    lateinit var adapter: GridImageAdapter

    lateinit var userInfo: User

    var project: Project? = null

    var mLocation: AMapLocation? = null
    var selectList = ArrayList<LocalMedia>()

    // 项目原始的进度
    var initSchedule = 0f

    override fun intiLayout(): Int {
        return R.layout.activity_update_daily_report
    }

    override fun initView() {
        title_tv_title.text = "日报上传"

        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = GridImageAdapter(this, 9, {
            getPermissions()
        })
        adapter.setOnItemClickListener { position, v ->

            var localMedia = selectList.get(position)
            if (localMedia.mimeType == PictureMimeType.ofImage()) {
                var intent = Intent(this, ImageActivity::class.java)
                intent.putExtra("url", localMedia.path)
                startActivity(intent)
            } else if (localMedia.mimeType == PictureMimeType.ofVideo()) {
                PictureSelector.create(this@UpdateDailyReportActivity).externalPictureVideo(localMedia.path);
            }
        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

        typeTv.setOnClickListener { showTypeDialog() }

        bt_add.setOnClickListener {
            var title = typeTv.text.toString()
            if (title.isEmpty()) {
                ToastUtil.show(this, "日报名称不能为空,请选择")
                showTypeDialog()
                return@setOnClickListener
            }

            var checkId = -1
            val checkRadioId = radiogroup.checkedRadioButtonId
            when (checkRadioId) {
                R.id.radiobut1 -> checkId = 0
                R.id.radiobut2 -> checkId = 1
                R.id.radiobut3 -> checkId = 2
                else -> {
                    ToastUtil.show(this, "存在的问题不能为空")
                    return@setOnClickListener
                }
            }

            var content = edt_work.text.toString().trim()
            if (content.isEmpty()) {
                ToastUtil.show(this, "日报详情不能为空")
                edt_work.requestFocus()
                return@setOnClickListener
            }

            var files = ArrayList<String>()
            selectList.forEach { file -> files.add(file.path) }

            var url = "report/uploadDailyReport"
            var params: HashMap<String, Any> = HashMap()
            var headers: HashMap<String, Any> = HashMap()
            params["title"] = title
            params["content"] = content
            params["pid"] = project!!.id
            params["uid"] = userInfo.id
            params["check"] = checkId
            params["schedule"] = bubbleSeekbar.progress
            if (null != ProjectData.getInstance().location) {
                params["address"] = ProjectData.getInstance().location.address
                params["lnglat"] = ProjectData.getInstance().location.longitude.toString() + "," + ProjectData.getInstance().location.latitude.toString()
            }
            RestClient.create()
                    .params(params)
                    .headers(headers)
                    .files(files)
                    .url(url)
                    .request(object : IRequest {
                        override fun onRequestStart() {
                            showDialog("正在添加日报记录...")
                        }

                        override fun onRequestEnd() {
                            dismissDialogNow()
                        }
                    }).success { result ->
                        ToastUtil.show(mContext, "上传成功")
                        var intent = Intent()
                        intent.putExtra("schedule", bubbleSeekbar.progress)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
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
    }

    /**
     * 显示选择日报的对话框
     */
    fun showTypeDialog() {
        var builder = AlertDialog.Builder(this);
        builder.setTitle("请选择日报的名称");
        var items = resources.getStringArray(R.array.daily_report_types)

        // -1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                typeTv.text = items[which]

                dialog?.dismiss()
            }
        });

        // 最后一步 一定要记得 和Toast 一样 show出来
        builder.show();
    }

    override fun initData() {
        userInfo = SharedPreferencesUtils.loadLogin(this)

        if (intent.hasExtra("project")) {
            project = intent.getSerializableExtra("project") as Project
            initSchedule = project!!.process * 1.0f

            bubbleSeekbar.setProgress(initSchedule)
        }

        bubbleSeekbar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                if (progress < initSchedule) {
                    ToastUtil.show(this@UpdateDailyReportActivity, "新进度不能小于当前进度:" + initSchedule + "%")
                    bubbleSeekbar.setProgress(initSchedule)
                }
                if (progress > (initSchedule + 5)) {
                    ToastUtil.show(this@UpdateDailyReportActivity, "新进度不能超过进度:" + (initSchedule + 5) + "%")
                    bubbleSeekbar.setProgress(initSchedule + 5)
                }
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }
        mLocation = ProjectData.getInstance().location

        if (intent.hasExtra("code")) {
            val resultCode = intent.getIntExtra("code", 0)
            initIntent(resultCode, intent)
        }
    }

    fun initIntent(resultCode: Int, data: Intent) {
        if (resultCode == 101) {
            var path = data.getStringExtra("path");
            var localMedia = LocalMedia()
            localMedia.path = path
            localMedia.mimeType = PictureMimeType.ofImage()
            localMedia.pictureType = "image/jpeg"
            selectList.add(localMedia)
            adapter.setList(selectList)
            adapter.notifyDataSetChanged()
        } else if (resultCode == 102) {
            var path = data.getStringExtra("path");
            var url = data.getStringExtra("url");
            var localMedia = LocalMedia()
            localMedia.path = url
            localMedia.mimeType = PictureMimeType.ofVideo()
            // 获取时长
            var mediaPlayer = MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            localMedia.duration = mediaPlayer.getDuration().toLong() + 1000
            localMedia.pictureType = "video/mp4"
            localMedia.cutPath = path
            localMedia.isCut = true
            selectList.add(localMedia)
            adapter.setList(selectList)
            adapter.notifyDataSetChanged()
            mediaPlayer.release()

        } else if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取权限
     */
    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                startActivityForResult(Intent(this, CameraActivity::class.java), 100);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                ), GET_PERMISSION_REQUEST);
            }
        } else {
            startActivityForResult(Intent(this, CameraActivity::class.java), 100);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            initIntent(resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GET_PERMISSION_REQUEST) {
            var size = 0;
            if (grantResults.size >= 1) {
                var writeResult = grantResults[0];
                //读写内存权限
                var writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                var recordPermissionResult = grantResults[1];
                var recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                var cameraPermissionResult = grantResults[2];
                var cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startActivityForResult(Intent(this, CameraActivity::class.java), 100);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
