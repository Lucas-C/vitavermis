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

	GameLoop(GameState gs) {
		super(gs.appConfig.windowName);
		this.gameState	= gs;
		this.input		= new InputMgr();
		this.physics	= new PhysicsMgr();
		this.renderer	= new RenderMgr();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		physics.init();
		renderer.init();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		input.update(gc.getInput(), delta);
		physics.update(delta);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		renderer.render(g);
	}
}