$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'hospital/departmentmenu/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '菜单名称', name: 'name', index: 'name', width: 80 },
			{ label: '菜单启用', name: 'menuIs', index: 'menu_is', width: 80, formatter: function(value, options, row){
                    return value == 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }},
			{ label: '菜单logo', name: 'logo', index: 'logo', width: 80 },
			{ label: '图片链接', name: 'picUrl', index: 'pic_url', width: 80 },
			{ label: '排序', name: 'sort', index: 'sort', width: 80 },
            { label: '状态', name: 'status', index: 'status', width: 80, formatter: function(value, options, row){
                    return value == 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }},
			{ label: '所属部门', name: 'deptName', index: 'deptName', width: 80 },
			{ label: '宣教类型', name: 'publicityTypeName', index: 'publicityTypeName', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '父id', name: 'pid', index: 'pid', width: 80 }
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
		showList: true,
		title: null,
		departmentMenu: {
            deptId:null,
            deptName:null
		},
        publicityTypes:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.departmentMenu = {deptName:null, deptId:null};
            vm.getDept();
            vm.getTypes();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            $.ajaxSettings.async = false;//关闭异步
            vm.getInfo(id);
            vm.getDept();
            vm.getTypes();
            $.ajaxSettings.async = true;//开启异步
		},
		saveOrUpdate: function (event) {
			var url = vm.departmentMenu.id == null ? "hospital/departmentmenu/save" : "hospital/departmentmenu/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.departmentMenu),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "hospital/departmentmenu/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "hospital/departmentmenu/info/"+id, function(r){
                vm.departmentMenu = r.departmentMenu;
                if(vm.departmentMenu.status==1){
                    $('#mySwitch input').attr("checked", true);
                }else{
                    $('#mySwitch input').attr("checked", false);
                }

                if(vm.departmentMenu.menuIs==1){
                    $('#myMenuIsSwitch input').attr("checked", true);
                }else{
                    $('#myMenuIsSwitch input').attr("checked", false);
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.departmentMenu.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.departmentMenu.deptName = node.name;
                }
            })
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
                    vm.departmentMenu.deptId = node[0].deptId;
                    vm.departmentMenu.deptName = node[0].name;
                    //TODO：设置角色多选框
                    // $.get(baseURL + "sys/role/list/"+node[0].deptId, function(r){
                    //     vm.roleList = r.list;
                    // });
                    layer.close(index);
                }
            });
        },
        getTypes: function(){
            //加载banner类型
            $.get(baseURL + "hospital/publicitytype/publicityTypeList", function(r){
                vm.publicityTypes=r.publicityTypes;
            })
        }
	}
});