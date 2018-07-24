<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.reddragonfly.iplsqldevj.bean.UserBean"%>

<%	
    UserBean ub = (UserBean)session.getAttribute("user");
    //phanrider 2009-07-27 改了jeanwendy此处的tree和提交的全局标志在同一个页面，增加了lefttop.jsp
    //更改了显示方式，使页面显示更人性化，超过范围拖动div时，右边工具条不再连上面的下拉框一起滚动
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>tree</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<META HTTP-EQUIV="pragma"   CONTENT="no-cache">   
        <META HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">   
        <META HTTP-EQUIV="expires"   CONTENT="Mon,   23   Jan   1978   12:52:30   GMT">
		<link type="text/css" rel="stylesheet" href="../css/xtree2.links.css">
		<link type="text/css" rel="stylesheet" href="../css/xtree2.css">
		<link type="text/css" rel="stylesheet" href="../css/xmenu.windows.css">
		<script type="text/javascript" src="../js/xtree2.js"></script>
		<script type="text/javascript" src="../js/xloadtree2.js"></script>
		<script type="text/javascript" src="../js/cssexpr.js"></script>
		<script type="text/javascript" src="../js/xmenu.js"></script>
		<script type="text/javascript" src="../js/others.js"></script>
		<style>
			select{vertical-align:middle;height:20px;}
			form {margin:0;}
		</style>
	</head>
	
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="background-color: FFFFFF" oncontextmenu="return false">
	 <div style="background-color: FFFFFF; height:100%; overflow: no;">
		<script type="text/javascript">
		        //展示树
            	var tree = new WebFXLoadTree("<%=ub.getDbglobal()?"All objects":"My  objects" %>", "showTree.action?type=root&name=&field=");
            	tree.write();
            	tree.expand();	//展开根节点第一层
            	tree.setShowRootNode('b'); //当为b时显示根节点，否则不显示
            	var menuMap = new MenuMap(10); //定义MenuType对象与WebFXMenu对象(WebFXMenu是第三方公司封装的菜单类)的键值对，缓存个数为10个
            	
            	//定义显示和隐藏菜单的方法
                var myMenu = new WebFXMenu;
                document.write(myMenu);
                //该方法会用指定的菜单替换原来文档对象模型中的菜单，并将之显示出来
            	function showAppointedMenu(type,name,field,e){
                    var isie=document.all;
                    var rightnum=isie?event.button:e.which;
                    if ((isie && rightnum==2) || (!isie && rightnum==3)){        //IE or firefox
                        //获取鼠标位置
		            	var x;
		            	var y;
		            	if(e.pageX || e.pageY){
		            	   x = e.pageX;
		            	   y = e.pageY;
		            	}else{
		            	   x = e.clientX + document.body.scrollLeft - document.body.clientLeft;
		            	   y = e.clientY + document.body.scrollTop - document.body.clientTop;
		            	}
		            	//以上为获取当前鼠标位置
		            	
		            	//获取原来文档对象模型中的菜单对象并递归其所有子菜单对象，将之删除
		            	deleteMenuElement(myMenu);
		            	
                        var menuType = new MenuType(type,name,field);
                        myMenu = menuMap.get(menuType);
                        if(myMenu == undefined){    //所请求的菜单未缓存在客户端
							var xmlhttp;
						    try{
							  	xmlhttp=new ActiveXObject('Msxml2.XMLHTTP');
							}catch(e){
								try{
									 xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
								}catch(e){
									 try{
									      xmlhttp=new XMLHttpRequest();
									 }catch(e){
								     }
							     }
						    }
						    xmlhttp.open("get","showMenu.action?type="+type+"&name="+name+"&field="+field);
							xmlhttp.onreadystatechange=function(){
								if(xmlhttp.readyState==4){
									if(xmlhttp.status==200){//获取状态
									    myMenu = new WebFXMenu;
									    var result=xmlhttp.responseText;　//得到返回值
									    eval(result);
						                menuMap.put(menuType,myMenu);
					            	    insertHtml('beforeEnd',document.body,myMenu.toString());
						            	myMenu.left = x;
						            	myMenu.top = y;
						            	showMenu();
									}else{
										//alert("网络连接失败，无法获取菜单数据");
										alert("Network connection fails, can not access the menu data.");
									}
								}
							}
							xmlhttp.send(null);
                        }else{
		            	    insertHtml('beforeEnd',document.body,myMenu.toString());
			            	myMenu.left = x;
			            	myMenu.top = y;
			            	showMenu();
                        }
                    }
            	}
            	//将菜单所对应的文档对象模型中的对象删除，并递归其所有子菜单对象，执行相同的操作
            	function deleteMenuElement(menuObj){
	            	var divElement = document.getElementById(menuObj.id);
	            	divElement.parentNode.removeChild(divElement);
					for (var i = 0; i < menuObj._subMenus.length; i++) {
					   deleteMenuElement(menuObj._subMenus[i]);
					}
            	}
            	//该方法只对当前的菜单做简单的显示，并不会在文档对象模型中创建新的菜单
            	function showMenu(){
            	    wFxmhl.showMenu(myMenu);
            	}
            	//该方法只对当前的菜单做简单地隐藏，并不会将其从文档对象模型中删除
            	function hideMenu(){
            	    wFxmhl.hideMenu(myMenu);
            	}
            	
            	//定义菜单类型类，该实例封装了菜单的一些不同的特征，用于区别不同的菜单
            	function MenuType(type,name,field){
            	   if(!type instanceof String) throw new Error("the first parameter for MenuType must be a string");
            	   if(!name instanceof String) throw new Error("the second parameter for MenuType must be a string");
            	   if(!field instanceof String) throw new Error("the third parameter for MenuType must be a string");
            	   this.type = type;
            	   this.name = name;
            	   this.field = field;
            	   this.equals = function(anotherMenuType){
            	      if(anotherMenuType instanceof MenuType){
            	          //菜单类型的判断现遵循以下原则：(by jeanwendy)
            	          //1.同一类型同一域的菜单是相同的(这包含了域为空即类型本身的菜单)，而且考虑类型的名称是否相同
            	          //2.尽管在大多数情况下，同一类型不同域的菜单也是相同的，但存在特例，故这里判断为不相同，不得已而为之
            	          if(anotherMenuType.type == this.type && anotherMenuType.field == this.field && anotherMenuType.name == this.name) return true;
            	          else return false;
            	      }else{
            	         throw new Error("the parameter for equals must be a type of MenuType");
            	      }
            	   }
            	}
            	
            	//定义MenuType对象与WebFXMenu对象(WebFXMenu是第三方公司封装的菜单类)的键值对类型，size指定键值对个数，若超过此值，先前被加入的键值对将被移除
            	function MenuMap(size){
            	   if(!size instanceof Number) throw new Error("the parameter for MenuMap must be a number");
            	   if(size <= 0) throw new Error("the parameter for MenuMap must be great than zero");
            	   this.size = size;
            	   this.keyArray = new Array(size);
            	   this.valueArray = new Array(size);
            	   this.get = function(menuTypeObj){
            	      if(!menuTypeObj instanceof MenuType) throw new Error("the parameter for get must be a type of MenuType");
            	      for(var i = 0; i < this.keyArray.length;i++){
            	         if(this.keyArray[i] != undefined){
            	            if(this.keyArray[i].equals(menuTypeObj)) return this.valueArray[i];
            	         }else break;
            	      }
            	      return undefined;
            	   }
            	   this.put = function(menuTypeObj,menuObj){
            	      if(!menuTypeObj instanceof MenuType) throw new Error("the first parameter for put must be a type of MenuType");
            	      if(!menuObj instanceof WebFXMenu) throw new Error("the second parameter for put must be a type of WebFXMenu");
            	      var index = -1;
            	      for(var i = 0; i < this.keyArray.length;i++){
            	         if(this.keyArray[i] != undefined){
            	            if(this.keyArray[i].equals(menuTypeObj)){
            	               this.valueArray[i] = menuObj;
            	               return;
            	            }
            	         }else{
            	            index = i;
            	            break;
            	         }
            	      }
            	      if(index != -1){
            	         this.keyArray[index] = menuTypeObj;
            	         this.valueArray[index] = menuObj;
            	      }else{
            	         for(var i = 0;i < this.size-1;i++){
            	            this.keyArray[i] = this.keyArray[i+1];
            	            this.valueArray[i] = this.valueArray[i+1];
            	         }
            	         this.keyArray[this.size-1] = menuTypeObj;
            	         this.valueArray[this.size-1] = menuObj;
            	      }
            	   }
            	}
            	
            	//insertAdjacentHTML方法功能的实现，该方法兼容于IE和firefox
				function insertHtml(where, el, html) {
					where = where.toLowerCase();
					if (el.insertAdjacentHTML) {
						switch (where) {
						  case "beforebegin":
							el.insertAdjacentHTML("BeforeBegin", html);
							return el.previousSibling;
						  case "afterbegin":
							el.insertAdjacentHTML("AfterBegin", html);
							return el.firstChild;
						  case "beforeend":
							el.insertAdjacentHTML("BeforeEnd", html);
							return el.lastChild;
						  case "afterend":
							el.insertAdjacentHTML("AfterEnd", html);
							return el.nextSibling;
						}
						throw "Illegal insertion point -> \"" + where + "\"";
					}
					var range = el.ownerDocument.createRange();
					var frag;
					switch (where) {
					  case "beforebegin":
						range.setStartBefore(el);
						frag = range.createContextualFragment(html);
						el.parentNode.insertBefore(frag, el);
						return el.previousSibling;
					  case "afterbegin":
						if (el.firstChild) {
							range.setStartBefore(el.firstChild);
							frag = range.createContextualFragment(html);
							el.insertBefore(frag, el.firstChild);
							return el.firstChild;
						} else {
							el.innerHTML = html;
							return el.firstChild;
						}
					  case "beforeend":
						if (el.lastChild) {
							range.setStartAfter(el.lastChild);
							frag = range.createContextualFragment(html);
							el.appendChild(frag);
							return el.lastChild;
						} else {
							el.innerHTML = html;
							return el.lastChild;
						}
					  case "afterend":
						range.setStartAfter(el);
						frag = range.createContextualFragment(html);
						el.parentNode.insertBefore(frag, el.nextSibling);
						return el.nextSibling;
					}
					throw "Illegal insertion point -> \"" + where + "\"";
				}
				
				var selectedNote = null;
         </script>
        </div>
	</body>
</html>
