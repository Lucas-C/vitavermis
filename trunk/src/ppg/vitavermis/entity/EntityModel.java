package ppg.vitavermis.entity;

import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.render.SpriteModel;

public interface EntityModel {
	public ItemModel getModelPhy();
	public SpriteModel getModelVis();
}
