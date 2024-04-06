const list = document.querySelectorAll('.list');
function activeLink() {
    list.forEach((item) =>
    item.classList.remove('active'));
    this.classList.add('active');
    // 선택한 항목을 로컬 스토리지에 저장합니다.
    localStorage.setItem('selectedNavItem', this.querySelector('.title').textContent);
}

// 네비게이션 항목에 클릭 이벤트를 추가
list.forEach((item) => item.addEventListener('click', activeLink));


// 페이지가 로드될 때 실행되는 함수
window.addEventListener('DOMContentLoaded', () => {
    // 저장된 선택한 항목이 있으면 해당 항목을 활성화합니다.
    const selectedNavItem = localStorage.getItem('selectedNavItem');
    if (selectedNavItem) {
        list.forEach((item) => {
            if (item.querySelector('.title').textContent === selectedNavItem) {
                item.classList.add('active');
            }
        });
    }
});
