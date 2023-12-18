/* 아이디/비밀번호찾기 폼 제출 */
function submitFindForm() {
	event.preventDefault();
	let target = event.target.className;
	if(target.indexOf('findIdBtn') >= 0) { //아이디 찾기
		let form = document.getElementById('findIdForm');
		let input = document.querySelectorAll('input[class=id-input]');
		let count = 0;
		input.forEach((input, index) => { if(input.value.length != 0) count++; });
		if(input.length == count) {
			form.submit();
		} else {
			Swal.fire({
				icon: 'warning',
				title: '이름, 이메일을 모두 입력해 주세요',
				confirmButtonColor: '#00008b',
				confirmButtonText: '확인'
			}).then((result) => {
              	if(result.isConfirmed) {}
            })
		}
	} else { //비밀번호 찾기
		let form = document.getElementById('findPwdForm');
		let input = document.querySelectorAll('input[class=pwd-input]');
		let count = 0;
		input.forEach((input, index) => { if(input.value.length != 0) count++; });
		if(input.length == count) {
			form.submit();
		} else {
			Swal.fire({
				icon: 'warning',
				title: '아이디, 이름, 이메일을 모두 입력해 주세요',
				confirmButtonColor: '#00008b',
				confirmButtonText: '확인'
			}).then((result) => {
              	if(result.isConfirmed) {}
            })
		}
	}
}






