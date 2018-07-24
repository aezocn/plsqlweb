//<script>/*
 * This script was created by Erik Arvidsson (erik@eae.net)
 * for WebFX (http://webfx.eae.net)
 * Copyright 2001
 * 
 * For usage see license at http://webfx.eae.net/license.html	
 *
 * Created:		2001-01-12
 * Updates:		2001-11-20	Added hover mode support and removed Opera focus hacks *				2001-12-20	Added auto positioning and some properties to support this
 *				2002-08-13	toString used ' for attributes. Changed to " to allow in args
 */ 
// check browsers
var ua = navigator.userAgent;
var opera = /opera [56789]|opera\/[56789]/i.test(ua);var ie = !opera && /MSIE/.test(ua);
var ie50 = ie && /MSIE 5\.[01234]/.test(ua);var ie6 = ie && /MSIE [6789]/.test(ua);
var ieBox = ie && (document.compatMode == null || document.compatMode != "CSS1Compat");
var moz = !opera && /gecko/i.test(ua);var nn6 = !opera && /netscape.*6\./i.test(ua);// define the default values
webfxMenuDefaultWidth			= 100;

webfxMenuDefaultBorderLeft		= 1;webfxMenuDefaultBorderRight		= 1;webfxMenuDefaultBorderTop		= 1;webfxMenuDefaultBorderBottom	= 1;
webfxMenuDefaultPaddingLeft		= 1;webfxMenuDefaultPaddingRight	= 1;
webfxMenuDefaultPaddingTop		= 1;webfxMenuDefaultPaddingBottom	= 1;
webfxMenuDefaultShadowLeft		= 0;
webfxMenuDefaultShadowRight		= ie && !ie50 && /win32/i.test(navigator.platform) ? 4 :0;
webfxMenuDefaultShadowTop		= 0;
webfxMenuDefaultShadowBottom	= ie && !ie50 && /win32/i.test(navigator.platform) ? 4 : 0;
webfxMenuItemDefaultHeight		= 18;
webfxMenuItemDefaultText		= "Untitled";
webfxMenuItemDefaultHref		= "javascript:void(0)";

webfxMenuSeparatorDefaultHeight	= 6;

webfxMenuDefaultEmptyText		= "Empty";
webfxMenuDefaultUseAutoPosition	= nn6 ? false : true;

// other global constants
webfxMenuImagePath				= "../images/";

wfMUH				= opera ? true : false;webfxMenuHideTime				= 100;
webfxMenuShowTime				= 100;

var wFxmhl = {
	idCounter		:	0,
	idPrefix		:	"Rfme-",	all				:	{},	getId			:	function () { return this.idPrefix + this.idCounter++; },
	overMenuItem	:	function (oItem) {		if (this.showTimeout != null)			window.clearTimeout(this.showTimeout);
		if (this.hideTimeout != null)			window.clearTimeout(this.hideTimeout);
		var jsItem = this.all[oItem.id];
		if (webfxMenuShowTime <= 0)
			this._over(jsItem);
		else			//this.showTimeout = window.setTimeout(function () { wFxmhl._over(jsItem) ; }, webfxMenuShowTime);
			//I hate IE5.0 because the piece of shit crashes when using setTimeout with a function object			this.showTimeout = window.setTimeout("wFxmhl._over(wFxmhl.all['" + jsItem.id + "'])", webfxMenuShowTime);
	},
	outMenuItem	:	function (oItem) {
		if (this.showTimeout != null)			window.clearTimeout(this.showTimeout);
		if (this.hideTimeout != null)			window.clearTimeout(this.hideTimeout);
		var jsItem = this.all[oItem.id];
		if (webfxMenuHideTime <= 0)
			this._out(jsItem);
		else			//this.hideTimeout = window.setTimeout(function () { wFxmhl._out(jsItem) ; }, webfxMenuHideTime);
			this.hideTimeout = window.setTimeout("wFxmhl._out(wFxmhl.all['" + jsItem.id + "'])", webfxMenuHideTime);	},	blurMenu		:	function (oMenuItem) {
		window.setTimeout("wFxmhl.all[\"" + oMenuItem.id + "\"].subMenu.hide();", webfxMenuHideTime);
	},
	_over	:	function (jsItem) {		if (jsItem.subMenu) {
			jsItem.parentMenu.hideAllSubs();			jsItem.subMenu.show();
		}
		else
			jsItem.parentMenu.hideAllSubs();	},	_out	:	function (jsItem) {
		// find top most menu
		var root = jsItem;
		var m;		if (root instanceof WebFXMenuButton)			m = root.subMenu;		else {
			m = jsItem.parentMenu;
			while (m.parentMenu != null && !(m.parentMenu instanceof WebFXMenuBar))				m = m.parentMenu;		}		if (m != null)	
			m.hide();		},	hideMenu	:	function (menu) {
		if (this.showTimeout != null)			window.clearTimeout(this.showTimeout);
		if (this.hideTimeout != null)			window.clearTimeout(this.hideTimeout);
		this.hideTimeout = window.setTimeout("wFxmhl.all['" + menu.id + "'].hide()", webfxMenuHideTime);	},	showMenu	:	function (menu, src, dir) {		if (this.showTimeout != null)			window.clearTimeout(this.showTimeout);
		if (this.hideTimeout != null)			window.clearTimeout(this.hideTimeout);
		if (arguments.length < 3)			dir = "vertical";
				menu.show(src, dir);
	}
};

function WebFXMenu() {
	this._menuItems	= [];
	this._subMenus	= [];
	this.id			= wFxmhl.getId();
	this.top		= 0;
	this.left		= 0;
	this.shown		= false;	this.parentMenu	= null;
	wFxmhl.all[this.id] = this;
}
WebFXMenu.prototype.width			= webfxMenuDefaultWidth;
WebFXMenu.prototype.emptyText		= webfxMenuDefaultEmptyText;WebFXMenu.prototype.useAutoPosition	= webfxMenuDefaultUseAutoPosition;
WebFXMenu.prototype.borderLeft		= webfxMenuDefaultBorderLeft;
WebFXMenu.prototype.borderRight		= webfxMenuDefaultBorderRight;
WebFXMenu.prototype.borderTop		= webfxMenuDefaultBorderTop;
WebFXMenu.prototype.borderBottom	= webfxMenuDefaultBorderBottom;
WebFXMenu.prototype.paddingLeft		= webfxMenuDefaultPaddingLeft;
WebFXMenu.prototype.paddingRight	= webfxMenuDefaultPaddingRight;WebFXMenu.prototype.paddingTop		= webfxMenuDefaultPaddingTop;WebFXMenu.prototype.paddingBottom	= webfxMenuDefaultPaddingBottom;

WebFXMenu.prototype.shadowLeft		= webfxMenuDefaultShadowLeft;
WebFXMenu.prototype.shadowRight		= webfxMenuDefaultShadowRight;
WebFXMenu.prototype.shadowTop		= webfxMenuDefaultShadowTop;
WebFXMenu.prototype.shadowBottom	= webfxMenuDefaultShadowBottom;

WebFXMenu.prototype.add = function (menuItem) {
	this._menuItems[this._menuItems.length] = menuItem;
	if (menuItem.subMenu) {
		this._subMenus[this._subMenus.length] = menuItem.subMenu;
		menuItem.subMenu.parentMenu = this;	}
	
	menuItem.parentMenu = this;
};
WebFXMenu.prototype.show = function (relObj, sDir) {
	if (this.useAutoPosition)		this.position(relObj, sDir);	
	var divElement = document.getElementById(this.id);
	divElement.style.left = opera ? this.left : this.left + "px";
	divElement.style.top = opera ? this.top : this.top + "px";
	divElement.style.visibility = "visible";	this.shown = true;
	if (this.parentMenu)
		this.parentMenu.show();};
WebFXMenu.prototype.hide = function () {
	this.hideAllSubs();
	var divElement = document.getElementById(this.id);
	divElement.style.visibility = "hidden";	this.shown = false;
};
WebFXMenu.prototype.hideAllSubs = function () {
	for (var i = 0; i < this._subMenus.length; i++) {
		if (this._subMenus[i].shown)
			this._subMenus[i].hide();
	}
};
WebFXMenu.prototype.toString = function () {
	var top = this.top + this.borderTop + this.paddingTop;
	var str = "<div id='" + this.id + "' class='webfx-menu' style='" + 
	"width:" + (!ieBox  ?		this.width - this.borderLeft - this.paddingLeft - this.borderRight - this.paddingRight  : 		this.width) + "px;" +
	(this.useAutoPosition ?		"left:" + this.left + "px;" + "top:" + this.top + "px;" :		"") +
	(ie50 ? "filter: none;" : "") +
	"'>";
	
	if (this._menuItems.length == 0) {
		str +=	"<span class='webfx-menu-empty'>" + this.emptyText + "</span>";
	}
	else {	
		// loop through all menuItems
		for (var i = 0; i < this._menuItems.length; i++) {
			var mi = this._menuItems[i];
			str += mi;			if (!this.useAutoPosition) {
				if (mi.subMenu && !mi.subMenu.useAutoPosition)
					mi.subMenu.top = top - mi.subMenu.borderTop - mi.subMenu.paddingTop;
				top += mi.height;			}
		}

	}
	
	str += "</div>";

	for (var i = 0; i < this._subMenus.length; i++) {
		this._subMenus[i].left = this.left + this.width - this._subMenus[i].borderLeft;
		str += this._subMenus[i];
	}
	
	return str;
};// WebFXMenu.prototype.position defined later
function WFXMI(sText, sHref, sToolTip, oSubMenu) {
	this.text = sText || webfxMenuItemDefaultText;
	this.href = (sHref == null || sHref == "") ? webfxMenuItemDefaultHref : sHref;
	this.subMenu = oSubMenu;
	if (oSubMenu)
		oSubMenu.parentMenuItem = this;
	this.toolTip = sToolTip;
	this.id = wFxmhl.getId();
	wFxmhl.all[this.id] = this;
};
WFXMI.prototype.height = webfxMenuItemDefaultHeight;
WFXMI.prototype.toString = function () { //针对有时点击菜单后this.href脚本不能执行的问题，现将this.href转移至onclick事件，这就要求传进来的href必须是脚本而不能再是简单的url(jeanwendy,2009.07.16)
	return	"<a" +
			" id='" + this.id + "'" +
			//" href=\"" + this.href + "\"" +			" href=\"#\"" +			" onclick=\""+this.href+"\"" + 
			(this.toolTip ? " title=\"" + this.toolTip + "\"" : "") +
			" onmouseover='wFxmhl.overMenuItem(this)'" +			(wfMUH ? " onmouseout='wFxmhl.outMenuItem(this)'" : "") +			(this.subMenu ? " unselectable='on' tabindex='-1'" : "") +
			">" +
			(this.subMenu ? "<img class='arrow' src=\"" + webfxMenuImagePath + "arrow.right.png\">" : "") +
			this.text + 
			"</a>";
};


function WebFXMenuSeparator() {
	this.id = wFxmhl.getId();
	wFxmhl.all[this.id] = this;
};
WebFXMenuSeparator.prototype.height = webfxMenuSeparatorDefaultHeight;
WebFXMenuSeparator.prototype.toString = function () {
	return	"<div" +
			" id='" + this.id + "'" +			(wfMUH ? 
			" onmouseover='wFxmhl.overMenuItem(this)'" +			" onmouseout='wFxmhl.outMenuItem(this)'"
			:
			"") +			"></div>"
};

function WebFXMenuBar() {
	this._parentConstructor = WebFXMenu;
	this._parentConstructor();
}
WebFXMenuBar.prototype = new WebFXMenu;
WebFXMenuBar.prototype.toString = function () {
	var str = "<div id='" + this.id + "' class='webfx-menu-bar'>";
	
	// loop through all menuButtons
	for (var i = 0; i < this._menuItems.length; i++)
		str += this._menuItems[i];
	
	str += "</div>";

	for (var i = 0; i < this._subMenus.length; i++)
		str += this._subMenus[i];
	
	return str;
};

function WebFXMenuButton(sText, sHref, sToolTip, oSubMenu) {
	this._parentConstructor = WFXMI;
	this._parentConstructor(sText, sHref, sToolTip, oSubMenu);
}
WebFXMenuButton.prototype = new WFXMI;
WebFXMenuButton.prototype.toString = function () {
	return	"<a" +
			" id='" + this.id + "'" +
			" href='" + this.href + "'" +
			(this.toolTip ? " title='" + this.toolTip + "'" : "") +			(wfMUH ?				(" onmouseover='wFxmhl.overMenuItem(this)'" +				" onmouseout='wFxmhl.outMenuItem(this)'") :
				(
					" onfocus='wFxmhl.overMenuItem(this)'" +
					(this.subMenu ?						" onblur='wFxmhl.blurMenu(this)'" :						""					)
				)) +
			">" +
			this.text + 
			(this.subMenu ? " <img class='arrow' src='" + webfxMenuImagePath + "arrow.down.png' align='absmiddle'>" : "") +				
			"</a>";
};


/* Position functions */

function getInnerLeft(el) {
	if (el == null) return 0;
	if (ieBox && el == document.body || !ieBox && el == document.documentElement) return 0;
	return getLeft(el) + getBorderLeft(el);
}

function getLeft(el) {
	if (el == null) return 0;
	return el.offsetLeft + getInnerLeft(el.offsetParent);
}

function getInnerTop(el) {
	if (el == null) return 0;
	if (ieBox && el == document.body || !ieBox && el == document.documentElement) return 0;
	return getTop(el) + getBorderTop(el);
}

function getTop(el) {
	if (el == null) return 0;
	return el.offsetTop + getInnerTop(el.offsetParent);
}

function getBorderLeft(el) {
	return ie ?
		el.clientLeft :
		parseInt(window.getComputedStyle(el, null).getPropertyValue("border-left-width"));
}

function getBorderTop(el) {
	return ie ?
		el.clientTop :
		parseInt(window.getComputedStyle(el, null).getPropertyValue("border-top-width"));
}

function opera_getLeft(el) {
	if (el == null) return 0;
	return el.offsetLeft + opera_getLeft(el.offsetParent);
}

function opera_getTop(el) {
	if (el == null) return 0;
	return el.offsetTop + opera_getTop(el.offsetParent);
}

function getOuterRect(el) {
	return {
		left:	(opera ? opera_getLeft(el) : getLeft(el)),
		top:	(opera ? opera_getTop(el) : getTop(el)),
		width:	el.offsetWidth,
		height:	el.offsetHeight
	};
}

// mozilla bug! scrollbars not included in innerWidth/height
function getDocumentRect(el) {
	return {
		left:	0,
		top:	0,
		width:	(ie ?
					(ieBox ? document.body.clientWidth : document.documentElement.clientWidth) :
					window.innerWidth
				),
		height:	(ie ?
					(ieBox ? document.body.clientHeight : document.documentElement.clientHeight) :
					window.innerHeight
				)
	};
}

function getScrollPos(el) {
	return {
		left:	(ie ?
					(ieBox ? document.body.scrollLeft : document.documentElement.scrollLeft) :
					window.pageXOffset
				),
		top:	(ie ?
					(ieBox ? document.body.scrollTop : document.documentElement.scrollTop) :
					window.pageYOffset
				)
	};
}
/* end position functions */
WebFXMenu.prototype.position = function (relEl, sDir) {
	var dir = sDir;	// find parent item rectangle, piRect
	var piRect;	if (!relEl) {
		var pi = this.parentMenuItem;		if (!this.parentMenuItem)			return;
		
		relEl = document.getElementById(pi.id);		if (dir == null)			dir = pi instanceof WebFXMenuButton ? "vertical" : "horizontal";				piRect = getOuterRect(relEl);
	}	else if (relEl.left != null && relEl.top != null && relEl.width != null && relEl.height != null) {	// got a rect		piRect = relEl;	}	else		piRect = getOuterRect(relEl);
		var menuEl = document.getElementById(this.id);
	var menuRect = getOuterRect(menuEl);
	var docRect = getDocumentRect();
	var scrollPos = getScrollPos();	var pMenu = this.parentMenu;
		if (dir == "vertical") {
		if (piRect.left + menuRect.width - scrollPos.left <= docRect.width)			this.left = piRect.left;		else if (docRect.width >= menuRect.width)			this.left = docRect.width + scrollPos.left - menuRect.width;		else			this.left = scrollPos.left;					if (piRect.top + piRect.height + menuRect.height <= docRect.height + scrollPos.top)
			this.top = piRect.top + piRect.height;
		else if (piRect.top - menuRect.height >= scrollPos.top)
			this.top = piRect.top - menuRect.height;
		else if (docRect.height >= menuRect.height)
			this.top = docRect.height + scrollPos.top - menuRect.height;
		else
			this.top = scrollPos.top;	}
	else {		if (piRect.top + menuRect.height - this.borderTop - this.paddingTop <= docRect.height + scrollPos.top)
			this.top = piRect.top - this.borderTop - this.paddingTop;
		else if (piRect.top + piRect.height - menuRect.height + this.borderTop + this.paddingTop >= 0)
			this.top = piRect.top + piRect.height - menuRect.height + this.borderBottom + this.paddingBottom + this.shadowBottom;
		else if (docRect.height >= menuRect.height)
			this.top = docRect.height + scrollPos.top - menuRect.height;
		else
			this.top = scrollPos.top;

		var pMenuPaddingLeft = pMenu ? pMenu.paddingLeft : 0;
		var pMenuBorderLeft = pMenu ? pMenu.borderLeft : 0;
		var pMenuPaddingRight = pMenu ? pMenu.paddingRight : 0;
		var pMenuBorderRight = pMenu ? pMenu.borderRight : 0;
		
		if (piRect.left + piRect.width + menuRect.width + pMenuPaddingRight +
			pMenuBorderRight - this.borderLeft + this.shadowRight <= docRect.width + scrollPos.left)
			this.left = piRect.left + piRect.width + pMenuPaddingRight + pMenuBorderRight - this.borderLeft;
		else if (piRect.left - menuRect.width - pMenuPaddingLeft - pMenuBorderLeft + this.borderRight + this.shadowRight >= 0)
			this.left = piRect.left - menuRect.width - pMenuPaddingLeft - pMenuBorderLeft + this.borderRight + this.shadowRight;
		else if (docRect.width >= menuRect.width)
			this.left = docRect.width  + scrollPos.left - menuRect.width;
		else
			this.left = scrollPos.left;
	}
};