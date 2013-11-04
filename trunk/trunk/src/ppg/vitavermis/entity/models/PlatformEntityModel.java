package ppg.vitavermis.entity.models;

import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.entity.EntityModel;
import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.render.SpriteModel;

public class PlatformEntityModel implements EntityModel {

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
	
	private PlatformEntityModel(
		@Param("width") int width,
		@Param("height") int height,
		@Param("spriteName") String spriteName,
		@Param("name") String name
	) throws SlickException {
		this.modelVis = new SpriteModel(spriteName, spriteName, width, height);
		this.modelPhy = ItemModel.newStaticItemModel("PlatformEntityModel_" + name).asBox(width / 2, height / 2).build();
	}
}
