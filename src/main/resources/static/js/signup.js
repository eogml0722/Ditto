var id = document.getElementById("member_id");
var idMsg = document.getElementById("idMsg");
var pw = document.getElementById("passwd");
var pwcheck = document.getElementById("user_passwd_confirm");
var testpw = document.getElementById("testpw");
var mobileMsg = document.getElementById("mobileMsg");
var nameMsg = document.getElementById("nameMsg");
var pwMsg = document.getElementById("pwConfirmMsg");
var mobile = document.getElementById("mobile");
var emailcheck = document.getElementById("e_mail");
var emailMsg = document.getElementById("emailMsg");
var detailAddress = document.getElementById("detailAddress");

id.addEventListener("blur", function() {
	checkId();
})

function checkId() {
	var regExp = /^[a-z]+[a-z0-9]{4,16}$/g;
	if (id.value.length == 0) {
		idMsg.innerHTML = "아이디를 입력해주세요.";
		idMsg.style.color ="red"
		id.style.border = "1px solid red";

	} else if (!regExp.test(id.value)) {
		idMsg.innerHTML = "아이디는 영문소문자 또는 숫자 4~16자로 입력해 주세요.";
	} else {
		idMsg.innerHTML = id.value + "는 사용가능한 아이디 입니다.";
		idMsg.style.color = "black";
		id.style.border = "1px solid black";
	}
}

pw.addEventListener("blur", function() {
	checkPw();
})

function checkPw() {
	var regpw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
	if (!regpw.test(pw.value)) {
		pw.style.border = "1px solid red";
	} else if (pw.value.length == 0) {
		/*실험용*/
		testpw.innerHTML = "비밀번호를 입력해 주세요.";
		pw.style.border = "1px solid red";
	} else if (pw.value == id.value) { //비밀번호에 아이디포함
		pw.style.border = "1px solid red";
	} else {
		pw.style.border = "1px solid black";
		tip.style.display = "none";
	}
}

pwcheck.addEventListener("blur", function() {
	checkPws();
})

function checkPws() {
	if (pwcheck.value.length == 0) {
		pwMsg.innerHTML = "비밀번호를 입력해 주세요.";
		pwcheck.style.border = "1px solid red";
	} else if (pw.value !== pwcheck.value) {
		pwMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
		pwcheck.style.border = "1px solid red";
	} else {
		pwMsg.innerHTML = "";
		pwcheck.style.border = "1px solid black";
	}
}

mobile.addEventListener("blur", function() {
	checkPhoneNum();
})

function checkPhoneNum() {
	var telExp = /^010-[0-9]{4}-[0-9]{4}$/;
	if (mobile.value.length == 0) {
		mobile.style.border = "1px solid red";
		mobileMsg.innerHTML = "전화번호를 입력해주세요!"
	} else if (!telExp.test(mobile.value)) {
		mobile.style.border = "1px solid red";
		mobileMsg.innerHTML = "기호 '-' 포함하여 입력해 주세요!"
	} else {
		mobile.style.border = "1px solid black";
		mobileMsg.innerHTML = "";
	}
}

emailcheck.addEventListener("blur", function() {
	checkema();
})

function checkema() {
	var mailexp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	if (emailcheck.value.length == 0) {
		emailMsg.innerHTML = "이메일을 입력해 주세요.";
		emailcheck.style.border = "1px solid red";
		emailcheck.style.backgroundColor = "white";
	} else if (!mailexp.test(emailcheck.value)) {
		emailMsg.innerHTML = "유효한 이메일을 입력해 주세요.";
		emailcheck.style.border = "1px solid red";
		emailcheck.style.backgroundColor = "white";
	} else {
		emailMsg.innerHTML = "";
		emailcheck.style.border = "1px solid black";
		emailcheck.style.backgroundColor = "PowderBlue";
	}
}

detailAddress.addEventListener("blur", function() {
	dtAddress();
})

function dtAddress() {
	if (detailAddress.value.length == 0) {
		/*실험용
		detailAddress.style.border = "1px solid red";
		*/
	} else {
		detailAddress.style.border = "1px solid black";
	}
}

var state = false;

function toggle() { //비밀번호 보이기/숨기기
	if (state) {
		document.getElementById("passwd").setAttribute("type", "password");
		document.getElementById("eye").style.color = "#7a797e";
		document.getElementById("user_passwd_confirm").setAttribute("type", "password");
		document.getElementById("eyes").style.color = "#7a797e";

		state = false;
	} else {
		document.getElementById("passwd").setAttribute("type", "text");
		document.getElementById("eye").style.color = "#5887ef";
		document.getElementById("user_passwd_confirm").setAttribute("type", "text");
		document.getElementById("eyes").style.color = "#5887ef";

		state = true;
	}
}

//회원가입 유효성검사

function memberJoinAction() {
	var uid = document.getElementById("member_id")
	var upw = document.getElementById("passwd")
	var cpw = document.getElementById("user_passwd_confirm")
	var uname = document.getElementById("name")
	var phmM = document.getElementById("mobile2")
	var phmL = document.getElementById("mobile3")
	var address = document.getElementById("address")
	var dtAddress = document.getElementById("detailAddress")
	var chkMail = document.getElementById("e_mail")
	var zoneCode = document.getElementById("zipcode")

	//정규식
	var regId = /^[a-z]+[a-z0-9]{4,16}$/g;
	var regpwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
	var regName = /^[가-힣a-zA-Z]{2,15}$/;
	var phExp = /^010-[0-9]{4}-[0-9]{4}$/
	var regadd = /^[가-힣]+$/;
	var regmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	//아이디 확인

	if (uid.value == "") {

		alert("아이디를 입력해 주세요.")

		uid.focus();

		return false;

	} else if (!regId.test(uid.value)) {

		alert("아이디는 영문소문자와 숫자가 포함된 4~16자로 입력해 주세요.")

		uid.focus();

		return false;

	}

	//비밀번호 확인

	if (upw.value == "") {

		alert("비밀번호를 입력해 주세요.")

		upw.focus();

		return false;

	} else if (!regpwd.test(upw.value)) {

		alert("비밀번호는 대소문자,숫자,특수문자 중 2가지 이상 조합,10자~16자")

		upw.focus();

		return false;

	} else if (upw.value == uid.value) {

		alert("아이디와 동일한 비밀번호를 사용할 수 없습니다.")

		upw.focus();

		return false;

	}

        //비밀번호 재확인

    if(cpw.value == "") {
        alert("비밀번호를 재입력 해주세요.")
        cpw.focus();
        return false;
    }else if (cpw.value != upw.value) {
        alert("비밀번호와 동일하지 않습니다.")
        cpw.focus();
        return false;
    }

	//이름확인

	if (uname.value == "") {
		alert("이름을 입력해 주세요.")
		uname.focus();
		return false;

	} else if (!regName.test(uname.value)) {
		alert("이름은 한글,영어만 사용가능합니다.")
		uname.focus();
		return false;
	}

	//폰번호확인

	if(mobile.value == "") {
	alert("휴대폰 번호를 입력해 주세요.")
	mobile.focus();
	return false;
	} else if (!phExp.test(mobile.value)) {
      		alert("올바른 휴대폰 번호를 입력해 주세요.")
      		mobile.focus();
      		return false;
    }

//주소확인
if (zoneCode.value == "") {
	alert("우편번호를 입력해 주세요.")
	zoneCode.focus();
	return false;
} else if (dtAddress.value == 0) {
	alert("나머지 주소를 입력해 주세요.")
	dtAddress.focus();
	return false;
} else if (!regadd.test(dtAddress.value)) {
	alert("주소는 한글로 적어주세요.")
	dtAddress.focus();
	return false;
}

//이메일확인
if (chkMail.value == "") {
	alert("메일을 입력해 주세요.")
	chkMail.focus();
	return false;
} else if (!regmail.test(chkMail.value)) {
	alert("입력하신 이메일은 사용할 수 없습니다.")
	regmail.focus();
	return false;
	}

	joinForm.submit();
}

function openZipSearch() {
    new daum.Postcode({
    	oncomplete: function(data) {
		var addr = '';
		if (data.userSelectedType === 'R') {
			addr = data.roadAddress;
		} else {
			addr = data.jibunAddress;
		}

		$("#zipcode").val(data.zonecode);
		$("#streetAddress").val(addr);
		$("#detailAddress").val("");
		$("#detailAddress").focus();
        }
    }).open();
}

//비밀번호 툴팁창 클릭시 화면보이게
var tip = document.getElementById("tooltip");
var tipBtn = document.getElementById("passwd");

tipBtn.addEventListener("click", function() {
	tip.style.display = "block";
})

//비밀번호 툴팁창 클릭시 화면창닫기
function closebtn() {
	document.getElementById("tooltip").style.display = "none";
}

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
