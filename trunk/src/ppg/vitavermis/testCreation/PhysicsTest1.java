package ppg.vitavermis.testCreation;

import java.util.HashMap;
import java.util.Map;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.modelloader.ClassGenerator;
import ppg.vitavermis.entity.EntityModel;
import ppg.vitavermis.entity.EntityVisPhy;
import ppg.vitavermis.entity.models.BallEntityModel;
import ppg.vitavermis.entity.models.PlatformEntityModel;
import ppg.vitavermis.maincharacter.MainCharacter;
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
		Map<Vec2, String> level = new HashMap<Vec2, String>();
		level.put(new Vec2(0, 0), "Block");
		level.put(new Vec2(5, 10), "Block");
		level.put(new Vec2(6, 10), "Block");
		level.put(new Vec2(7, 10), "Block");
		level.put(new Vec2(8, 10), "Block");
		level.put(new Vec2(9, 10), "Block");
		level.put(new Vec2(10, 10), "Block");
		level.put(new Vec2(11, 10), "Block");
		level.put(new Vec2(12, 10), "Block");
		level.put(new Vec2(13, 10), "Block");
		level.put(new Vec2(14, 10), "Block");
		level.put(new Vec2(15, 10), "Block");
		level.put(new Vec2(16, 10), "Block");
		level.put(new Vec2(10, 0), "Bouncer");
				
		Map<String, PlatformEntityModel> platforms = ClassGenerator.generateClasses(PlatformEntityModel.class, "models", ".conf");
		Map<String, BallEntityModel> balls = ClassGenerator.generateClasses(BallEntityModel.class, "models", ".conf");

		Map<String, EntityModel> models = new HashMap<String, EntityModel>();
		models.putAll(platforms);
		models.putAll(balls);

		// TODO: move that elsewhere - Only valid for EntityVisPhy
		MainCharacter hero = null;
		for (Map.Entry<Vec2, String> entityPos : level.entrySet()) {
			final Vec2 position = entityPos.getKey();
			final String modelName = entityPos.getValue();
			System.out.println("modelName=" + modelName);	
			final EntityModel model = models.get(modelName);
			System.out.println("model=" + model);	
			final ItemState itemPhy = physics.createItem(modelName + "_Item", model.getModelPhy(), position);
			final Sprite sprite = renderer.createSprite(modelName + "_Sprite", model.getModelVis(), itemPhy);
			if (modelName.equals("Bouncer")) {
				hero = new MainCharacter(model, itemPhy, sprite);				
			} else {
				new EntityVisPhy(model, itemPhy, sprite);
			}
		}

		//MainCharacter hero = ClassGenerator.generateClasses(MainCharacter.class, "models", ".conf").get("bob");
		assert hero != null;
		return new GameState(hero);
	}	
}
