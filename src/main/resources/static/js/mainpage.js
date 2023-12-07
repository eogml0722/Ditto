/* 탑버튼 클릭시 맨위로, 사이트를 일정이하로 스크롤 하면 탑버튼이 켜짐 */
function topFunction(){
	window.scrollTo(0,0);
}

window.onscroll=function(){scrollFunction()};
function scrollFunction(){
	if(document.body.scrollTop > 200 ||
	document.documentElement.scrollTop > 200){
		document.getElementById("topbtn").style.display="block";
		}
		else{document.getElementById("topbtn").style.display="none";
	}
}

/* 새로고침시 맨위로 */
history.scrollRestoration = "manual"

/* 팝업창 키기 */
function showPopup(){
	document.getElementById("popup").style.display = "block";
}
function popupClose(){
	document.getElementById("popup").style.display = "none";
}

window.onload = function() {
	showPopup();
}

/* content1 bx슬라이드 설정, 마진조정*/

$(document).ready(function(){
	$('.bxslider').bxSlider({
		mode: 'horizontal',// 가로 방향 수평 슬라이드
		auto: true,        // 자동 실행 여부
		speed: 500,        // 이동 속도를 설정
		pager: false,      // 현재 위치 페이징 표시 여부 설정
		moveSlides: 1,     // 슬라이드 이동시 개수
		minSlides: 4,      // 최소 노출 개수
		maxSlides: 4,      // 최대 노출 개수
		slideMargin: 0,    // 슬라이드간의 간격
		autoHover: true,   // 마우스 호버시 정지 여부
		controls: true,    // 이전 다음 버튼 노출 여부
		pager: true,       // 슬라이드 밑 버튼 노출 여부
	});
});

$(document).ready(function(){
	$('.bxslider2').bxSlider({
		mode: 'horizontal',// 가로 방향 수평 슬라이드
		auto: true,        // 자동 실행 여부
		speed: 500,        // 이동 속도를 설정
		pager: false,      // 현재 위치 페이징 표시 여부 설정
		moveSlides: 1,     // 슬라이드 이동시 개수
		minSlides: 4,      // 최소 노출 개수
		maxSlides: 4,      // 최대 노출 개수
		slideMargin: 0,    // 슬라이드간의 간격
		autoHover: true,   // 마우스 호버시 정지 여부
		controls: true,    // 이전 다음 버튼 노출 여부
		pager: false,       // 슬라이드 밑 버튼 노출 여부
	});
});


$(document).ready(function(){
    $('.bx-wrapper').css('margin-bottom', '0px');
});

var swiper = new Swiper(".mySwiper1", {
	slidesPerView: 3,
	spaceBetween: 30,
	navigation: {
		nextEl: ".swiper-button-next",
		prevEl: ".swiper-button-prev"
    },
	pagination: {
		el: ".swiper-pagination",
		clickable: true,
	},
});

/* 테스트용 */

function chgslide1() {
    document.getElementById("content2rightbox1").style.display = "block";
	document.getElementById("content2rightbox2").style.display = "none";
	document.getElementById("content2rightbox3").style.display = "none";
};

function chgslide2() {
	document.getElementById("content2rightbox1").style.display = "none";
	document.getElementById("content2rightbox2").style.display = "block";
	document.getElementById("content2rightbox3").style.display = "none";
};

function chgslide3() {
    document.getElementById("content2rightbox1").style.display = "none";
    document.getElementById("content2rightbox2").style.display = "none";
    document.getElementById("content2rightbox3").style.display = "block";
};