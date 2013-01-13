package ot;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import ot.dwarves.DwarfFortress;
import ot.physics.PhysicsMgr;



public class TerminOtter extends BasicGame {
	static final int 	m_winWidth	= 800;
	static final int 	m_winHeight	= 600;
	
	Player				m_player	= new Player();
	RenderMgr			m_renderer	= new RenderMgr();
	PhysicsMgr			m_physics	= new PhysicsMgr();
	InputMgr			m_input		= new InputMgr(m_player, m_physics);
	DwarfFortress		m_dwarfAI	= new DwarfFortress(m_player, m_physics, m_renderer);
	Music				m_music		= null;
	
	TerminOtter() {
		super("TerminOtter");
	}

	@Override
	public void init(GameContainer _gc) throws SlickException {
		m_renderer.init();
		LevelLoader.loadLevel(m_player, m_renderer, m_physics, m_dwarfAI, m_winWidth, m_winHeight);
		m_music = new Music("data/imperial_march.ogg");
		m_music.loop();
	}

	@Override
	public void update(GameContainer _gc, int _delta) throws SlickException {
		m_input.update(_gc.getInput(), _delta);
		m_dwarfAI.update(_delta);
		m_physics.update(_delta);
	}

	@Override
	public void render(GameContainer _gc, Graphics _g) throws SlickException {
		m_renderer.render(_g);
	}

	public static void main(String[] _args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TerminOtter());

		app.setDisplayMode(m_winWidth, m_winHeight, false);
		//app.setShowFPS(false);
		//app.setVSync(true);
		
		app.start();
	}
}