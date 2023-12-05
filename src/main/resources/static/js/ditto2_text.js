/**
 * 
 */


$(document).ready(function(){
	var $text_cancle = $("#text_bottom input:eq(1)")

	$text_cancle.click(function(){
		$(location).attr("href","file:///D:/kk/kk/WebContent/nb/backdabang2.html#")
	})	
	
	var $text_submit = $("#text_bottom input:eq(0)")
	var $text_textarea = $("#text_article textarea")
	$text_submit.click(function(){
		$text_textarea.text(new Date());
		setTimeout(function(){
			$(location).attr("href","file:///D:/kk/kk/WebContent/nb/backdabang2.html#");
		
		
		},2500)
	})
	
})
