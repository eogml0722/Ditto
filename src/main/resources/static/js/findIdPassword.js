//아이디 찾기 회원유형 법인사업자회원

$(document).ready(function() {
	$("#SelectBox").change(function() {
	var result = $("#SelectBox option:selected").val();
	if (result == "A") {
	$("#check_method2").click();
	$("#check_method2").css('display', 'inline');

	$("#CpNumber1").css('display', 'none');

	$("#CpNumber").css('display', 'none');

	$("#check_method0").css('display', 'none');

	$(".change_name").text("이름");

	$("#email_text").css('display', 'block');

	$("#check_method3").css('display', 'none');

	$("#FNumber1").css('display', 'none');

	} if (result == "B") {

	$("#check_method2").click();

	$("#check_method3").css('display', 'none');

	$("#FNumber1").css('display', 'none');

	$(".change_name").text("이름");

	$("#CpNumber1").css('display', 'none');

	$("#check_method0").css('display', 'none');

	} if (result == "C") {

	$("#check_method0").click(); //셀렉트박스 체인지할때마다 라디오버튼 클릭이벤트

	$("#check_method0").css('display', 'inline');

	$("#CpNumber1").css('display', 'inline');

	$("#CpNumber").css('display', 'block');

	$("#email_text").css('display', 'none');

	$(".change_name").text("법인명");

	$("#ssn_name").text("법인번호로 찾기");

	$("#check_method3").css('display', 'none');

	$("#FNumber1").css('display', 'none');

	} if (result == "D") {

	$("#check_method3").click();

	$("#check_method3").css('display', 'inline');

	$("#CpNumber").css('display', 'block');

	$("#ssn_name").text("등록번호로 찾기");

	$("#email_text").css('display', 'none');

	$("#FNumber1").css('display', 'inline');

	$("#check_method0").css('display', 'none');

	$("#CpNumber1").css('display', 'none');

	$(".change_name").text("이름");

	}

	});

});

// 찾는방법 클릭시 입력창 전환//

$(document).ready(function() {

	$('input[type="radio"]').on('click', function() {

	var chkValue1 = $('input[type=radio][id="check_method0"]:checked').val();

	var chkValue2 = $('input[type=radio][id="check_method2"]:checked').val();

	var chkValue3 = $('input[type=radio][id="check_method3"]:checked').val();

	if(chkValue1) {//법인번호 라디오버튼 클릭시//

	$('#ssn_name').css('display', 'block');

	$('#email_text').css('display', 'none');

	$('#CpNumber').css('display', 'block');

	$('.check_mail').val("");

	$('#find4').val("");

	$('#find2').val("");

	$('.cpfindNum1').val("");

	$('.check_name').val("");

	$('.check_mail').val("");

	return;

	}else if (chkValue2) {//이메일 라디오버튼 클릭시//

	$('#CpNumber').css('display', 'none');

	$('#email_text').css('display', 'block');

	$('#find2').val("");

	$('#find4').val("");

	$('.check_name').val("");

	$('.check_mail').val("");

	return;

	} else if (chkValue3) {

	$('#email_text').css('display', 'none');

	$('#CpNumber').css('display', 'block');

	$('.check_mail').val("");

	$('#find2').val("");

	$('#find4').val("");

	$('.cpfindNum1').val("");

	$('.check_name').val("");

	$('.check_mail').val("");

	return;

	}

	});

});

//아이디찾기 유효성검사//

function find_check(){

	var findvalue = $(".findcheck:checked").val();

	var mailexp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	var cpExp = /^([0-9]{6})$/;

	var cpExp1 = /^([0-9]{7})$/;

	if((findvalue == "email") && ($(".check_name").val() == "")){

	alert("이름을 입력해 주세요.");

	$(".check_name").focus();

	return false;

	}

	if((findvalue == "email") && ($(".check_mail").val() == "")){

	alert("이메일을 입력해 주세요.");

	$(".check_mail").focus();

	return false;

	}

	if((findvalue == "email") && (!mailexp.test($(".check_mail").val()))){

	alert("이메일 항목이 이메일 형식이 아닙니다.");

	$(".check_mail").focus();

	return false;

	}	

	if((findvalue == "Cnumber") && ($(".check_name1").val() == "")){

	alert("법인명을 입력해 주세요.");

	$(".check_name1").focus();

	return false;

	}

	if((findvalue == "Cnumber") && ($(".cpfindNum").val() == "")){

	alert("법인번호를 입력해 주세요.");

	$(".cpfindNum").focus();

	return false;

	}

	if((findvalue == "Cnumber") && (!cpExp.test($(".cpfindNum").val()))){

	alert("법인번호 항목 6자이상 입력해 주세요.");

	$(".cpfindNum").focus();

	return false;

	}

	if((findvalue == "Cnumber") && ($(".cpfindNum1").val() == "")){

	alert("법인번호를 입력해 주세요.");

	$(".cpfindNum1").focus();

	return false;

	}

	if((findvalue == "Cnumber") && (!cpExp1.test($(".cpfindNum1").val()))){

	alert("법인번호 항목 7자이상 입력해 주세요.");

	$(".cpfindNum1").focus();

	return false;

	}

	if((findvalue == "Fnumber") && ($(".check_name").val() == "")){

	alert("이름을 입력해 주세요.");

	$(".check_name").focus();

	return false;

	}

	if((findvalue == "Fnumber") && ($(".cpfindNum").val() == "")){

	alert("등록번호를 입력해 주세요.");

	$(".cpfindNum").focus();

	return false;

	}

	if((findvalue == "Fnumber") && (!cpExp.test($(".cpfindNum").val()))){

	alert("등록번호 항목 6자이상 입력해 주세요.");

	$(".cpfindNum").focus();

	return false;

	}

	if((findvalue == "Fnumber") && ($(".cpfindNum1").val() == "")){

	alert("등록번호를 입력해 주세요.");

	$(".cpfindNum1").focus();

	return false;

	}

	if((findvalue == "Fnumber") && (!cpExp1.test($(".cpfindNum1").val()))){

	alert("등록번호 항목 7자이상 입력해 주세요.");

	$(".cpfindNum1").focus();

	return false;

	}else{

	alert("등록된 사용자가 없습니다.")

	}

}

//비밀번호 찾기//

$(document).ready(function() {

	$("#SelectBox1").change(function() {

	var result1 = $("#SelectBox1 option:selected").val();

	if (result1 == "A") { //개인회원 셀렉트

	$("#check_method6").click();

	$("#check_method6").css('display', 'inline');

	$("#CpNumber2").css('display', 'none');

	$("#CpNumber3").css('display', 'none');

	$("#check_method4").css('display', 'none');

	$(".change_name2").text("이름");

	$("#email_text2").css('display', 'block');

	$("#check_method5").css('display', 'none');

	$("#FNumber2").css('display', 'none');

	} if (result1 == "B") {//개인 사업자회원 셀렉트

	$("#check_method6").click();

	$("#check_method5").css('display', 'none'); //등록번호안보이게

	$("#FNumber2").css('display', 'none');

	$("#CpNumber2").css('display','none');

	$("#check_method4").css('display','none');

	$(".change_name2").text("이름");

	} if (result1 == "C") {//법인 사업자회원 셀렉트

	$("#check_method4").click(); //셀렉트박스 체인지할때마다 라디오버튼 클릭이벤트

	$("#check_method4").css('display', 'inline');

	$("#CpNumber2").css('display', 'inline');

	$("#CpNumber3").css('display', 'block');

	$("#email_text2").css('display', 'none');

	$(".change_name2").text("법인명");

	$("#ssn_name2").text("법인번호로 찾기");

	$("#check_method5").css('display', 'none');

	$("#FNumber2").css('display', 'none');

	} if (result1 == "D") {//외국인회원 셀렉트

	$("#check_method5").click();

	$("#check_method5").css('display', 'inline');

	$("#CpNumber2").css('display', 'block');

	$("#ssn_name2").text("등록번호로 찾기");

	$("#email_text2").css('display', 'none');

	$("#FNumber2").css('display', 'inline');

	$("#check_method4").css('display', 'none');

	$("#CpNumber2").css('display', 'none');

	$(".change_name2").text("이름");

	}

	});

});

// 찾는방법 클릭시 입력창 전환//

$(document).ready(function() {

	$('input[type="radio"]').on('click', function() {

	var chkValue4 = $('input[type=radio][id="check_method4"]:checked').val();

	var chkValue6 = $('input[type=radio][id="check_method6"]:checked').val();

	var chkValue7 = $('input[type=radio][id="check_method5"]:checked').val();

	if(chkValue4) {//법인번호 라디오버튼 클릭시//

	$('#ssn_name2').css('display', 'inline');

	$('#email_text2').css('display', 'none');

	$('#CpNumber3').css('display', 'block');

	$('.check_mail2').val("");

	$('.find_number1').val("");

	$('.find_number2').val("");

	$('.check_id').val("");

	$('.check_name2').val("");

	return;

	}else if(chkValue6) {//이메일 라디오버튼 클릭시//

	$('#CpNumber3').css('display', 'none');

	$('#email_text2').css('display', 'block');

	$('.find_number2').val("");

	$('.find_number1').val("");

	$('.check_id').val("");

	$('.check_mail2').val("");

	$('.check_name2').val("");

	return;

	} else if(chkValue7) { //등록번호 라디오버튼 클릭시//

	$('#email_text2').css('display', 'none');

	$('#CpNumber2').css('display', 'none');

	$('#check_method4').css('display', 'none');

	$('#CpNumber3').css('display', 'block');

	$('.check_mail2').val("");

	$('.find_number1').val("");

	$('.find_number2').val("");

	$('.check_id').val("");

	$('.check_name2').val("");

	return;

	}

	});

});

//비밀번호찾기 유효성검사

function find_check1(){

	var regId = /^[a-z]+[a-z0-9]{4,16}$/g;

	var findvalue1 = $(".findcheck1:checked").val();

	var mailexp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	var cpExp = /^([0-9]{6})$/;

	var cpExp1 = /^([0-9]{7})$/;

	if($(".check_id").val() == ""){

	alert("아이디 항목을 입력해 주세요.")

	$(".check_id").focus();

	return false;

	}

	if(!regId.test($(".check_id").val())){

	alert("아이디 입력형식이 올바르지 않습니다.")

	$(".check_id").focus();

	return false;

	}

	if((findvalue1 == "email") && ($(".check_name2").val() == "")){

	alert("이름을 입력해 주세요.");

	$(".check_name2").focus();

	return false;

	}

	if((findvalue1 == "email") && ($(".check_mail2").val() == "")){

	alert("이메일을 입력해 주세요.");

	$(".check_mail2").focus();

	return false;

	}

	if((findvalue1 == "email") && (!mailexp.test($(".check_mail2").val()))){

	alert("이메일 항목이 이메일 형식이 아닙니다.");

	$(".check_mail2").focus();

	return false;

	}	

	if((findvalue1 == "Cnumber") && ($(".check_names").val() == "")){

	alert("법인명을 입력해 주세요.");

	$(".check_names").focus();

	return false;

	}

	if((findvalue1 == "Cnumber") && ($(".find_number1").val() == "")){

	alert("법인번호를 입력해 주세요.");

	$(".find_number1").focus();

	return false;

	}

	if((findvalue1 == "Cnumber") && (!cpExp.test($(".find_number1").val()))){

	alert("법인번호 항목 6자이상 입력해 주세요.");

	$(".find_number1").focus();

	return false;

	}

	if((findvalue1 == "Cnumber") && ($(".find_number2").val() == "")){

	alert("법인번호를 입력해 주세요.");

	$(".find_number2").focus();

	return false;

	}

	if((findvalue1 == "Cnumber") && (!cpExp1.test($(".find_number2").val()))){

	alert("법인번호 항목 7자이상 입력해 주세요.");

	$(".find_number2").focus();

	return false;

	}

	if((findvalue1 == "Fnumber") && ($(".check_name2").val() == "")){

	alert("이름을 입력해 주세요.");

	$(".check_name2").focus();

	return false;

	}

	if((findvalue1 == "Fnumber") && ($(".find_number1").val() == "")){

	alert("등록번호를 입력해 주세요.");

	$(".find_number1").focus();

	return false;

	}

	if((findvalue1 == "Fnumber") && (!cpExp.test($(".find_number1").val()))){

	alert("등록번호 항목 6자이상 입력해 주세요.");

	$(".find_number1").focus();

	return false;

	}

	if((findvalue1 == "Fnumber") && ($(".find_number2").val() == "")){

	alert("등록번호를 입력해 주세요.");

	$(".find_number2").focus();

	return false;

	}

	if((findvalue1 == "Fnumber") && (!cpExp1.test($(".find_number2").val()))){

	alert("등록번호 항목 7자이상 입력해 주세요.");

	$(".find_number2").focus();

	return false;

	}else{

	alert("등록된 사용자가 없습니다.")

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

/*탑버튼*/

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