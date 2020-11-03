import processing.core.PApplet;

public class HealthBar {
    float posx,posy;
    float health =100;
    PApplet p;
    HealthBar(PApplet p,float posx,float posy){
    this.p = p;
    this.posx = posx;
    this.posy = posy;
    }
    void tegnHealthBar(){
        p.text("Health",posx,posy);
        p.rect(posx,posy,100,20);
        p.fill(250,0,0);
        p.rect(posx,posy,health,19);



    }
}
