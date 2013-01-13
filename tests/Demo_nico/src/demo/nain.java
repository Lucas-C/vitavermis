package demo;


import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class nain
{

	private Image image = null;
	public caca c = null;
	private loutre l = null;
	private int x = 0;
	private int y = 0;
	private int count = 0;
	private int caca_present = 0;
	
	
	public nain(int x, int y, loutre l) throws SlickException {
		this.image = new Image("image/dwarf.png");	
		this.x = x;
		this.y = y;
		this.l = l;
	}
	public nain(loutre l) throws SlickException {
		this.image = new Image("image/dwarf.png");	
		this.x = 0;
		this.y = 0;
		this.l = l;
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
		if (caca_present == 1)
		{
			c.render(arg0, arg1);
		}
	}

	public void init(GameContainer arg0) throws SlickException {
		image = image.getScaledCopy((float) 0.5);
		
	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		count ++;
		if (count == 900 && caca_present == 0)
		{
			c = new caca(this.x + (image.getWidth())/2, this.y +image.getHeight());
			c.init(arg0);
			caca_present = 1;
		}
		if (caca_present == 1) 
		{
			c.update(arg0, arg1);
			if (l.rectangle().intersects((Rectangle)c.rectangle()))
			{
				if (l.getVie() == 0)
				{
					
				}else
				{
					l.setVie(l.getVie() - 1);
					caca_present = 0;
					count = 0;				
					System.out.println(l.getVie());
				}
			}
			if ( c.getY() == Main.taille_fenetre_y - c.image.getHeight())
			{
				caca_present = 0;
				count = 0;				
			}
		}
		
	}

}
