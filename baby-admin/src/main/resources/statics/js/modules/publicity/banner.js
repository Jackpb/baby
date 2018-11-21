$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'publicity/banner/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: 'banner名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '链接', name: 'mediaUrl', index: 'media_url', width: 80 }, 			
			{ label: '显示顺序', name: 'sort', index: 'sort', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '启用状态：1启用，0禁用', name: 'status', index: 'status', width: 80, formatter: function(value, options, row){
                    if (value == 0 ){
                        return '<span class="label label-danger">禁用</span>';
                    }else {
                        return '<span class="label label-success">正常</span>';
                    }
                }
            },
			{ label: '所属类型ID', name: 'typeName', index: 'typeName', width: 80 },
			{ label: '所属科室ID', name: 'deptName', index: 'deptName', width: 80 }
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
		banner: {
            deptId:null,
            deptName:null,
            status:0
		},
        bannerTypes:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            vm.banner = {deptName:null, deptId:null,status:0};
            $('#mySwitch input').bootstrapSwitch('state',false);
            $("#material_url").val("");//初始化地址框内容
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
			var url = vm.banner.id == null ? "publicity/banner/save" : "publicity/banner/update";
            vm.banner.mediaUrl=$("#material_url").val();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.banner),
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
				    url: baseURL + "publicity/banner/delete",
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
			$.get(baseURL + "publicity/banner/info/"+id, function(r){
                vm.banner = r.banner;
                if(vm.banner.status==1){
                    $('#mySwitch input').bootstrapSwitch('state',true);
                }else{
                    $('#mySwitch input').bootstrapSwitch('state',false);
                }
                $("#material_url") .val(r.banner.mediaUrl).change();//为地址框赋值
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
                var node = ztree.getNodeByParam("deptId", vm.banner.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.banner.deptName = node.name;
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
                    vm.banner.deptId = node[0].deptId;
                    vm.banner.deptName = node[0].name;
                    layer.close(index);
                }
            });
        },
        getTypes: function(){
            //加载banner类型
            $.get(baseURL + "hospital/bannertype/bannerTypeList", function(r){
                vm.bannerTypes=r.bannerTypes;
            })
        }
	}
});