	var params; 
	var comboVal;
	var comboVal1;
	var lastsel;
	var fnMerge;
	var gridParam;
	
	var datarow = {code:"",name:"",aply_knd:"",sort:"",ipe_eeno:""
					,inp_ymd:"",updr_eeno:"",updr_ymd:"",code_h:""};

	function fnSetDocumentReady(){
		initMenus();
		
		var url        = "doComboListToRequest.do";
		getCommonCode("bc_type:02::A;", "N", "init();", url);
	}

	function init(){
		
		var params = {
				knd	: '02',
				bc_type	: $('#bc_type').val(),
				corp_cd	: sess_corp_cd
		};
		
		cn =['Card Code', 'Card Name','Use Y/N','Sort','Enter Id','Enter Date'
  		     ,'Update ID','Update Date','code_h','bc_type_h'
  		     ],
		
		cm = [
				{name:'code',index:'code', formatter:'string', width:110,align:'left',editable:true,sortable:false},
				{name:'name',index:'name', formatter:'string',width:170,align:'left',editable:true,sortable:false},
				{name:'aply_knd',index:'aply_knd',width:70,align:'center',editable:true,sortable:false,
					edittype:'select', formatter: "select",editoptions:{value:"Y:Y;N:N"}},
				{name:'sort',index:'sort', formatter:'string',width:70,align:'left',editable:true,sortable:false},
				{name:'ipe_eeno',index:'ipe_eeno', formatter:'string',width:130,align:'center',editable:false,sortable:false},
				{name:'inp_ymd',index:'inp_ymd', formatter:'string',width:130,align:'center',editable:false,sortable:false},
				{name:'updr_eeno',index:'updr_eeno', formatter:'string',width:130,align:'center',editable:false,sortable:false},
				{name:'updr_ymd',index:'updr_ymd', formatter:'string',width:130,align:'center',editable:false,sortable:false},
				{name:'code_h',index:'code_h', formatter:'string',hidden:true, width:130,align:'left',editable:true,sortable:false},
				{name:'bc_type_h',index:'bc_type_h', formatter:'string',hidden:true, width:130,align:'left',editable:true,sortable:false}
			],
		
			gridParam = {
				viewEdit : [{
					gridName     : "htmlTable",
					url          : "doSearchGridBcToCardTypeManagement.do",
					colNames     : cn,
					colModel     : cm,
					height       : "100%",
					sortname     : "xcod_code",
					sortorder    : "desc",
					rowNum		 : 10,
					rownumbers   : true,
					multiselect  : true,
					cellEdit     : true,
					fnMerge      : false,
					paramJson    : params,
					pager		 : "htmlPager",
					completeFc	 : "addGridRow();initAfterMenus();"
				}]
			};
		commonJqGridInit(gridParam);
		
		setGridColumnOptions();
		
		jQuery("#htmlTable").jqGrid('navGrid',"#htmlPager",{edit:false,add:false,del:false,search:false,refresh:false});
		
	}
	function retrieve(btnFlag){
		var f = document.frm;
		switch(btnFlag){
		   case "search" :
			    doSearch();
				break;
		   case "save" :
			    doInsert();
				break;
		   case "edit" :
			    doModify();
			    break;
		   case "delete" :
			    doDelete();
				break;
		}
	}
	function doSearch(msgFlag){
		params = {
				knd	: '02',
				bc_type	: $('#bc_type').val(),
				corp_cd	: sess_corp_cd
		};
		
		doCommonSearch("doSearchGridBcToCardTypeManagement.do", util.jsonToString(params), "loadCallBack();", "htmlTable", msgFlag);
	}
	function doInsert(){
		var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
		var tempStr = [];
		if(selectRow.length == 0){
			alertUI("데이터를 선택하세요.");
			return;
		}
		for(i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId)	{
				if(getColValue("code",rowId) == ''){
					alertUI(rowId + " Line : Please enter Card Code.");
					return;
				}
				if(getColValue("name",rowId) == ''){
					alertUI(rowId + " Line : Please enter Card Name");
					return;
				}
				data =
				{
						knd			: '02',
						code		: getColValue("code",rowId),
						bc_type		: '1',
						aply_knd	: getColValue("aply_knd",rowId),
						name		: getColValue("name",rowId),
						sort		: getColValue("sort",rowId),
						ipe_eeno	: sess_empno,
						updr_eeno	: sess_empno,
						corp_cd	    : sess_corp_cd
				};
				tempStr.push(data);;

			} else { alertUI("데이터를 선택하세요.");}
		}
		
		confirmUI("저장 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							paramJson : util.jsonToList(tempStr)
					};
					doCommonAjax("doInsertBcToCardTypeManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
				}
			});
		});
	}	
	function doModify(){
		var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
		var tempStr = [];
		if(selectRow.length == 0){
			alertUI("데이터를 선택하세요.");
			return;
		}
		
		for(i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId)	{
				if(getColValue("code",rowId) == ''){
					alertUI(rowId + " Line : Please enter Card Code");
					return;
				}
				if(getColValue("name",rowId) == ''){
					alertUI(rowId + " Line : Please enter Card Name");
					return;
				}
				data =
				data =
				{
						knd			: '02',
						code		: getColValue("code",rowId),
						aply_knd	: getColValue("aply_knd",rowId),
						name		: getColValue("name",rowId),
						sort		: getColValue("sort",rowId),
						ipe_eeno	: sess_empno,
						updr_eeno	: sess_empno,
						code_h		: getColValue("code_h",rowId),
						bc_type_h	: '1',
						corp_cd	    : sess_corp_cd
				};
				tempStr.push(data);;

			} else { alertUI("데이터를 선택하세요.");}
		}
		
		confirmUI("수정하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							paramJson : util.jsonToList(tempStr)
					};
					doCommonAjax("doModifyBcToCardTypeManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
				}
			});
		});
	}
	
	function doDelete(){
		var selectRow = jQuery("#htmlTable").jqGrid('getGridParam','selarrrow');
		var tempStr = [];
		if(selectRow.length == 0){
			alertUI("데이터를 선택하세요.");
			return;
		}
		
		for(i=0; i<selectRow.length; i++){
			rowId = selectRow[i];
			if(rowId)	{
				data =
				{
						knd			: '02',
						code_h		: getColValue("code_h",rowId),
						bc_type_h	: '1',
						corp_cd	    : sess_corp_cd
				};
				tempStr.push(data);;

			} else { alertUI("데이터를 선택하세요.");}
		}
		
		confirmUI("삭제 하시겠습니까?");
		$("#pop_yes").click(function(){
			$.unblockUI({
				onUnblock: function(){
					var paramData = {
							paramJson : util.jsonToList(tempStr)
					};
					doCommonAjax("doDeleteBcToCardTypeManagement.do", paramData, "setBottomMsg(jsonData.sendResult.message, true);doSearch('N');");
				}
			});
		});
	}	
	
	/**
	 * callback
	 */
	function loadCallBack(){
		if(fnMerge !== ""){
			eval(fnMerge);
		}
		$("option[value=1000000]").text('All');
		addRow();
	}
	/**
	 * addRow $.IDs.length + 1
	 * datarow colModel keyName:"", ....
	 */
	function addRow(){
		var gridRowId = jQuery("#htmlTable").getDataIDs().length;
		for(var i = 1; i <= 10 - gridRowId ; i++){
			jQuery("#htmlTable").jqGrid("addRowData", gridRowId+i, datarow);
		}
	}
	