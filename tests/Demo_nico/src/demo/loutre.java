package demo;


import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class loutre
{
	


	// position
	private int pos_x;
	private int pos_y;
	// saut
	private int count = 0;
	private int saut = 0;

	// données
	public Image image;
	private Sound sond = null;
	
	// vie
	private int vie = 5;
	
	public loutre() throws SlickException {
		this.image = new Image("image/otter_right.png");
		this.pos_x = 0;
		this.pos_y = 0;
		vie = 5;
	}

	// rectangle pour gérer les collisions
	public Rectangle rectangle ()
	{
		Rectangle rect = new Rectangle (pos_x, pos_y, image.getWidth(), image.getHeight());
	    return rect;
	}
	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	

	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		image.draw(getPos_x(), getPos_y());
		
	}


	public void init(GameContainer arg0) throws SlickException {
		sond = new Sound("son/loutre.ogg");
		image = image.getScaledCopy((float)0.5);
		pos_x = (Main.taille_fenetre_x - image.getWidth()) /2;
		pos_y = Main.taille_fenetre_y - image.getHeight();
	}


	public void update(GameContainer arg0, int arg1) throws SlickException {
	
		Input input = arg0.getInput();
		// deplacement	
		if (input.isKeyDown (Input.KEY_LEFT))
		{
			pos_x -= 1;
			image = new Image("image/otter_left.png");
			image = image.getScaledCopy((float)0.5);
		}
		if (input.isKeyDown (Input.KEY_RIGHT))
		{
			pos_x += 1;
			image = new Image("image/otter_right.png");
			image = image.getScaledCopy((float)0.5);
		}
		// saut
		if ( input.isKeyDown(Input.KEY_SPACE) && saut == 0 ) 
		{
			saut = 1;
			sond.play();
		}
		if (saut != 0) 
		{
			if ( saut == 1)
			{
				pos_y -=2;
				count += 1;;	
				if (count == 100)
				{
					saut = -1 ;
					count = 0;
				}
			}
			if ( saut == -1)
			{
				pos_y +=2;
				count ++;
				if (count == 100)
				{
					saut = 0 ;
					count = 0;
					sond.stop();
				}
				
			}
		}
		
		// pour rester dans la fenetre
		if ( pos_x >= Main.taille_fenetre_x - image.getWidth() )
		{
			pos_x = Main.taille_fenetre_x - image.getWidth();
		}
		if  ( pos_x < 0 )
		{
			pos_x = 0;
		}
		
		
	}

	
	
}
