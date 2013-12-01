package ppg.vitavermis;

import org.jbox2d.callbacks.DebugDraw;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.input.InputEventDispatcher;
import ppg.vitavermis.input.InputEventListener;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.testCreation.Scene;


public class GameLoop extends BasicGame {
	@Param static String windowName;
	@Param static String gameVersion;

	GameState gameState;
	final RenderMgr renderer;
	final PhysicsMgr physics;
	final InputEventListener evtListener;
	final InputEventDispatcher evtDispatcher;
	
	public static boolean debug_mode_up_down = false;
	private boolean debug_mode_rectangle = false;
	private boolean debug_mode_com = false;
	private boolean debug_mode_reset = false;
	private boolean delta_mode = false;
	private boolean incrementation_delta = false;
	
	public int count = 0;
	
	GameLoop() {
		super(windowName + " - " + gameVersion);
		this.gameState = null;
		this.evtListener = new InputEventListener();
		this.physics = new PhysicsMgr();
		this.renderer = new RenderMgr();
		this.evtDispatcher = new InputEventDispatcher();
	}

	@Override
	public void init(GameContainer _gc) throws SlickException {
		Scene scene = Scene.createInitialScene();
		this.gameState = scene.getInitialGameState(physics, renderer);
		renderer.init(gameState, _gc.getGraphics());
		physics.init(renderer.getDebugDraw());
		if (gameState.physicsDebugLevel > 0) {
			renderer.getDebugDraw().setFlags(DebugDraw.e_shapeBit|DebugDraw.e_aabbBit|DebugDraw.e_jointBit);
		}
		evtListener.init(_gc.getInput());
	}

	@Override
	public void update(GameContainer _gc, int delta_ms) throws SlickException {
		//System.out.println("Physics update");
		evtListener.update(_gc.getInput());
		physics.update(delta_ms);
	}

	@Override
	public void render(GameContainer _gc, Graphics _g) throws SlickException {
		//System.out.println("Renderer update");
		renderer.render(_g, this.gameState);
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
    		//itemsList.removeAll(itemsList);
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
