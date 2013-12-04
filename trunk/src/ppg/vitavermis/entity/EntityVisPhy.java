package ppg.vitavermis.entity;

import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.render.Sprite;

// An Entity with both a physical model & a rendering sprite
public class EntityVisPhy extends Entity {
	private final EntityModel model;
	private final ItemState itemPhy;
	private final Sprite sprite;
	
	public ItemState getItemState() { return itemPhy; } 
	
	public EntityVisPhy(EntityModel model, ItemState itemPhy, Sprite sprite, EntityBehaviour... behaviours){
		this.model = model;
		this.itemPhy = itemPhy;
		this.sprite = sprite;
	}
}
