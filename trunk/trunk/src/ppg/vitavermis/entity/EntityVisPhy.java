package ppg.vitavermis.entity;

import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.render.Sprite;

// An Entity with both a physical model & a rendering sprite
public class EntityVisPhy implements Entity {
	EntityModel model;
	ItemState itemPhy;
	Sprite sprite;
	
	public EntityVisPhy(EntityModel model, ItemState itemPhy, Sprite sprite){
		this.model = model;
		this.itemPhy = itemPhy;
		this.sprite = sprite;
	}
}
