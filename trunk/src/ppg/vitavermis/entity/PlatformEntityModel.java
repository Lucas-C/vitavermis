package ppg.vitavermis.entity;

import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.render.SpriteModel;

public class PlatformEntityModel {

	public ItemModel modelPhy;
	public SpriteModel modelVis;
	
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
