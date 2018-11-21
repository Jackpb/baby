$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'hospital/doctor/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '简介', name: 'introduce', index: 'introduce', width: 80 }, 			
			//{ label: '点赞数', name: 'clickRate', index: 'click_rate', width: 80 },
			//{ label: '浏览量', name: 'viewRate', index: 'view_rate', width: 80 },
			{ label: '状态', name: 'status', index: 'status', width: 80, formatter: function(value, options, row){
                    if (value == 0 ){
                        return '<span class="label label-danger">禁用</span>';
                    }else {
                        return '<span class="label label-success">正常</span>';
                    }
                }},
			{ label: '登录用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '所属科室', name: 'deptName', index: 'deptName', width: 80 },
			{ label: '所属团队', name: 'teamName', index: 'teamName', width: 80 },
			{ label: '职称', name: 'titlesName', index: 'titlesName', width: 80 },
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
		doctor: {
            deptId:null,
            deptName:null,
            status:0
		},
        titles:{},
        medicalTeams:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.doctor = {
                deptId:null,
                deptName:null,
                status:0
			};
            $('#mySwitch input').bootstrapSwitch('state',false);
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
            $.ajaxSettings.async = false;//关闭异步
            vm.getInfo(id);
            vm.getDept();
            vm.getTitles(vm.doctor.deptId);
            vm.getTeams(vm.doctor.deptId);
            $.ajaxSettings.async = true;//开启异步
        },
		saveOrUpdate: function (event) {
            var content= ue.getContent();
            vm.doctor.introduce = encode64(strUnicode2Ansi(content));//加密，解决html在转json时乱码

            var url = vm.doctor.id == null ? "hospital/doctor/save" : "hospital/doctor/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.doctor),
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
				    url: baseURL + "hospital/doctor/delete",
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
			$.get(baseURL + "hospital/doctor/info/"+id, function(r){
                vm.doctor = r.doctor;
                if(vm.doctor.status==1){
                    $('#mySwitch input').bootstrapSwitch('state',true);
                }else{
                    $('#mySwitch input').bootstrapSwitch('state',false);
                }
                var htmlStr = strAnsi2Unicode(decode64(vm.doctor.introduce));//解密富文本内容
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
                var node = ztree.getNodeByParam("deptId", vm.doctor.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.doctor.deptName = node.name;
                }
            })
        },
        getTeams: function(deptId){
            //加载团队
            $.get(baseURL + "hospital/medicalteam/dept/"+deptId, function(r){
                vm.medicalTeams=r.medicalTeams;
            })
        },
        getTitles:function(deptId){
            //加载职称
            $.get(baseURL + "hospital/titles/dept/"+deptId, function(r){
                vm.titles=r.titles;
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
                    vm.doctor.deptId = node[0].deptId;
                    vm.doctor.deptName = node[0].name;
                    vm.getTitles(vm.doctor.deptId);
                    vm.getTeams(vm.doctor.deptId);
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