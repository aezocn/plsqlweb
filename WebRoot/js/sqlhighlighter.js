//by Reddragonfly & Studio,2009.09.04
//Reddragonfly & Studio All Rights Reserved.

var SQLHighlighter = function() {
	this.functions	=	'abs avg case cast coalesce convert count current_timestamp current_user day isnull left '+
	                'lower month nullif replace right session_user space substring sum system_user upper user year';

	this.keywords = 'absolute action add after alter as asc at authorization begin bigint binary bit by cascade char character check '+
	               'checkpoint close collate column commit committed connect connection constraint contains continue ' +
					       'create cube current current_date current_time cursor database date deallocate dec decimal declare default '+
					       'delete desc distinct double drop dynamic else end end-exec escape except exec execute false fetch first ' +
					       'float for force foreign forward free from full function global goto grant group grouping having hour ignore index '+
					       'inner insensitive insert instead int integer intersect into is isolation key last level load local max min ' +
					       'minute modify move name national nchar next no numeric of off on only open option order out output partial password '+
					       'precision prepare primary prior privileges procedure public read real references relative repeatable ' +
					       'restrict return returns revoke rollback rollup rows rule schema scroll second section select sequence serializable '+
					       'set size smallint static statistics table temp temporary then time timestamp to top transaction ' +
					       'translation trigger true truncate uncommitted union unique update values varchar varying view when where with work';

	this.operators = 'all and any between cross in join like not null or outer some';
	
	this.regexLib = [
		{ regex: new RegExp("\\b" + this.functions.replace(/ /g, "\\b|\\b") + "\\b", 'gmi'),css: 'functions' },	     // functions
		{ regex: new RegExp("\\b" + this.keywords.replace(/ /g, "\\b|\\b") + "\\b", 'gmi'),	css: 'keywords' },       // keywords
		{ regex: new RegExp("\\b" + this.operators.replace(/ /g, "\\b|\\b") + "\\b", 'gmi'),css: 'operators' }	     // operators
	];
	
	this.getMatches = function(str) {
		var matches = new Array();
	  for (var i = 0; i < this.regexLib.length; i++) {
	  	var oneregex = this.regexLib[i].regex;
	  	var onecss = this.regexLib[i].css;
	  	var temp = null;
		  while ((temp = oneregex.exec(str)) != null) {
			  matches[matches.length] = new MatchObj(temp[0],onecss);
		  }
		}
		return matches;
	}
	
	this.highlight = function(divObj){
		var pos;
		var caretPos;
		var ie = /msie/i.test(navigator.userAgent);
		if (ie) {
			pos = document.selection.createRange();
			pos.text = "caret_pos";   //设置光标所在位置
			caretPos = divObj.innerText.indexOf("caret_pos");
		} else {
			
			//pos = window.getSelection().toString();
    	  	//pos = "caret_pos";
    	  	//$(divObj).set('text',$(divObj).get('text')+'caret_pos');
    	  	//caretPos = $(divObj).get('text').indexOf("caret_pos");
    	  	
		}
		
		var str = divObj.innerHTML;
		str = str.replace(new RegExp("<p>","gmi"),"");
		str = str.replace(new RegExp("</p>","gmi"),"<br>");
		if(str.toLowerCase().lastIndexOf("<br>") != -1 && str.toLowerCase().lastIndexOf("<br>") == str.length-4) str = str.substring(0,str.length-4);
		var subS = str.substring(0,str.indexOf("caret_pos")).toLowerCase();
		var brcount = 0;
		var startIndex = 0;
		while((startIndex = subS.indexOf("<br>",startIndex)) != -1){
			brcount = brcount + 1;
			startIndex = startIndex + 4;
		}
		caretPos = caretPos - brcount;
		
		str = str.replace("caret_pos","");
		str = str.replace(new RegExp("<span class=([\"\'a-z]+)>","gmi"),"");
		str = str.replace(new RegExp("</span>","gmi"),"");
		var matches = this.getMatches(str);
		for(var i = 0; i < matches.length; i++) {
			var thismatch = matches[i];
			var alreadyReplace = false;
			for(var j = 0;j < i;j++){
				if(thismatch.value == matches[j].value){
					 alreadyReplace = true;
					 break;
				}
			}
			if(alreadyReplace) continue;
			str = str.replace(new RegExp("\\b"+thismatch.value+"\\b","gm"), "<span class='"+thismatch.css+"'>"+thismatch.value+"</span>");
		}
		divObj.innerHTML = str;
		
		pos.collapse(true);
		pos.moveStart('character',caretPos);
		pos.select();
	}
}

function MatchObj(thisvalue,thiscss){
	this.value = thisvalue;
	this.css = thiscss;
};
