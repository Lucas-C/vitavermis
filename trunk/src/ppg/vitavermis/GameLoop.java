package ppg.vitavermis;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.input.InputMgr;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;


public class GameLoop extends BasicGame {
	final GameState		gameState;
	final RenderMgr		renderer;
	final PhysicsMgr	physics;
	final InputMgr		input;

	GameLoop(GameState gameState) {
		super(gameState.appConfig.windowName);
		this.gameState	= gameState;
		this.input		= new InputMgr();
		this.physics	= new PhysicsMgr();
		this.renderer	= new RenderMgr();
	}

	@Override
	public void init(GameContainer _gc) throws SlickException {
		physics.init();
		renderer.init();
	}

	@Override
	public void update(GameContainer _gc, int _delta) throws SlickException {
		input.update(_gc.getInput(), _delta);
		physics.update(_delta);
	}

	@Override
	public void render(GameContainer _gc, Graphics _g) throws SlickException {
		renderer.render(_g);
	}
}