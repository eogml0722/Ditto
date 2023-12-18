// 왼쪽 버튼 클릭하면 오른쪽 화면 바뀌고 페이지 초기화

var showMyPage = document.getElementById("shoWrightPage");

showMyPage.onclick = function() {
    document.getElementById("rightPageMenu").style.display = "block";
	document.getElementById("rightPageMenu1").style.display = "none";
	document.getElementById("rightPageMenu2").style.display = "none";
	document.getElementById("rightPageMenu3").style.display = "none";
	document.getElementById("rightPageMenu4").style.display = "none";
	document.getElementById("rightPageMenu5").style.display = "none";
};

function showRightPageMenu1() {
	document.getElementById("rightPageMenu").style.display = "none";
	document.getElementById("rightPageMenu1").style.display = "block";
	document.getElementById("rightPageMenu2").style.display = "none";
	document.getElementById("rightPageMenu3").style.display = "none";
	document.getElementById("rightPageMenu4").style.display = "none";
	document.getElementById("rightPageMenu5").style.display = "none";
};

function showRightPageMenu2() {
    document.getElementById("rightPageMenu").style.display = "none";
    document.getElementById("rightPageMenu1").style.display = "none";
	document.getElementById("rightPageMenu2").style.display = "block";
	document.getElementById("rightPageMenu3").style.display = "none";
	document.getElementById("rightPageMenu4").style.display = "none";
	document.getElementById("rightPageMenu5").style.display = "none";
};

function showRightPageMenu3() {
    document.getElementById("rightPageMenu").style.display = "none";
    document.getElementById("rightPageMenu1").style.display = "none";
	document.getElementById("rightPageMenu2").style.display = "none";
	document.getElementById("rightPageMenu3").style.display = "block";
	document.getElementById("rightPageMenu4").style.display = "none";
	document.getElementById("rightPageMenu5").style.display = "none";
};

function showRightPageMenu4() {
	document.getElementById("rightPageMenu").style.display = "none";
	document.getElementById("rightPageMenu1").style.display = "none";
	document.getElementById("rightPageMenu2").style.display = "none";
	document.getElementById("rightPageMenu3").style.display = "none";
	document.getElementById("rightPageMenu4").style.display = "block";
	document.getElementById("rightPageMenu5").style.display = "none";
}

function showRightPageMenu5() {
	document.getElementById("rightPageMenu").style.display = "none";
    document.getElementById("rightPageMenu1").style.display = "none";
	document.getElementById("rightPageMenu2").style.display = "none";
	document.getElementById("rightPageMenu3").style.display = "none";
	document.getElementById("rightPageMenu4").style.display = "none";
	document.getElementById("rightPageMenu5").style.display = "block";
};

// 회원탈퇴 알림

let unregibtn = document.getElementById("unregisterbtn")
unregibtn.addEventListener("click", function(){
	if(!confirm("정말 탈퇴하겠습니까?")){
		alert("취소되었습니다")
	} else {
		if(!confirm("복구 할 수 없습니다 정말 탈퇴하겠습니까?")){
			alert("취소되었습니다")
		}else{
			alert("회원탈퇴 완료")
			location.href="/"
		}
	}
});

// 오른쪽 화면 바뀌면 input 초기화

//function resetPage() {
//	var inputFields = document.querySelectorAll(".menu2Input");
//	var inputFields1 = document.querySelectorAll(".menu2InputNum");
//	inputFields.forEach(function(inputField) {
//		inputField.value = "";
//	});
//	inputFields1.forEach(function(inputField1) {
//		inputField1.value = "";
//	});
//}


