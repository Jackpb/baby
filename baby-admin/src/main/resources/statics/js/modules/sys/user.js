$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
            { label: '所属部门', name: 'deptName', sortable: false, width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};
            //1、初始化角色列表
            this.getRoleList();
            //2、初始化部门树
            vm.getDept();
        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";
            //1、初始化用户信息及其所在部门角色列表
            vm.getUser(userId);
            //2、初始化部门树
            vm.getDept();
            //3、初始化角色列表
            //this.getRoleList();
        },
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                if(vm.user.deptId!=null){
                    var deptId = vm.user.deptId;
                    vm.getRoleListed(deptId);
                }
                //vm.user.password = null;
            });
        },
        getRoleList: function(){

            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        getRoleListed: function(deptId){

            //TODO：设置角色多选框
            $.get(baseURL + "sys/role/list/"+deptId, function(r){
                vm.roleList = r.list;
            });
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },

        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;
                    //TODO：设置角色多选框
                    // $.get(baseURL + "sys/role/list/"+node[0].deptId, function(r){
                    //     vm.roleList = r.list;
                    // });
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        }
    }
});