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
		minSlides: 5,      // 최소 노출 개수
		maxSlides: 5,      // 최대 노출 개수
		slideMargin: 0,    // 슬라이드간의 간격
		autoHover: true,   // 마우스 호버시 정지 여부
		controls: true,    // 이전 다음 버튼 노출 여부
		pager: true,       // 슬라이드 밑 버튼 노출 여부
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

var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(35.542, 129.3383),
	level: 2
};
var map = new kakao.maps.Map(container, options);

var markerPosition  = new kakao.maps.LatLng(35.542, 129.3383);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);

var iwContent = '<div style="padding:5px;">Ditto Coffee<br><a href="https://map.kakao.com/link/map/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(35.542, 129.3383); //인포윈도우 표시 위치입니다

// 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
    position : iwPosition,
    content : iwContent
});

// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
infowindow.open(map, marker);

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);