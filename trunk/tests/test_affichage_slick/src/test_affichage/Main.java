package test_affichage;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;


public class Main  extends BasicGame implements KeyListener {

	// taille de la fenêtre
	static final int taille_fenetre_x = 800;
	static final int taille_fenetre_y = 600;
	private ArrayList<caca> liste = null;
	private long temps_deb = 0;
	private long temps_fin = 0;
	private int count = 0;
	private Music musique = null; 
	private int nb_caca = 100;
	
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
        app.start();
	}

	public void init(GameContainer arg0) throws SlickException {
		int i = 0;
		Random r, v ;
//		musique = new Music("imperial_march.ogg");
//		musique.play();
		  
		// generation de la liste d'éléments
		liste  =  new ArrayList<caca>();
		while ( i < nb_caca ){
			r = new Random();
			v = new Random();
			caca element = new caca(r.nextInt(taille_fenetre_x),r.nextInt(taille_fenetre_y), v.nextInt(9) + 1);
			liste.add(element);
			i++;
		}
		temps_deb = System.currentTimeMillis();
	}

	public void render(GameContainer arg0, Graphics arg1) throws SlickException {

		for (caca element : liste) {
			element.image.draw(element.getX(), element.getY());
		}
		
	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		if (count == 10000 ){
			temps_fin = System.currentTimeMillis();
			System.out.println(temps_fin - temps_deb);

		}
		for (caca element : liste) {
			element.setY(element.getY() + element.getVitesse());
			/* 
			if (element.getVitesse() < 3) {
				//element.setVitesse(element.getVitesse() + 1);
			}
			*/
			if (element.getY() > taille_fenetre_y ) {
				element.setY(0);
//				element.setVitesse(1);
			}
			
		}
		count++;
			

	}

    public void keyPressed(int key, char c)
    {
    	if(key == Input.KEY_ESCAPE)
      	{
//    		musique.stop();
       		System.exit(0);
       	}
    }

}