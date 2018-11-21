$(function () {
    //1、初始化列表
    $("#jqGrid").jqGrid({
        url: baseURL + 'hospital/material/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '类型', name: 'type', index: 'type', width: 80 },
			{ label: '文件地址', name: 'url', index: 'url', width: 80 , formatter: function(value, options, row){
                    return '<a href="'+value+'" target="_blank" download="">'+value+'</a>';
                }
            },
            { label: '状态', name: 'state', index: 'state', width: 60, formatter: function(value, options, row){
                    if (value == 0 ){
                        return '<span class="label label-danger">素材</span>';
                    }else {
                        return '<span class="label label-success">成品</span>';
                    }
                }
             },
            { label: '上传时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '所属部门', name: 'deptName', index: 'deptName', width: 80 },
			{ label: '使用说明', name: 'comment', index: 'comment', width: 80 }			
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
    //2、初始化开关按钮
    $('#mySwitch input').bootstrapSwitch({
        onColor: "danger",
        offColor: "success",
        onText: '素材',
        offText: '成品',
        size: "normal",
        onSwitchChange: function (event, state) {
            if (state == true) {
                vm.material.state = 0;
            } else {
                vm.material.state = 1;
            }
        }
    });

    initWebuploader();
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		material: {
            deptId:null,
            deptName:null,
            state:0
        }
	},
	methods: {

		query: function () {
			vm.reload();
		},

		add: function(){
			vm.showList = false;
			vm.title = "新增";
            vm.material= {  deptId:null,deptName:null, state:0 };
            $("#material_url").val("");
            // $("#thelist").empty();
            vm.getDept();
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
            if(vm.material.state==0){
                $('#mySwitch input').bootstrapSwitch('state',true);
            }else{
                $('#mySwitch input').bootstrapSwitch('state',false);
            }

            $.ajaxSettings.async = true;//开启异步

        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.material.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.material.deptName = node.name;
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

                    vm.material.deptId = node[0].deptId;
                    vm.material.deptName = node[0].name;
                    layer.close(index);
                }
            });
        },
        saveOrUpdate: function (event) {
            vm.material.url=$("#material_url").val();
			var url = vm.material.id == null ? "hospital/material/save" : "hospital/material/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.material),
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
				    url: baseURL + "hospital/material/delete",
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
			$.get(baseURL + "hospital/material/info/"+id, function(r){
                vm.material = r.material;
                $("#material_url").val(r.material.url);
            });
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});