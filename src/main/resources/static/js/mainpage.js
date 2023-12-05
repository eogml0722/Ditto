/* 탑버튼 기능 */
function topFunction(){
	window.scrollTo(0,0);
}

window.onscroll=function(){scrollFunction()};
function scrollFunction(){
	if(document.body.scrollTop > 110 ||
	document.documentElement.scrollTop > 110){
		document.getElementById("topbtn").style.display="block";
		}
		else{document.getElementById("topbtn").style.display="none";
	}
}

/* 수동 자동 슬라이드 분석필요... */

var slideIndex = 1;
	showSlides(slideIndex);

function plusSlides(n){
	showSlides(slideIndex += n);
}

function currentSlide(n){
	showSlides(slideIndex = n);
}

function showSlides(n){
	var i;
	var slides = document.getElementsByClassName("slides");
	var dots = document.getElementsByClassName("slidedot");

if (n > slides.length){
	slideIndex = 1;
}

if (n < 1) {
	slideIndex = slides.length;
}

for (i = 0; i < slides.length; i++) {
	slides[i].style.display = "none";
}

for (i = 0; i < dots.length; i++) {
	dots[i].classList.remove("active");
}

slides[slideIndex - 1].style.display = "block";
dots[slideIndex - 1].classList.add("active");

	var slidetime = 7000;
	setTimeout(function(){
		showSlides(slideIndex +=1);
	}, slidetime);
}

/* 팝업창! */
function showPopup(){
	document.getElementById("popup").style.display = "block";
}
function popupClose(){
	document.getElementById("popup").style.display = "none";
}

window.onload = function() {
	showPopup();
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

function CheckForm(Tos){
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