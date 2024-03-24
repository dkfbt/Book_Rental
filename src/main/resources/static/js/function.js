/**
 * 문자열의 마지막(끝) 문자의 종성 포함 여부 확인
 * @param value - Target String
 * @returns 종성 포함 여부
 */
function hasCoda(value) {
    //value : 문자열
    //value.charCodeAt(인덱스) : 마지막문자
    return ((value.charCodeAt(value.length - 1) - 0xAC00) % 28) > 0;
}


/**
 * 필드(Elemenet) 널 유효성 검사
 * @param target - 검사 대상 Element. 원래마우스가 있던곳
 * @param fieldName - 필드명
 * @returns 필드 입력(선택) 여부
 */
function isValid(target, fieldName) {
    //공백이 아닌 값이 있으면 유효성검사 통과
    console.log(`${target}, ${fieldName}에 대한 기본 유효성 검사`);
    if (target.value.trim()) {
        return true;
    }
    const particle = (hasCoda(fieldName)) ? '을' : '를'; // 조사
    const elementType = (target.type === 'text' || target.type === 'password' || target.type === 'search' || target.type === 'textarea') ? '입력' : '선택';
    alert( `${fieldName + particle} ${elementType}해 주세요.` );

    //타겟으로 포커스 이동. 3번째파라미터인 포커스타겟이 비었으면 1번째 파라미터인 타겟으로, 값이 있으면 포커스타겟으로
    target.focus();
    throw new Error(`"${target.id}" 의 값을 입력해주세요`);
}


/**
 * 필드(Elemenet) 회원가입폼 유효성검사
 * @param form
 * @returns 유효성
 */
function validateForm(form) {
    const id = form.userLoginId.value.trim();
    const password = form.userPassword.value.trim();
    const birthday = form.birthday.value;
    const phoneNumber = form.phone.value;
    const cleanedPhoneNumber = phoneNumber.replace(/[-_.\s]/g, ''); // 허용된 문자 제거 후 숫자만 남김

    // 나이 검사
    // 입력값이 비어있지 않은지 먼저 확인
    if (birthday !== '') {
        const age = calculateKoreanAge(birthday);
        // 입력값이 숫자가 아니거나 0보다 작거나 120보다 크면 경고
        if (age < 0 || age > 120) {
            alert('나이는 0살이상 120살 이하여야 합니다.');
            return false;
        }
    } else {
        // 입력값이 비어있을 경우의 처리 (필요에 따라 처리를 추가하세요)
    }

    // 전화번호 검사
    // 전화번호가 비어있는지 먼저 확인
    if (cleanedPhoneNumber.trim() !== '') {
        // 숫자, -, _, 공백, . 이외의 문자를 제거
        var numericPhoneNumber = cleanedPhoneNumber.replace(/[^0-9]/g, '');

        // 순수 숫자만 남긴 전화번호의 길이가 11자리 이하인지 검사
        if (!/^\d{1,11}$/.test(numericPhoneNumber)) {
            alert('전화번호는 숫자, -, _, 공백, . 만 포함할 수 있으며, 숫자만 뽑았을 때 11자리 이하여야 합니다.');
            return false;
        }
    } else {
        // 전화번호 입력값이 비어있을 경우의 처리 (필요에 따라 처리를 추가하세요)
    }

    return true;
}


function calculateKoreanAge(birthday) {
    // birthday는 'YYYY-MM-DD' 형식의 문자열로 가정. input은 문자열로 넘어옴
    // 연, 월, 일로 분리
    const year = birthday.substring(0, 4);
    const month = birthday.substring(4, 6);
    const day = birthday.substring(6, 8);

    // 주의: Date 객체의 월(month)은 0부터 시작하므로, 실제 월보다 1을 빼주어야 합니다.
    const birthdayDate = new Date(year, month - 1, day);
    const today = new Date();
    const thisYear = today.getFullYear();
    const birthYear = birthdayDate.getFullYear();

    // 한국 나이 계산
    const koreanAge = thisYear - birthYear + 1;
    return koreanAge;
}


/**
 * 데이터 조회
 * @param uri - API Request URI
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function getJson(uri, params) {

    let json = {}

    $.ajax({
        url : uri,
        type : 'get',
        dataType : 'json',
        data : params,
        async : false,
        success : function (response) {
            json = response;
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })

    return json;
}


/**
 * 데이터 저장/수정/삭제
 * @param uri - API Request URI
 * @param method - API Request Method
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function callApi(uri, method, params) {

    let json = {}

     $.ajax({
        url : uri,
        type : method,
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        data : (params) ? JSON.stringify(params) : {},
        async : false,
        success : function (response) {
            json = response;
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })

    return json;
}
