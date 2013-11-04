package ppg.vitavermis.entity.models;

import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.entity.EntityModel;
import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.render.SpriteModel;

public class BallEntityModel implements EntityModel {

	public ItemModel modelPhy;
	public SpriteModel modelVis;
	
	@Override
	public ItemModel getModelPhy() {
		return this.modelPhy;
	}

	@Override
	public SpriteModel getModelVis() {
		return this.modelVis;
	}

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
