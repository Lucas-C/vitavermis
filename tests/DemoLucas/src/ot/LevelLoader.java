package ot;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ot.dwarves.Dwarf;
import ot.dwarves.DwarfFortress;
import ot.dwarves.DwarvesParams;
import ot.physics.Obstacle;
import ot.physics.PhyMgrParams;
import ot.physics.PhysicsMgr;



public class LevelLoader {

	//TODO: load data from XML
	static void loadLevel(Player _player, RenderMgr _renderer, PhysicsMgr _physics, DwarfFortress _dwarfAI, int _width, int _height) throws SlickException {
		PhyMgrParams phyParams = new PhyMgrParams();
		phyParams.groundHeight = 450;
		_physics.init(phyParams);
		
		// Configure player
		_player.setPairImg(
				(new Image("data/otter_left.png")).getScaledCopy(0.33f),
				(new Image("data/otter_right.png")).getScaledCopy(0.33f)
		);
		_player.halfHeight = _player.img.getHeight() / 2;
		_player.x = 100;
		_player.y = 100;
		_renderer.add(_player);
		_physics.add(_player);
		
		// Loading background
		Entity background = new Entity();
		background.img = new Image("data/background_BW_horiz450p.png");
		assert background.img.getWidth() == _width;
		assert background.img.getHeight() == _height;
		background.x = _width / 2;
		background.y = _height / 2;
		background.layerLvl = -2;
		_renderer.add(background);
		
		// Loading obstacles
		Obstacle platform = new Obstacle();
		platform.img = (new Image("data/nenuphar.png")).getScaledCopy(0.33f);
		platform.layerLvl = -1;
		platform.halfHeight = 130;
		platform.x = 600;
		platform.y = 300;
		platform.dbgDrawHeight = true;
		_renderer.add(platform);
		_physics.add(platform);

		// Loading dwarves
		DwarvesParams dwfParams = new DwarvesParams();
		dwfParams.dwarfImg = (new Image("data/dwarf.png")).getScaledCopy(0.33f);
		dwfParams.turdImg = (new Image("data/turd.png")).getScaledCopy(0.5f);
		dwfParams.turdHalfHeight = dwfParams.turdImg.getHeight() / 2;
		_dwarfAI.init(dwfParams);
		
		Dwarf d = _dwarfAI.createDwarf();
		d.x = 400;
		d.y = 100;
	}
	
}
