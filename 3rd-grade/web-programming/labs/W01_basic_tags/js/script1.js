// HTML에서 id가 "box"인 요소를 찾아서 box 변수에 저장
let box = document.getElementById("box");

// 박스의 시작 위치를 0으로 설정
let position = 0;
      
// 박스를 움직이는 함수
function moveBox() {
  // 박스의 위치가 150px보다 작을 때만 계속 실행
  if (position < 150) {
    // 위치를 1씩 증가
    position += 1;
    
    // box의 왼쪽 스타일(left)을 현재 position 값으로 변경 (예: "1px", "2px", ...)
    box.style.left = position + "px";
    
    // 다음 화면 프레임에 맞춰서 moveBox 함수를 다시 실행 (부드러운 움직임)
    requestAnimationFrame(moveBox);
  }
}
      
// 처음에 한 번 실행해서 애니메이션 시작
moveBox();