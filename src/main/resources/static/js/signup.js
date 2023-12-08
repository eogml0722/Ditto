var id = document.getElementById("member_id");
var idMsg = document.getElementById("idMsg");
var pw = document.getElementById("passwd");
var pwcheck = document.getElementById("user_passwd_confirm");
var testpw = document.getElementById("testpw");
var mobileMsg = document.getElementById("mobileMsg")
var nameMsg = document.getElementById("nameMsg")
var pwMsg = document.getElementById("pwConfirmMsg");
var mobileM = document.getElementById("mobile2");
var mobileL = document.getElementById("mobile3");
var emailcheck = document.getElementById("e_mail");
var emailMsg = document.getElementById("emailMsg")
var detailaddress = document.getElementById("detailaddress")

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
	var regpw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/
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

mobileM.addEventListener("blur", function() {
	checkphM();
})

function checkphM() {
	var telexp = /^[0-9]{4,}$/
	if (mobileM.value.length == 0) {
		mobileM.style.border = "1px solid red";
		mobileMsg.innerHTML = "전화번호를 입력해주세요!"
	} else if (!telexp.test(mobileM.value)) {
		mobileM.style.border = "1px solid red";
		mobileMsg.innerHTML = "숫자4자리로 입력헤주세요!"
	} else {
		mobileM.style.border = "1px solid black";
		mobileMsg.innerHTML = ""
	}
}

mobileL.addEventListener("blur", function() {
	checkphL();
})

function checkphL() {
	var telexp = /^[0-9]{4,}$/
	if (mobileL.value.length == 0) {
		mobileL.style.border = "1px solid red";
		mobileMsg.innerHTML = "전화번호를 입력해주세요!"
	} else if (!telexp.test(mobileL.value)) {
		mobileL.style.border = "1px solid red";
		mobileMsg.innerHTML = "숫자4자리로 입력헤주세요!"
	} else {
		mobileL.style.border = "1px solid black";
		mobileMsg.innerHTML = ""
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

detailaddress.addEventListener("blur", function() {
	dtaddress();
})

function dtaddress() {
	if (detailaddress.value.length == 0) {
		/*실험용
		detailaddress.style.border = "1px solid red";
		*/
	} else {
		detailaddress.style.border = "1px solid black";
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
	var phm = document.getElementsByClassName("mb")
	var address = document.getElementById("address")
	var dtAdress = document.getElementById("detailAddress")
	var chkMail = document.getElementById("e_mali")
	var zoneCode = document.getElementById("zipcode")
	
	//정규식
	var regId = /^[a-z]+[a-z0-9]{4,16}$/g;
	var regpwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/
	var regName = /^[가-힣a-zA-Z]{2,15}$/;
	var phExp = /^[0-9]{4,}$/;
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

	//비밀번호 확인
if (!cpw.value == upw.value) {
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

	if (phm.value == "") {
		alert("휴대폰 번호를 입력해 주세요.")
		phm.focus();
		return false;
	} else if (!phExp.test(phm.value)) {
		alert("올바른 휴대전화번호를 입력해 주세요.")
		phm.focus();
		return false;
	}

//주소확인
if (zoneCode.value == "") {
	alert("우편번호를 입력해 주세요.")
	zoneCode.focus();
	return false;
} else if (address.value == 0) {
	alert("기본주소를 입력해 주세요.")
	address.focus();
	return false;
} else if (!regadd.test(adress.vale)) {
	alert("주소는 한글로 적어주세요.")
	address.focus();
	return false;
} else if (dtAddress.vale == 0) {
	alert("나머지 주소를 입력해 주세요.")
	dtAddress.focus();
	return false;
} else if (!regadd.test(dtadress.vale)) {
	alert("주소는 한글로 적어주세요.")
	dtAddress.focus();
	return false;
}
	
//이메일확인
if (chkMail.value == "") {
	alert("메일을 입력해 주세요.")
	chkMail.focus();
	return false;
} else if (!regmail.test(chkmail.value)) {
	alert("입력하신 이메일은 사용할 수 없습니다.")
	regmail.focus();
	return false;
	}
}

function openZipSearch() {
	new daum.Postcode({
		oncomplete: function(data) {
			//팝업을 통한 검색 결과 항목 클릭시 실행
			console.log(data)
			document.getElementById('zipcode').value = data.zonecode;
			document.getElementById('address').value = data.address;
			//우편번호,상세주소 입력되면 나머지주소로 포커스 이동
			document.getElementById('detailaddress').focus();

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

/*로그인 모달 기능*/

var open = () => { //open했을때 클래스네임 hidden을 지운다. (css에 hidden을 display:none;으로 적용시켜놓음)
	document.querySelector(".modal").classList.remove("hidden");
}

var close = () => { //close했을때 클레스네임 hidden을 추가한다. 
	document.querySelector(".modal").classList.add("hidden");
}

document.querySelector(".btn1").addEventListener("click", open);
document.querySelector("#closebtn4").addEventListener("click", close);

/*이용약관*/

function selectAll(selectAll) {
	var checkboxes = document.getElementsByName("agreements");
	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

//모달팝업창 열기,닫기

var openModa1 = () => {  //open일때 클래스네임 'hidden2'를 지운다.
	document.querySelector(".modal2").classList.remove("hidden2");
}

var closeModa1 = () => { //close일때 클래스네임 'hidden2'를 추가한다.
	document.querySelector(".modal2").classList.add("hidden2");
}

document.querySelector(".btn2").addEventListener("click", openModa1); //열기버튼 클릭했을때
document.querySelector("#closebtn2").addEventListener("click", closeModa1); //닫기버튼 클릭했을때

//로그인->약관창으로 넘어가기

var closeLogin = () => { //close했을때 클레스네임 hidden을 추가한다. 
	document.querySelector(".modal").classList.add("hidden");
}
var openTerms = () => {  //open일때 클래스네임 'hidden2'를 지운다.
	document.querySelector(".modal2").classList.remove("hidden2");
}

document.querySelector("#signup").addEventListener("click", closeLogin); //로그인창을 닫는다
document.querySelector("#signup").addEventListener("click", openTerms); //약관창을 연다

// 유효성 검사 and 약관창 -> 회원가입으로 넘어가기

chkbtn = document.getElementById("chkbtn")
chkbtn.addEventListener("click",function(event){
	 event.preventDefault();

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
	if(termOfService && privacy){
		alert("확인되었습니다!")
		window.location.assign("teamproject.html")
	}
})

// 로그인 모달 유효성 검사

var idcmd = document.getElementById("id");
var pwcmd = document.getElementById("password");
var regExp = /^[a-z]+[a-z0-9]{4,16}$/g; //영어소문자 or 영어소문자와 숫자로 이루어진
var regpw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/

function chklogin(){

if(idcmd.value.length == 0){
	alert("아이디를 입력해 주세요.")
	return false;
	}else if(!regExp.test(idcmd.value)){
		alert("아이디를 확인해 주세요.")
		return false;
	}else if(pwcmd.value.length == 0){
		alert("비밀번호를 입력해 주세요.")
		return false;
	}else if(!regpw.test(pwcmd.value)){
		alert("비밀번호를 확인해 주세요.")
		return false;
	}else{
		alert("환영합니다.")
	}
}

/* sns로고 클릭시 이동 */

function sns1(){
	window.location.href="https://www.instagram.com/"
}
function sns2(){
	window.location.href="https://ko-kr.facebook.com/"
}