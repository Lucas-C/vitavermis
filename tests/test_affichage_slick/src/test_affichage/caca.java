package test_affichage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class caca {

	public Image image = null;
	private int x = 0;
	private int y = 0;
	private int vitesse = 0;
	
	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public caca(int x, int y) throws SlickException {
		this.image = new Image("turd.png");
		this.x = x;
		this.y = y;
	}

	public caca(int x, int y, int v) throws SlickException {
		this.image = new Image("turd.png");
		this.x = x;
		this.y = y;
		this.vitesse = v;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
