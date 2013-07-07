package ppg.vitavermis;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.event.EventAnalyserMgr;
import ppg.vitavermis.input.InputMgr;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.testCreation.Scene;


public class GameLoop extends BasicGame {
	@Param static String windowName;
	@Param static String gameVersion;

	final GameState		gameState;
	final RenderMgr		renderer;
	final PhysicsMgr	physics;
	final InputMgr		input;
	
	final Scene		scene;				// phase de test
	final EventAnalyserMgr event;
	ArrayList<Item> itemsList = null;
	private boolean debug_mode_rectangle = false;
	private boolean debug_mode_com = false;
	private boolean debug_mode_reset = false;
	public static boolean debug_mode_up_down = false;
	private boolean delta_mode = false;
	private boolean incrementation_delta = false;
	
	public int count = 0;
	
	GameLoop(GameState gameState) {
		super(windowName + " - " + gameVersion);
		this.gameState	= gameState;
		this.input		= new InputMgr();
		this.physics	= new PhysicsMgr();
		this.renderer	= new RenderMgr();
		this.scene 		= new Scene();
		this.event      = new EventAnalyserMgr();
	}

	@Override
	public void init(GameContainer _gc) throws SlickException {
		physics.init();
		renderer.init();
		scene.init();
		event.init();
		itemsList = scene.getItemsList();
		
	}

	@Override
	public void update(GameContainer _gc, int delta) throws SlickException {
		/* test pour le debug et avoir une même vitesse 
		if (delta <3 ){
		*/
		//System.out.println(delta);
		delta = 10;
		if ( delta_mode != true ) {
			if ( delta > 10 ){
				delta = 10;
			}
			count ++;
			if (count == 2) {
				//System.exit(0);
			}
			input.update(_gc.getInput(), itemsList.get(0));
			physics.update(itemsList, delta);
			//event.update(listeItems, delta);	
		
			//System.out.println("-----------------");
			//System.out.println(delta);
			
			if (debug_mode_reset == true) {
				debug_mode_reset = false;
				init(_gc);
			}
		} else {
			delta = 0 ;
			if ( incrementation_delta ) {
				delta = 10;
				incrementation_delta = false;
				count ++;
				if (count == 2) {
					//System.exit(0);
				}
				input.update(_gc.getInput(), itemsList.get(0));
				physics.update(itemsList, delta);
				//event.update(listeItems, delta);		
		
				//System.out.println("-----------------");
				System.out.println(delta);
			
				if (debug_mode_reset == true) {
					debug_mode_reset = false;
					init(_gc);
				}
			}
		}
		
	}

	@Override
	public void render(GameContainer _gc, Graphics _g) throws SlickException {
		renderer.render(_g, itemsList, debug_mode_rectangle, debug_mode_com);
	}
	
	
	/* deplacer dans les input) */
	@Override
    public void keyPressed(int key, char c)
    {
    	if(key == Input.KEY_1)
      	{
    		debug_mode_rectangle = !debug_mode_rectangle;
       	}
    	if(key == Input.KEY_2)
      	{
    		debug_mode_com = !debug_mode_com;
       	}
    	if(key == Input.KEY_3)
      	{
    		debug_mode_reset = true;
       	}
    	if (key == Input.KEY_ESCAPE) {
    		itemsList.removeAll(itemsList);
    		System.exit(0);
    	}
    	if ( key == Input.KEY_4) {
    		debug_mode_up_down = !debug_mode_up_down;
    	}
    	// gestion de delat (etape par etape)
    	if ( key == Input.KEY_5) {
    		delta_mode = !delta_mode;
    		System.out.println("------------------------------------------");
    		System.out.println("debug mode delta " + delta_mode);
    	}
    	if ( key == Input.KEY_ADD ) {
    		incrementation_delta =! incrementation_delta;

    	}
    }
	

}
