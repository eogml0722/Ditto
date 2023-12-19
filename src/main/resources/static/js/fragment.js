//
///*이용약관*/
//
//function selectAll(selectAll) {
//	var checkboxes = document.getElementsByName("agreements");
//	checkboxes.forEach((checkbox) => {
//		checkbox.checked = selectAll.checked;
//	})
//}

////모달팝업창 열기,닫기
//
//var openModa1 = () => {  //open일때 클래스네임 'hidden2'를 지운다.
//	document.querySelector(".modal2").classList.remove("hidden2");
//}
//
//var closeModa1 = () => { //close일때 클래스네임 'hidden2'를 추가한다.
//	document.querySelector(".modal2").classList.add("hidden2");
//}
//
//document.querySelector("#signupbtn").addEventListener("click", openModa1); //열기버튼 클릭했을때
//document.querySelector("#closebtn2").addEventListener("click", closeModa1); //닫기버튼 클릭했을때

////로그인->약관창으로 넘어가기
//
//var closeLogin = () => { //close했을때 클레스네임 hidden을 추가한다.
//	document.querySelector(".modal").classList.add("hidden");
//}
//var openTerms = () => {  //open일때 클래스네임 'hidden2'를 지운다.
//	document.querySelector(".modal2").classList.remove("hidden2");
//}
//
//document.querySelector("#signup").addEventListener("click", closeLogin); //로그인창을 닫는다
//document.querySelector("#signup").addEventListener("click", openTerms); //약관창을 연다
//
//// 유효성 검사 and 약관창 -> 회원가입으로 넘어가기
//
//chkbtn = document.getElementById("chkbtn")
//chkbtn.addEventListener("click",function(event){
//	 event.preventDefault();
//
//	var termOfService = document.frmTos.termOfService.checked;
//	var privacy = document.frmTos.privacy.checked;
//
//	if(!termOfService){
//		alert("필수약관에 동의해 주세요.");
//		return false;
//	}
//	if(!privacy){
//		alert("필수약관에 동의해 주세요");
//		return false;
//	}
//	if(termOfService && privacy){
//		alert("확인되었습니다!")
//		window.location.assign("/members/new")
//	}
//})
//
//}