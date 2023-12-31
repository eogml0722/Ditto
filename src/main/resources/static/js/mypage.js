// 왼쪽 버튼 클릭하면 오른쪽 화면 바뀌고 페이지 초기화

var showMyPage = document.getElementById("showRightPage");

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

//탭 메뉴 기능
function change_btn(e) {
	var btns = document.querySelectorAll(".chgbtn");
	btns.forEach(function(btn, i) {
		if(e.currentTarget == btn) {
			btn.classList.add("active");
		}
		else {
			btn.classList.remove("active");
		}
	});
	console.log( e.currentTarget );
}

// 비밀번호 유효성 검사
function passwordChk() {
	var pw1 = document.getElementById("password")
	var pw2 = document.getElementById("newPasswordChk")
	var regpwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
	if (!regpwd.test(pw1.value)) {
    	alert("비밀번호는 대소문자,숫자,특수문자 중 2가지 이상 조합,8자~15자")
    	pw1.focus();
    	return false;
    }
    if (pw1.value !== pw2.value) {
        alert("비밀번호가 동일하지 않습니다.")
        pw1.focus();
        return false;
    }
    return true;
}

// 회원 정보수정 유효성 검사
function updateMember(){
    var form = document.getElementById("updateForm")
	var name = document.getElementById("name")
	var mobile = document.getElementById("phoneNum")
	var zipcode = document.getElementById("zipcode")
	var streetAddress = document.getElementById("streetAddress")
	var detailAddress = document.getElementById("detailAddress")
	var email = document.getElementById("email")

    var regName = /^[가-힣a-zA-Z]{2,15}$/;
    var phExp = /^010-[0-9]{4}-[0-9]{4}$/;
    var regadd = /^[가-힣]+$/;
    var regmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (name.value == ""){
        alert("이름을 입력해주세요")
        name.focus();
        return false;
    }

    if (!regName.test(name.value)) {
        alert("이름은 한글 영어만 가능합니다")
        name.focus();
        return false;
    }

    if (mobile.value == ""){
        alert("전화번호를 입력해주세요")
        mobile.focus;
        return false;
    }

    if (!phExp.test(mobile.value)) {
        alert("올바른 전화 번호를 입력해 주세요.")
        mobile.focus();
        return false;
    }

    if (zipcode.value == 0) {
    	alert("우편번호를 입력해 주세요.")
    	zipcode.focus();
    	return false;
    } else if (streetAddress.value == ""){
        alert("주소를 입력해주세요")
        return false;
    } else if (detailAddress.value == "") {
    	alert("나머지 주소를 입력해 주세요.")
    	detailAddress.focus();
    	return false;
    } else if (!regadd.test(detailAddress.value)) {
    	alert("주소는 한글로 적어주세요.")
    	detailAddress.focus();
    	return false;
    }

    if(email.value == ""){
        alert("이메일을 입력해주세요")
        chkMail.focus();
        return false;
    }
    if (!regmail.test(email.value)) {
	    alert("입력하신 이메일은 사용할 수 없습니다.")
	    chkMail.focus();
	    return false;
	}
    form.submit();
}


