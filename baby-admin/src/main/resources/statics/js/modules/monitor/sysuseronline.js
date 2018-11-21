$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/sysuseronline/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户会话id', name: 'sessionid', index: 'sessionId', width: 80 }, 			
			{ label: '账号', name: 'loginName', index: 'login_name', width: 80 },
			//{ label: '部门名称', name: 'deptName', index: 'dept_name', width: 80 },
			{ label: 'IP地址', name: 'ipaddr', index: 'ipaddr', width: 80 },
			{ label: '登录地点', name: 'loginLocation', index: 'login_location', width: 80 },
			{ label: '浏览器', name: 'browser', index: 'browser', width: 80 },
			{ label: '操作系统', name: 'os', index: 'os', width: 80 }, 			
			{ label: '状态', name: 'status', index: 'status', width: 80,
                formatter: function(value, options, row){
                        return value == 'on_line'?
                            '<span class="label label-success">在线</span>' :
                            '<span class="label label-danger">离线</span>';
                 }
			},
			{ label: '创建时间', name: 'startTimestamp', index: 'start_timestamp', width: 80 },
			{ label: '最后访问时间', name: 'lastAccessTime', index: 'last_access_time', width: 80 },
			{ label: '超时时间(分)', name: 'expireTime', index: 'expire_time', width: 80 }
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysUserOnline: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysUserOnline = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysUserOnline.id == null ? "monitor/sysuseronline/save" : "monitor/sysuseronline/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysUserOnline),
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
				    url: baseURL + "monitor/sysuseronline/delete",
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
			$.get(baseURL + "monitor/sysuseronline/info/"+id, function(r){
                vm.sysUserOnline = r.sysUserOnline;
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