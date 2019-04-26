[TOC]
## 1 ZTREE

API:<http://www.treejs.cn/v3/api.php>

### 1.1 导入
```
    <link th:href="@{/assets/css/plugins/ztree/zTreeStyle/zTreeStyle.css}" rel="stylesheet"/>
    <script th:src="@{/assets/js/plugins/zTree/jquery.ztree.core.js}"></script>
    <script th:src="@{/assets/js/plugins/zTree/jquery.ztree.excheck.js}"></script>
```
### 1.2 使用
```
var zTreeObj;
var setting = {
  view: {
    showIcon: false, //设置是否显示节点图标
    showLine: true, //设置是否显示节点与节点之间的连线
    showTitle: true //设置是否显示节点的title提示信息
  },
  data: {
    simpleData: {
      enable: true, //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
      idKey: "id", //设置启用简单数据格式时id对应的属性名称
      pidKey: "pid", //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
      rootPid: 0
    }
  },
  check: {
    enable: true,  //设置是否显示checkbox复选框
    chkStyle: "checkbox",
    chkboxType: { "Y": "p", "N": "p" }
  },
  callback: {
    // onClick: setPidInfo,  //定义节点单击事件回调函数
    // onRightClick: OnRightClick, //定义节点右键单击事件回调函数
    // beforeRename: beforeRename, //定义节点重新编辑成功前回调函数，一般用于节点编辑时判断输入的节点名称是否合法
    // onDblClick: onDblClick, //定义节点双击事件回调函数
    // onCheck: onCheck  //定义节点复选框选中或取消选中事件的回调函数
  },
  async: {
    enable: true,   //设置启用异步加载
    type: "get",   //异步加载类型:post和get
    contentType: "application/json", //定义ajax提交参数的参数类型，一般为json格式
    url: "/systemMenuController/selectMenuTreeList",  //定义数据请求路径
    autoParam: ["id=id", "name=name"] //定义提交时参数的名称，=号前面标识节点属性，后面标识提交时json数据中参数的名称
  }
};
$(function () {
  $.post("/systemMenuController/selectMenuTreeList", {}, function (menus) {  //id=3是初始输入，确立根节点的id=3
    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, eval(menus));
    for (var item in data.obj) {
      zTreeObj.checkNode(zTreeObj.getNodeByParam("id", data.obj[item]), true, true);
    }
  });
});
```
### 1.3 后台
```
<!-- 获得菜单树 -->
<select id="listMenuTree" resultType="cn.pzh.system.web.project.common.model.ZTreeNode">
SELECT
  m1.id AS id,
  (CASE WHEN (m2.id = 0 OR m2.id IS NULL) THEN 0 ELSE m2.id END ) AS pId,
  m1.MENU_REAL_NAME AS NAME,
  (CASE WHEN (m2.id = 0 OR m2.id IS NULL) THEN 'true' ELSE 'false' END ) as isOpen
FROM
  sys_menu m1
LEFT JOIN sys_menu m2 
ON m1.pid = m2.id
ORDER BY
  m1.id ASC
</select>
```
### 1.4 数据格式

![TIM截图20190425145324](E:\Note\front-end\插件\ztree\TIM截图20190425145324.png)