package ppg.vitavermis.entity;

import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.render.Sprite;

public class BallEntity {
	private ItemState itemPhy;
	private Sprite sprite;
	
	private BallEntity(ItemState itemPhy, Sprite sprite){
		this.itemPhy = itemPhy;
		this.sprite = sprite;
	}
}
