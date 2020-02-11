package com.qdhc.ny.activity

import android.content.DialogInterface
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.lcodecore.ILabel
import com.lcodecore.LabelLayout
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.TagLabel
import com.qdhc.ny.bmob.Project
import com.qdhc.ny.common.ProjectData
import com.sj.core.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.layout_title_theme.*


/**
 * 添加新的工程
 */
class AddProjectActivity : BaseActivity() {

    var areaId = 0
    lateinit var villageId: String

    // 标签的数据
    var labels = ArrayList<ILabel>()

    override fun intiLayout(): Int {
        return R.layout.activity_add_project
    }

    override fun initView() {
        title_tv_title.text = "创建新工程"
    }

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

        managerTv.setOnClickListener {
            var builder = AlertDialog.Builder(this);
            builder.setTitle("请选择工程的负责人");
            var items = arrayOfNulls<String>(ProjectData.getInstance().userInfos.size)

            ProjectData.getInstance().userInfos.forEachIndexed { index, user -> items[index] = user.nickName }

            // -1代表没有条目被选中
            builder.setSingleChoiceItems(items, -1, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var user = ProjectData.getInstance().userInfos[which]
                    managerTv.setText(user.nickName)
                    managerTv.tag = user.objectId
                    dialog?.dismiss()
                }
            });
            builder.show()
        }

        label_me.setOnCheckChangedListener(object : LabelLayout.OnCheckChangeListener {
            override fun onCheckChanged(label: ILabel?, isChecked: Boolean) {
                Log.e("TAG", "Change->" + label?.name + "   " + isChecked)
            }

            override fun onBeyondMaxCheckCount() {
                ToastUtil.show(this@AddProjectActivity, "最多可以选择3个标签")
            }
        })

        tv_add.setOnClickListener {
            if (labels.size >= 20) {
                ToastUtil.show(this@AddProjectActivity, "创建的自定义标签太多了")
            } else {
                getdialog()
            }
        }

        bt_add.setOnClickListener {
            var name = nameTv.text.toString().trim()
            if (name.isEmpty()) {
                ToastUtil.show(this, "工程名称不能为空")
                return@setOnClickListener
            }

            var content = edt_work.text.toString().trim()
            if (content.isEmpty()) {
                ToastUtil.show(this, "工程简介不能为空")
                return@setOnClickListener
            }

            var person = managerTv.text.toString().trim()
            if (person.isEmpty()) {
                ToastUtil.show(this, "请指定工程的负责人")
                return@setOnClickListener
            }

            showDialog("正在创建工程...")

            var sb = StringBuffer()
            label_me.checkedLabelIds.forEachIndexed { index, id ->
                sb.append(id)
                if (index < label_me.checkedLabelsCount - 1) {
                    sb.append(",")
                }
            }

            var project = Project()
            project.name = name
            project.introduce = content
            project.area = areaId
            project.village = villageId
            project.tags = sb.toString().trim()
            project.manager = managerTv.tag.toString()

            project.save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    if (e == null) {
                        showDialog("工程创建成功...")
                        Handler().postDelayed({
                            dismissDialogNow()
                            finish()
                        }, 1500)
                    } else {
                        showDialog("工程失败...")
                        dismissDialog()
                    }
                }
            })

        }
    }

    private fun getdialog() {
        var v = layoutInflater.inflate(R.layout.dialog_input, null)
        var et = v.findViewById<EditText>(R.id.inputEt)

        AlertDialog.Builder(this).setTitle("请输入标签").setView(v).setPositiveButton("确定") { arg0, arg1 ->
            val text = et.text.toString().trim()
            if (text.isEmpty())
                return@setPositiveButton

            labels.forEach { label ->
                if (label.id.equals(text)) {
                    ToastUtil.show(this, "标签已经存在")
                    return@setPositiveButton
                }
            }
            labels.add(TagLabel(text, text))
            label_me.setLabels(labels)
        }.setNegativeButton("取消", null).show()
    }

    override fun initData() {
        areaId = intent.getIntExtra("area", 0)
        villageId = intent.getStringExtra("village")

        labels.add(TagLabel("水利", "水利"))
        labels.add(TagLabel("农业", "农业"))
        labels.add(TagLabel("其他", "其他"))

        label_me.setLabels(labels)
        // 最大5个标签
        label_me.setMaxCheckCount(3)
    }

}
