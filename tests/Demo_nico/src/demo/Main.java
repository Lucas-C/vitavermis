package demo;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame implements KeyListener
{



	// taille de la fentre
	static final int taille_fenetre_x = 1000;
	static final int taille_fenetre_y = 600;
	

	// fond
	private Image fond;
	
	// élément présent
	private loutre l;
	private nain n;
	private nain n1;
	private nain n2;
	
	
	public Main() {
		super("Demo");
	}
	
	public static int getTailleFenetreX() {
		return taille_fenetre_x;
	}

	public static int getTailleFenetreY() {
		return taille_fenetre_y;
	}



	public static void main(String[] args) throws SlickException {
		
		Main main = new Main();
		AppGameContainer app = new AppGameContainer(main);
		app.setDisplayMode(taille_fenetre_x  , taille_fenetre_y, false);
        app.setMinimumLogicUpdateInterval(3);
        app.setMaximumLogicUpdateInterval(3);
        app.start();
	}

	// méthode de dessins de slick
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {

		
		if ( l.getVie() == 0)
		{
			arg1.drawRect(0, 0, taille_fenetre_x, taille_fenetre_y);
			arg1.drawString("GAME OVER", taille_fenetre_x / 2 - 50, taille_fenetre_y/2);
			
		} else 
		{
			fond.draw(0,taille_fenetre_y - 300);
			l.render(arg0, arg1);
			n.render(arg0, arg1);
			n1.render(arg0, arg1);
			n2.render(arg0, arg1);
		}
	}

	// méthode d'initialisation de slick
	@Override
	public void init(GameContainer arg0) throws SlickException {
		fond = new Image("image/nenuphar.png");
		fond = fond.getScaledCopy((float) 0.9);
		l = new loutre();
		l.init(arg0);
		n = new nain(l);
		n.init(arg0);
		n1 = new nain(150,50,l);
		n1.init(arg0);
		n2 = new nain(550,125,l);
		n2.init(arg0);		
		
	}

	
	// méthode de calcul de slick
	// on ne peut faire apparaitre un element graphic dedant
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		if ( l.getVie() == 0)
		{
			
			
		} else 
		{
			l.update(arg0, arg1);
			n.update(arg0, arg1);
			n1.update(arg0, arg1);
			n2.update(arg0, arg1);
		}
	}

	@Override
    public void keyPressed(int key, char c)
    {
    	if(key == Input.KEY_ESCAPE)
      	{
     		
       		System.exit(0);
       	}
    }
}
