//定义全局redImagePath
if (redImagePath == null)	var redImagePath='images/';
//每页显示条数
if (countPage == null)		var countPage = 20;
//多少页
if (pageNo == null)   	var pageNo = 1;
//上一次执行的FETCH SQL
if (fetchSql == null)	var fetchSql = 0;
//设置fetchFlag标志
if (fetchFlag == null)   	var fetchFlag = 0;
//设置事务处理标志 0 表示自动提交， 1 表示手工提交
if (transactionFlag == null)   	var transactionFlag = 0;

//设置执行标志
if (executeFlag == null)	var executeFlag = 0;

//设置锁定标志，默认为已锁定
if (lockFlag == null) 	var lockFlag = 1; 

//设置数据是否改变标志，默认为没有改变
if (postChangeFlag == null) 	var postChangeFlag = 0; 

//设置单个数据显示是否改变标志，默认为没有改变
if (singleRecordViewFlag == null) 	var singleRecordViewFlag = 0; 

//设置for update标志
if (forUpdateFlag == null) 	var forUpdateFlag = 0; 
	
//设置errOracleMsg 
if (errOracleMsg == null) 	var errOracleMsg = ""; 	

//设置queryByExample标志
if (queryByExampleFlag == null) var queryByExampleFlag = 0;

//设置编辑区全局选中SQL
if (baisworkSQL == null) var baisworkSQL="";

//结果集head的备份
if (resultbakHead == null )  var resultbakHead=""; 

//数据是否存在
if (globaldataFlag == null) var globaldataFlag = false;

//div全局临时变量
if (DestDiv == null ) var DestDiv = "";

//设置是否新创建的标志
if (globalIsNotCreate == null) var globalIsNotCreate = false;

//设置全局变量windowList的tr,pretr,windowType
if (globalTrType == null) var globalTrType = "";

if (globalTrRow == null) var globalTrRow = "";

if (globalSameWindowFlag == null) var globalSameWindowFlag = false;

//全局调用JSP页面地址 
if (warname == null) var warname = ""; //包名,换包名时需修改该值
if (sqlURL == null) var sqlURL = warname + "/login/editor.jsp";
if (funURL == null) var funURL = "../treeoperate/common/New.jsp";
if (funViewURL == null) var funViewURL = "../treeoperate/common/View.jsp";
if (funEditURL == null) var funEditURL = "../treeoperate/common/Edit.jsp";

//设置全局tree节点变量
if (nodeType == null) var nodeType = "root";

//全局用户对像
if (UserObject == null ) var UserObject;

//设置提示 --已改
function setFootView(errNo, ename) {
	if (errNo == 0) parent.editorFrame.$('footview').set('text','Initializing...');
	else if (errNo == 1) parent.editorFrame.$('footview').set('text','Executing...');
	else if (errNo == 2) parent.editorFrame.$('footview').set('text','Compiling...');
	else if (errNo == 3) parent.editorFrame.$('footview').set('text','Compiled succesfully');
	else if (errNo == 4) parent.editorFrame.$('footview').set('text','Compiled with errors');
	else if (errNo == 5) parent.editorFrame.$('footview').set('text','File is read-only');
	else if (errNo == 9999) parent.editorFrame.$('footview').set('text',ename);
}



//测试函数
function callBackHello(mydata) {
	alert("hello");
}


//创建新的textarea页面，即按下new按钮 --已改
//phanrider 2009-07-26 改成按下new按钮，不再调用此函数改成调用createNewSql， 留此函数是为了让别的函数调用
function createNew(textareaname) {
	parent.editorFrame.$(textareaname).set('text','');
}
                        
                
//页面重载  --不用改 正常                 	
function myreload(){
	parent.leftFrame.location.reload();
	parent.topFrame.location.reload();
}

//重新登录 --不用改 正常
function relogon() {
	window.open("./relogin.jsp", "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,width=320,height=260,top=360,left=500");
}
          	
//使执行按钮不可用，同时停止按钮可用 （按下execute按钮） --已改
function executebuttonpress() {

	var executeId = 'executeTd';
	var breakId = 'breakTd';
	var executeObject = parent.editorToolFrame.document.getElementById(executeId);
	var breakObject = parent.editorToolFrame.document.getElementById(breakId);
	
	if((!getExecuteFlag()) && (!breakObject.getEnabled()) ) {
		parent.editorToolFrame.setExecuteFlag(1);
		if (parent.leftFrameList.getWindowType() == "SQL") {
			changeNextSql("nextSql"); //右侧的向下按钮
		}
		executeObject.setEnabled(false); //执行按钮被按，不可再次被按
		breakObject.setEnabled(true);	//同时中止按钮可用
	}
	
}


//使停止按钮不可用，同时执行按钮可用 （按下break按钮） --已改
function breakbuttonpress() {

	var executeId = 'executeTd';
	var breakId = 'breakTd';
	var executeObject = parent.editorToolFrame.document.getElementById(executeId);
	var breakObject = parent.editorToolFrame.document.getElementById(breakId);
	
	
	if(getExecuteFlag() && breakObject.getEnabled()) {
		parent.editorToolFrame.setExecuteFlag(0);
		executeObject.setEnabled(true); //执行按钮恢复
		breakObject.setEnabled(false);	//同时中止按钮不可用
	}
}

//分页按钮是否可按
//true : 可按
//false : 不可按
function fetchnextbuttonpress() {
	var fetchnextId = 'fetchNextTd';
	var fetchnextObject = parent.editorFrame.document.getElementById(fetchnextId);
	
	if (fetchnextObject.getEnabled()) {
		return true;
	} else  {
		return false;
	}
}


//全部分页按钮是否可按
//true : 可按
//false : 不可按
function fetchlastbuttonpress() {
	var fetchLastTd = 'fetchLastTd';
	var fetchLastObject = parent.editorFrame.document.getElementById(fetchLastTd);
	
	if (fetchLastObject.getEnabled()) {
		return true;
	} else  {
		return false;
	}
}

//退出函数 --不用改 正常
function logout() {
	//alert("1");
	var name="webfx-tree-cookie-persistence";
	var ie = /msie/i.test(navigator.userAgent);
	if (ie) {
	if(confirm(' Are you sure logout? ')){
		//Cookie.remove(name);
		parent.location.replace("./quit.action");
		
	}
	} else {
		//Cookie.remove(name);
		//alert('test');
		parent.location.replace("./quit.action");
	}
}



//改变autorefresh图标 --已改
function changeAutorefresh(imagesName) {
	var imagesIcon = '../images/autorefresh_true.gif';
	var oldImagesIcon = '../images/autorefresh.gif';
	var oldImagesName = 'autorefresh.gif';
	var Flag = parent.editorFrame.$(imagesName).getProperty('src').test(oldImagesName);
	if (Flag) {
		parent.editorFrame.$(imagesName).setProperty('src',imagesIcon);
	} else {
		parent.editorFrame.$(imagesName).setProperty('src',oldImagesIcon);
	}
	//alert($('myTextarea').getSelected());
	//这一句加入测试用
	
	//parent.parent.leftFrameList.createNewSql("PRO", "myTextarea");
	//parent.editorFrame.location.replace("../treeoperate/common/View.jsp");
}


//改变save execute图标  --已改
function changeExecNoRun(runFlag,imagesName) {
	var execNoRunIcon = '../images/exec_norun.gif';
	var execRunningIcon = '../images/exec_running.gif';
	
	if (parent.leftFrameList.getWindowType() != "SQL") {
			execNoRunIcon = '../../images/exec_norun.gif';
			execRunningIcon = '../../images/exec_running.gif';
		}
	
	if (runFlag) {
		parent.editorFrame.$(imagesName).setProperty('src',execRunningIcon);
	} else {
		parent.editorFrame.$(imagesName).setProperty('src',execNoRunIcon);
	}
}
	

//改变lock 图标 --框架内元素，不用改，正常
function changeLock(imagesName) {
	
	var lockIcon = '../images/lock.gif';
	var unlockIcon = '../images/unlock.gif';
	if (lockFlag) {
		if (!setWarning(forUpdateFlag)) return;
		$(imagesName).setProperty('src',unlockIcon);
		setLockFlag(0);
		var insertRecordObject = document.getElementById('insertRecordTd');
			insertRecordObject.setEnabled(true);
		var deleteRecordObject = document.getElementById('deleteRecordTd');
			deleteRecordObject.setEnabled(true);
	} else {
		$(imagesName).setProperty('src',lockIcon);
		setLockFlag(1);
		var insertRecordObject = document.getElementById('insertRecordTd');
			insertRecordObject.setEnabled(false);
		var deleteRecordObject = document.getElementById('deleteRecordTd');
			deleteRecordObject.setEnabled(false);
			setPostChangeFlag(0);	//再次锁定，置数据改变标志为0
			changePostChange();		//使post_change按钮自动失效
			
	}
}


//插入数据 --已失效，需重写方法体
function insertRecord() {
	
	var insertRecordObject = document.getElementById('insertRecordTd');
	
	if (insertRecordObject.getEnabled() ) {
		setPostChangeFlag(1);
		changePostChange();
	}
}



//删除数据 --已失效，需重写方法体
function deleteRecord() {

	var deleteRecordObject = document.getElementById('deleteRecordTd');
	
	if (deleteRecordObject.getEnabled() ) {
		setPostChangeFlag(1);
		changePostChange();
	}

}



//提交改变数据 --暂未使用，需重写方法体
function postChangeRecord() {
	var postChangeObject = document.getElementById('postChangesTd');
	if (postChangeObject.getEnabled()) {  //判断本身按钮是否可用，如果可用才设置成不可用
		setPostChangeFlag(0);
		changePostChange();
	}
}


//改变post_change图标 --暂未使用，需重写方法体
function changePostChange() {
	var postChangeObject = parent.editorFrame.document.getElementById('postChangesTd');
	if (getPostChangeFlag()) { 
		postChangeObject.setEnabled(true);
	} else { 
		postChangeObject.setEnabled(false);	
	}
}


//改变previous sql按钮，使其可用  --已改
function changePreviousSql(tdID) {
	
	var nextSqlObject = parent.editorFrame.document.getElementById('nextSql');
	
	if (nextSqlObject.getEnabled() ) {
		var tdObject = parent.editorFrame.document.getElementById(tdID);
		tdObject.setEnabled(true);
	
		//执行实际操作
		//ExecuteCommand( 'myTextarea', 'Undo' );
		execSysCommand('myTextarea','undo');
	}
	
	
}


//改变querybyExample按钮
function changeQueryByExample(queryFlag) {
	var queryByExampleObject = parent.editorFrame.document.getElementById('queryByExampleTd');
	queryByExampleObject.setEnabled(queryFlag);
}

//改变querybyExample按钮
function changeSingleRecordView(queryFlag) {
	var singleRecordViewObject = parent.editorFrame.document.getElementById('singleRecordViewTd');
	singleRecordViewObject.setEnabled(queryFlag);
}

//改变querybyExample按钮
function changeExportResultResults(queryFlag) {
	var exportResultResultsObject = parent.editorFrame.document.getElementById('exportResultResultsTd');
	exportResultResultsObject.setEnabled(queryFlag);
}


//改变next sql按钮，使其可用 --已改
function changeNextSql(tdID) {
	
	var tdObject = parent.editorFrame.document.getElementById(tdID);
	tdObject.setEnabled(true);
	
}

//改变next sql 按钮，使其可用  --已改
function execNextSql() {
	var nextSqlObject = parent.editorFrame.document.getElementById('nextSql');
	if (nextSqlObject.getEnabled() ) {
		
		//执行实际操作
		//ExecuteCommand( 'myTextarea', 'Redo' );
		execSysCommand('myTextarea','redo');
	}
	
}


//改变next_record按钮，使其可用或不可用
function changeRecordView() {

	var nextRecordId = 'nextRecord';
	var previousRecordId = 'previousRecord';
	var nextRecordObject = parent.editorFrame.document.getElementById(nextRecordId);
	var previousRecordObject = parent.editorFrame.document.getElementById(previousRecordId);
	
	if(!getsingleRecordViewFlag() && globaldataFlag == true ) { //如果按钮外于未被按下状态，则做如下动作，本身按钮显示为按下
		
		nextRecordObject.setEnabled(true);  //使 nextrecord 按钮可用
		previousRecordObject.setEnabled(true);  //使 previousrecord 按钮可用
		setsingleRecordViewFlag(1); //使 singlerecord 按钮状态改为按下 singleRecordViewFlag = 1
		
		changeRecordViewInit();	//调用列表功能初始化函数
		
	} else {	//如果按钮是已按下状态，则做如下动作，本身按钮显示为未按下
		nextRecordObject.setEnabled(false);  //使 nextrecord 按钮不可用
		previousRecordObject.setEnabled(false); //使 previousrecord 按钮不可用
		setsingleRecordViewFlag(0); //使 singlerecord 按钮状态改未按下 singleRecordViewFlag = 0
		
		changeRecordViewRestore(); //调用列表功能恢复函数
	}
}

function changeRecordViewInit() {
	var nextRecordId = 'nextRecord';
	var previousRecordId = 'previousRecord';
	var nextRecordObject = parent.editorFrame.document.getElementById(nextRecordId);
	var previousRecordObject = parent.editorFrame.document.getElementById(previousRecordId);
	$('outResultDiv').set('style','display:none');
	
	setDivValueNull ('changeOutResultDiv');
	
	//setDivValueHtml ('changeOutResultDiv','asdfasdfas');
	//执行显示函数changeNextRecordView
	if( !mygrid.getSelectedId() ) { 
		mygrid.setSelectedRow(mygrid.getRowId(0));
	} 
	if (mygrid.getSelectedId() == 1) {	//如果为第一行，则向前按钮不可用
			previousRecordObject.setEnabled(false);
	}
	if (mygrid.getSelectedId() == mygrid.getRowsNum()) {  //如果为最后一行，则向后按钮不可用
		nextRecordObject.setEnabled(false);
	}
	if (mygrid.getSelectedId() < mygrid.getRowsNum()) { //如果不为最后一行，则向后按钮改为可用
			if (!nextRecordObject.getEnabled()) {
				nextRecordObject.setEnabled(true);
			}
		}
	if(mygrid.getSelectedId() == null) { //如果没有结果集，则向前、向后按钮都不可用
			previousRecordObject.setEnabled(false);
			nextRecordObject.setEnabled(false);
		}
	changeRecordShowHtml();
	
	$('changeOutResultDiv').set('style','width:100%;height:100%; background-color:white; display:block');
	//alert($('changeOutResultDiv').get('style'));
}

//按下NextRecord按钮执行的功能
function changeNextRecordView() {
	var nextRecordId = 'nextRecord';
	var previousRecordId = 'previousRecord';
	var nextRecordObject = document.getElementById(nextRecordId);
	var previousRecordObject = document.getElementById(previousRecordId);
	if (nextRecordObject.getEnabled()) {
		if( !mygrid.getSelectedId() ) { 
			mygrid.setSelectedRow(mygrid.getRowId(0));
			previousRecordObject.setEnabled(false);
		} else {
			if (!previousRecordObject.getEnabled()) {
				previousRecordObject.setEnabled(true);
			}
			mygrid.selectRow(mygrid.getSelectedId());
			//alert(mygrid.getSelectedId());
		}
		//如果已移动数组的最后一行，则自己不可再按
		if (mygrid.getSelectedId() == mygrid.getRowsNum()) {
			nextRecordObject.setEnabled(false);
		}
		changeRecordShowHtml();
	}
}

//按下PreviousRecord按钮执行的功能
function changePreviousRecordView() {
	var nextRecordId = 'nextRecord';
	var previousRecordId = 'previousRecord';
	var nextRecordObject = parent.editorFrame.document.getElementById(nextRecordId);
	var previousRecordObject = parent.editorFrame.document.getElementById(previousRecordId);
	if (previousRecordObject.getEnabled()) {
		if (mygrid.getSelectedId() >= 1) {
			mygrid.selectRow(mygrid.getSelectedId()-2);
		}
		if (mygrid.getSelectedId() == 1) { //如果为第一行，则向前按钮不可用
			previousRecordObject.setEnabled(false);
		}
		if (mygrid.getSelectedId() < mygrid.getRowsNum()) { //如果不为最后一行，则向后按钮改为可用
			if (!nextRecordObject.getEnabled()) {
				nextRecordObject.setEnabled(true);
			}
		}
		changeRecordShowHtml();
	}
}

//恢复按钮，使用为初始状态
function controlbuttonReset() {
	var nextRecordId = 'nextRecord';
	var previousRecordId = 'previousRecord';
	var nextRecordObject = parent.editorFrame.document.getElementById(nextRecordId);
	var previousRecordObject = parent.editorFrame.document.getElementById(previousRecordId);
	
	nextRecordObject.setEnabled(false);
	previousRecordObject.setEnabled(false);
	setFetchNext(false);
	setFetchLast(false);
}


function changeRecordViewRestore() {
	
	setDivValueNull ('changeOutResultDiv');
	$('changeOutResultDiv').set('style','display:none');
	$('outResultDiv').set('style','width:100%;height:100%; background-color:white; display:block');
}

//改变执行标志
function setExecuteFlag(exFlag) {
	executeFlag = exFlag;
}

//得到执行标志
function getExecuteFlag() {
	return executeFlag; 
}

//改变锁定标志
function setLockFlag(loFlag) {
	lockFlag = loFlag;
}

//得到锁定标志
function getLockFlag() {
	return lockFlag; 
}


//改变数据是否改变标志
function setPostChangeFlag(poFlag) {
	postChangeFlag = poFlag;
}

//得到数据是否改变标志
function getPostChangeFlag() {
	return postChangeFlag; 
}

function setsingleRecordViewFlag(siFlag) {
	singleRecordViewFlag = siFlag;
}


function getsingleRecordViewFlag() {
	return singleRecordViewFlag; 
}


//执行系统命令函数 --已改
function execSysCommand(textareaname, commandName) {
	var commandObj = parent.editorFrame.document.getElementById(textareaname);
	commandObj.focus();
	parent.editorFrame.document.execCommand(commandName);
}


//div值清空
function setDivValueNull (divName) {
	$(divName).set('text','');
}

//div赋值html
function setDivValueHtml (divName,htmlvalue) {
	$(divName).set('html',htmlvalue);
}

//div赋值Text
function setDivValueText (divName,textvalue) {
	$(divName).set('text',textvalue);
}

//监听F8/F9/F10/F12按键事件，正常
function mykeydown(myevent,textareaname){
		//alert(myevent.key);
		//$('myTextarea').focus();
		if(myevent.key == 'f8') {
			//'F8' 按下，和点击图标调用同一个事件处理函数
			parent.editorToolFrame.executeRun(textareaname);
		}
		if(myevent.key == 'f9') {
			//'F9' 按下，和点击图标调用同一个事件处理函数
			//parent.editorFrame.rollback();
			alert("coding...");
		}
		if(myevent.key == 'f10') {
			//'F10' 按下，和点击图标调用同一个事件处理函数
			parent.editorFrame.commit();
		}
		if(myevent.key == 'f12') {
			//'F12' 按下，和点击图标调用同一个事件处理函数
			parent.editorFrame.rollback();
		}
		

}

//execute执行按钮的function --正常
function executeRun(textareaname) {
	debugger
	tempSql = getTextareaContents(textareaname);
	alltmpSql = parent.editorFrame.$(textareaname).get('text');
	if(tempSql == "") {
		alert("Error: SQL can not null!");
	} else {
	
		var currWindoType = parent.leftFrameList.getWindowType();
	 
	 
		//调用真正的设置相应按钮状态函数
		executebuttonpress();
		//设置页脚注释 初始化...
		setFootView(0, "");
		//改变左下角执行图标状态
		parent.editorToolFrame.changeExecNoRun(1, "execIsRunButton");
		//by phanrider 2009-08-02 增加左边框架中的windowList的更改与恢复
		parent.leftFrameList.changeWindowListTitle(parent.leftFrameList.getWindowType(),parent.leftFrameList.getWindowTr(),alltmpSql);
		
		if (currWindoType == "SQL") {	
			parent.editorFrame.executeSQL(textareaname);
		} else if (currWindoType == "FUN" || currWindoType == "PRO" || currWindoType == "PAC"
					|| currWindoType == "PAB" || currWindoType == "TYP" || currWindoType == "TYB"
					|| currWindoType == "TRI" || currWindoType == "JAV" || currWindoType == "VIE"
					|| currWindoType == "VIM") {
			executeFUN(alltmpSql, currWindoType);
		}
	}
}


//SQL执行控制
function executeSQL(textareaname) {
	tempSql = getTextareaContents(textareaname);
	debugger
	if (getIfForupdate(textareaname) && getIfSelect(textareaname)) {
		//如果为for update，并且开始为select
		//设置commit、rollback按钮可用
		setCommit(true);
		setRollback(true);
		
		//提交给接口
		//getResultFromSql(tempSql); 有问题
		//alert("oK");
	} else if (getIfDelete(textareaname) || getIfInsertInto(textareaname) || !getIfSelect(textareaname)) {
		
		//提交给接口
		var deleteFlag = 0;
		if (getIfInsertInto(textareaname))  { 
			setCommit(true);
			setRollback(true);
			deleteFlag = 1; 
		} else if (getIfDelete(textareaname)) {
					setCommit(true);
					setRollback(true);
					deleteFlag = 2; 
				} else {
					deleteFlag = 3;
					//alert("h");
					
		}
		if (deleteFlag == 2) {
			var msg = "Are you sure you want to delete all records?";
			var msge = "Don't show this message again";
			if (!getIfwhere(textareaname)) {
				if (confirm(msg,"yes","no")) {
					execResultFromSql(tempSql,deleteFlag);
				} else {
					//处理一下点no时的按钮状态
					breakRun("myTextarea");
					setCommit(false);
					setRollback(false);
				}
			} else {
				execResultFromSql(tempSql,deleteFlag);
			}
		} else {
			execResultFromSql(tempSql,deleteFlag);
 		}
 		//alert("delete or insert");	
		} else {
		//其他sql语句
		//设置commit、rollback按钮不可用
		//setCommit(false);
		//setRollback(false);
		//否则为正常select
		
		setFetchNext(false);
		setFetchLast(false);
		//提交给接口
		getResultFromSql(tempSql);
		//alert(tempSql);
		//breakRun(textareaname);
		//changeExecute();
		
		}

}

//FUN执行控制
function executeFUN(sqlString,currWindoType) {
	execObject(sqlString);
	parent.editorFrame.$('objTitle').set('text',getStrFunctionName(sqlString,currWindoType));  //设置TAB页面的Title
}

//取得SQL中的函数名
function getStrFunctionName(sqlString,currWindoType) {
	var strat = "";
	var end = "";
	var tmpStr = "";
	var endStr = "";
	if (currWindoType == "FUN" || currWindoType == "PRO") {
		if (currWindoType == "FUN") { 
			tmpStr = "function ";
			endStr = " return";
		} else if (currWindoType == "PRO") {
			tmpStr = "procedure ";
			endStr = " is";
		}
	} else if (currWindoType == "PAC") {
		tmpStr = "package ";
		endStr = " is";
	} else if (currWindoType == "PAB") {
		tmpStr = "package body ";
		endStr = " is";
	} else if (currWindoType == "TYP") {
		tmpStr = "type ";
		endStr = " as";
	} else if (currWindoType == "TYB") {
		tmpStr = "type body ";
		endStr = " is";
	} else if (currWindoType == "TRI") {
		tmpStr = "trigger ";
		endStr = "\n";
	} else if (currWindoType == "JVA") {
		tmpStr = "java source named ";
		endStr = " as";
	} else if (currWindoType == "VIE") {
		tmpStr = "view ";
		endStr = " as";
	} else if (currWindoType == "VIM") {
		tmpStr = "view ";
		endStr = " as";
	}
	start = sqlString.indexOf(tmpStr);
	end = sqlString.indexOf(endStr);
	start = start + tmpStr.length;
	tempStr = sqlString.substring(start,end); //现在字符串类似于：iplsqldeveloper(pin varchar2)
	tempStr = tempStr.trim();
	if (currWindoType == "FUN" || currWindoType == "PRO") {
		return tempStr.substring(0,tempStr.indexOf("(")); //返回 iplsqldeveloper
	} else if (currWindoType == "PAC" || currWindoType == "PAB" || currWindoType == "TYP"
	 			|| currWindoType == "TYB" || currWindoType == "TRI" || currWindoType == "JVA"
	 			|| currWindoType == "VIE" || currWindoType == "VIM") {
		return tempStr;
	}
}

//break中止按钮的function --目前正常
function breakRun(textareaname) {
	//tempSql = getTextareaContents(textareaname);
	//调用真正的得到结果函数
	parent.editorToolFrame.breakbuttonpress();
	
	//var oracleTitle = "OK";	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
	//setFootView(9999, oracleTitle);
	changeExecNoRun(0, "execIsRunButton"); //恢复左下角执行图标状态
}


//得到获取编辑器中选取文字内容div  --已改
function getTextareaContents(EditorName) {
	var Objname = parent.editorFrame.document.getElementById(EditorName);
	var myText = "";
	var ie = /msie/i.test(navigator.userAgent);
	if (ie) {
		var range = Objname.document.selection.createRange();
		if (range.text.length > 0) {
			myText = range.text;
		} else {
            //如果未选中任何文字，则取编辑区所有内容
			myText = parent.editorFrame.$(EditorName).get('text');
		}
	} else {
       
       var range = getSelectedText();
       //	var  range = Objname.value.substr(Objname.selectionStart,Objname.selectionEnd-Objname.selectionStart);
		if (range.length > 0)  {
			myText = range;
		}
		else
		{
			//如果未选中任何文字，则取编辑区所有内容
			myText = parent.editorFrame.$(EditorName).get('text');
		}
	}
	//alert(myText);
	return myText;
}


//得到页面选择的元素 --已改
function getSelectedText() {
	if (parent.editorFrame.window.getSelection) {
		// This technique is the most likely to be standardized.
		// getSelection() returns a Selection object, which we do not document.
		return parent.editorFrame.window.getSelection().toString();
	} else if (parent.editorFrame.document.getSelection) {
		// This is an older, simpler technique that returns a string
		return parent.editorFrame.document.getSelection();
	} else if (parent.editorFrame.document.selection) {
		// This is the IE-specific technique.
		// We do not document the IE selection property or TextRange objects.
		return parent.editorFrame.document.selection.createRange().text;
	}
}


//对选中的文字进行注释
function addComment(EditorName) {
	var Objname = parent.editorFrame.document.getElementById(EditorName);
	var myText = "";
	var ie = /msie/i.test(navigator.userAgent);
	if (ie) {
		var range = parent.editorFrame.Objname.document.selection.createRange();
		if (range.text.length > 0) {
			myText = range.text;
		}
	} else {
           var range = getSelectedText();
    	   myText = range;   
	}
	
	//myText.replace();
	myText = "<font color='red'>/*" + myText + "*/</red>";
	alert(myText);
	parent.editorFrame.$(EditorName).set('html',myText);
}

//对页面进行重新设置
function resetEditor() {
	//得到footdiv
	var footcorpy = $('foot_outputDiv').clone();
	//删除t_controlDiv及其所有子div
	$('t_controlDiv').dispose();
	
	footcorpy.injectAfter('myTextToolbar');
}

//对显示结果区的工具条进行重新设置
function resetBaseWorkToolBar(baisWorkFlag) {
	globaldataFlag = baisWorkFlag;
	changeQueryByExample(baisWorkFlag);
    changeSingleRecordView(baisWorkFlag);
    changeExportResultResults(baisWorkFlag);
}

//对树操作的接口方法(该方法的实现已改成由phanrider提供的showTreeOperateOther方法实现,showTreeOperateOther已经去除，改用showCommon)
function showTreeOperate(type,name,field,operate,width,height){
   	alert("Coding...");
   	/*
   	selectedNote = tree.getSelected();
   	var url = "../treeoperate/"+type+"/";
   	if(field != '') url = url + field + "/";
   	url = url + operate + ".jsp?name="+name+"&type="+type+"&field="+field;
   	window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,width="+width+"height="+height+",top=360,left=380'");
	*/
}

//表单提交时进行逻辑判断的实用方法
function isEmpty(text){              //是否为空
     var isEmpty = true;
     for(var i = 0;i<text.length;i++){
        if(text.charAt(i) != ' '){
           isEmpty = false;
           break;
        }
     }
     return isEmpty;
}

//表单提交时进行逻辑判断的实用方法
function isGreatZeroInteger(str){     //是否是一个大于或等于0的整数
    if(isEmpty(str)) return false;
	var temp1 = true;
	var temp2 = '0123456789';
	for(var i=0;i<str.length;i++){
		  var c = str.charAt(i);
			if(temp2.indexOf(c)==-1) {
				temp1 = false;
				break;
			}else{
				if(c == '0' && i == 0 && str.length > 1){
					temp1 = false;
					break;
				}
		  }
	}
	return temp1;
}

//表单提交时进行逻辑判断的实用方法
function isGreatZeroFloat(str){       //是否是一个大于或等于0的实数
  if(isEmpty(str)) return false;
  var temp1 = true;
  var temp2 = '0123456789';
  for(var i = 0,j = 0;i < str.length;i++){
     var c = str.charAt(i);
     if(temp2.indexOf(c) != -1 || c == '.'){
        if(c == '.'){
           if(i == 0 || i == str.length-1){
              temp1 = false;
              break;
           }else{
              j = j + 1;
              if(j > 1){
                 temp1 = false;
                 break;
              }
           }
        } else if(c == '0'){
           if(i == 0){
              if(str.length > 1){
                 if(str.charAt(1) != '.'){
                   temp1 = false;
                   break;
                 }
              }
           }
        }
     } else {
        temp1 = false;
        break;
     }
  }
  return temp1;
}


//将展用新的展示方式， 对数据进行动态创建表格 
//phanrider 2009-05-21
function showDataHtml(rows,data) {
	
    mygrid = new dhtmlXGridObject('outResultDiv');
	
    mygrid.setImagePath("../imgs/");

	resultbakHead = data[0];    //得到head的备份
	
	tlow_flag_num = data.length; //表格展示的行数
	if (data.length > 21) {
			//tlow 表格展示的行数
			tlow = data.length - 1;  //去掉最后一个测试标志行
		} else {
			tlow = data.length ;  //无标志行可去
	}
	//alert(data.length);
    tcell = data[0].length; //表格展示的列数
    var strHeader = "";
    var strSort = "";
    for(var i = 0; i < tcell; i++) {
    	if(i == (tcell - 1)) {
    		strHeader = strHeader + data[0][i];
    		strSort = strSort + "str";
    	} else  {
    		strHeader = strHeader + data[0][i] + "," ;
    		strSort = strSort + "str" + ",";
    	}
    }
    
    
    strHeader = " ," + strHeader; //前面加一个空行
    strSort = "int," + strSort; //前面数字排序
    mygrid.setHeader(strHeader);
    mygrid.setColAlign("right");
	//mygrid.setColTypes("ro,ed"); 
	mygrid.setColSorting(strSort);
	

	var strHeaderWidth = "";
	var size = 100;
	for(var i = 0; i < tcell; i++) {
		var word = 8; //初定8个单词长度
	    for( var ii = 0; ii < tlow ;  ii++) {
	    	if ( data[ii][i].length > word ) word = data[ii][i].length;
	    }
	    
	    if (word > 8 && word <= 15 ) size = 120;
					else if  (word > 15 && word <= 20 ) size = 160;
					else if  (word > 20 && word <= 30 ) size = 240;
					else if  (word > 30 ) size = 300;
    	if(i == (tcell - 1)) strHeaderWidth = strHeaderWidth + size;
    	else strHeaderWidth = strHeaderWidth + size + "," ;
    }
    
    var i = tlow;
    var leftwidth = 20;
    if ( i < 99 ) leftwidth = 20;
    else if ( i < 999 && i >= 100 ) leftwidth = 30;
    else if ( i < 9999 && i >= 1000 ) leftwidth = 40;
    else if ( i < 99999 && i >= 10000 ) leftwidth = 50;
    else if ( i < 999999 && i >= 100000 ) leftwidth = 60;
                		
    strHeaderWidth = leftwidth + "," + strHeaderWidth; 
	
					
	mygrid.setInitWidths(strHeaderWidth); //定义各列的宽度
    
    if(tcell == 2 && data[0][0] == "ReddragonflyErrorFlag*") {
    	errOracleMsg = data[0][1];
    	alert(data[0][1]);
    } else {
    	
    	mygrid.init();  //进行初始化
    	globaldataFlag = true;
    
    for(var i = 1; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    		
    		var tmpS = changeHtml(data[i][j]);
    		
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    		
    		
    		if (i % 2 != 0) {
    			if (data[i][j] == "")  
    				if (j == (tcell - 1)) trstyle = trstyle + "#FFFFE5";  //空值 白淡黄色
    				else trstyle = trstyle + "#FFFFE5" + ",";
				else
					if (j == (tcell - 1)) trstyle = trstyle + "#FFFFFF";  //空值 白色
    				else trstyle = trstyle + "#FFFFFF" + ",";
				
			} else {
				if (data[i][j] == "")  
					if (j == (tcell - 1)) trstyle = trstyle + "#F2FFE5";  //空值 绿淡黄色
    				else trstyle = trstyle + "#F2FFE5" + ",";
				else 
					if (j == (tcell - 1)) trstyle = trstyle + "#E5FFE5";  //空值 绿色
    				else trstyle = trstyle + "#E5FFE5" + ",";
			}
    	}
    	//alert(strRow);
    
    	
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	
		mygrid.setColumnColor(trstyle);
		strRow = i + "," + strRow; 
    	mygrid.addRow(i,strRow); 
    	
    	if (getsingleRecordViewFlag()) {
    		changeRecordViewInit();
    	}
    	//mygrid.setRowColor(i,trstyle);
    	//mygrid.setColumnColor(trstyle);
    	}
    	
    	//最终得到数据，呈现后的其他按钮状态和改变
    	resetBaseWorkToolBar(true);
    	
    }
       	
    function addRow(){
   	var newId = (new Date()).valueOf()
   	mygrid.addRow(newId,"",mygrid.getRowsNum())
  	 mygrid.selectRow(mygrid.getRowIndex(newId),false,false,true);
  	}
  	
  	function removeRow(){
   	var selId = mygrid.getSelectedId()
   	mygrid.deleteRow(selId);
  	}
  
  	function doOnRowSelected(rowID,celInd){
  	alert("Selected row ID is "+rowID+"\nUser clicked cell with index "+celInd);
  }
    
}

function addDataHtml(rows,data) {
	//mygrid = new dhtmlXGridObject('outResultDiv');
	
	tlow_flag_num = data.length; //表格展示的行数
	
	if (data.length > 20) {
			//tlow 表格展示的行数
			tlow = data.length - 1;  //去掉最后一个测试标志行
		} else {
			tlow = data.length ;  //无标志行可去
	}
	
	tcell = data[0].length; //表格展示的列数
	for(var i = 0; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    	
    		var tmpS = changeHtml(data[i][j]);
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    		
    		
    		if (i % 2 == 0) {
    			if (data[i][j] == "")  
    				if (j == (tcell - 1)) trstyle = trstyle + "#FFFFE5";  //空值 白淡黄色
    				else trstyle = trstyle + "#FFFFE5" + ",";
				else
					if (j == (tcell - 1)) trstyle = trstyle + "#FFFFFF";  //空值 白色
    				else trstyle = trstyle + "#FFFFFF" + ",";
				
			} else {
				if (data[i][j] == "")  
					if (j == (tcell - 1)) trstyle = trstyle + "#F2FFE5";  //空值 绿淡黄色
    				else trstyle = trstyle + "#F2FFE5" + ",";
				else 
					if (j == (tcell - 1)) trstyle = trstyle + "#E5FFE5";  //空值 绿色
    				else trstyle = trstyle + "#E5FFE5" + ",";
			}
    	}
    	//alert(strRow);
    
    	
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	
		mygrid.setColumnColor(trstyle);
		var ii = i + 1  + (rows - 1) * countPage ;
		strRow = ii + "," + strRow; 
    	mygrid.addRow(ii,strRow,mygrid.getRowsNum()); 
    	//mygrid.setRowColor(i,trstyle);
    	//mygrid.setColumnColor(trstyle);
    	if (getsingleRecordViewFlag()) {
    		changeRecordViewInit();
    	}
    }
}


function addFullDataHtml(rows,data) {
	//mygrid = new dhtmlXGridObject('outResultDiv');
	
	tlow_flag_num = data.length; //表格展示的行数
		
	tlow = data.length ;  
	
	tcell = data[0].length; //表格展示的列数
	for(var i = 0; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    	
    		var tmpS = changeHtml(data[i][j]);
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    		
    		
    		if (i % 2 == 0) {
    			if (data[i][j] == "")  
    				if (j == (tcell - 1)) trstyle = trstyle + "#FFFFE5";  //空值 白淡黄色
    				else trstyle = trstyle + "#FFFFE5" + ",";
				else
					if (j == (tcell - 1)) trstyle = trstyle + "#FFFFFF";  //空值 白色
    				else trstyle = trstyle + "#FFFFFF" + ",";
				
			} else {
				if (data[i][j] == "")  
					if (j == (tcell - 1)) trstyle = trstyle + "#F2FFE5";  //空值 绿淡黄色
    				else trstyle = trstyle + "#F2FFE5" + ",";
				else 
					if (j == (tcell - 1)) trstyle = trstyle + "#E5FFE5";  //空值 绿色
    				else trstyle = trstyle + "#E5FFE5" + ",";
			}
    	}
    	//alert(strRow);
    
    	
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	
		mygrid.setColumnColor(trstyle);
		var ii = i + 1  + (rows - 1) * countPage ;
		strRow = ii + "," + strRow; 

		mygrid.addRow(ii,strRow,mygrid.getRowsNum()); 
		
		if (getsingleRecordViewFlag()) {
    		changeRecordViewInit();
    	}
    	//mygrid.setRowColor(i,trstyle);
    	//mygrid.setColumnColor(trstyle);
    }
}


//改变记录显示的grid
function changeRecordShowHtml() {
	
	var tmprows = mygrid.getSelectedId();
	if (mygrid.getSelectedId() == null) tmprows = "0";
	changemygrid = new dhtmlXGridObject('changeOutResultDiv');
	changemygrid.setImagePath("../imgs/");
    changemygrid.setHeader(" ,Row " + tmprows + ",Fields");
    changemygrid.setInitWidths("20,180,300"); //定义各列的宽度
    changemygrid.setColTypes("ro,ro,ed"); 
    changemygrid.setColSorting("str,str,str");
    changemygrid.init();
    //得到列的总数
    var mygridlength = mygrid.getColumnCount();
    
    for (i = 1; i < mygridlength; i++) {
    	var strrow = "";
    	var tmpcol = "";
    	var trstyle = "";
	
		if (mygrid.getSelectedId() == null) {	//为空，则特殊处理
			tmpcol = " ";
		} else {
    		tmpcol = mygrid.cells(mygrid.getSelectedId(),i).getValue();
    	}
    	strRow = " ," + resultbakHead[i-1] + ",";
    	
    	var tmpS = changeHtml(tmpcol);
    		
    	strRow = strRow + tmpS ;
    	
    	if ((mygrid.getSelectedId()-1) % 2 == 0) {
    			if (tmpcol == "")  {
    				trstyle = "#FFFFE5";  //空值 白淡黄色
    			}
			}
		if (mygrid.getSelectedId() % 2 == 0) {
				if (tmpcol == "") { 
					trstyle = "#F2FFE5";  //空值 绿淡黄色
    			}
			}
		trstyle = "#D4D0C8,#D4D0C8," + trstyle ;
		changemygrid.setColumnColor(trstyle);
		changemygrid.addRow(i-1,strRow);     	
		//alert(strRow);
    }
}



//设置分页按钮状态 True 或者 False
function setFetchNext(flag) {
	var fetchnextId = 'fetchNextTd';
	var fetchnextObject = parent.editorFrame.document.getElementById(fetchnextId);
	
	fetchnextObject.setEnabled(flag);
}

//设置全部分页按钮状态 True 或者 False
function setFetchLast(flag) {
	var fetchLastId = 'fetchLastTd';
	var fetchLastObject = parent.editorFrame.document.getElementById(fetchLastId);
	
	fetchLastObject.setEnabled(flag);
}

//按钮Fetch next page按下执行的函数
function getFYSql() {
	if ( fetchnextbuttonpress () ) { 
		//改变相互作用的按钮状态	
		executebuttonpress();
		//设置页脚注释 正在执行...
		setFootView(1, "");
		//改变左下角执行图标状态
		changeExecNoRun(1, "execIsRunButton");
		//同时更改左边工具条的状态
		parent.leftFrameList.changeWindowListTitle(parent.leftFrameList.getWindowType(),parent.leftFrameList.getWindowTr(),parent.editorFrame.$('myTextarea').get('text'));
		//调用真正的分页执行方法
		getFYSql_run();
	}
}


//按钮Fetch last page按下执行的函数
function getFYQSql() {
	if( fetchlastbuttonpress() ) {
		//改变相互作用的按钮状态	
		executebuttonpress();
		//设置页脚注释 正在执行...
		setFootView(1, "");
		//改变左下角执行图标状态
		changeExecNoRun(1, "execIsRunButton");
		//同时更改左边工具条的状态
		parent.leftFrameList.changeWindowListTitle(parent.leftFrameList.getWindowType(),parent.leftFrameList.getWindowTr(),parent.editorFrame.$('myTextarea').get('text'));
		//调用真正的全部分页执行方法
		getFYQSql_run();
	}
}
   	

//是否显示for update时的警告信息   	
function setWarning(flag) {
	var lockButtonObject = parent.editorFrame.document.getElementById('lockButtonTd');
	if(flag == 0) {
		
		errMsg="These query results are not updateable.\nInclude the ROWID to get updateable results.";
		alert(errMsg);
		
		//lockButtonObject.setToggle(false);	
		return false;
	} else {
		
		return true;
	}
}   	

//得到sql语句是否为for update
//返回值： true or false
function getIfForupdate(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var lockButtonObject = parent.editorFrame.document.getElementById('lockButtonTd');
	if (tempStr.trim().test(" for update$","i")) {
		//设置forupdate标志为1
		forUpdateFlag = 1;
		//如果为真，设置为固定按钮
		lockButtonObject.setToggle(true);
		return true;
	} else {
		//设置forupdate标志为0
		forUpdateFlag = 0;
		//否则，设置为非固定按钮
		lockButtonObject.setToggle(false);
		return false;
	}
}

//得到sql语句是否为select开头
//返回值： true or false
function getIfSelect(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var selectFlag = false;
	if (tempStr.trim().test("^select ","i")) {
		selectFlag = true;
	} else {
		selectFlag = false;
	}
	return selectFlag;
}

//得到sql语句是否为delete开头
//返回值： true or false
function getIfDelete(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var Flag = false;
	if (tempStr.trim().test("^delete ","i")) {
		Flag = true;
	} else {
		Flag = false;
	}
	return Flag;
}

//得到sql语句是否为insert into开头或者update开头
//返回值： true or false
function getIfInsertInto(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var Flag = false;
	if (tempStr.trim().test("^insert into ","i") || 
		tempStr.trim().test("^update ","i") ) {
		Flag = true;
	} else {
		Flag = false;
	}
	return Flag;
}


//得到sql语句是否为alter、drop、create开头
//返回值： true or false
function getIfDDL(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var Flag = false;
	if (tempStr.trim().test("^alter ","i") || 
		tempStr.trim().test("^drop ","i") ||
		tempStr.trim().test("^create ","i") ) {
		Flag = true;
	} else {
		Flag = false;
	}
	return Flag;
}

//设置commit按钮的可用或不可用
//true: 可用
//false: 不可用
function setCommit(commitFlag) {
	var commitId = 'commitTd';
	var commitObject = parent.editorToolFrame.document.getElementById(commitId);
	
	commitObject.setEnabled(commitFlag);
	
}

//设置rollback按钮的可用或不可用
//true: 可用
//false: 不可用
function setRollback(rollbackFlag) {
	var rollbackId = 'rollbackTd';
	var rollbackObject = parent.editorToolFrame.document.getElementById(rollbackId);
	
	rollbackObject.setEnabled(rollbackFlag);
	
}

function setNoresult(divname,str) {
	setDivValueNull(divname);
	htmlStr = "<table style='height: 100%; width: 100%;'><tr><td align='center'>" + str + "</td></tr></table>";
	$(divname).set('html',htmlStr);
}

function commit() {
	var commitID = 'commitTd';
	var commitObject = parent.editorToolFrame.document.getElementById(commitID);

	if(commitObject.getEnabled()) {
		BaisWorkBean.setDbCommit();
		setCommit(false);
		setRollback(false);
	} else {
		//alert("commit");
	}
}

function rollback() {
	var rollbackID = 'rollbackTd';
	var rollbackObject = parent.editorToolFrame.document.getElementById(rollbackID);
	
	if(rollbackObject.getEnabled()) {
		BaisWorkBean.setDbRollback();
		setCommit(false);
		setRollback(false);
	} else {
		//alert("rollback");
	}
}

//判断是否存在where条件字句
function getIfwhere(textareaname) {
	var tempStr =  getTextareaContents(textareaname);
	var Flag = false;
	if (tempStr.trim().test(" where ","i")) {
		Flag = true;
	} else {
		Flag = false;
	}
	return Flag;
}


function queryByExample() {
	var queryByExampleID = 'queryByExampleTd';
	var clearRecordID = 'clearRecordTd';
	var queryByExampleObject = parent.editorFrame.document.getElementById(queryByExampleID);
	var clearRecordObject = parent.editorFrame.document.getElementById(clearRecordID);
	
	if (!clearRecordObject.getEnabled() && queryByExampleFlag == 0 && queryByExampleObject.getEnabled())  {
		clearRecordObject.setEnabled(true);
		queryByExampleFlag = 1;
	} else  {
		if (queryByExampleFlag == 1 && clearRecordObject.getEnabled()) {
			clearRecordObject.setEnabled(false);
			queryByExampleFlag = 0;
		}
	}
}


function clearRecord() {

}



function showBaisworkMenu(divName,menuname,evt) {
	
	var isie=document.all;
    var rightnum=isie?event.button:evt.which;
    if ((isie && rightnum==2) || (!isie && rightnum==3)){        //IE or firefox
    	//获取鼠标位置
		var x;
		var y;
		if(evt.pageX || evt.pageY){
			x = evt.pageX;
		    y = evt.pageY;
		} else {
		    x = evt.clientX + document.body.scrollLeft - document.body.clientLeft;
		    y = evt.clientY + document.body.scrollTop - document.body.clientTop;
		}
	if (divName == 'myTextarea') baisworkSQL = getTextareaContents(divName);
	document.getElementById('BaisworkMenu').style.display = "none";
    document.getElementById('outResultMenu').style.display = "none";
	evt = evt || window.event;
	e = document.getElementById(menuname);
	e.style.top = evt.clientY;
	e.style.left = evt.clientX;
	e.style.display = "inline";
	}
}


function hiddenBaisworkMenu(evt) {
	var isie=document.all;
    var rightnum=isie?event.button:evt.which;
    if ((isie && rightnum==2) || (!isie && rightnum==3)){        //IE or firefox
    	return true;
    } else {
    	parent.editorFrame.document.getElementById('BaisworkMenu').style.display = "none";
    	parent.editorFrame.document.getElementById('outResultMenu').style.display = "none";
    }
}


//创建编辑区的右键菜单
function createBaisWorkMenu(divname) {
	var stemp ='<a onclick="execExplainPlan(event);" ><font color="#808080">Explain Plan</font></a>' +
	'<a onclick="execTest(event)" ><font color="#808080">Test</font></a>' +
	'<ul></ul>' +
	'<a onclick="execPaste(\'myTextarea\',\'paste\',event);" href="#">Paste&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+V</a>' +
	'<ul></ul>' +
	'<a onclick="execWorkProperties(\'myTextarea\',event)" href="#">Properties</a>' +
	'<a onclick="execWorkDescribe(\'myTextarea\',event)" href="#">Describe</a>' +
	'<ul></ul>' +
	'<a onclick="execWorkView(\'myTextarea\',event)" href="#">View</a>' +
	'<a onclick="execWorkEdit(event)" ><font color="#808080">Edit</font></a>' +
	'<a onclick="execWorkRename(\'myTextarea\',event)" href="#">Rename</a>' +
	'<a onclick="execWorkDrop(\'myTextarea\',event)" href="#">Drop</a>' +
	'<ul></ul>' +
	'<a onclick="execQueryData(\'myTextarea\',event);" href="#">Query data</a>' +
	'<a onclick="execEditDatat(\'myTextarea\',event)" ><font color="#808080">Edit data</font></a>' +
	'<ul></ul>' +
	'<a onclick="execClear(\'myTextarea\',event);" href="#">Clear</a>';
	
	setDivValueHtml(divname,stemp);
}

//工作编辑区右键Explain Plan命令执行函数
function execExplainPlan(e) {
	hiddenBaisworkMenu(e);
	//alert(baisworkSQL);
}

//工作编辑区右键Test命令执行函数
function execTest(e) {
	hiddenBaisworkMenu(e);
}

//工作编辑区右键Paste命令执行函数
function execPaste(textareaname,command, e) {
	hiddenBaisworkMenu(e);
	execSysCommand(textareaname, command);
}

//工作编辑区右键Properties命令执行函数
function execWorkProperties(e) {
	hiddenBaisworkMenu(e);
	menuShowObjPD('Properties', '1', '500px', '312px');
}

//工作编辑区右键Describe命令执行函数
function execWorkDescribe(e) {
	hiddenBaisworkMenu(e);
	menuShowObjPD('Describe', '1', '500px', '312px');
}

//工作编辑区右键View命令执行函数
function execWorkView(e) {
	hiddenBaisworkMenu(e);
	menuShowObjPD('View', '2', '500px','312px');
}

//工作编辑区右键Edit命令执行函数
function execWorkEdit(e) {
	hiddenBaisworkMenu(e);
}

//工作编辑区右键Rename命令执行函数
function execWorkRename(e) {
	hiddenBaisworkMenu(e);
	menuShowObjPD('Rename', '1', '500px', '140px');
}

//工作编辑区右键Drop命令执行函数
function execWorkDrop(e) {
	hiddenBaisworkMenu(e);
	menuShowObjPD('Drop', '1', '500px', '120px');
}


//工作编辑区右键Query data命令执行函数
function execQueryData(textareaname, e) {
	var texttmp = "select * from " + baisworkSQL + " t";
	hiddenBaisworkMenu(e);
	parent.leftFrameList.createNewSql('SQL','myTextarea');
	//createNew(textareaname);
	setDivValueText (textareaname,texttmp);
	parent.editorToolFrame.executeRun(textareaname);
}

//工作编辑区右键Edit data命令执行函数
function execEditData(textareaname, e) {
	hiddenBaisworkMenu(e);
}


//工作编辑区右键Clear命令执行函数
function execClear(textareaname, e) {
	hiddenBaisworkMenu(e);
	createNew(textareaname);
}

//str: 属性或描述字符串
//flag: 1 表示显示属性或描述 
//		  2 表示显示视图（view）
//从右键菜单中显示对象的 Properties 和 Describe
function menuShowObjPD (str, flag, strTopX, strTopY) {
	var tmpData = parent.topFrame.UserObject;
	for (i=0; i<tmpData.length; i++) {
		if (baisworkSQL.toUpperCase().trim() == tmpData[i][0]) {
			if (flag == '1') showCommonFormRightMenu(tmpData[i][1], tmpData[i][0],'',str, strTopX, strTopY);
			if (flag == '2') showViewObject(tmpData[i][1], tmpData[i][0],'1',str);  // 1 表示是从右键菜单调用，不需要刷新左边菜单
			i = tmpData.length;
		}
	}
}

//从右键菜单中显示对象的View 和 Edit
//用已有的 showViewObject



//创建结果输出区的右键菜单
function createOutResultMenu(divname) {
	var stemp ='<a onclick="execClearAll(\'myTextarea\',event);" href="#">Clear</a>' +
	'<a onclick="execClearResults(event);" href="#">Clear Results</a>' +
	'<a onclick="execExportResults(\'myTextarea\',event);" ><font color="#808080">Export Results</font></a>' +  //未实现功能的菜单先恢化
	'<ul></ul>' +
	'<a onclick="execFetchNextPage(event);" href="#">Fetch Next Page</a>' +
	'<a onclick="execFetchLastPage(event);" href="#">Fetch Last Page</a>' +
	'<ul></ul>' +
	'<a onclick="execCopyResults(event);" ><font color="#808080">Copy&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+C</font></a>' + //未实现功能的菜单先恢化
	'<a onclick="execExportResults(event);" ><font color="#808080">Copy to Excel</font></a>'; //未实现功能的菜单先恢化
	
	setDivValueHtml(divname,stemp);
}

//工作结果输出区右键clear命令执行函数
function execClearAll(textareaname, e) {
	execClear(textareaname, e);
	execClearResults(e);
}


//工作结果输出区右键clear result命令执行函数
function execClearResults(e) {
	hiddenBaisworkMenu(e);
	setDivValueText('outResultDiv','');
	setDivValueText('changeOutResultDiv','');
	//按钮恢复成初始状态
	controlbuttonReset();
	//状态栏初始为空
	setFootView(9999, '　');
	//清除结果集后，需重新设置工作结果区的工具条状态
	resetBaseWorkToolBar(false);
}

//工作结果输出区右键Export Results命令执行函数
function execExportResults(textareaname, e) {
	hiddenBaisworkMenu(e);
}

//工作结果输出区右键Fetch Next Page命令执行函数
function execFetchNextPage(e) {
	hiddenBaisworkMenu(e);
	//直接调用fetch next page按钮执行的函数
	getFYSql();
}

//工作结果输出区右键Fetch Last Page命令执行函数
function execFetchLastPage(e) {
	hiddenBaisworkMenu(e);
	//直接调用fetch last page按钮执行的函数
	getFYQSql();
}

//工作结果输出区右键Copy命令执行函数
function execCopyResults(e) {
	hiddenBaisworkMenu(e);
}

//工作结果输出区右键Export Results命令执行函数
function execExportResults(e) {
	hiddenBaisworkMenu(e);
}

//Properties phanrider add by 2009-06-17
function showDataHtmlP(data) {
	
    mygrid = new dhtmlXGridObject('resultdiv');
	
    mygrid.setImagePath("../../imgs/");

	//alert(data.length);
	
	tlow_flag_num = data.length; //表格展示的行数
	
	tlow = data.length ;  //无标志行可去
	
	//alert(data.length);
    tcell = data[0].length; //表格展示的列数
    var strHeader = "";
    var strSort = "";
    for(var i = 0; i < tcell; i++) {
    	if(i == (tcell - 1)) {
    		strHeader = strHeader + data[0][i];
    		strSort = strSort + "str";
    	} else  {
    		strHeader = strHeader + data[0][i] + "," ;
    		strSort = strSort + "str" + ",";
    	}
    }
    
    	
    strHeader = " ," + strHeader; //前面加一个空行
    strSort = "int," + strSort; //前面数字排序
    mygrid.setHeader(strHeader);
    mygrid.setColAlign("right");
	//mygrid.setColTypes("ro,ed"); 
	mygrid.setColSorting(strSort);
	

	var strHeaderWidth = "";
	var size = 150;
	for(var i = 0; i < tcell; i++) {
		var word = 8; //初定8个单词长度
	    for( var ii = 0; ii < tlow ;  ii++) {
	    	if ( data[ii][i].length > word ) word = data[ii][i].length;
	    }
	    
	    if (word > 8 && word <= 15 ) size = 190;
					else if  (word > 15 && word <= 20 ) size = 220;
					else if  (word > 20 && word <= 30 ) size = 260;
					else if  (word > 30 ) size = 320;
    	if(i == (tcell - 1)) strHeaderWidth = strHeaderWidth + size;
    	else strHeaderWidth = strHeaderWidth + size + "," ;
    }
    
    var i = tlow;
    var leftwidth = 20;
    if ( i < 99 ) leftwidth = 30;
    else if ( i < 999 && i >= 100 ) leftwidth = 40;
    else if ( i < 9999 && i >= 1000 ) leftwidth = 50;
    else if ( i < 99999 && i >= 10000 ) leftwidth = 60;
    else if ( i < 999999 && i >= 100000 ) leftwidth = 70;
                		
    strHeaderWidth = leftwidth + "," + strHeaderWidth; 
	
					
	mygrid.setInitWidths(strHeaderWidth); //定义各列的宽度
    
    if(tcell == 2 && data[0][0] == "ReddragonflyErrorFlag*") {
    	errOracleMsg = data[0][1];
    	alert(data[0][1]);
    } else {
    	mygrid.enableAutoHeigth(true);
    	
    	mygrid.init();  //进行初始化

    for(var i = 1; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    		
    		var tmpS = changeHtml(data[i][j]);
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    	}
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	
		mygrid.setColumnColor(trstyle);
		strRow = i + "," + strRow; 
    	mygrid.addRow(i,strRow); 
    	}
    }
       	
}

//Describe phanrider add by 2009-06-17
function showDataHtmlD(data) {
	
    mygrid = new dhtmlXGridObject('resultdiv');
	
    mygrid.setImagePath("../../imgs/");

	//alert(data.length);
	
	tlow_flag_num = data.length; //表格展示的行数
	tlow = data.length ;  //无标志行可去

	//alert(data.length);
    tcell = data[0].length; //表格展示的列数
    var strHeader = "";
    var strSort = "";
    for(var i = 0; i < tcell; i++) {
    	if(i == (tcell - 1)) {
    		strHeader = strHeader + data[0][i];
    		strSort = strSort + "str";
    	} else  {
    		strHeader = strHeader + data[0][i] + "," ;
    		strSort = strSort + "str" + ",";
    	}
    }
    
    	
    strHeader = " ," + strHeader; //前面加一个空行
    strSort = "int," + strSort; //前面数字排序
    mygrid.setHeader(strHeader);
    mygrid.setColAlign("right");
	//mygrid.setColTypes("ro,ed"); 
    
	mygrid.setColSorting(strSort);
		

	var strHeaderWidth = "";
	var size = 50;
	for(var i = 0; i < tcell; i++) {
		var word = 8; //初定8个单词长度
	    for( var ii = 0; ii < tlow ;  ii++) {
	    	if ( data[ii][i].length > word ) word = data[ii][i].length;
	    }
	    
	    if (word > 8 && word <= 15 ) size = 115;
					else if  (word > 15 && word <= 20 ) size = 150;
					else if  (word > 20 ) size = 200;
    	if(i == (tcell - 1)) strHeaderWidth = strHeaderWidth + size;
    	else strHeaderWidth = strHeaderWidth + size + "," ;
    }
    
    var i = tlow;
    var leftwidth = 20;
    if ( i < 99 ) leftwidth = 20;
    else if ( i < 999 && i >= 100 ) leftwidth = 30;
    else if ( i < 9999 && i >= 1000 ) leftwidth = 40;
    else if ( i < 99999 && i >= 10000 ) leftwidth = 50;
    else if ( i < 999999 && i >= 100000 ) leftwidth = 60;
                		
    strHeaderWidth = leftwidth + "," + strHeaderWidth; 
	
					
	mygrid.setInitWidths(strHeaderWidth); //定义各列的宽度
    
    if(tcell == 2 && data[0][0] == "ReddragonflyErrorFlag*") {
    	errOracleMsg = data[0][1];
    	alert(data[0][1]);
    } else {
    	
    	//mygrid.enableAutoHeigth(true,380);
    	
    	mygrid.init();  //进行初始化
    	
    for(var i = 1; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    	
    		var tmpS = changeHtml(data[i][j]);
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    	}
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	mygrid.setColumnColor(trstyle);
		strRow = i + "," + strRow; 
    	mygrid.addRow(i,strRow); 
    	}
    }
}

//phanrider add by 2011-04-12
//每个DIV调用的回调函数，会带上本地DIV名称调用真正显示函数 showDataHtmlReal
function showDataHtmlKeys(data) {
	var keydivname = 'resultdiv_keys';
	showDataHtmlReal(data, keydivname);
}

function showDataHtmlChecks(data) {
	var keydivname = 'resultdiv_checks';
	showDataHtmlReal(data, keydivname);
}

function showDataHtmlIndexs(data) {
	var keydivname = 'resultdiv_indexs';
	showDataHtmlReal(data, keydivname);
}

function showDataHtmlPrivileges(data) {
	var keydivname = 'resultdiv_privileges';
	showDataHtmlReal(data, keydivname);
}

//真正调用本地及服务器参数的回调函数
//data： 服务器返回的数据
//divname：本地参数，各个DIV名称或ID
function showDataHtmlReal(data, divname)
{
	mygrid = new dhtmlXGridObject(divname);
	mygrid.setImagePath("../../imgs/");
	tlow_flag_num = data.length; //表格展示的行数
	tlow = data.length ;  //无标志行可去

	//alert(data.length);
    tcell = data[0].length; //表格展示的列数
    var strHeader = "";
    var strSort = "";
    for(var i = 0; i < tcell; i++) {
    	if(i == (tcell - 1)) {
    		strHeader = strHeader + data[0][i];
    		strSort = strSort + "str";
    	} else  {
    		strHeader = strHeader + data[0][i] + "," ;
    		strSort = strSort + "str" + ",";
    	}
    }
    
    	
    strHeader = " ," + strHeader; //前面加一个空行
    strSort = "int," + strSort; //前面数字排序
    mygrid.setHeader(strHeader);
    mygrid.setColAlign("right");
	//mygrid.setColTypes("ro,ed"); 
    
	mygrid.setColSorting(strSort);
		

	var strHeaderWidth = "";
	var size = 50;
	for(var i = 0; i < tcell; i++) {
		var word = 8; //初定8个单词长度
	    for( var ii = 0; ii < tlow ;  ii++) {
	    	if ( data[ii][i].length > word ) word = data[ii][i].length;
	    }
	    
	    if (word > 8 && word <= 15 ) size = 115;
					else if  (word > 15 && word <= 20 ) size = 150;
					else if  (word > 20 ) size = 200;
    	if(i == (tcell - 1)) strHeaderWidth = strHeaderWidth + size;
    	else strHeaderWidth = strHeaderWidth + size + "," ;
    }
    
    var i = tlow;
    var leftwidth = 20;
    if ( i < 99 ) leftwidth = 20;
    else if ( i < 999 && i >= 100 ) leftwidth = 30;
    else if ( i < 9999 && i >= 1000 ) leftwidth = 40;
    else if ( i < 99999 && i >= 10000 ) leftwidth = 50;
    else if ( i < 999999 && i >= 100000 ) leftwidth = 60;
                		
    strHeaderWidth = leftwidth + "," + strHeaderWidth; 
	
					
	mygrid.setInitWidths(strHeaderWidth); //定义各列的宽度
    
    if(tcell == 2 && data[0][0] == "ReddragonflyErrorFlag*") {
    	errOracleMsg = data[0][1];
    	alert(data[0][1]);
    } else {
    	
    	//mygrid.enableAutoHeigth(true,380);
    	
    	mygrid.init();  //进行初始化
    	
    for(var i = 1; i < tlow; i++) {
    	var strRow = "";
    	var trstyle = "";
    	for (var j = 0; j < tcell; j++) {
    	
    		var tmpS = changeHtml(data[i][j]);
    		if(j == (tcell - 1)) strRow = strRow + tmpS;
    		else strRow = strRow + tmpS + "," ;
    		
    		//if(j == (tcell - 1)) strRow = strRow + data[i][j];
    		//else strRow = strRow + data[i][j] + "," ;
    	}
    	trstyle="#D4D0C8"+ "," + trstyle; 
    	mygrid.setColumnColor(trstyle);
		strRow = i + "," + strRow; 
    	mygrid.addRow(i,strRow); 
    	}
    }
}


function showCommon(type,name,field,operate,width,height){
	selectedNote = tree.getSelected();
    var url = "../treeoperate/common/";
	//if(field != '') url = url + field + "/";
    url = url + operate + ".jsp?name="+name+"&type="+type+"&field="+field;
    //window.showModalDialog(url,"","dialogWidth:"+width+";dialogHeight:"+height+";center:yes;resizable:yes");
    window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes,copyhistory=no,width="+width+",height="+height+",top=360,left=500");
}

function showCommonFormRightMenu(type,name,field,operate,width,height){
	var url = "../treeoperate/common/";
	url = url + operate + ".jsp?name="+name+"&type="+type+"&field="+field;
    window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes,copyhistory=no,width="+width+",height="+height+",top=360,left=500");
}

function showRoot(type,name,field,operate,width,height){
	selectedNote = tree.getSelected();
	field = field.replace(/ /gi,"_");  //转换" "空格为"_"下划线
    var url = "../treeoperate/root/" + field + "/";
	//if(field != '') url = url + field + "/";
    url = url + operate + ".jsp?name="+name+"&type="+type+"&field="+field;
    //window.showModalDialog(url,"","dialogWidth:"+width+";dialogHeight:"+height+";center:yes;resizable:yes");
    window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes,copyhistory=no,width="+width+",height="+height+",top=360,left=500");
}

function showNewObject(type,name,parameters,returnType,operate,nodetype,tablelist,statementLevlel) {
	selectedNote = tree.getSelected();
	var url = "../../../treeoperate/common/";
	url = url + operate + ".jsp" + "?name=" + name + "&parameters=" + parameters + "&returnType=" + returnType + "&objType=" + type + "&tablelist=" + tablelist + "&statementLevlel=" + statementLevlel;
	tmptype = "SQL";
	if(type == "function") {
		tmptype = "FUN";
	} else if(type == "procedure") { 
		tmptype = "PRO";
	} else if(type == "package") { 
		tmptype = "PAC";
	} else if(type == "package_body") { 
		tmptype = "PAB";
	} else if(type == "type") { 
		tmptype = "TYP";
	} else if(type == "type_body") { 
		tmptype = "TYB";
	} else if(type == "trigger") { 
		tmptype = "TRI";
	} else if(type == "java_source") { 
		tmptype = "JAV";
	} else if(type == "view") { 
		tmptype = "VIE";
	} else if(type == "materialized_view") { 
		tmptype = "VIM";
	} else if(type == "table") { 
		tmptype = "TAB";
	}
	//alert(url);
	parent.editorToolFrame.nodeType = nodetype;
	parent.parent.leftFrameList.createNewSql(tmptype, "myTextarea");
	parent.editorFrame.location.replace(url);
}


//type: 用户对象的类型
//name: 用户对象的名称
//field: 1 为 右键菜单 调用，其他调用为空   by  phanrider 2011-4-21
//operate: 用户对象的操作，即View、Edit
function showViewObject(type,name,field,operate) {
	if (field == '') selectedNote = tree.getSelected();
	var url = "../treeoperate/common/";
	url = url + operate + ".jsp" + "?name=" + name + "&type=" + type ;
	tmptype = "SQL";
	if(type == "function") {
		tmptype = "FUN";
	} else if(type == "procedure") { 
		tmptype = "PRO";
	} else if(type == "package") { 
		tmptype = "PAC";
	} else if(type == "package body") { 
		tmptype = "PAB";
	} else if(type == "type") { 
		tmptype = "TYP";
	} else if(type == "type body") { 
		tmptype = "TYB";
	} else if(type == "trigger") { 
		tmptype = "TRI";
	} else if(type == "java source") { 
		tmptype = "JAV";
	} else if(type == "view") { 
		tmptype = "VIE";
	} else if(type == "materialized view") { 
		tmptype = "VIM";
	} else if(type == "table") { 
		tmptype = "TAB";
	}
	parent.parent.leftFrameList.createNewSql(tmptype, "myTextarea");
	parent.editorFrame.location.replace(url);
}

//type: 用户对象的类型
//name: 用户对象的名称
//field: 传过来对象类型，即Funtions，作为树判断是否为根节点的依据
//operate: 用户对象的操作，即View、Edit
function showEditObject(type,name,field,operate) {
	selectedNote = tree.getSelected();
	var url = "../treeoperate/common/";
	url = url + operate + ".jsp" + "?name=" + name + "&type=" + type ;
	tmptype = "SQL";
	if(type == "function") {
		tmptype = "FUN";
	} else if(type == "procedure") { 
		tmptype = "PRO";
	} else if(type == "package") { 
		tmptype = "PAC";
	} else if(type == "package body") { 
		tmptype = "PAB";
	} else if(type == "type") { 
		tmptype = "TYP";
	} else if(type == "type body") { 
		tmptype = "TYB";
	} else if(type == "trigger") { 
		tmptype = "TRI";
	} else if(type == "java_source") { 
		tmptype = "JAV";
	} else if(type == "view") { 
		tmptype = "VIE";
	} else if(type == "materialized_view") { 
		tmptype = "VIM";
	} else if(type == "table") { 
		tmptype = "TAB";
	}
	parent.editorToolFrame.nodeType = field;
	parent.parent.leftFrameList.createNewSql(tmptype, "myTextarea");
	parent.editorFrame.location.replace(url);
}

function recompile(objectType, objectName, debugFlag) {

	//真正通过dwr调用相应的Bean
	parent.editorToolFrame.BaisWorkBean.execObjectCompile(objectType, objectName, debugFlag, callexecobjectback);
	
	function callexecobjectback(intdata){
	
	var oracleTitle = "";
	var insertordel = "";
	var rows="";
	var tcell = intdata[0].length;
	
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
			oracleTitle = objectName + " " + rows ;	//这里需要把SQL执行后ORACLE反映出来的提示信息放进变量
	}
	
	//左边树的选中节点重新加载，只有执行成功才重新加载
	if(tcell == 2 && intdata[0][0] == "ReddragonflySuccessFlag*") {
			alert(oracleTitle);
			parent.leftFrame.tree.getSelected().getParent().reload();  //父节点刷新
		}
	}
}

function changeHtml(str) {
	var tmp = str.replace(/,/gi,"&#44;"); //转换","
	tmp = tmp.replace(/</gi,"&lt;"); //转换"<"
	tmp = tmp.replace(/>/gi,"&gt;"); //转换">"
	return tmp;
}


//创建windowlist工具条，通用新创建工具条方法
function createWindowList(windowType, str) {
	
	var initStr = "";
	var newStr = "";
	var endStr = "";
	var img = "../images/no_saved.gif";
	var maxAltLength = 30 //字符串最大长度
	
	//此处为接口，不同的窗口类型，定义不同的windowType即可，如果新增不同窗口类型，此处需添加针对该窗口类型的方法
	if(windowType == "SQL") {
		initStr = "SQL Window - ";
		newStr = "New";
	} else if (windowType == "FUN") {
		initStr = "Programe Window - ";
		newStr = "Edit source of function ";
	} else if (windowType == "PRO") {
		initStr = "Programe Window - ";
		newStr = "Edit source of procedure";
	} else if (windowType == "PAC") {
		initStr = "Programe Window - ";
		newStr = "Edit source of package";
	} else if (windowType == "PAB") {
		initStr = "Programe Window - ";
		newStr = "Edit source of package body";
	} else if (windowType == "TYP") {
		initStr = "Programe Window - ";
		newStr = "Edit source of type";
	} else if (windowType == "TYB") {
		initStr = "Programe Window - ";
		newStr = "Edit source of type body";
	} else if (windowType == "TRI") {
		initStr = "Programe Window - ";
		newStr = "Edit source of trigger";
	} else if (windowType == "JAV") {
		initStr = "Programe Window - ";
		newStr = "Edit source of java source";
	} else if (windowType == "VIE") {
		initStr = "SQL Window - ";
		newStr = "NEW";
	} else if (windowType == "VIM") {
		initStr = "SQL Window - ";
		newStr = "NEW";
	} else if (windowType == "TAB") {
		initStr = "View table ";
		newStr = "NEW";
	} 
	
	endStr = initStr + newStr;
	
	
	//得到已有表格对象
	var tableObject=document.getElementById("windowListBar");

	//判断已经存在的行的最大值
	var introws = tableObject.rows.length;
	
	if (introws > 8) {
		//超过9个弹出提示
		alert("Has exceeded the maximum window can be created!");
	} else {
		var newTr = tableObject.insertRow(introws);
		var newTd = newTr.insertCell(0);
	
		if (str == "initnew") {
			newTd.setAttribute("class","coolButtonActive");
		} else {
			newTd.setAttribute("class","coolButton");
		}
		var sType = windowType;
		var TdId = sType + "NewTd" + introws;
		newTd.setAttribute("id",TdId);
		newTd.setAttribute("width","98%");
		newTd.setAttribute("align","left");
		//newTd.setAttribute("onclick","test1()");
		newTd.onclick= function() {
			//alert(introws);
			//只有这样，才可以带参数
			//alert(introws);
			//alert(parent.leftFrameList.$('windowListBar').get('html'));
			windowListTdOnclick(windowType,introws);
		};	
		//alert(newTd.onclick);
		
		var spanId = "WindowListValue" + introws;
		var imgId = "columnButton" + introws;
		var htmlTemp = "<img id=" + imgId + " src='" + img + "' align='absmiddle'>" +
							"<span id='" + spanId + "' style=' vertical-align: middle;position:absolute; overflow: no;'  title='" + endStr + "' alt='" + endStr + "'></span>";
		endStr = initStr + newStr;
		//如果字符串超出定义长度，则截断
		if(endStr.length > maxAltLength) {
			endStr = endStr.substr(0, maxAltLength) + " ... ";
		}
		//alert(spanId);
	
		//为新列增加HTML
		newTd.innerHTML = htmlTemp;
		divname = spanId;
		$(divname).set('text',endStr);
	
	
		if (windowType == "SQL") {
			//如果为初始登录，则不创建按钮
			if (str != "initnew") {
			
				//新建一个新窗口时再保存当前已被按下的窗口的内容
				var i = getWindowTr();
				var SQLWindowString = "SQLWindow" + i;
				saveDivValue("SQLWindow",SQLWindowString, getWindowType(), i); 
				initWindwoListButton(introws);
				
			} else {
				initWindwoListButton(introws);
			}
		} else if (windowType == "FUN" || windowType == "PRO" || windowType == "PAC"
					|| windowType == "PAB" || windowType == "TYP" || windowType == "TYB"
					|| windowType == "TRI" || windowType == "JAV" || windowType == "VIE"
					|| windowType == "VIM" || windowType == "TAB") {
				var i = getWindowTr();
				var SQLWindowString = "SQLWindow" + i;
				//alert(getWindowType());
				//alert(SQLWindowString);
				saveDivValue("SQLWindow",SQLWindowString, getWindowType(), i); 
				initWindwoListButton(introws);
		}  else if (windowType == "JAVA") {
				var i = getWindowTr();
				var SQLWindowString = "SQLWindow" + i;
				//alert(getWindowType());
				//alert(SQLWindowString);
				saveDivValue("SQLWindow",SQLWindowString, getWindowType(), i); 
				initWindwoListButton(introws);
		}
		
	}
}

//得到已按下窗口的TR
function getWindowTr() {
	//得到已有表格对象
	var tableObject=document.getElementById("windowListBar");

	//判断已经存在的行的最大值
	var introws = tableObject.rows.length;
	
	for (var i = 0; i < introws; i++) {
		var windowlistcell = document.getElementById('windowListBar').rows[i].cells;
		if (windowlistcell[0].getValue()) {
				return i;
		}
	}
	return -1;
}

//得到已按下窗口的类型，首先取得按下窗口的TR的rowid
function getWindowType() {
	var i = getWindowTr();
	var returnStr="";
	if (i > -1) {
		var windowlistcell = document.getElementById('windowListBar').rows[i].cells;
		returnStr = $(windowlistcell[0]).get('id').trim().substr(0,3);
	}
	return returnStr;
}

//初始化一个windowList
function initWindwoListButton(rowid) {
	var windowlistcell = document.getElementById('windowListBar').rows[rowid].cells;
	
	var rowlength = document.getElementById('windowListBar').rows.length;
	for(var j = 0; j < rowlength; j++) {
		var celltmp = document.getElementById('windowListBar').rows[j].cells;
		for( var k = 0; k < celltmp.length; k++) {
			createButton(celltmp[k]);
			celltmp[k].setAlwaysUp(true);
			celltmp[k].setValue(false, false);
			//alert(celltmp[k]);
		}
	}
							
	for (var i = 0; i < windowlistcell.length; i++)
	{
			createButton(windowlistcell[i]);
			windowlistcell[i].setAlwaysUp(true);
			windowlistcell[i].setToggle(true);
	 		windowlistcell[i].setValue(true, true);
	}
	
	 
			
}
	
//功能：保存旧SQL窗口内容，并新建SQL窗口，并在windowlistFrame中显示
//创建每个SQL窗口时，会调到此函数
function createNewSql(windowType, textareaname) {
	//先要保存原始的div中的数据
	//得到输入框中的所有文本，以下div的name都已写固定
	//var getText = parent.editorFrame.$('myTextarea').get('html');
	//var getExecValue = parent.editorFrame.$('outResultDiv').get('html');
	//var getChangeExecValue = parent.editorFrame.$('changeOutResultDiv').get('html'); 
	var prevWindowType = getWindowType();
	setIsNotCreate(false);
	
	if (windowType == "SQL" ) {
		//创建windowlist工具条
		createWindowList(windowType, "new");
	
		//更改右边工作区的内容
		if (prevWindowType == windowType) {
			//如果上一个窗口与当前窗口类型一致，则需重置数据即可
			clearSQLWindow(prevWindowType);
		} else {
			//如果上一个窗口与当前窗口类型不一致，则需要重新加载
			parent.parent.editorFrame.location.replace(sqlURL);
		}
	} else if (windowType == "FUN" || windowType == "PRO" || windowType == "PAC"
					|| windowType == "PAB" || windowType == "TYP" || windowType == "TYB"
					|| windowType == "TRI" || windowType == "JAV" || windowType == "VIE"
					|| windowType == "VIM" || windowType == "TAB") {
		
		//创建windowlist工具条
		createWindowList(windowType, "new");
		//更改右边工作区的内容
		if (prevWindowType == windowType) {
			//如果上一个窗口与当前窗口类型一致，则需重置数据即可
			clearSQLWindow(prevWindowType);
		} else {
			//如果上一个窗口与当前窗口类型不一致，则需要重新加载
			parent.parent.editorFrame.location.replace(funURL);
		}
		
	} else if (windowType == "JAVA" ) {
		
		//创建windowlist工具条
		createWindowList(windowType, "new");
		
	}
	
	

}

//每次实时切换窗口，实际上是保存上一行的内容，并且切换到当前行
function doRadio(_trType, _prevTrType, _trRow) {
		var sameWindowFlag = false;
		//alert(_trType);
		//得到当前已按行的trRow
		var i = getWindowTr(); 
		var SQLWindowString = "SQLWindow" + i;
		//每次都得保存原窗口内容
		saveDivValue("SQLWindow",SQLWindowString, _prevTrType, i); 
		//调用恢复窗口内容的函数,_trRow为被点击行的trRow
		if( _trType != _prevTrType ) {
			//不同窗口，统一置为false
			parent.editorFrame.globalOnLoadFlag = false;
			//如果上一个窗口与当前窗口类型不一致，则需要重新加载
			//并且按照当前点击窗口的类型，加截不同的页面
			//先设置一下全局变量
			setGlobal(_trType, _trRow, sameWindowFlag);
			//不是从新建窗口进入的
			setIsNotCreate(true);
			if(_trType == "SQL") {
				parent.parent.editorFrame.location.replace(sqlURL);
			} else if (_trType == "FUN" || _trType == "PRO" || _trType == "PAC"
					|| _trType == "PAB" || _trType == "TYP" || _trType == "TYB"
					|| _trType == "TRI" || _trType == "JAV" || _trType == "VIE"
					|| _trType == "VIM" || _trType == "TAB") {
				//alert(funURL);
				parent.parent.editorFrame.location.replace(funURL);
			} else if (_trType == "JAVA") {
				parent.parent.editorFrame.location.replace(funURL);
			}
			sameWindowFlag = false;
			//restoreDivValue(_trType, _trRow, sameWindowFlag);
		} else {
			//如果窗口类型相同，则直接赋值
			sameWindowFlag = true;
			restoreDivValue(_trType, _trRow, sameWindowFlag);
		}

	//得到当前行的所有列
	var windowlistcell = document.getElementById('windowListBar').rows[_trRow].cells;
	//设置除了本行外所有行的列为按钮按起状态
	var rowlength = document.getElementById('windowListBar').rows.length;
	for(var j = 0; j < rowlength; j++) {
		var celltmp = document.getElementById('windowListBar').rows[j].cells;
		for( var k = 0; k < celltmp.length; k++) {
			createButton(celltmp[k]);
			celltmp[k].setAlwaysUp(true);
			celltmp[k].setValue(false, false);
			//alert(celltmp[k]);
		}
	}
	
	for (var i = 0; i < windowlistcell.length; i++) {
			windowlistcell[i].setAlwaysUp(true);
			windowlistcell[i].setToggle(true);
			windowlistcell[i].setValue(true,true);	
		}
	//parent.parent.editorFrame.location.replace("./editor.jsp");
	
}

function windowListTdOnclick(trType, trRow) {
	//alert(trRow);
	var windowlistcell = parent.leftFrameList.document.getElementById('windowListBar').rows[trRow].cells;
	
	//只取窗口类型的前三位 *********
	tmpWindowtype = $(windowlistcell[0]).get('id').trim().substr(0,3);
	
	//alert("tmpWindowtype:"+tmpWindowtype);
	//alert("trType:"+trType);
	//alert("getwin:"+getWindowType());
	//alert(windowlistcell[0].getValue());
	//如果按钮是未按下状态
	if (!windowlistcell[0].getValue()) {
		//则执行一系列动作
		doRadio(tmpWindowtype,getWindowType(),trRow);
	
	}
}


//新建SQLWindow时，调用此函数
function clearSQLWindow(windowtype) {
	//清空编辑输入区--SQL窗口
	createNew('myTextarea');
	//清空输出结果区--SQL窗口
	if (windowtype == "SQL") {
		parent.editorFrame.$('outResultDiv').set('text','');
		parent.editorFrame.$('changeOutResultDiv').set('text','');
		//按钮恢复成初始状态
		controlbuttonReset();
		//清除结果集后，需重新设置工作结果区的工具条状态
		resetBaseWorkToolBar(false);
	}
	
	//状态栏初始为空
	setFootView(9999, '　');
	
}

//保存原先的DIV内容，有bug,如果按了工具条的按钮，按钮的效果将会在新窗口中体现
//而工具条一旦被克隆，则所有JS及CSS失效，待解决！！！
function saveDivValue(DivSource, DivDest, windowType, trRow) {
	//复制元素并复制下所有的事件
	var divObject = parent.parent.editorFrame.$(DivSource);
	//var _div1 = divObject.clone(true,true).cloneEvents(divObject);
	//保存原先的div内容
	//$('hiddenDiv') = _div1;
	//$(_div1).replaces($('SQLWindow'));
	//alert(DivSource);
	//alert(divObject.get('html'));
	//alert(windowType);
	//alert(trRow);
	$(DivDest).set('html',divObject.get('html'));
	//alert(divObject.get('html'));
	
	
	//此处为接口，可为多个窗口类型
	if (windowType == "SQL") {
		var destMyTextarea = "myTextarea" + trRow;
		var destOutResultDiv = "outResultDiv" + trRow;
		var destChangeOutResultDiv = "changeOutResultDiv" + trRow;
		var destFootview = "footview" + trRow;
		//更改一下每个DIV中的ID，不然ID会重复
		$('myTextarea').set('id',destMyTextarea);
		$('outResultDiv').set('id',destOutResultDiv);
		$('changeOutResultDiv').set('id',destChangeOutResultDiv);
		$('footview').set('id',destFootview);
		//alert($(destMyTextarea).get('id'));
	} else if (windowType == "FUN" || windowType == "PRO" || windowType == "PAC"
					|| windowType == "PAB" || windowType == "TYP" || windowType == "TYB"
					|| windowType == "TRI" || windowType == "JAV" || windowType == "VIE"
					|| windowType == "VIM" || windowType == "TAB") {
		var destMyTextarea = "myTextarea" + trRow;
		var destobjTitle = "objTitle" + trRow;
		var desttmpImg = "tmpImg" + trRow;
		var destobjIcoId = "objIcoId" + trRow;
		var destFootview = "footview" + trRow;
		
		//更改一下每个DIV中的ID，不然ID会重复
		if (windowType == "TAB") {
			destMyTextarea = "tabPanel" + trRow;
			$('tabPanel').set('id',destMyTextarea);
			$('footview').set('id',destFootview);
		} else {
			$('myTextarea').set('id',destMyTextarea);
			$('objTitle').set('id',destobjTitle);
			$('tmpImg').set('id',desttmpImg);
			$('objIcoId').set('id',destobjIcoId);
			$('footview').set('id',destFootview);
		}
		
		
	} else if (windowType == "JAVA") {
		var destMyTextarea = "myTextarea" + trRow;
		var destProTitle = "objTitle" + trRow;
		//更改一下每个DIV中的ID，不然ID会重复
		$('myTextarea').set('id',destMyTextarea);
		$('proTitle').set('id',destProTitle);
		//alert($(destMyTextarea).get('id'));
	}
	
	//alert($('hiddenDiv').get('text'));
}


//恢复对应窗口的内容
function restoreDivValue(windowType, trRow, sameWindow) {
	if (windowType == "SQL") {
		var destMyTextarea = "myTextarea" + trRow;
		var destOutResultDiv = "outResultDiv" + trRow;
		var destChangeOutResultDiv = "changeOutResultDiv" + trRow;
		var destFootview = "footview" + trRow;
		//恢复编辑区内容--SQL窗口
		//alert(parent.leftFrameList.$('SQLWindow0').get('html'));
		
		parent.editorFrame.$('myTextarea').set('html',$(destMyTextarea).get('html'));
		
		//恢复工具条按钮事件会失效 BUG，暂不恢复
		//parent.editorFrame.$('foot_outputDiv1').set('html',$('foot_outputDiv1').get('html'));

		//不采用new的方法，IE会报错
		new parent.editorFrame.dhtmlXGridObject('outResultDiv');
		//恢复输出结果区--SQL窗口
		parent.editorFrame.$('outResultDiv').set('html',parent.leftFrameList.$(destOutResultDiv).get('html'));
		parent.editorFrame.$('changeOutResultDiv').set('html',parent.leftFrameList.$(destChangeOutResultDiv).get('html'));
		parent.editorFrame.$('footview').set('text',parent.leftFrameList.$(destFootview).get('text'));
	} else if (windowType == "FUN" || windowType == "PRO" || windowType == "PAC"
					|| windowType == "PAB" || windowType == "TYP" || windowType == "TYB"
					|| windowType == "TRI" || windowType == "JAV" || windowType == "VIE"
					|| windowType == "VIM" || windowType == "TAB") {
		var destMyTextarea = "myTextarea" + trRow;
		var destobjTitle = "objTitle" + trRow;
		var desttmpImg = "tmpImg" + trRow;
		var destobjIcoId = "objIcoId" + trRow;
		var destFootview = "footview" + trRow;
		var SQLWindowobj = 'SQLWindow' + trRow;
		//alert("h:" + parent.leftFrameList.$(SQLWindowobj).get('html'));
		//alert(parent.leftFrameList.$(destobjIcoId).get('src').substr(parent.leftFrameList.$(destobjIcoId).get('src').lastIndexOf("/")));
		if (windowType == "TAB") {
			destMyTextarea =  "tabPanel" + trRow;
			parent.editorFrame.$('tabPanel').set('html',parent.leftFrameList.$(destMyTextarea).get('html'));
			parent.editorFrame.$('footview').set('text',parent.leftFrameList.$(destFootview).get('text'));
		} else {
			parent.editorFrame.$('myTextarea').set('html',parent.leftFrameList.$(destMyTextarea).get('html'));
			parent.editorFrame.$('tmpImg').set('text',parent.leftFrameList.$(desttmpImg).get('text'));
			parent.editorFrame.$('objTitle').set('text',parent.leftFrameList.$(destobjTitle).get('text'));
			parent.editorFrame.$('objIcoId').set('src',parent.leftFrameList.$(desttmpImg).get('text'));
			parent.editorFrame.$('footview').set('text',parent.leftFrameList.$(destFootview).get('text'));
		}
		
	} else if (windowType == "JAVA") {
		var destMyTextarea = "myTextarea" + trRow;
		//alert(destMyTextarea);
		parent.editorFrame.$('myTextarea').set('html',$(destMyTextarea).get('html'));
	}
	
}

//更改windowList的提示语
function changeWindowListTitle(windowType,trRow,titleStr) {
	var spanListVauleId = "WindowListValue" + trRow;
	var imgListVauleId = "columnButton" + trRow;
	var initStr = "";
	var tmpStr = "";
	var imgIcon = "../images/windowlist_running.gif";
	var maxLength = 30; //设置左边工具条最大显示的字符数
	var maxAltLength = 200; //设置鼠标放在上面提示的最大字符数
	
	if(windowType == "SQL") {
		initStr = "SQL Window - ";
	} else if (windowType == "FUN" || windowType == "PRO" || windowType == "PAC"
					|| windowType == "PAB" || windowType == "TYP" || windowType == "TYB"
					|| windowType == "TRI" || windowType == "JAV" || windowType == "VIE"
					|| windowType == "VIM" || windowType == "TAB") {
		strTmp = "";
		if  (windowType == "FUN") {
			strTmp = "function ";
		} else if (windowType == "PRO") {
			strTmp = "procedure ";
		} else if (windowType == "PAC") {
			strTmp = "package ";
		} else if (windowType == "PAB") {
			strTmp = "package body ";
		} else if (windowType == "TYP") {
			strTmp = "type ";
		} else if (windowType == "TYB") {
			strTmp = "type body ";
		} else if (windowType == "TRI") {
			strTmp = "trigger ";
		} else if (windowType == "JAV") {
			strTmp = "java source ";
		} else if (windowType == "VIE" || windowType == "VIM") {
			strTmp = "NEW";
		}  else if (windowType == "TAB") {
			strTmp = "table";
		}  
		if (windowType == "VIE" || windowType == "VIM") {
			initStr = "SQL Window - " + strTmp;
		} else if (windowType == "TAB"){
			initStr = "View" + strTmp;
		} else {
			initStr = "Programe Window - Edit source of " + strTmp;
		}
	} 
	
	titleTmp = "";

	tmpStr = initStr + titleStr;
	
	if(tmpStr.length > maxLength) {
		tmpStr = tmpStr.substr(0, maxLength) + " ... ";
	}
	
	tmpStrA = initStr + titleStr;
	if(tmpStrA.length > maxAltLength) {
		tmpStrA = tmpStr.substr(0, maxAltLength) + " ... ";
	}
	
	$(spanListVauleId).set('text', tmpStr);
	$(spanListVauleId).set('title', tmpStrA);
	$(spanListVauleId).set('alt', tmpStrA);
	$(imgListVauleId).set('src', imgIcon);
	
}

//恢复当前windowList窗口的图片
function restoreWindowListImg(trRow) {
	var imgListVauleId = "columnButton" + trRow;
	var imgIcon = "../images/no_saved.gif";
	
	$(imgListVauleId).set('src', imgIcon);
	
}

//睡眠函数：毫秒
function sleep(millisec) {
	var dt_old = new Date();
	for (;;) {
		var dt_new = new Date();
		if ( (dt_new.getTime() - dt_old.getTime()) >= millisec ) break;
	}
}


//设置是否新建窗口标志
function setIsNotCreate(flag) {
	parent.leftFrameList.globalIsNotCreate = flag;
}

//页面载入后的初始化函数
function initOnload() {
	//如果不是新建的窗口，则调用恢复内容的函数
	if (parent.leftFrameList.globalIsNotCreate) {
		//alert(parent.leftFrameList.globalTrType);
		//alert(parent.leftFrameList.globalTrRow);
		//alert(parent.leftFrameList.globalSameWindowFlag);
		parent.leftFrameList.restoreDivValue(parent.leftFrameList.globalTrType, parent.leftFrameList.globalTrRow, parent.leftFrameList.globalSameWindowFlag);
	}
}

//设置全局变量，当前窗口类型及当前行
function setGlobal(trType, trRow, WindowFlag) {
	parent.leftFrameList.globalTrType = trType;
	parent.leftFrameList.globalTrRow = trRow;
	parent.leftFrameList.globalSameWindowFlag = WindowFlag;
}

//删除一个window list
function deleteWindowList() {
	//得到当前已按下的window list
	var currTrRown = parent.leftFrameList.getWindowTr();
	var currWindowFlag = parent.leftFrameList.getWindowType();
	
	//alert(currTrRown);
	//alert(currWindowFlag);
	
	//得到已有表格对象
	var tableObject=parent.leftFrameList.document.getElementById("windowListBar");

	//判断已经存在的行的最大值
	var introws = tableObject.rows.length;
	
	if (introws == 1 ) {
		//最后一个弹出提示，不可以删除
		alert("Do not delete last window!");
	} else {
		//清空leftFrameList中当前的div值
		parent.leftFrameList.deleteWindowListDiv(currTrRown);
		
		//开始删除当前行
		tableObject.deleteRow(currTrRown);
		
		var currRow = currTrRown;
		//eftFrameList其他div值依次调整，并且返回最大的TrRow
		var maxRow = parent.leftFrameList.adjustmentWindowListDiv(currRow);
	
		//如果最大行大于0，则减1
		if (maxRow > 0)	maxRow--;
	
		//当前最大的row设为活动窗口
		parent.leftFrameList.setRowWindowList(maxRow);
	
		//恢复右边工作区数据
		parent.leftFrameList.recoverPage(parent.leftFrameList.getWindowType(),parent.leftFrameList.getWindowTr(),false);
	
	}
}

//删除当前活动窗口的div中的值
function deleteWindowListDiv(currTrRown) {
	//alert(currTrRown);
	var windowDiv = "SQLWindow" + currTrRown;
	parent.leftFrameList.$(windowDiv).set('html','');
}

//依次调整各个DIV的值
function adjustmentWindowListDiv(trRow) {
	//首先得到当前活动窗口的trRow
	var currTrRown = trRow;
	
	//得到已有表格对象
	var tableObject=parent.leftFrameList.document.getElementById("windowListBar");

	//判断已经存在的行的最大值
	var maxrows = tableObject.rows.length;
	
	
	for (i = currTrRown + 1; i <= maxrows ; i++) {
		
		var old = i - 1;
		//得到前一个窗口的div的ID
		var oldWindowDiv = "SQLWindow" + old;
		//得到被删除窗口的下一个窗口的div的	ID
		var windowDiv = "SQLWindow" + i;
		
		
		//重新把每个tr中的ID改过来
		var windowlistcell = document.getElementById('windowListBar').rows[old].cells;
		
		tmplength = $(windowlistcell[0]).get('id').length;
		tmpTdId = $(windowlistcell[0]).get('id').trim().substr(0,tmplength-1);
		tmpWindowtype = $(windowlistcell[0]).get('id').trim().substr(0,3);
		
		
		//为各个不同窗口类型更改ID，可为多种窗口类型
		if (tmpWindowtype == "SQL") {
			var destMyTextarea = "myTextarea" + i;
			var destOutResultDiv = "outResultDiv" + i;
			var destChangeOutResultDiv = "changeOutResultDiv" + i;
			var destFootview = "footview" + i;
			
			var oldMyTextarea = "myTextarea" + old;
			var oldOutResultDiv = "outResultDiv" + old;
			var oldChangeOutResultDiv = "changeOutResultDiv" + old;
			var oldFootview = "footview" + old;
			
			//更改一下每个DIV中的ID，不然ID会重复
			parent.leftFrameList.$(destMyTextarea).set('id',oldMyTextarea);
			parent.leftFrameList.$(destOutResultDiv).set('id',oldOutResultDiv);
			parent.leftFrameList.$(destChangeOutResultDiv).set('id',oldChangeOutResultDiv);
			parent.leftFrameList.$(destFootview).set('id',oldFootview);
			
		} else if (tmpWindowtype == "FUN" || tmpWindowtype == "PRO" || tmpWindowtype == "PAC"
					|| tmpWindowtype == "PAB" || tmpWindowtype == "TYP" || tmpWindowtype == "TYB"
					|| tmpWindowtype == "TRI" || tmpWindowtype == "JAV" || tmpWindowtype == "VIE"
					|| tmpWindowtype == "VIM" || tmpWindowtype == "TAB") {
			var destMyTextarea = "myTextarea" + i;
			var destObjTitle = "objTitle" + i;
			var destObjIcoId = "objIcoId" + i;
			var destFootview = "footview" + i;
			
			var oldMyTextarea = "myTextarea" + old;
			var oldObjTitle = "objTitle" + old;
			var oldObjIcoId = "objIcoId" + old;
			var oldFootview = "footview" + old;
			//更改一下每个DIV中的ID，不然ID会重复
			parent.leftFrameList.$(destMyTextarea).set('id',oldMyTextarea);
			parent.leftFrameList.$(destObjTitle).set('id',oldObjTitle);
			parent.leftFrameList.$(destObjIcoId).set('id',oldObjIcoId);
			parent.leftFrameList.$(destFootview).set('id',oldFootview);
			
			//alert($(destMyTextarea).get('id'));
		} else if (tmpWindowtype == "JAVA") {
			var destMyTextarea = "myTextarea" + i;
			var oldMyTextarea = "myTextarea" + old;
			//更改一下每个DIV中的ID，不然ID会重复
			parent.leftFrameList.$(destMyTextarea).set('id',oldMyTextarea);
			//alert($(destMyTextarea).get('id'));
		}
	
		//依次把当前窗口的内容重新赋值给上一个窗口
		parent.leftFrameList.$(oldWindowDiv).set('html',parent.leftFrameList.$(windowDiv).get('html'));
		parent.leftFrameList.$(windowDiv).set('html','');
		
		//alert(parent.leftFrameList.$(oldWindowDiv).get('id'));
		
		var oldTdId = tmpTdId + old;
		var oldSpanId = "WindowListValue" + old;
		var oldImgId = "columnButton" + old;
		var newTdId = tmpTdId + i;
		var newSpanId = "WindowListValue" + i;
		var newImgId = "columnButton" + i;
		
	
		parent.leftFrameList.$(newTdId).set('id',oldTdId);
		parent.leftFrameList.$(newSpanId).set('id',oldSpanId);
		parent.leftFrameList.$(newImgId).set('id',oldImgId);
		windowlistcell[0].onclick = '';
		windowlistcell[0].onclick = function () {
			//alert(tmpWindowtype);
			//不同Frame的onclick事件，参数tmpWindowtype此处没有被实时转换，估
			//windowListTdOnclick函数已重写，参数tmpWindowtype已不用
			windowListTdOnclick(tmpWindowtype,this.parentNode.rowIndex);
		};
		
		//alert($(windowlistcell[0]).get('onclick'));
		
	}
	return maxrows;
	
}
//设置指定行的窗口为活动窗口
function setRowWindowList(Row) {
	//得到当前行的所有列
	var windowlistcell = parent.leftFrameList.document.getElementById('windowListBar').rows[Row].cells;
	//设置除了本行外所有行的列为按钮按起状态
	var rowlength = parent.leftFrameList.document.getElementById('windowListBar').rows.length;
	for(var j = 0; j < rowlength; j++) {
		var celltmp = parent.leftFrameList.document.getElementById('windowListBar').rows[j].cells;
		for( var k = 0; k < celltmp.length; k++) {
			createButton(celltmp[k]);
			celltmp[k].setAlwaysUp(true);
			celltmp[k].setValue(false, false);
		}
	}
	
	for (var i = 0; i < windowlistcell.length; i++) {
			windowlistcell[i].setAlwaysUp(true);
			windowlistcell[i].setToggle(true);
			windowlistcell[i].setValue(true,true);	
		}
}

//恢复右边工作区页面中的数据，关闭前一个窗口时最后调用此函数
function recoverPage(_trType, _trRow, sameWindowFlag) {

	parent.editorFrame.globalOnLoadFlag = false;
	
	//alert(_trType);
	//alert(_trRow);
	//alert(sameWindowFlag);
	
	setGlobal(_trType, _trRow, sameWindowFlag);
	
	//如果上一个窗口与当前窗口类型不一致，则需要重新加载
	//并且按照当前点击窗口的类型，加截不同的页面
	//先设置一下全局变量
	setIsNotCreate(true);
	if(_trType == "SQL") {
		parent.parent.editorFrame.location.replace(sqlURL);
	} else if (_trType == "FUN" || _trType == "PRO" || _trType == "PAC"
					|| _trType == "PAB" || _trType == "TYP" || _trType == "TYB"
					|| _trType == "TRI" || _trType == "JAV" || _trType == "VIE"
					|| _trType == "VIM" || _trType == "TAB") {
		parent.parent.editorFrame.location.replace(funURL);
	} else if (_trType == "JAVA") {
		parent.parent.editorFrame.location.replace(funURL);
	}
	parent.leftFrameList.sameWindowFlag = false;
}

//关闭窗口
function closeWindowList() {

	parent.leftFrameList.deleteWindowList();
	
}

//改边关闭按钮样式，鼠标左键按下时调用此函数
function changeWindowListClassDown(imgId,evt) {
	var isie=document.all;
    var leftnum=isie?event.button:evt.which;
    if (leftnum == 1){        //IE or firefox
		imgPath = "../images/close_window_down.gif";
		$(imgId).setProperty('src',imgPath);
	}
}


//改边关闭按钮样式，鼠标左键恢复时调用此函数
function changeWindowListClassUp(imgId,evt) {
	var isie=document.all;
    var leftnum=isie?event.button:evt.which;
    if (leftnum == 1){        //IE or firefox
		imgPath = "../images/close_window.gif";
		$(imgId).setProperty('src',imgPath);
	}
}

//关闭左则Window List
function closeWindow(imgId,evt) {
	
	//关闭当前窗口
	closeWindowList();
	//最后恢复按钮样式
	changeWindowListClassUp(imgId,evt);
}

function execQueryObjData(textareaname,objname) {
	var texttmp = "select * from " + objname + " t";
	parent.leftFrameList.createNewSql('SQL','myTextarea');
	parent.editorFrame.setDivValueText (textareaname,texttmp);
	parent.editorToolFrame.executeRun(textareaname);
}

function execQueryTable(tablename, textareaname) {
	var texttmp = "select * from " + tablename + " t";
	parent.leftFrameList.createNewSql('SQL','myTextarea');
	setDivValueText (textareaname,texttmp);
	parent.editorToolFrame.executeRun(textareaname);
}

function getUserObject(data) {
	UserObject = data;
	//alert(UserObject[1][0]);
}

//关于我们团队
function aboutUS() {
	url = "../login/about.jsp";
	window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes,copyhistory=no,width=500,height=300,top=360,left=500");
}