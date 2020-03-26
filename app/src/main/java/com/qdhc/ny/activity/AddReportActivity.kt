package com.qdhc.ny.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
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
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_add_project_schedule.bt_add
import kotlinx.android.synthetic.main.activity_add_project_schedule.nameTv
import kotlinx.android.synthetic.main.activity_add_project_schedule.rlv
import kotlinx.android.synthetic.main.activity_add_report.*
import kotlinx.android.synthetic.main.layout_title_theme.*

class AddReportActivity : BaseActivity() {

    val GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    lateinit var project: Project

    lateinit var adapter: GridImageAdapter

    lateinit var userInfo: User

    var mLocation: AMapLocation? = null

    var selectList = ArrayList<LocalMedia>()

    var type = 0

    override fun intiLayout(): Int {
        return R.layout.activity_add_report
    }

    override fun initView() {
        //禁止rcyc嵌套滑动
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = GridImageAdapter(this, 9, {
            getPermissions()
        })
        adapter.setOnItemClickListener { position, v ->

            var localMedia = selectList.get(position)
            if (localMedia.mimeType == PictureMimeType.ofImage()) {
                PictureSelector.create(this)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(position, selectList);
            } else if (localMedia.mimeType == PictureMimeType.ofVideo()) {
                PictureSelector.create(this@AddReportActivity).externalPictureVideo(localMedia.path);
            }

        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

        bt_add.setOnClickListener {
            var workContent = edt_work.text.toString().trim()
            if (workContent.isEmpty()) {
                ToastUtil.show(this, "工作内容不能为空")
                return@setOnClickListener
            }

            var questionContent = edt_question.text.toString().trim()
            if (questionContent.isEmpty()) {
                ToastUtil.show(this, "存在的问题不能为空")
                return@setOnClickListener
            }

            var methodContent = edt_method.text.toString().trim()
            if (methodContent.isEmpty()) {
                ToastUtil.show(this, "解决办法不能为空")
                return@setOnClickListener
            }

            var checkContent = edt_check.text.toString().trim()
//            if (checkContent.isEmpty()) {
//                ToastUtil.show(this, "质量自检不能为空")
//                return@setOnClickListener
//            }


            showDialog("正在添加记录...")

        }
    }

    override fun initData() {
        userInfo = ProjectData.getInstance().userInfo

        mLocation = ProjectData.getInstance().location
        locationTv.text = mLocation?.address

        project = intent.getSerializableExtra("project") as Project
        type = intent.getIntExtra("type", 0)
        when (type) {
            1 -> {
                title_tv_title.text = "添加日报"
                tv_work.text = "今日工作"
                edt_work.hint = "请输入今日的工作内容"
            }
            2 -> {
                title_tv_title.text = "添加周报"
                tv_work.text = "本周工作"
                edt_work.hint = "请输入本周的工作内容"
                ll_location.visibility = View.GONE
            }
            3 -> {
                title_tv_title.text = "添加月报"
                tv_work.text = "本月工作"
                edt_work.hint = "请输入本月的工作内容"
                ll_location.visibility = View.GONE
            }
        }

        nameTv.text = project.name
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
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            var path = data?.getStringExtra("path");
            var localMedia = LocalMedia()
            localMedia.path = path
            localMedia.mimeType = PictureMimeType.ofImage()
            localMedia.pictureType = "image/jpeg"
            selectList.add(localMedia)
            adapter.setList(selectList)
            adapter.notifyDataSetChanged()
        } else if (resultCode == 102) {
            Log.i("CJT", "video");
            var path = data?.getStringExtra("path");
            var url = data?.getStringExtra("url");
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
