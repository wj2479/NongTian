package com.qdhc.ny.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.qdhc.ny.R
import com.qdhc.ny.adapter.OrgInfoAdapter
import com.qdhc.ny.base.BaseActivity
import com.qdhc.ny.common.ProjectData
import com.qdhc.ny.entity.SelectUser
import com.qdhc.ny.entity.User
import com.qdhc.ny.entity.UserAreaTreeNode
import com.qdhc.ny.utils.TreeNodeHelper
import com.sj.core.net.Rx.RxRestClient
import com.sj.core.utils.ToastUtil
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notify_publish.*
import kotlinx.android.synthetic.main.layout_title_theme.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


/**
 * 发布通知
 */
class NotifyPublishActivity : BaseActivity() {

    lateinit var user: User

    lateinit var treeNode: UserAreaTreeNode

    /**
     * 用户区域列表
     */
    var userAreaList = ArrayList<UserAreaTreeNode>()

    /**
     * 当前选中的Node
     */
    lateinit var selectNode: UserAreaTreeNode

    var viewList = LinkedList<View>()

    var total = 0

    override fun intiLayout(): Int {
        return R.layout.activity_notify_publish
    }

    override fun initView() {
        title_tv_title.text = "发布通知"
    }

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

            var sb = java.lang.StringBuilder()
            for (index in 0 until selectedNodes.size) {
                sb.append(selectedNodes[index].id)
                if (index < selectedNodes.size - 1) {
                    sb.append(",")
                }
            }
            Log.e("TAG", "接受者:" + sb.toString())
            publish(user.id, content, sb.toString())
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

    private fun publish(uid: Int, content: String, receivers: String) {

        showDialog("正在发布通知...")
        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        params["content"] = content
        params["receivers"] = receivers
        RxRestClient.create()
                .url("notify/publish")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            dismissDialogNow()
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                ToastUtil.show(this, "通知发布成功")
                                finish()
                            } else {
                                Log.e("TAG", "失败:" + result)
                                ToastUtil.show(this, "通知发布失败")
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                            ToastUtil.show(this, "发布失败")
                            dismissDialogNow()
                        })
    }


    override fun initData() {
        user = ProjectData.getInstance().userInfo
        treeNode = UserAreaTreeNode()
        treeNode.area = user.area

        selectNode = treeNode
        addLinkTv(treeNode)

        initRefresh()

        getReceiverUsers(user.id)
    }

    /**
     * 获取接收用户
     */
    private fun getReceiverUsers(uid: Int) {

        var params: HashMap<String, Any> = HashMap()
        params["uid"] = uid
        RxRestClient.create()
                .url("user/getSubAreaUser")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            var json = JSONObject(result)
                            if (json.getInt("code") == 1000) {
                                var jArray = json.getJSONArray("result")
                                var nodeList = ArrayList<UserAreaTreeNode>()
                                for (index in 0 until jArray.length()) {
                                    val jsonObject = jArray.getJSONObject(index)
                                    val node = gson.fromJson(jsonObject.toString(), UserAreaTreeNode::class.java)
                                    nodeList.add(node)
                                }
                                treeNode.childs = nodeList

                                total = TreeNodeHelper.getChildCount(treeNode)
                                updateCountTv()
                                Log.e("TAG", "成功:" + gson.toJson(treeNode))

                            } else {
                                Log.e("TAG", "失败:" + result)
                            }

                        },
                        { throwable ->
                            throwable.printStackTrace()
                        })

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

            override fun onUserInfoSelectedChanged(userNodes: MutableList<SelectUser>?, isSelected: Boolean) {
                Log.e("点击-----》", userNodes?.size.toString() + "  isCheck  " + isSelected)
                selectAllIfNeed()

                updateCountTv()
            }

            override fun onItemClick(position: Int, v: View?) {
            }

            override fun onNodeClick(treeNode: UserAreaTreeNode?) {
                Log.e("下级-----》", treeNode.toString())
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
    fun addLinkTv(node: UserAreaTreeNode) {

        if (node == null)
            return

        var view = LayoutInflater.from(this).inflate(R.layout.view_org_add, null)

        val tv = view.findViewById<TextView>(R.id.nameTv)
        if (node.area != null) {
            tv.text = node.area.name
            view.setTag(R.id.tag_text_view, node);
            view.setOnClickListener { view ->
                var node = view.getTag(R.id.tag_text_view) as UserAreaTreeNode
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
    }

    /**
     * 选择是否全选
     */
    fun selectAllIfNeed() {
        var childCount = TreeNodeHelper.getChildCount(selectNode)
        var selectCount = TreeNodeHelper.getSelectedNodes(selectNode).size
        Log.e("比较-----》", childCount.toString() + "  ====  " + selectCount.toString())

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
