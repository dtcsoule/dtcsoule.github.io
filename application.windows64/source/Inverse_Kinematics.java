import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Inverse_Kinematics extends PApplet {


arm[] link = new arm[2];                    //Array named arm with 2 links
PVector base;                               //places the location of base revolute joint
float L = 120;                              //the lenght of the link
PVector position;                           //Position of object (ball)
PVector velocity;                           //velocity of object movement speed of ball



public void setup() {
                          //defines the dimension of display window width and hight of pixels

  position = new PVector(30, 0);            //inital position of object
  velocity = new PVector(2, 6);             //(x,y) direction of inital movement

  velocity.mult(1);                         //increasews the velocity by (n) times
  link[0] = new arm(10, 10, L, 0);          //sets link at index 0 of the array

  for (int l = 1; l < link.length; l++) {    //arm1 = new link(100, 800, 200, 0);
    link[l] = new arm(link[l-1], L, l);      // arm2 = new link(arm1,200, degrees(-PI/4));
  }
  base = new PVector(width/2, height/2);     //position of the base of the link 1 "(width/2, height/2)' is centerd
}

public void draw() {
  background(80);
  noStroke(); 
  fill(0xff000000);                              //hex code for color black
  ellipse(width/2, height/2, 50, 50);         //(x,y, size, size) 

  //
  int total = link.length;                  
  arm end = link[link.length-1];              //is the last link position 
  end.endEffector(position.x, position.y);    //the end effector will follow the object 
  end.update();


  //All object features 
  position.add(velocity);                       // movement on the object
  noStroke();                                   //no outline around ellipse
  fill(0xffFF00FF);                                //hex color of ellipse fuchsia
  ellipse(position.x, position.y, 20, 20);

  //changes movement of the object direction 
  //with a bounce in the window
  if (position.x > width || position.x < 0) {  
    velocity.x *= -1;
  }
  if (position.y > height || position.y < 0) {  
    velocity.y *= -1;
  }

  for (int l = total-2; l>=0; l--) {
    link[l].follow(link[l+1]);
    link[l].update();
  }

  link[0].setA(base);                          //sets the base in locked position

  for (int l = 1; l < total; l++) {
    link[l].setA(link[l-1].a2);
  } 
  for (int l = 0; l < total; l++) {
    link[l].show();
  }
}
class arm {
  PVector a1 = new PVector();        //position of the base link with revolute joint
  float angle = 0;                   //setting start angle at 0 degrees 
  float l;                           //length of arm
  PVector a2 = new PVector();        //position of the conecting revolute joint

  //constructor for the first arm link
  arm(float x, float y, float len, float q) {    
    a1.set(x, y);
    l = len;
    angle = q; 
    calcB();
  }
  //constructor for the following arm link(s)
  arm(arm parent, float len, float q) {
    a2 = parent.a2.copy();
    l = len;
    angle = q;  
    calcB();
    //follow(parent.a.x, parent.a.y);
  }

  public void follow(arm child) {
    float targetX = child.a1.x; 
    float targetY = child.a1.y;
    endEffector(targetX, targetY);
  }
  /*
* The next three blocks are the inverse kinematics 
   */
  public void endEffector(float objectx, float objecty) {
    PVector object = new PVector(objectx, objecty);
    PVector direction = PVector.sub(object, a1);
    angle = direction.heading();                   //heading in this function uses inverse tan
    direction.setMag(l);
    direction.mult(-1);
    a1 = PVector.add(object, direction);
  }

  //sets a1 to copy what a2 end position is doing 
  public void setA(PVector position) {
    a1 = position.copy(); 
    calcB();
  }  

  //changes the angle with respect to the movement of dx,dy
  public void calcB() {
    float dx = l * cos(angle);        
    float dy = l * sin(angle);        
    a2.set(a1.x+dx, a1.y+dy);
  }

  public void update() {
    calcB();
  }

  // The Links physical properties
  public void show() {
    stroke(255, 200);                 //Link color
    strokeWeight(20);                 //Link thickness
    line(a1.x, a1.y, a2.x, a2.y);     //Position of links
  }
}
  public void settings() {  size (800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Inverse_Kinematics" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
