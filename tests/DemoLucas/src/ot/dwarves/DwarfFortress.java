package ot.dwarves;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import ot.Player;
import ot.RenderMgr;
import ot.physics.PhysicsMgr;



//TODO: make shit disappear

public class DwarfFortress {

	Player				m_player	= null; // Unused for now, "target"
	PhysicsMgr			m_physics	= null;
	RenderMgr			m_renderer	= null;
	
	Random				m_randGen	= new Random();
	DwarvesParams		m_params	= null;

	ArrayList<Dwarf>	m_dwarves	= null;
	
	Sound				m_shitSound = null;

	public DwarfFortress(Player _player, PhysicsMgr _physics, RenderMgr _renderer) {
		m_player = _player;
		m_physics = _physics;
		m_renderer = _renderer;
	}
	
	public void init(DwarvesParams _params) throws SlickException {
		m_params = _params;
		m_dwarves = new ArrayList<Dwarf>(m_params.baseDwarvesNb);
		m_shitSound = new Sound("data/destruction.wav");
	}
	
	public Dwarf createDwarf() {
		Dwarf d = new Dwarf();
		d.img = m_params.dwarfImg;
		m_dwarves.add(d);
		m_renderer.add(d);
		return d;
	}

	public void remove(Dwarf _Dwarf) {
		m_dwarves.remove(_Dwarf);
	}
	
	public void update(int _delta) {
		for (Dwarf d : m_dwarves) {
			d.timeSinceLastLaunch += _delta;
			if (d.timeSinceLastLaunch > m_params.shitLaunchDelay) {
				// Dwarf shits
				d.timeSinceLastLaunch -= m_params.shitLaunchDelay;
				Turd t = new Turd();
				t.img = m_params.turdImg;
				t.halfHeight = m_params.turdHalfHeight;
				t.x = d.x;
				t.y = d.y;
				t.speedX = (2 * m_randGen.nextFloat() - 1) * m_params.shitLaunchMaxSpeed;
				m_renderer.add(t);
				m_physics.add(t);
				
				m_shitSound.play();
			}
		}
	}
}
