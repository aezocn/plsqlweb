/*2009-3-27insertAdjacentHTML 和 insertAdjacentText 是IE下特有的JS，功能非常好用
可惜Firefox 没有这两东东，不过，加上下面的这段的，Firefox下也可以支持这
两个方法了*/
if (typeof HTMLElement != "undefined" && !HTMLElement.prototype.insertAdjacentElement) {
	HTMLElement.prototype.insertAdjacentElement = function (where, parsedNode) {
		switch (where) {
		  case "beforeBegin":
			this.parentNode.insertBefore(parsedNode, this);
			break;
		  case "afterBegin":
			this.insertBefore(parsedNode, this.firstChild);
			break;
		  case "beforeEnd":
			this.appendChild(parsedNode);
			break;
		  case "afterEnd":
			if (this.nextSibling) {
				this.parentNode.insertBefore(parsedNode, this.nextSibling);
			} else {
				this.parentNode.appendChild(parsedNode);
			}
			break;
		}
	};
	HTMLElement.prototype.insertAdjacentHTML = function (where, htmlStr) {
		var r = this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML = r.createContextualFragment(htmlStr);
		this.insertAdjacentElement(where, parsedHTML);
	};
	HTMLElement.prototype.insertAdjacentText = function (where, txtStr) {
		var parsedText = document.createTextNode(txtStr);
		this.insertAdjacentElement(where, parsedText);
	};
}
var sqlNum = [];//数组记录每次传到BEAN中的SQL和分页的行数起始
var fyNum = 0;//记录分页的次数
var sql2 = "";//记录sql
/*传入SQL把数据从数据库里面拉出来展示--新*/
function getResultFromSql(localsql) {
	deletTable();//置空
	fyNum = 1;
	sqlNum[0] = localsql;
	sqlNum[1] = fyNum;
	sqlNum[2] = "";
	sql2 = localsql;
    //设置右下角提示信息--正在执行 --phanrider 2009-04-30 增加
	setFootView(1, "");
	//这里加入执行开始的时间计算
	//BaisWorkBean.initBean(sqlNum, callbackadd);
	
	//将展用新的展示方式，返回类型为list 
	//phanrider 2009-05-17
	BaisWorkBean.GetResultList(sqlNum, callbackadd);
}
/*传入SQL把数据从数据库里面拉出来展示--新*分页用*/
function getFYSql_run() {
	++fyNum;
	sqlNum[0] = sql2;
	sqlNum[1] = fyNum;
	sqlNum[2] = "";
	//BaisWorkBean.initBean(sqlNum, callbackadd);
	BaisWorkBean.GetResultList(sqlNum, callbackadd);
}
/*传入SQL把数据从数据库里面拉出来展示--新*全显示*/
function getFYQSql_run() {
	++fyNum;
	sqlNum[0] = sql2; //sql
	sqlNum[1] = fyNum; //全局变理，分页数
	sqlNum[2] = "Q";  //代表全部分页
	//BaisWorkBean.initBean(sqlNum, callbackadd);
	
	BaisWorkBean.GetResultList(sqlNum, callbackadd);
}
function callbackadd(dataadd) {
	var optionadd = parent.editorFrame.document.getElementById("outResultDiv");
	var oldtime = $time();
	var rows;
	var more = "";
	//alert(dataadd);
	//setDivValueNull("outResultDiv");
	
	//phanrider add 2009-05-21
	//alert(fyNum);
	if (fyNum > 1 && sqlNum[2] != "Q") {
		addDataHtml(fyNum, dataadd);	 //点击按钮 Fetch Next 调用的方法
		if (dataadd.length > 20) {
			rows = dataadd.length - 1;  //去掉最后一个测试标志行
			more = " (more...)";
			setFetchNext(true);
			setFetchLast(true);
		} else {
			rows = dataadd.length;  //无标志行可去
			setFetchNext(false);
			setFetchLast(false);
		}
	} else {
		if (sqlNum[2] == "Q") {
			addFullDataHtml(fyNum, dataadd);  //点击按钮 Fetch Last 调用的方法
			rows = dataadd.length;
			setFetchNext(false);
			setFetchLast(false);
		} else {
			showDataHtml(fyNum, dataadd);	//初始 Execute 调用的方法 
			if (dataadd.length > 21) {
				rows = dataadd.length - 2; //去掉第一个标题行与最后一个测试标志行
				more = " (more...)";
				setFetchNext(true);
				setFetchLast(true);
			} else {
				rows = dataadd.length - 1; //只能去掉第一个标题行
				setFetchNext(false);
				setFetchLast(false);
			}
		}
	}
	//optionadd.insertAdjacentHTML("beforeEnd", dataadd);
	
	//TrColor();
	breakRun("myTextarea");
	//这里加入执行的结束时间计算
	//这里设置右下角提示信息--执行条数和时间，例：1(结果集) row selected in 0.062(执行时间) seconds
	var newtime = ($time() - oldtime) / 1000;
	pageNo = fyNum - 1;
	rows = pageNo * countPage + rows;
	var oracleTitle = "";
	if ( errOracleMsg != "") {
	 	oracleTitle = errOracleMsg;
	 	errOracleMsg = "";
	 	var errResult = "(no result set)";
	 	setNoresult('outResultDiv',errResult);
	} else  {
		oracleTitle = rows + " rows selected in " + newtime + " seconds" + more;	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
	}
	setFootView(9999, oracleTitle);
	parent.leftFrameList.restoreWindowListImg(parent.leftFrameList.getWindowTr());
}
/*删除已有TABLE*/
function deletTable() {
	//if (fyNum > 0) {
		//var Table = parent.editorFrame.document.getElementById("outResultDiv").getElementsByTagName("table");
		//for (var i = 0; i < fyNum; i++) {
		//	Table[0].removeNode(true);
		//}
	parent.editorFrame.$("outResultDiv").set("text", "");
	//}
}
/*设置颜色*/
function TrColor() {
	var TrObject = parent.editorFrame.document.getElementById("outResultDiv").getElementsByTagName("tr");
	var Tdobject;
	for (var k = 0; k < TrObject.length; k++) {
		if (k == 0) {
			TrObject[k].style.background = "ButtonFace";
		} else {
			if (k % 2 != 0) {
				TrObject[k].style.background = "#FFFFFF";
			} else {
				TrObject[k].style.background = "#E5FFE5";
    	//少一个空白的颜色设置 即淡黄色 #FFFFE5 
			}
		}
		Tdobject = TrObject[k].getElementsByTagName("td");
		Tdobject[0].style.background = "ButtonFace";
		Tdobject[1].style.background = "ButtonFace";
	}
}
/*增加前面单独的两行暂不实现*/
function IdH() {
	var TrObject = parent.editorFrame.document.getElementById("outResultDiv").getElementsByTagName("tr");
	var idhNum = parent.editorFrame.document.getElementById("idtu");
	var idHtml = "<table>";
	for (var j = 0; j <= TrObject.length; j++) {
		idHtml += "<tr><td>" + j + "</td></tr>";
	}
	idHtml += "</table>";
	alert(idHtml);
	idhNum.insertAdjacentHTML("beforeEnd", idHtml);
}
/*2009-3-25鼠标移动到哪一行小箭头就追踪到哪一行*/
var trId = -1; //记录鼠标在哪一行的ID
function trclick(obj) {
	var mycell = obj.cells;
	if (trId != -1) {
		parent.editorFrame.document.getElementById(trId).cells[0].innerHTML = "&nbsp;";
	}
	var trIdlast = trId;
	trId = obj.id;
	if (trIdlast != "" && trId != "") {
		parent.editorFrame.document.getElementById(trIdlast).cells[0].innerHTML = "&nbsp;";
	}
	mycell[0].innerHTML = "&nbsp;<div id='triangle1'></div>";
	return trId;
}
/*2009-3-26点(+)增加一行的操作*/
var num = 0; //为了给新增加的TR标记不同的ID
function gettrId() {
	var trHTML = parent.editorFrame.document.createElement("tr");
	num++;
	trHTML.setAttribute("id", "ad" + num);
  //trHTML.setAttribute('onclick','trclick(this)');//目前IE不能用
	trHTML.onclick = function () {
		trclick(this);
	};
	trHTML.setAttribute("style", "COLOR:#ffffff; background-color:#ffffff");//用于火狐
	trHTML.style.cssText = "COLOR:#ffffff; background-color:#ffffff"; //用于IE
	for (var i = 1; i <= (parent.editorFrame.document.getElementById(trId).cells.length); i++) {
		if (i > 2) {
			var tdHTML = parent.editorFrame.document.createElement("td");
			tdHTML.setAttribute("style", "border:solid 1px #ffffff; background:#ffffff");//用于火狐
			tdHTML.style.cssText = "border:solid 1px #ffffff; background:#ffffff"; //用于IE 
			tdHTML.innerHTML = "<input type=text value=000>";
		} else {
			var tdHTML = parent.editorFrame.document.createElement("th");
			tdHTML.setAttribute("class", "c");
			tdHTML.setAttribute("className", "c");
			tdHTML.innerHTML = "&nbsp;";
		}
		trHTML.appendChild(tdHTML);
	}
	var trOption = parent.editorFrame.document.getElementById(trId);
	alert(trId);
	trOption.parentNode.insertBefore(trHTML, trOption.nextSibling);//关键的一行啊！
}
/*(-)减一行的操作*/
function deletHTML() {
	var tb = parent.editorFrame.document.getElementById("tableName");
	var tr = parent.editorFrame.document.getElementById(trId);
	var trIdnew = "";
	if ((tr.previousSibling == null) && (tr.nextSibling != null) && (tr.nextSibling.id != "")) {//如果上一行不存在，下一行存在且ID不为空（排除最后一行）
		trIdnew = tr.nextSibling.id;
	} else {
		if ((tr.previousSibling != null) && (tr.previousSibling.id != "") && (tr.nextSibling == null)) {
			trIdnew = tr.previousSibling.id;
		} else {
			if ((tr.previousSibling != null) && (tr.previousSibling.id != "") && (tr.nextSibling != null) && (tr.nextSibling.id != "")) {
				trIdnew = tr.nextSibling.id;
			} else {
				if ((tr.previousSibling != null) && (tr.previousSibling.id != "") && (tr.nextSibling != null) && (tr.nextSibling.id == "")) {
					trIdnew = tr.previousSibling.id;
				} else {
					if ((tr.previousSibling == null) && (tr.nextSibling == null)) {
						tb.deleteRow(tr.rowIndex);
						return false;   //上下不存在，删除该行不做任何操作
					}
				}
			}
		}
	}
    //var trIdnewl =tr.previousSibling.id; //获取该行的上一行的ID
    //var trIdnewn =tr.nextSibling.id; //获取该行的下一行ID  
	if (trIdnew != "") {
		parent.editorFrame.document.getElementById(trIdnew).cells[0].innerHTML = "&nbsp;<div id='triangle1'></div>";
	}
	tb.deleteRow(tr.rowIndex);
	trId = trIdnew;
}
/*得到插入数据*/
function insertNumber() {
	var addvalues = [];
	var valueID = 0;
	var troption = parent.editorFrame.document.getElementById("tableName").rows;
	var trlen = parent.editorFrame.document.getElementById("tableName").rows.length;//得到所有行数    
	for (var i = 0; i < trlen; i++) {//循环取新增加的行数，得到该行的ID，根据ID取每一行的input,文本框的值
		var values = "";
		var trhave = false;
		if (troption[i].id.substring(0, 2) == "ad") {//判断增加的行数
			var trone = parent.editorFrame.document.getElementById(troption[i].id).getElementsByTagName("input");//得到该行文本框对象数组
			for (var j = 0; j < trone.length; j++) {//得到该行文本框的值
				values += trone[j].value + ",";
				trhave = true;
			}
			if (trhave) { //如果值存在就加到数组里面       
				addvalues[valueID] = values.substring(0, values.length - 1);//这里有问题
				alert(valueID + "=" + addvalues[valueID]);
				valueID++;
			}
		}
	}
    //所有要插入的数据值的数组addvalues
	insert(addvalues);
}
function insert(addvalues) {
	alert(addvalues[0]);
	BaisWorkBean.insertNumber(addvalues, backInsert);
}
function backInsert(dataResult) {
	alert(dataResult);
}
/*得到删除数据，在做（-）操作的时候记录删除去的数据*/
/*得到修改数据，在做小图标移动操作时候，对小图标经过的数据，做记录，是否修改*/


//inserted or deleted 最终调用此函数据 by phanrider
function execResultFromSql(localsql,insertordelflag) {
	//置空
	setFootView(1, "");
	var oldtime = $time();
	
	BaisWorkBean.intOfInsertDelete(localsql, callinsertdelback);
	
	function callinsertdelback(intdata){
	var newtime = ($time() - oldtime) / 1000;
	var oracleTitle = "";
	var insertordel = "";
	var rows="";
	var tcell = intdata[0].length;
	
	if (insertordelflag == 1)	insertordel = "inserted";
	if (insertordelflag == 2)	insertordel = "deleted";
	
	if(tcell == 2 && intdata[0][0] == "ReddragonflyErrorFlag*") {
		errOracleMsg = intdata[0][1];
    	alert(intdata[0][1]);
	} else if(tcell == 2 && intdata[0][0] == "ReddragonflySuccessFlag*") {
		rows = intdata[0][1];
	} 
	
	breakRun("myTextarea");
	if ( errOracleMsg != "") {
	 	oracleTitle = errOracleMsg;
	 	//出错处理
	 	
	 	//重新设置为空
	 	errOracleMsg = "";
	} else  {
		if (insertordelflag == 3)	{
			if (getIfDDL('myTextarea')) {
				setCommit(false);
				setRollback(false);
			}
			oracleTitle = "Done in " + newtime + " seconds";
		} else { 
			oracleTitle = rows + " rows " + insertordel + " in " + newtime + " seconds";	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
		}
	}
	
	setFootView(9999, oracleTitle);
	parent.leftFrameList.restoreWindowListImg(parent.leftFrameList.getWindowTr());
	}
	
}

//executeFUN 最终调用此函数据 by phanrider
function execObject(localsql) {
	//置空
	setFootView(1, "");
	var oldtime = $time();
	
	BaisWorkBean.execObject(localsql, callexecobjectback);
	
	function callexecobjectback(intdata){
	var newtime = ($time() - oldtime) / 1000;
	var oracleTitle = "";
	var insertordel = "";
	var rows="";
	var tcell = intdata[0].length;
	
	
	if(tcell == 2 && intdata[0][0] == "ReddragonflyErrorFlag*") {
		errOracleMsg = intdata[0][1];
    	alert(intdata[0][1]);
	} else if(tcell == 2 && intdata[0][0] == "ReddragonflySuccessFlag*") {
		rows = intdata[0][1];
		//alert(intdata[0][1]);
	} 
	
	breakRun("myTextarea");
	if ( errOracleMsg != "") {
	 	oracleTitle = errOracleMsg;
	 	//出错处理
	 	
	 	//重新设置为空
	 	errOracleMsg = "";
	} else  {
		
			oracleTitle = rows ;	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
		
	}
	
	setFootView(9999, oracleTitle);
	parent.leftFrameList.restoreWindowListImg(parent.leftFrameList.getWindowTr());
	//左边树的选中节点重新加载，只有执行成功才重新加载
	if(tcell == 2 && intdata[0][0] == "ReddragonflySuccessFlag*") {
		if(parent.editorToolFrame.nodeType == "root") {
			parent.leftFrame.tree.getSelected().reload();
		} else  {
			selectedNote = parent.leftFrame.tree.getSelected();
			parent.leftFrame.tree.getSelected().getParent().reload();  //父节点刷新
			parent.leftFrame.tree.setSelected(selectedNote);
			parent.editorToolFrame.nodeType = "root";
		}
	}
	}
}

//executeOtherObj 最终调用此函数据 by phanrider
function execOtherObject(localsql) {
	
	
	BaisWorkBean.execObject(localsql, callexecOtherobjectback);
	
	function callexecOtherobjectback(intdata) {
	
		var insertordel = "";
		var rows="";
		var tcell = intdata[0].length;
		var errOracleMsg = "";
		
		if(tcell == 2 && intdata[0][0] == "ReddragonflyErrorFlag*") {
			errOracleMsg = intdata[0][1];
	    	alert(intdata[0][1]);
		} else if(tcell == 2 && intdata[0][0] == "ReddragonflySuccessFlag*") {
			rows = intdata[0][1];
		} 
		
		
		if ( errOracleMsg != "") {
		 	oracleTitle = errOracleMsg;
		 	//出错处理
		 	
		 	//重新设置为空
		 	errOracleMsg = "";
		} else  {
				oracleTitle = rows ;	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
		}
	}
}


//getUserName 
function getUserName(selectId) {
	var str = "";
	BaisWorkBean.getInstanceUser(callgetUserNameback);
	
	function callgetUserNameback(intdata){
		var rows="";
		var tcell = intdata[0].length;
		var str = "";
		var select = "";
		for ( i = 0; i < intdata.length; i++) {
			for ( j = 0; j < tcell; j++) {
				str += intdata[i][j] + "\n";
			}
		}
		$(selectId).set('html',str);
	}
}

//getTalbeAndView 
function getTalbeAndView(selectId) {
	var str = "";
	BaisWorkBean.getInstanceTableAndView(callgetUserNameback);
	
	function callgetUserNameback(intdata){
		var rows="";
		var tcell = intdata[0].length;
		var str = "";
		var select = "";
		for ( i = 0; i < intdata.length; i++) {
			for ( j = 0; j < tcell; j++) {
				str += intdata[i][j].toLowerCase() + "\n";
			}
		}
		$(selectId).set('html',str);
	}
}