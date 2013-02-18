package ppg.vitavermis;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.staticfield.Param;
import ppg.vitavermis.event.EventAnalyserMgr;
import ppg.vitavermis.input.InputMgr;
import ppg.vitavermis.items.ItemsPhy;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.testCreation.creation;


public class GameLoop extends BasicGame {
	@Param static String windowName;

	final GameState		gameState;
	final RenderMgr		renderer;
	final PhysicsMgr	physics;
	final InputMgr		input;
	
	final creation		crea;				// phase de test
	final EventAnalyserMgr event;
	ArrayList<ItemsPhy> listeItems = null;
	private boolean debug_mode_rectangle = false;
	private boolean debug_mode_com = false;
	private boolean debug_mode_reset = false;
	public static boolean debug_mode_up_down = false;
	
	public int count = 0;
	
	GameLoop(GameState gameState) {
		super(windowName);
		this.gameState	= gameState;
		this.input		= new InputMgr();
		this.physics	= new PhysicsMgr();
		this.renderer	= new RenderMgr();
		this.crea 		= new creation();
		this.event      = new EventAnalyserMgr();
	}

	@Override
	public void init(GameContainer _gc) throws SlickException {
		physics.init();
		renderer.init();
		crea.init();
		event.init();
		listeItems =crea.getListe();
		
	}

	@Override
	public void update(GameContainer _gc, int delta) throws SlickException {
		count ++;
		if (count == 2) {
			//System.exit(0);
		}
		input.update(_gc.getInput(), listeItems.get(0));
		physics.update(listeItems, delta);
		event.update(listeItems, delta);
		//System.out.println("-----------------");
		if (debug_mode_reset == true) {
			debug_mode_reset = false;
			init(_gc);
		}
	}

	@Override
	public void render(GameContainer _gc, Graphics _g) throws SlickException {
		renderer.render(_g, listeItems, debug_mode_rectangle, debug_mode_com);
	}
	
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
    		listeItems.removeAll(listeItems);
    		System.exit(0);
    	}
    	if ( key == Input.KEY_4) {
    		debug_mode_up_down = !debug_mode_up_down;
    	}

    }


}
