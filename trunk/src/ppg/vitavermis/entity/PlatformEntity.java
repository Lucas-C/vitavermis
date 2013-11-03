package ppg.vitavermis.entity;

import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.render.Sprite;

public class PlatformEntity {
	private ItemState itemPhy;
	private Sprite sprite;
	
	private PlatformEntity(ItemState itemPhy, Sprite sprite){
		this.itemPhy = itemPhy;
		this.sprite = sprite;
	}
}
