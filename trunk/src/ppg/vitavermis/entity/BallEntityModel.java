package ppg.vitavermis.entity;

import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.render.SpriteModel;

public class BallEntityModel {

	public ItemModel modelPhy;
	public SpriteModel modelVis;
	
	private BallEntityModel(
		@Param("radius") int radius,
		@Param("restitution") float restitution,
		@Param("spriteName") String spriteName,
		@Param("name") String name
	) throws SlickException {
		this.modelVis = new SpriteModel(spriteName, spriteName, radius*2, radius*2);
		this.modelPhy = ItemModel.newDynamicItemModel("BallEntityModel_" + name).asCircle(radius).withRestitution(restitution).build();
	}
}
