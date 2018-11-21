$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/sysoperlog/list',
        datatype: "json",
        colModel: [			
			{ label: '序号', name: 'operId', index: 'oper_id', width: 50, key: true },
			{ label: '模块标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '业务类型', name: 'businessType', index: 'business_type', width: 80,
					formatter: function(value, row, index) {
                        var temp = "";
                        if(value==0){
                            temp = "<span class=\"label label-success\">其他</span>";
                        }else if(value==1){
                            temp = "<span class=\"label label-success\">新增</span>";
                        }else if(value==2){
                            temp = "<span class=\"label label-success\">修改</span>";
                        }else if(value==3){
                            temp = "<span class=\"label label-danger\">删除</span>";
                        }
                    	return temp;
             		}
             },
			{ label: '方法名称', name: 'method', index: 'method', width: 80 },
			{ label: '操作人员', name: 'operName', index: 'oper_name', width: 80 },
			{ label: '请求URL', name: 'operUrl', index: 'oper_url', width: 80 }, 			
			{ label: '主机地址', name: 'operIp', index: 'oper_ip', width: 80 }, 			
			{ label: '操作地点', name: 'operLocation', index: 'oper_location', width: 80 }, 			
			{ label: '请求参数', name: 'operParam', index: 'oper_param', width: 80 }, 			
			{ label: '操作状态', name: 'status', index: 'status', width: 80 ,
                formatter: function(value, options, row){
                    return value == 0 ?
                        '<span class="label label-success">正常</span>' :
                        '<span class="label label-danger">异常</span>';
                }
             },
			{ label: '错误消息', name: 'errorMsg', index: 'error_msg', width: 80 }, 			
			{ label: '操作时间', name: 'operTime', index: 'oper_time', width: 80 }			
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
		sysOperLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysOperLog = {};
		},
		update: function (event) {
			var operId = getSelectedRow();
			if(operId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(operId)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysOperLog.operId == null ? "monitor/sysoperlog/save" : "monitor/sysoperlog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysOperLog),
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
			var operIds = getSelectedRows();
			if(operIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/sysoperlog/delete",
                    contentType: "application/json",
				    data: JSON.stringify(operIds),
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
		getInfo: function(operId){
			$.get(baseURL + "monitor/sysoperlog/info/"+operId, function(r){
                vm.sysOperLog = r.sysOperLog;
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