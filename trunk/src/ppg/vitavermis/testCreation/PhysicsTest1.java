package ppg.vitavermis.testCreation;

import java.util.HashMap;
import java.util.Map;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.modelloader.ClassGenerator;
import ppg.vitavermis.entity.EntityModel;
import ppg.vitavermis.entity.EntityVisPhy;
import ppg.vitavermis.entity.models.BallEntityModel;
import ppg.vitavermis.entity.models.PlatformEntityModel;
import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.render.Sprite;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public final class PhysicsTest1 extends Scene {
	@Override
	public GameState getInitialGameState(PhysicsMgr physics, RenderMgr renderer) throws SlickException {
		// TODO: Convert to JSON file
		Map<String, Vec2> level = new HashMap<String, Vec2>();
		level.put("Block", new Vec2(10, 10));
		level.put("Bouncer", new Vec2(10, 0));
		
		//MainCharacter hero = ClassGenerator.generateClasses(MainCharacter.class, "models", ".conf").get("bob");
		
		Map<String, PlatformEntityModel> platforms = ClassGenerator.generateClasses(PlatformEntityModel.class, "models", ".conf");
		Map<String, BallEntityModel> balls = ClassGenerator.generateClasses(BallEntityModel.class, "models", ".conf");

		Map<String, EntityModel> models = new HashMap<String, EntityModel>();
		models.putAll(platforms);
		models.putAll(balls);

		// TODO: move that elsewhere - Only valid for EntityVisPhy
		for (Map.Entry<String, Vec2 > entityPos : level.entrySet()) {
			final String modelName = entityPos.getKey();
			final Vec2 position = entityPos.getValue();
			System.out.println("modelName=" + modelName);	
			final EntityModel model = models.get(modelName);
			System.out.println("model=" + model);	
			final ItemState itemPhy = physics.createItem(modelName + "_Item", model.getModelPhy(), position);
			final Sprite sprite = renderer.createSprite(modelName + "_Sprite", model.getModelVis(), itemPhy);
			new EntityVisPhy(model, itemPhy, sprite);
		}

		return new GameState(Color.black);
	}	
}
