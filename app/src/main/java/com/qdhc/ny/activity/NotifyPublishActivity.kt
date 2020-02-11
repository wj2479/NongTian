package com.qdhc.ny.activity

import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.qdhc.ny.R
import com.qdhc.ny.adapter.OrgInfoAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.bean.UserNode
import com.qdhc.ny.bean.UserTreeNode
import com.qdhc.ny.bmob.Notify
import com.qdhc.ny.bmob.NotifyReceiver
import com.qdhc.ny.bmob.UserInfo
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.User
import com.qdhc.ny.utils.TreeNodeHelper
import com.sj.core.utils.ToastUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_notify_publish.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import java.util.*


/**
 * 发布通知
 */
class NotifyPublishActivity : BaseActivity() {

    lateinit var user: User

    lateinit var treeNode: UserTreeNode

    /**
     * 当前选中的Node
     */
    lateinit var selectNode: UserTreeNode

    var viewList = LinkedList<View>()

    var total = 0

    override fun intiLayout(): Int {
        return R.layout.activity_notify_publish
    }

    override fun initView() {
        title_tv_title.text = "发布通知"
    }

    var MAX_COUNT = 0
    var count = 0

    override fun initClick() {
        title_iv_back.setOnClickListener { finish() }

        bt_publish.setOnClickListener {
            var content = edt_content.text.toString().trim()
            if (content.isEmpty()) {
                ToastUtil.show(this, "通知内容不能为空")
                return@setOnClickListener
            }
            val selectedNodes = TreeNodeHelper.getSelectedNodes(treeNode);
            if (selectedNodes.size == 0) {
                ToastUtil.show(this, "您还没有选择接收人")
                return@setOnClickListener
            }

            showDialog("正在发布通知...")

            var notify = Notify()
            notify.content = content
//            notify.publish = user.objectId
            notify.save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    if (e == null) {
                        MAX_COUNT = selectedNodes.size
                        count = 0
                        for (i in selectedNodes.indices) {
                            var userInfo = selectedNodes.get(i).userInfo as UserInfo

                            var notifyReceiver = NotifyReceiver()
                            notifyReceiver.nid = objectId
                            notifyReceiver.uid = userInfo.objectId
                            notifyReceiver.isRead = false
                            notifyReceiver.save(object : SaveListener<String>() {
                                override fun done(objectId: String?, e: BmobException?) {
                                    count++
                                    if (count == MAX_COUNT) {
                                        showDialog("通知发布成功...")
                                        Handler().postDelayed({
                                            dismissDialogNow()
                                            finish()
                                        }, 1500)
                                    }
                                }
                            })
                        }
                    } else {
                        showDialog("通知发布失败...")
                        dismissDialog()
                    }
                }
            })
        }

        selectAllcb.setOnClickListener {
            val checked = selectAllcb.isChecked()
            val impactedChildren = TreeNodeHelper.selectNodeAndChild(selectNode, checked)
            Log.e("selectAll", "节点-->" + impactedChildren.size)
            if (impactedChildren.size > 0) {
                mAdapter.notifyDataSetChanged()
            }
            updateCountTv()
        }
    }

    override fun initData() {
        user = ProjectData.getInstance().userInfo
        treeNode = ProjectData.getInstance().rootNode
        selectNode = treeNode
        total = TreeNodeHelper.getChildCount(treeNode)
        updateCountTv()

        addLinkTv(treeNode)

        initRefresh()
    }

    lateinit var mAdapter: OrgInfoAdapter

    private fun initRefresh() {
        smrw.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        smrw.addItemDecoration(DefaultItemDecoration(ContextCompat.getColor(this, R.color.backgroundColor)))

        // RecyclerView Item点击监听。
        smrw.setSwipeItemClickListener { itemView, position ->
            //            startActivity(intent)
        }

        mAdapter = OrgInfoAdapter(this, treeNode)
        mAdapter.setOnItemClickListener(object : OrgInfoAdapter.OnItemClickListener {

            override fun onUserInfoSelectedChanged(userNodes: MutableList<UserNode>?, isSelected: Boolean) {
                Log.e("点击-----》", userNodes?.size.toString() + "  isCheck  " + isSelected)
                selectAllIfNeed()

                updateCountTv()
            }

            override fun onItemClick(position: Int, v: View?) {
            }

            override fun onNodeClick(treeNode: UserTreeNode?) {
                mAdapter.setTreeNode(treeNode)
                addLinkTv(treeNode!!)
                selectNode = treeNode
                selectAllIfNeed()
            }
        })
        smrw.adapter = mAdapter
    }

    /**
     * 添加链接视图
     */
    fun addLinkTv(node: UserTreeNode) {
        var view = LayoutInflater.from(this).inflate(R.layout.view_org_add, null)

        val tv = view.findViewById<TextView>(R.id.nameTv)
        tv.text = node.name
        view.setTag(R.id.tag_text_view, node);
        view.setOnClickListener { view ->
            var node = view.getTag(R.id.tag_text_view) as UserTreeNode
            if (node.equals(selectNode)) {
                return@setOnClickListener
            }

            mAdapter.setTreeNode(node)
            selectNode = node

            var index = viewList.indexOf(view) + 1
            while (viewList.size > index) {
                var view = viewList.removeLast()
                orgLayout.removeView(view)
            }

            selectAllIfNeed()
        }
        orgLayout.addView(view)
        viewList.add(view)
    }

    /**
     * 选择是否全选
     */
    fun selectAllIfNeed() {
        var childCount = TreeNodeHelper.getChildCount(selectNode)
        var selectCount = TreeNodeHelper.getSelectedNodes(selectNode).size
//        Log.e("比较-----》", childCount.toString() + "  ====  " + selectCount.toString())

        if (childCount == selectCount) {
            selectAllcb.isChecked = true
            selectNode.isSelected = true
        } else {
            selectAllcb.isChecked = false
            selectNode.isSelected = false
        }
    }

    fun updateCountTv() {
        var sb = StringBuilder()
        sb.append("已选择").append(TreeNodeHelper.getSelectedNodes(treeNode).size).append("人")
        sb.append(" / ")
        sb.append("共计").append(total).append("人")

        selectTv.text = sb.toString()
    }
}
