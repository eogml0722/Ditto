/**
 * 
 */



// 이전 페이지 다음페이지 버튼.
$(document).ready(function(){
	
	var navPrevBtn = $("#main_main3_table_navPrevBtn")
	var navNextBtn = $("#main_main3_table_navNextBtn")
	var navLink_div = $("#navLink_div")
	var $navLinkSpan = navLink_div.children("span")
	var pageUrl = $(location).attr('href'); 
	var j = pageNumber(pageUrl)  //현재 페이지 숫자
	
	function pageNumber(str){
        let answer;
        str = str.replace(/[^0-9]/g, '');
        answer = parseInt(str);
        return answer;
    }
/*
$navLinkSpan.click(function(){
	for(var i=1 ; i<$navLinkSpan.length; i++){
		if(j == i){
			$(location).attr("href", "file:///D:/kk/kk/WebContent/nb/backdabang"+j+ ".html#")
		}
		
//		
	}
})
*/
navPrevBtn.click(function(){
	for(var i=1 ; i<$navLinkSpan.length; i++){
		if	(j == i){
			j -= 1
			$(location).attr("href", "file:///D:/kk/kk/WebContent/nb/backdabang"+j+".html#")
		}
	}
//		
})
navNextBtn.click(function(){
	for(var i=1 ; i<$navLinkSpan.length; i++){
		if(j == i){
			j += 1
			$(location).attr("href", "file:///D:/kk/kk/WebContent/nb/backdabang"+j+".html#")
		}
		
//		
	}
})



////////
var $navLink_div_span = $("#navLink_div > span") 
$navLink_div_span.click(function(){
	$(location).attr("href", "file:///D:/kk/kk/WebContent/nb/backdabang"+$(this).text()+".html#")
})



var $main_main3_table_textBtn = $("#main_main3_table_textBtn")

$main_main3_table_textBtn.click(function(){
	$(location).attr("href","file:///D:/kk/kk/WebContent/nb/backdabang2_text.html#")
})




})