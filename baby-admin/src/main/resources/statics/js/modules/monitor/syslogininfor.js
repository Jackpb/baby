$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/syslogininfor/list',
        datatype: "json",
        colModel: [			
			{ label: 'infoId', name: 'infoId', index: 'info_id', width: 50, key: true },
			{ label: '登录账号', name: 'loginName', index: 'login_name', width: 80 }, 			
			{ label: '登录IP地址', name: 'ipaddr', index: 'ipaddr', width: 80 }, 			
			{ label: '登录地点', name: 'loginLocation', index: 'login_location', width: 80 }, 			
			{ label: '浏览器类型', name: 'browser', index: 'browser', width: 80 }, 			
			{ label: '操作系统', name: 'os', index: 'os', width: 80 }, 			
			{ label: '登录状态', name: 'status', index: 'status', width: 80 ,
                formatter: function(value, options, row){
                    return value == 0 ?
                        '<span class="label label-success">成功</span>' :
                        '<span class="label label-danger">失败</span>';
                }
             },
			{ label: '提示消息', name: 'msg', index: 'msg', width: 80 }, 			
			{ label: '访问时间', name: 'loginTime', index: 'login_time', width: 80 }			
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
		sysLogininfor: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysLogininfor = {};
		},
		update: function (event) {
			var infoId = getSelectedRow();
			if(infoId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(infoId)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysLogininfor.infoId == null ? "monitor/syslogininfor/save" : "monitor/syslogininfor/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysLogininfor),
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
			var infoIds = getSelectedRows();
			if(infoIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/syslogininfor/delete",
                    contentType: "application/json",
				    data: JSON.stringify(infoIds),
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
		getInfo: function(infoId){
			$.get(baseURL + "monitor/syslogininfor/info/"+infoId, function(r){
                vm.sysLogininfor = r.sysLogininfor;
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