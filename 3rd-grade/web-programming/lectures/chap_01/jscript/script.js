let box = document.getElementById("box");
let position = 0;
      
function moveBox() {
  if (position < 150) {
    position += 1;
    box.style.left = position + "px";
    requestAnimationFrame(moveBox);
  }
}
      
moveBox();