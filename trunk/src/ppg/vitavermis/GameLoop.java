package ppg.vitavermis;

import org.jbox2d.callbacks.DebugDraw;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.entity.EntityMgr;
import ppg.vitavermis.input.InputEventDispatcher;
import ppg.vitavermis.input.InputEventListener;
import ppg.vitavermis.maincharacter.MainCharacterMgr;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.testCreation.Scene;


public class GameLoop extends BasicGame {
	@Param static String windowName;
	@Param static String gameVersion;

	GameState gameState;
	final RenderMgr renderer;
	final PhysicsMgr physics;
	final EntityMgr entityMgr;
	final MainCharacterMgr heroMgr;
	final InputEventListener evtListener;
	final InputEventDispatcher evtDispatcher;
	
	GameLoop() {
		super(windowName + " - " + gameVersion);
		this.gameState = null;
		this.physics = new PhysicsMgr();
		this.renderer = new RenderMgr();
		this.entityMgr = EntityMgr.getInstance();
		this.heroMgr = new MainCharacterMgr();
		this.evtListener = new InputEventListener();
		this.evtDispatcher = new InputEventDispatcher(this.evtListener, this.heroMgr);
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		Scene scene = Scene.createInitialScene();
		this.gameState = scene.getInitialGameState(physics, renderer);
		heroMgr.init(this.gameState.getMainCharacter(), this.entityMgr);
		renderer.init(gameState, gameContainer.getGraphics());
		physics.init(renderer.getDebugDraw());
		renderer.getDebugDraw().setFlags(DebugDraw.e_shapeBit|DebugDraw.e_aabbBit|DebugDraw.e_jointBit);
		evtListener.init(gameContainer.getInput());
	}

	@Override
	public void update(GameContainer gameContainer, int delta_ms) throws SlickException {
		//System.out.println("Physics update");
		//evtListener.update(gameContainer.getInput());
		evtDispatcher.update();
		heroMgr.update(gameContainer.getInput());
		entityMgr.update();
		physics.update(delta_ms);
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		//System.out.println("Renderer update");
		renderer.render(graphics, this.gameState);
	}
	
	@Override
    public void keyPressed(int key, char c)
    {
		evtListener.sendKeyPressed(key, c);
    }
}