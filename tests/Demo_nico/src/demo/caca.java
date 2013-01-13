package demo;


import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class caca
{
	public Image image = null;
	private Sound son = null;
	private int x = 0;
	private int y = 0;

	public caca(int x, int y) throws SlickException {
		this.image = new Image("image/turd.png");
		this.x = x;
		this.y = y;
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
	
	public Rectangle rectangle ()
	{
		Rectangle rect = new Rectangle (x, y, image.getWidth(), image.getHeight());
	    return rect;
	}
	


	 
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		image.draw(x,y);
	}

	public void init(GameContainer arg0) throws SlickException {
		son = new Sound("son/plouf.ogg");
		image = image.getScaledCopy((float) 0.5);
	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		y++;
		if ( y == Main.taille_fenetre_y - image.getHeight())
		{
			son.play();
		}
	}

}
