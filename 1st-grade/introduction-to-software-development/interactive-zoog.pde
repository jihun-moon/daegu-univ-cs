float zoogX, zoogY;
float armAngle = 0;
float angleChange = 1;

void setup() {
  size(480, 270);
  rectMode(CENTER);
  ellipseMode(CENTER);
}

void draw() {
  background(255);

  zoogX = mouseX;
  zoogY = mouseY;

  armAngle = armAngle + angleChange;
  if (armAngle > 45 || armAngle < -45) {
    angleChange = angleChange * -1;
  }

  drawZoog(zoogX, zoogY);
}

void drawZoog(float x, float y) {
  stroke(0);
  fill(150);
  rect(x, y, 20, 100);

  fill(255);
  ellipse(x, y - 30, 60, 60);

  fill(mouseX % 255, 0, mouseY % 255);
  ellipse(x - 19, y - 30, 16, 32);
  ellipse(x + 19, y - 30, 16, 32);

  stroke(0);
  line(x - 10, y + 50, x - 20, y + 70);
  line(x + 10, y + 50, x + 20, y + 70);

  drawLeftArm(x, y);
  drawRightArm(x, y);
}

void drawLeftArm(float x, float y) {
  pushMatrix();
  translate(x - 10, y);
  rotate(radians(armAngle));
  stroke(0);
  fill(150);
  rect(0, 0, 10, 40);
  popMatrix();
}

void drawRightArm(float x, float y) {
  pushMatrix();
  translate(x + 10, y);
  rotate(radians(-armAngle));
  stroke(0);
  fill(150);
  rect(0, 0, 10, 40);
  popMatrix();
}

void mousePressed() {
  println("Take me to your leader!!!");
}
