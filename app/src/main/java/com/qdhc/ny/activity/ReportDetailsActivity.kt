package com.qdhc.ny.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qdhc.ny.R
import com.qdhc.ny.adapter.ImageAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.entity.Media
import com.qdhc.ny.entity.Project
import kotlinx.android.synthetic.main.activity_report_details.*
import kotlinx.android.synthetic.main.layout_title_theme.*


/**
 * 日志详情
 */
class ReportDetailsActivity : BaseActivity() {

    lateinit var adapter: ImageAdapter

    var selectList = ArrayList<Media>()

    override fun intiLayout(): Int {
        return R.layout.activity_report_details
    }

    override fun initView() {
        rlv.isNestedScrollingEnabled = false
        rlv.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager?
        adapter = ImageAdapter(this, selectList)
        adapter.setOnItemClickListener { position, v ->
            var url = selectList.get(position).url

            if (url.endsWith("mp4", true)) {
                PictureSelector.create(this@ReportDetailsActivity).externalPictureVideo(url);
            } else {
                var mediaList = ArrayList<LocalMedia>()

                var localMedia = LocalMedia()
                localMedia.path = url
                localMedia.mimeType = PictureMimeType.ofImage()
                localMedia.pictureType = "image/jpeg"
                mediaList.add(localMedia)

                PictureSelector.create(this)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(0, mediaList);
            }
        }
        rlv.adapter = adapter
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        var project = intent.getSerializableExtra("project") as Project


    }


}
