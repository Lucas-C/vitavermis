package ot;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RenderMgr {
	static final int nbLayers = 5;
	static final int zeroLayerOffset = 2;
	static final int baseCapacity = 10; // per layer
	
	ArrayList<ArrayList<Entity>> m_layeredEntities = null;
	
	public void init() {
		m_layeredEntities = new ArrayList<ArrayList<Entity>>(nbLayers);
		for (int i = 0; i < nbLayers; i++) {
			ArrayList<Entity> layer = new ArrayList<Entity>();
			layer.ensureCapacity(baseCapacity);
			m_layeredEntities.add(layer);
		}
	}
	
	public void add(Entity _e) {
		m_layeredEntities.get(_e.layerLvl + zeroLayerOffset).add(_e);
	}
	
	public void remove(Entity _e) {
		m_layeredEntities.get(_e.layerLvl + zeroLayerOffset).add(_e);
	}

	public void render(Graphics _g) throws SlickException {
		// Configuring dbgDrawHeight
		_g.setColor(Color.green);
		_g.setLineWidth(5); //TODO: arbitrary value
		// Looping through Entities
		for (ArrayList<Entity> layer : m_layeredEntities)
			for (Entity e : layer) {
				Image img = e.img;
				img.draw(e.x -img.getWidth() / 2, e.y - img.getHeight() / 2);
				if (e.dbgDrawHeight) {
					_g.drawLine(e.x - e.halfHeight, e.y, e.x + e.halfHeight, e.y);
				}
			}
	}
}
