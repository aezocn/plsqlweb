<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.CharSet" %>
<%@ page import="org.reddragonfly.iplsqldevj.DBUtilities"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.dbbean.DbObjectBean"%>

<%
	String type = CharSet.nullToEmpty(request.getParameter("type"))
			.toUpperCase();
	String name = CharSet.nullToEmpty(request.getParameter("name"))
			.toUpperCase();
	String[] ownerName = name.split("\\.", 2);
	String sql = "", keysql = "", checksql = "", indexsql = "", privilegesql = "", tablePropertysql = "";
	String owner = "";
	String objName = "";
	String maxExtent = "";
	String strchk = null;
	String strInitExtent = null;
	String strInitExtentUnit = null;

	UserBean ub = (UserBean) session.getAttribute("user");

	if (ownerName[0].equals(name)) {
		owner = ub.getUsername().toUpperCase();
		objName = ownerName[0];
		sql = "select a.column_name, a.data_type||decode(a.char_col_decl_length,'',null,'('||a.char_col_decl_length||')') type, a.nullable, "
				+ "a.data_default \\\"DEFAULT\\\", '' Storage, b.comments from user_tab_columns a, user_col_comments b "
				+ "where a.table_name='"
				+ ownerName[0]
				+ "' "
				+ "and a.table_name = b.table_name "
				+ "and a.column_name = b.column_name "
				+ "order by a.column_id asc";
		keysql = "select  constraint_name, decode(constraint_type,'P','Primary','U','Unique','F','Foreign','') type, decode(Status,'ENABLED','Y','') Enabled, "
				+ " '' ReferencingTable, '' ReferencingColumns, '' OnDelete, deferrable, deferred, last_change "
				+ " from user_constraints t "
				+ " where table_name = '"
				+ ownerName[0]
				+ "' "
				+ " and constraint_type in ('U', 'P', 'F')";
		checksql = "select '' a,'' b, '' c, '' d, '' e, '' f from dual where 1=2";
		indexsql = "select owner,index_name,decode(uniqueness,'UNIQUE','Unique','Normal') uniqueness,compression, prefix_length, '' reverse, "
				+ "'tablespace '||lower(tablespace_name)||' pctfree '|| pct_free || ' initrans ' || ini_trans || ' maxtrans ' || max_trans || "
				+ "' storage ( initial ' || initial_extent/1024 || 'k minextents ' ||min_extents|| ' maxextents ' || decode(max_extents,2147483645,'unlimited',max_extents) || ' )' "
				+ " from all_indexes "
				+ " where table_name='"
				+ ownerName[0]
				+ "' and table_owner='"
				+ owner.toUpperCase() + "' order by index_name";
		privilegesql = "select grantee,privilege,grantable,hierarchy from USER_TAB_PRIVS where table_name='"
				+ ownerName[0] + "'";
		tablePropertysql = "select tt.tablespace_name, tt.initial_extent, tt.pct_free, tt.next_extent, tt.pct_used, tt.pct_increase, "
				+ "tt.ini_trans, tt.min_extents, tt.max_trans, tt.max_extents, (select comments from user_tab_comments where table_name=tt.table_name) comments from user_tables tt where table_name='"
				+ ownerName[0] + "'";
	} else {
		owner = ownerName[0];
		objName = ownerName[1];
		sql = "select a.column_name, a.data_type||decode(a.char_col_decl_length,'',null,'('||a.char_col_decl_length||')') type, a.nullable, "
				+ "a.data_default \\\"DEFAULT\\\", '' Storage, b.comments from dba_tab_columns a, dba_col_comments b "
				+ "where a.table_name='"
				+ ownerName[1]
				+ "' "
				+ "and a.owner = '"
				+ ownerName[0]
				+ "' "
				+ "and a.table_name = b.table_name "
				+ "and a.column_name = b.column_name "
				+ "and a.owner = b.owner " + "order by a.column_id asc";
		privilegesql = "select grantee,privilege,grantable,hierarchy from DBA_TAB_PRIVS where table_name='"
				+ ownerName[1] + "' and owner= '" + ownerName[0] + "'";
		tablePropertysql = "select tt.tablespace_name, tt.initial_extent, tt.pct_free, tt.next_extent, tt.pct_used, tt.pct_increase, "
				+ "tt.ini_trans, tt.min_extents, tt.max_trans, tt.max_extents, (select comments from dba_tab_comments where table_name=tt.table_name and owner = tt.owner) comments  from dba_tables tt where table_name='"
				+ ownerName[1] + "' and owner= '" + ownerName[0] + "'";
	}
	DbObjectBean tbp = new DbObjectBean();
	if (type.equals("TABLE")) {
		tbp.getAllpropperty(tablePropertysql, ub.getDb());
		if (CharSet.nullToEmpty(tbp.getGetMaxExtent()).equals(
				"2147483645")) {
			strchk = "checked";
		} else {
			maxExtent = tbp.getGetMaxExtent();
		}
		if (Long.parseLong(CharSet.nullToEmpty(tbp.getGetInitExtent())) >= 1024
				&& Long.parseLong(CharSet.nullToEmpty(tbp
						.getGetInitExtent())) < 1048576) {
			strInitExtent = String.valueOf((Long.parseLong(CharSet
					.nullToEmpty(tbp.getGetInitExtent())) / 1024));
			strInitExtentUnit = "KB";
		} else if (Long.parseLong(CharSet.nullToEmpty(tbp
				.getGetInitExtent())) >= 1048576) {
			strInitExtent = String.valueOf((Long.parseLong(CharSet
					.nullToEmpty(tbp.getGetInitExtent())) / 1048576));
			strInitExtentUnit = "MB";
		} else {
			strInitExtent = CharSet.nullToEmpty(tbp.getGetInitExtent());
			strInitExtentUnit = "Byte";
		}
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>View</title>
	    <link type="text/css" rel="StyleSheet" href="../../css/cb2.css" />
	    <link type="text/css" rel="StyleSheet" href="../../css/tab.winclassic.css" />
	    <link type="text/css" rel="StyleSheet" href="../../css/dhtmlxgrid.css" />
	    <script type='text/javascript' src='../../dwr/interface/DbObjectBean.js'></script>
	    <script type='text/javascript' src='../../dwr/engine.js'></script>
	    <script type="text/javascript" src="../../js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="../../js/dhtmlxgridcell.js"></script>
	    <script type="text/javascript" src="../../js/tabpane.js"></script>
	    <script type="text/javascript" src="../../js/others.js"></script>
	    <script type="text/javascript" src="../../js/mootools.js"></script>
	    <script type="text/javascript" language="JavaScript1.5"
			src="../../js/ieemu.js"></script>
		<script type="text/javascript" src="../../js/cb2.js"></script>
	</head>
	<style>
		html,body {
			background: ButtonFace;
			margin: 2px;
			padding: 2px;
			font-family: 'Verdana', 'Tahoma', 'Helvetica', 'Arial';
			overflow: auto;
			height: 99%;
		}
		.lagerfont{
		    font-size: 12px;
		}
		.smallfont{
		    font-size: 12px;
		    text-align: right;
		}
		.editorout {
		    border-left: 1px ridge #999999; 
		    border-right: 1px ridge #999999;	
		    border-top: 1px ridge #999999; 
		    border-bottom: 1px ridge #999999;
		}
		.editorin {
		    border-left: 1px inset #999999; 
		    border-right: 1px inset #999999; 
		    border-top: 1px inset #999999; 
		    border-bottom: 1px inset #999999;
		}
		.editor {
			font-family: "Courier New", Courier, mono;
			font-size: 12px;
			overflow: auto;
			background-color: #FFFFFF;
			scrollbar-3dlight-color: threedhighlight;
			scrollbar-darkshadow-color: threedshadow;
			scrollbar-highlight-color: buttonface;
			scrollbar-shadow-color: buttonface;
			WIDTH: 100%;
			height:100%;
			float: left;
			
		}
		.dynamic-tab-pane-control .tab-page {
			height:		610px;
			
		}

		.dynamic-tab-pane-control .tab-page .dynamic-tab-pane-control .tab-page {
			height:		100px;
		}
		.dynamic-tab-pane-control h2 {
			text-align:	center;
			width:		auto;
		}

		.dynamic-tab-pane-control h2 a {
			display:	inline;
			width:		auto;
		}

		.dynamic-tab-pane-control a:hover {
			background: transparent;
		}
		p {
			margin: 0px;
		}
	</style>
	<style disabled="disabled" id="mozGrooveCSS">
	/* Mozilla does not understand disabled stylesheets but
			 * for forward compatibility I'll enabled it using js
			 * below
			 */
	table {
		border: 2px groove ButtonFace;
	}
	</style>
	<body ondragstart="return false" oncontextmenu="return false" onload="initOnload()">
		<div id="SQLWindow" style="min-height:100%; _height:100%; border: 2px; overflow: no; background-color: ButtonFace;">
			<div class="tab-pane" id="tabPanel" style=" min-height:90%; _height:90%; ">
				<%
					if (type.equals("TABLE")) {
				%>
					<div class="tab-page" id="tabpage_1" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_1"><img style="border:none" id='objIcoId_1' src='' align='absmiddle' /><span id="tmpImg_1" style="display:none"></span> <span id='objTitle_1'>General</span></h2>	
		                    <table style="border:0;">
		                    <tr>
		                    <td align="right" style="width: 30px;">
		                    <label class="smallfont">&nbsp;&nbsp;Owner</label>
		                    </td>
		                    <td>
		                    <input name="name" id="name" type="text" size="38" style="background:ThreeDFace" value="<%=owner%>" readonly />
		                    </td>
		                    </tr>
		                    <tr>
		                    <td align="right" style="width: 30px;">
		                    <label class="smallfont">&nbsp;&nbsp;Name</label>
		                    </td>
		                    <td>
		                    <input name="parameters" id="parameters" type="text" size="38" style="background:ThreeDFace" value="<%=objName%>"  readonly />
		                 	</td>
		                 	</tr>
		                 	</table>
		                 	<fieldset>
								<legend>Storage</legend>
									<table style="border:0;">
		                    			<tr>
		                    				<td align="right" style="width: 80px;">
												 <label class="smallfont">&nbsp;&nbsp;Tablespace</label>
									 		</td>
									 		<td>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="20" style="background:ThreeDFace" value="<%=tbp.getGetTablespace()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;&nbsp;Initial Extent</label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=strInitExtent%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=strInitExtentUnit%>" readonly /></label>
		                    		 		</td>
		                    		 	</tr>
		                    		 	<tr>
		                    		 		<td align="right" style="width: 80px;">
												 <label class="smallfont">&nbsp;&nbsp;%Free</label>
									 		</td>
									 		<td>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="20" style="background:ThreeDFace" value="<%=tbp.getGetFree()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;&nbsp;&nbsp;&nbsp;Next Extent</label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=tbp.getGetNextExtent()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="Bytes" readonly /></label>
		                    		 		</td>
		                    		 	</tr>
		                    		 	<tr>
		                    		 		<td align="right" style="width: 80px;">
												 <label class="smallfont">&nbsp;&nbsp;%Used</label>
									 		</td>
									 		<td>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="20" style="background:ThreeDFace" value="<%=tbp.getGetUsed()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;&nbsp;&nbsp;&nbsp;%Increase</label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=tbp.getGetIncrease()%>" readonly /></label>
		                    		 		</td>
		                    		 	</tr>
		                    		 	<tr>
		                    		    	<td align="right" style="width: 80px;">
												 <label class="smallfont">&nbsp;&nbsp;Ini Trans</label>
									 		</td>
									 		<td>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="20" style="background:ThreeDFace" value="<%=tbp.getGetIniTrans()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;&nbsp;&nbsp;&nbsp;Min Extents</label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=tbp.getGetMinExtent()%>" readonly /></label>
		                    		 		</td>
		                    		 	</tr>
		                    		 	<tr>
		                    		    	<td align="right" style="width: 80px;">
												 <label class="smallfont">&nbsp;&nbsp;Max Trans</label>
									 		</td>
									 		<td>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="20" style="background:ThreeDFace" value="<%=tbp.getGetMaxTrans()%>" readonly /></label>
		                    		 			<label class="smallfont">&nbsp;&nbsp;&nbsp;&nbsp;Max Extents</label>
		                    		 			<label class="smallfont">&nbsp;<input name="name" id="name" type="text" size="10" style="background:ThreeDFace" value="<%=maxExtent%>" readonly /></label>
		                    		 			<input type="checkbox" <%=strchk%>>Unlimited</input>
		                    		 		</td>
		                    		 	</tr>
		                    		 </table>
							</fieldset>
							<fieldset>
								<legend>Cluster</legend>
									<label class="smallfont">&nbsp;&nbsp;Name</label><input name="name" id="name" type="text" size="30" style="background:ThreeDFace" readonly />
		                    		<label class="smallfont">&nbsp;&nbsp;Columns</label><input name="name" id="name" type="text" size="10" style="background:ThreeDFace" readonly />
		                    		</label>
		                    		 
							</fieldset>
							<table style="border:0; width:480px;">
							<tr>
							<td style="width:70%; ">
							<fieldset style="width:90%" align="left">
							
								<legend>Duration</legend>
									<input type="checkbox">Temporary</input> <input type="checkbox">Preserve rows on commit</input>
		                    		 
							</fieldset>
							</td>
							<td style="width:30%;">
							<fieldset style="width:90%" align="right">
								<legend>Organization</legend>
									<input type="checkbox" checked readonly>Heap</input> <input type="checkbox">Index</input>
							</fieldset>
							</td>
							</tr>
							</table>
							<a align="right" width="20px" height="25" class="smallfont">&nbsp;&nbsp;Comments</a>
		                    <input name="name" id="name" type="text" size="50" style="background:ThreeDFace" value="<%=tbp.getGetComment()%>" readonly />
					</div>
					
					
					<div class="tab-page" id="tabpage_2" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_2"><img style="border:none" id='objIcoId_2' src='' align='absmiddle' /><span id="tmpImg_2" style="display:none"></span> <span id='objTitle_2'>Columns</span></h2>	
						<table style="border:0;">
		                    <tr>
		                    	<td align="right" style="width: 80px;">
		                    		<label class="smallfont">&nbsp;&nbsp;Type owner</label>
		                    	</td>
		                    	<td>
		                    		<input name="name" id="name" type="text" size="38" style="background:ThreeDFace;" value="" readonly />
		                    	</td>
		                        <td align="right" style="width: 80px;">
		                    		<label class="smallfont">&nbsp;&nbsp;Name</label>
		                    	</td>
		                    	<td>
		                    		<input name="parameters" id="parameters" type="text" size="38" style="background:ThreeDFace;" value="" readonly />
		                 		</td>
		                 	</tr>
		                 </table>
		                 
		                 <div id='resultdiv'  style="background-color:ThreeDFace; overflow:hidden; height:480px; width:95%">
		                 	<script>
		                 		var sqls="<%=sql%>";
		                 		DbObjectBean.getOther2(sqls,['Name','Type','Nullable','Default','Storage', 'Comments'], showDataHtmlD);
		                 	</script>
		                 </div>
		                 		
					</div>
					<div class="tab-page" id="tabpage_3" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_3"><img style="border:none" id='objIcoId_3' src='' align='absmiddle' /><span id="tmpImg_3" style="display:none"></span> <span id='objTitle_3'>Keys</span></h2>	
						<div id='resultdiv_keys' style="background-color:ThreeDFace; overflow:hidden; height:480px; width:95%">
		                 	<script>
		                 		var keysqls="<%=keysql%>";
		                 		DbObjectBean.getKeys(keysqls,['Name','Type','Columns','Enabled','Referencing Table','Referencing Columns','On Delete','Deferrable','Deferred', 'Last_change'], showDataHtmlKeys);
		                 	</script>
		                 </div>
					</div>
					<div class="tab-page" id="tabpage_4" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_4"><img style="border:none" id='objIcoId_4' src='' align='absmiddle' /><span id="tmpImg_4" style="display:none"></span> <span id='objTitle_4'>Checks</span></h2>	
						<div id='resultdiv_checks' style="background-color:ThreeDFace; overflow:hidden; height:90%; width:95%">
		                 	<script>
		                 		var checksqls="<%=checksql%>";
		                 		DbObjectBean.getOther2(checksqls,['Name','Condition','Enabled','Deferrable','Deferred', 'Last_change'], showDataHtmlChecks);
		                 	</script>
		                 </div>
					</div>
					<div class="tab-page" id="tabpage_5" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_5"><img style="border:none" id='objIcoId_5' src='' align='absmiddle' /><span id="tmpImg_5" style="display:none"></span> <span id='objTitle_5'>Indexes</span></h2>	
						<div id='resultdiv_indexs' style="background-color:ThreeDFace; overflow:hidden; height:90%; width:95%">
		                 	<script>
		                 		var indexsqls="<%=indexsql%>";
		                 		DbObjectBean.getIndexs(indexsqls,['Owner','Name','Type','Columns','Compress','Prefix_length','Reverse','Storage'], showDataHtmlIndexs);
		                 	</script>
		                 </div>
					</div>
					<div class="tab-page" id="tabpage_6" style=" min-height:90%; _height:90%;">
						<h2 class="tab" id="tabTitle_6"><img style="border:none" id='objIcoId_6' src='' align='absmiddle' /><span id="tmpImg_6" style="display:none"></span> <span id='objTitle_6'>Privileges</span></h2>	
						<div id='resultdiv_privileges' style="background-color:ThreeDFace; overflow:hidden; height:90%; width:95%">
		                 	<script>
		                 		var privilegesqls="<%=privilegesql%>";
		                 		DbObjectBean.getOther2(privilegesqls,['Grantee','Privilege','Grantable','Hierarchy'], showDataHtmlPrivileges);
		                 	</script>
		                 </div>
					</div>
				<%
					} else {
				%>
				<div class="tab-page" id="tabpage_1" style=" min-height:90%; _height:90%;">
				<h2 class="tab" id="tabTitle"><img style="border:none" id='objIcoId' src='' align='absmiddle' /><span id="tmpImg" style="display:none"></span> <span id='objTitle'><%=name.toLowerCase()%></span></h2>
					<div id="myTextarea" class="editor" contentEditable>
					<%=DBUtilities.showObjectView(request.getSession(),
						type, name).toString()%>
					</div>
				</div>
				<%
					}
				%>
			</div>
			<%
				if (type.equals("TABLE")) {
			%>
			<table align="center" style="border:0;" width="98%" cellpadding="0" cellspacing="0">
							<tr>
								<td align="left" height="30px" valign="bottom">
									<input type="button" value="  Apply  " onclick="" disabled>
									<input type="button" value="Refresh" onclick="" disabled>
									<input type="button" value=" Close  " onclick="closeWindowList();">
									<input type="button" value="Help" onclick="" disabled>
									<input type="button" value=" Query... " onclick="execQueryTable('<%=name%>','myTextarea')" disabled>
								</td>
							</tr>
			</table>
			
			<%
							} else {
						%> 
			<div id="foot_outputDiv"
					style="position:relative; overflow: no; background-color : ButtonFace; bottom: 0px; _bottom: -1px; height: 30px; width: 100%">
					<table border="0" id="toolBar_2" style="background: ButtonFace;"
						cellspacing="1" width="100%">
						<tr align='left'>
							<td class="coolButtonDisabled_my">
								<img id='execIsRunButton' src="../../images/exec_norun.gif" align="absmiddle" />
							</td>
							<td tabIndex="1" class="coolButtonActiveHover" onclick="">
								&
							</td>
							<td class="coolButtonActiveHover" width="5%">
								1:1
							</td>
							<td class="coolButtonActiveHover" width="95%">
								<div id="footview" style="font-family: Arial, Courier, mono;">
									&nbsp;
								</div>
							</td>
						</tr>
					</table>

			</div>
			<%
				}
			%>
		</div>
		
		<script>
			
			function addMyTextAreaKeyDown() {
							$('myTextarea').addEvent('keydown', function(event) {
								mykeydown(event, 'myTextarea');
							});
			}
			
			
			function initViewFootButton() {
				var cells1 = document.getElementById('toolBar_2').rows[0].cells;
				for (var i = 1; i <= 1; i++)
				{
					createButton(cells1[i]);
					//cells1[i].setAlwaysUp(true)
				}
				cells1[1].setToggle(true);
				
				cells1[1].setValue(true, true);
			}
			
			if ("<%=type%>" == "TABLE") {
				//暂时什么也不做
			} else {
				addMyTextAreaKeyDown();
				initViewFootButton();
				setFootView(5,'');
			}
			
			//setupAllTabs();
			function changeObjIco() {
				imgPath = "../../tree/dbimages/";
				imgIco = "";
				if ("<%=type%>" == "FUNCTION") {
					imgIco = "valid_funs.png";
				} else if ("<%=type%>" == "PROCEDURE") {
					imgIco = "valid_prcs.png";
				} else if ("<%=type%>" == "PACKAGE") {
					imgIco = "valid_pkgs.png";
				} else if ("<%=type%>" == "PACKAGE BODY") {
					imgIco = "valid_pkgs_b.png";
				} else if ("<%=type%>" == "TYPE") {
					imgIco = "valid_types.png";
				} else if ("<%=type%>" == "TYPE BODY") {
					imgIco = "valid_types_b.png";
				} else if ("<%=type%>" == "TRIGGER") {
					imgIco = "ena_trigers.png";
				} else if ("<%=type%>" == "JAVA SOURCE") {
					imgIco = "valid_javas.png";
				} else if ("<%=type%>" == "VIEW") {
					imgIco = "valid_views.png";
				} else if ("<%=type%>" == "MATERIALIZED VIEW") {
					imgIco = "mat_views.png";
				} else if ("<%=type%>" == "TABLE") {
					imgIco = "general.png";
				} 
				if ("<%=type%>" == "TABLE") {
					columnsIco = "col.png";
					keysIco = "key.png";
					checksIco = keysIco;
					indexesIco = "index.png";
					privilegesIco = "roles.png";
					$('tmpImg_1').set('text',imgPath + imgIco);
					$('tmpImg_2').set('text',imgPath + columnsIco);
					$('tmpImg_3').set('text',imgPath + keysIco);
					$('tmpImg_4').set('text',imgPath + checksIco);
					$('tmpImg_5').set('text',imgPath + indexesIco);
					$('tmpImg_6').set('text',imgPath + privilegesIco);
					$('objIcoId_1').set('src', imgPath + imgIco);
					$('objIcoId_2').set('src', imgPath + columnsIco);
					$('objIcoId_3').set('src', imgPath + keysIco);
					$('objIcoId_4').set('src', imgPath + checksIco);
					$('objIcoId_5').set('src', imgPath + indexesIco);
					$('objIcoId_6').set('src', imgPath + privilegesIco);
				} else {
					$('tmpImg').set('text',imgPath + imgIco);
					$('objIcoId').set('src', imgPath + imgIco);
				}
			}
			changeObjIco();
		</script>
	</body>
</html>