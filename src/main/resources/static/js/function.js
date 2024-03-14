/**
 * 문자열의 마지막(끝) 문자의 종성 포함 여부 확인
 * @param value - Target String
 * @returns 종성 포함 여부
 */
function hasCoda(value) {
    //value : 문자열
    //value.charCodeAt(인덱스) : 마지막문자
    console.log("종성검사할 value : ", value);
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
