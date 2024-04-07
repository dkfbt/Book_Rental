const list = document.querySelectorAll('.list');
function activeLink() {
    list.forEach((item) =>  item.classList.remove('active'));
    this.classList.add('active');
    // 선택한 항목을 로컬 스토리지에 저장합니다.
    //localStorage.setItem('selectedNavItem', this.querySelector('.title').textContent);
    localStorage.setItem('selectedNavItem', this.querySelector('a').getAttribute('href'));
}

// 네비게이션 항목에 클릭 이벤트를 추가
list.forEach((item) => item.addEventListener('click', activeLink));


// 페이지가 로드될 때 실행되는 함수
window.addEventListener('DOMContentLoaded', () => {
    // 저장된 선택한 항목이 있으면 해당 항목을 활성화합니다.
    const selectedNavItem = localStorage.getItem('selectedNavItem');
    const currentPath = window.location.pathname;   //현재브라우저의 뒷부분경로
    if (selectedNavItem) {
        list.forEach((item) => {
            console.log(item);
            if (item.querySelector('a').getAttribute('href') === currentPath) {
                item.classList.add('active');
            }
        });
    } else {
        // 저장된 선택한 항목이 없으면 홈 화면을 활성화합니다.
        localStorage.clear();
        goToHomePage();
    }
});


// 홈 화면으로 이동하는 함수
function goToHomePage() {
    console.log("goToHomePage");
    // 모든 네비게이션 항목에서 'active' 클래스를 제거합니다.
    list.forEach((item) => item.classList.remove('active'));
    // 홈 화면을 나타내는 네비게이션 항목에 'active' 클래스를 추가합니다.
    document.querySelector('.list a[href="/"]').parentNode.classList.add('active');
    // 선택한 항목을 로컬 스토리지에서 제거합니다.
    localStorage.removeItem('selectedNavItem');
}
