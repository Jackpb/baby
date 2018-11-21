$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'hospital/bed/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '床位代码', name: 'code', index: 'code', width: 80 }, 			
			{ label: '排序', name: 'sort', index: 'sort', width: 80 }, 			
			{ label: '责任护士', name: 'nursesName', index: 'nursesName', width: 80 },
			{ label: '所属科室', name: 'deptName', index: 'deptName', width: 80 }
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
		bed: {
            deptId:null,
            nurseId:null,
            code:null
        },
        nurses:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bed = {
                deptId:null,
                nurseId:null,
                code:null
			};
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
            vm.getNurses(vm.bed.deptId);
            $.ajaxSettings.async = true;//开启异步
		},
		saveOrUpdate: function (event) {
			var url = vm.bed.id == null ? "hospital/bed/save" : "hospital/bed/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bed),
			    success: function(r){
			    	if(r.code == 0){
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
				    url: baseURL + "hospital/bed/delete",
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
			$.get(baseURL + "hospital/bed/info/"+id, function(r){
                vm.bed = r.bed;
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
            //加载科室树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.bed.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.bed.deptName = node.name;
                }
            })
        },
        getNurses:function(deptId){
            //加载团队
            $.get(baseURL + "hospital/nurse/dept/"+deptId, function(r){
                vm.nurses=r.nurses;
            })
        },

        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择科室",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级科室
                    vm.bed.deptId = node[0].deptId;
                    vm.bed.deptName = node[0].name;
                    vm.getNurses(vm.bed.deptId);
                    //TODO：设置角色多选框
                    // $.get(baseURL + "sys/role/list/"+node[0].deptId, function(r){
                    //     vm.roleList = r.list;
                    // });
                    layer.close(index);
                }
            });
        }
	}
});