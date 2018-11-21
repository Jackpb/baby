$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'publicity/dptmenu/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '父菜单', name: 'pname', index: 'pid', width: 80 },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '种类：0菜单，1内容', name: 'type', index: 'type', width: 80 }, 			
			{ label: '图标', name: 'logo', index: 'logo', width: 80 }, 			
			{ label: '可打开的连接地址', name: 'url', index: 'url', width: 80 }, 			
			{ label: '排序', name: 'sort', index: 'sort', width: 80 },
            { label: '状态：1启用，0禁用', name: 'status', index: 'status', width: 80, formatter: function(value, options, row){
                    if (value == 0 ){
                        return '<span class="label label-danger">禁用</span>';
                    }else {
                        return '<span class="label label-success">正常</span>';
                    }
                }},
			{ label: '所属部门', name: 'deptName', index: 'dept_id', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
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

var ztree;
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

var ztree1;
var setting1 = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};


var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		dptMenu: {
            deptId:null,
            deptName:null,
            status:0,
            type:0,
            pid:null,
            pname:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dptMenu = {
			    deptName:null,
                deptId:null,
                status:0,
                type:0,
                pid:null,
                pname:null
			};
            $('#mySwitch input').bootstrapSwitch('state',false);
            $("#material_url").val("");//初始化地址框内容
            $("#logoPic").hide();//根据实际需要，隐藏自定义的控件
            vm.getDept();
            vm.getMenu();
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
            vm.getMenu();
            $.ajaxSettings.async = true;//开启异步
		},
		saveOrUpdate: function (event) {
            vm.dptMenu.logo=$("#material_url").val();
			var url = vm.dptMenu.id == null ? "publicity/dptmenu/save" : "publicity/dptmenu/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dptMenu),
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
				    url: baseURL + "publicity/dptmenu/delete",
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
			$.get(baseURL + "publicity/dptmenu/info/"+id, function(r){
                vm.dptMenu = r.dptMenu;
                if(vm.dptMenu.status==1){
                    $('#mySwitch input').bootstrapSwitch('state',true);
                }else{
                    $('#mySwitch input').bootstrapSwitch('state',false);
                }
                $("#material_url") .val(r.dptMenu.logo).change();//为地址框赋值
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
                var node = ztree.getNodeByParam("deptId", vm.dptMenu.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.dptMenu.deptName = node.name;
                }
            })
        },
        getMenu: function(){
            //加载菜单树
            $.get(baseURL + "publicity/dptmenu/select", function(r){
                ztree1 = $.fn.zTree.init($("#menuTree"), setting1, r.menuList);
                var node = ztree1.getNodeByParam("id", vm.dptMenu.pid);
                if(node != null){
                    ztree1.selectNode(node);
                    vm.dptMenu.pname = node.name;
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
                    vm.dptMenu.deptId = node[0].deptId;
                    vm.dptMenu.deptName = node[0].name;
                    //TODO：设置角色多选框
                    // $.get(baseURL + "sys/role/list/"+node[0].deptId, function(r){
                    //     vm.roleList = r.list;
                    // });
                    layer.close(index);
                }
            });
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '350px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree1.getSelectedNodes();
                    //选择上级部门
                    vm.dptMenu.pid = node[0].id;
                    vm.dptMenu.pname = node[0].name;
                    layer.close(index);
                }
            });
        },
	}
});