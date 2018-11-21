$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'hospital/medicalteam/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '团队名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '图片链接', name: 'picUrl', index: 'pic_url', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '团队类型：0管理，1医生，2护士，3其他', name: 'type', index: 'type', width: 80 },
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
		medicalTeam: {
            deptId:null,
            deptName:null
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.medicalTeam = {
				deptId:null,
                deptName:null,
                type:1
            };
            vm.getDept();
            ue.ready(function() {
                ue.setContent("", false);
            });
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id);
		},
		saveOrUpdate: function (event) {
            var content= ue.getContent();
            vm.medicalTeam.introduce = encode64(strUnicode2Ansi(content));//加密，解决html在转json时乱码
			var url = vm.medicalTeam.id == null ? "hospital/medicalteam/save" : "hospital/medicalteam/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.medicalTeam),
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
				    url: baseURL + "hospital/medicalteam/delete",
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
			$.get(baseURL + "hospital/medicalteam/info/"+id, function(r){
                vm.medicalTeam = r.medicalTeam;
                vm.getDept();
                var htmlStr = strAnsi2Unicode(decode64(vm.medicalTeam.introduce));//解密富文本内容
                ue.ready(function() {
                    ue.setContent(htmlStr, false);
                });
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
                var node = ztree.getNodeByParam("deptId", vm. medicalTeam.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.medicalTeam.deptName = node.name;
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
                    vm.medicalTeam.deptId = node[0].deptId;
                    vm.medicalTeam.deptName = node[0].name;
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