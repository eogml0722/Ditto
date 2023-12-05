/**
 *
 */
$(document).ready(function(){
	
var sliderWrapper = document.getElementById("main_main3")// #main_main3
var sliderContainer = document.getElementsByClassName("main_main3_article1")//#main_main3_article1 //ul
var slides = document.getElementsByClassName("main_main3_article1_div")//.main_main3_article1_div // li
var slideCount = slides.length; //슬라이드 개수 // li 갯수
var currentIndex = 0;
var topHeight = 0;
var navPrev = document.getElementById("prev")
var navNext = document.getElementById("next")


for(var i=0; i<slideCount; i++){
	slides[i].style.left = i*34 + "%";
}


//요소의 높이
//offset height
topHeight = slides.offsetHeight;

//가장 높은 요소의 높이
function callTallestSlide(){
	for(var i=0; i<slideCount; i++){
		if(slides[i].offsetHeight> topHeight){
			topHeight = slides[i].offsetHeight;
		}
	}
	sliderWrapper[0].style.height = topHeight + "px";
	sliderContainer[0].style.height = topHeight+ "px";
}




//main3 이미지 슬라이드 버튼

navPrev.addEventListener("click",function(){
	if(currentIndex > 0){
		goToSlide(currentIndex -1);
	}else{
//		goToSlide(slideCount-3)
	}
})	
navNext.addEventListener("click",function(){
	if(currentIndex < slideCount-3){
		goToSlide(currentIndex +1);
	}else{
//		goToSlide(0)
	}
})
	


function goToSlide(idx){
	sliderContainer[0].style.left = idx * -34 + "%";
	currentIndex = idx;
}





//main3 이미지 상세설명

//var $main_main3_article1_div = $(".main_main3_article1_div")
var $description1 = $(".main_main3_article1_description")
var description1_on = false;




$description1.on("click",function(){
	if(description1_on == false){
	$description1.css("opacity","0");
	$(this).css("opacity","0.7");	
	description1_on = true;
	}else{
		$(this).css("opacity","0");
		description1_on = false;
	}
	
})

/* 탑버튼 기능 */

/*//원래 탑버튼 기능
function topFunction(){
	window.scrollTop(0,0);
}
*/

var topbtn = $("#topbtn")
topbtn.click(function(){
	$(window).scrollTop(0);
})


window.onscroll=function(){scrollFunction()};
function scrollFunction(){
	if(document.body.scrollTop > 110 ||
	document.documentElement.scrollTop > 110){
		document.getElementById("topbtn").style.display="block";
		}
		else{document.getElementById("topbtn").style.display="none";
	}
}

/*로그인 모달 기능*/

var open = () => { //open했을때 클래스네임 hidden을 지운다. (css에 hiddne을 display:none;으로 적용시켜놓음)
	document.querySelector(".modal").classList.remove("hidden");
}

var close = () => { //close했을때 클레스네임 hidden을 추가한다. 
	document.querySelector(".modal").classList.add("hidden");
}

document.querySelector(".headbtn1").addEventListener("click", open);
document.querySelector("#closebtn4").addEventListener("click", close);

/*이용약관*/

function selectAll(selectAll) {
	var checkboxes = document.getElementsByName("agreements");
	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

//필수약관동의 유효성검사

function CheckForm(tos){
	var termOfService = document.frmTos.termOfService.checked;
	var privacy = document.frmTos.privacy.checked;

	if(!termOfService){
		alert("필수약관에 동의해 주세요.");
		return false;
	}
	if(!privacy){
		alert("필수약관에 동의해 주세요");
		return false;
	}
}

//모달팝업창 열기,닫기

var open = () => {  //open일때 클래스네임 'hidden2'를 지운다.
	document.querySelector(".modal2").classList.remove("hidden2");
}

var close = () => { //close일때 클래스네임 'hidden2'를 추가한다.
	document.querySelector(".modal2").classList.add("hidden2");
}

document.querySelector(".headbtn2").addEventListener("click", open); //열기버튼 클릭했을때
document.querySelector("#closebtn2").addEventListener("click", close); //닫기버튼 클릭했을때



//장바구니 기능







var price = $(".price")
var quantityOption = $(".quantity option")
var quantity = $(".quantity")
var amount = $(".amount")
var productName = $(".productName")
var	productImg = $(".productImg")
var totalPrice = $("#totalPrice")
var chkBox = $(".chkBox")





var list = $("#main_main3_article2 ul li")
var listImg = $("#main_main3_article2 ul li img")
var $listImg = $("#main_main3_article2 ul li img")
var $list = $("#main_main3_article2 ul li")
var optionValue = 0



$list.click(function(){
	
		var i = $(this).index();
		
		//상품 금액
		price[i].value = Number($(this).val())
			
		//상품 갯수
		optionValue = Number(optionValue) + Number(1)
		
		//i 번째 리스트를 클릭했을때 
		//i 번째 셀렉트 옵션에
		//수량을 1 늘린다.
//		for(var t=0 ; t<$list.length ; t++){
//		if( t == i ){
//		quantity.index(t)
//		console.log(quantity.index(t))
		
		
		
		for(var j=0 ; j<quantityOption.length; j++){
			let result ={};
			if(quantityOption[j].value == optionValue){	
//				quantity[i].childNodes[j].selected = true;
			console.log(quantity[i].options[j])	
			}
		}
//	}
//}
		
		
		
		
//		optionValue = Number(optionValue) + Number(1)
//		console.log(optionValue)
//		console.log($(".quantity[i] option:selected:eq(optionValue)").val(optionValue))
//		$("quantity option:selected:eq(optionValue)").val(optionValue).attr("selected", "selected");
//		
//		console.log()
		//클릭했을때 벨류가 오른다.
		//셀렉트 박스를 클릭 했을때 값 = 벨류값
		
		//상품 총 금액
//		amount[i].value = Number(price[i].value) * Number(quantity[i].value)


		//상품 이름
		productName[i].innerHTML = $(this).text()
//		productName[i].innerHTML = list[i].innerHTML
		
		//상품 이미지
		productImg[i].src = listImg[i].src
		console.log(listImg[i])
		
		//장바구니 총가격
		

		//총금액 = 현재총금액 + 상품 단일 가격
		totalPrice[0].value = Number(totalPrice[0].value) + Number(price[i].value)

		
});


chkBox.change(function(){
	//선택된 금액의 값만 더한다.
	//총금액을 0으로 만들고
		totalPrice[0].value = 0
	for(var j=0 ; j<chkBox.length ; j++)
		if(chkBox[j].checked){
			//총 금액에 체크된 금액만 더 해준다.
			totalPrice[0].value = Number(totalPrice[0].value) + Number(amount[j].value)
		}
})
		
		
/*console.log(cancleBtn)
console.log($cancleBtn)*/
		





//체크박스 취소버튼
var $cancleBtn = $(".cancleBtn")
//var cancleBtn = $(".cancleBtn")


$cancleBtn.click(function(){
	var i = $cancleBtn.index(this);
	//버튼을 눌렀을때 선택된 상태면
	if(chkBox[i].checked){
	chkBox[i].checked = false
	
	//금액 부분
	totalPrice[0].value = 0
	for(var j=0 ; j<chkBox.length ; j++)
		if(chkBox[j].checked){
			//총 금액에 체크된 금액만 더 해준다.
			totalPrice[0].value = Number(totalPrice[0].value) + Number(amount[j].value)
		}
	
	}
	//버튼을 눌렀을때 취소된 상태면
	else{
		chkBox[i].checked = true
		
		//금액 부분
		totalPrice[0].value = 0
		for(var j=0 ; j<chkBox.length ; j++)
			if(chkBox[j].checked){
				//총 금액에 체크된 금액만 더 해준다.
				totalPrice[0].value = Number(totalPrice[0].value) + Number(amount[j].value)
			}
		
		}
	
	
})

		




		
		




		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//온로드 코드
/*
		 <script>
 		var list = $("#main_main3_article2 ul li p") 
 		var productName = $(".productName") 

 		for(var i=0 ; list.length ; i++){
 			productName[i].innerHTML = list[i].innerHTML; 
 		}
</script> 
*/
		















})