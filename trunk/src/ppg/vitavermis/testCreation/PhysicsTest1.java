package ppg.vitavermis.testCreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ppg.vitavermis.GameState;
import ppg.vitavermis.config.Param;
import ppg.vitavermis.config.modelloader.ClassGenerator;
import ppg.vitavermis.entity.BallEntityModel;
import ppg.vitavermis.entity.PlatformEntityModel;
import ppg.vitavermis.items.*;
import ppg.vitavermis.maincharacter.MainCharacter;
import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.physics.PhysicsMgr;
import ppg.vitavermis.render.RenderMgr;
import ppg.vitavermis.render.Sprite;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 * Fichier test pour chargement d'objets
 * @author parpaing
 */
public final class PhysicsTest1 extends Scene {
	@Override
	public GameState getInitialGameState(PhysicsMgr physics, RenderMgr renderer) throws SlickException {
		/*/ hero
		MainCharacter hero = ClassGenerator.generateClasses(MainCharacter.class, "models", ".conf").get("bob");
		itemsList.add(hero);*/
		
		/*Map<String, BackgroundItem> BackgroundItems = ClassGenerator.generateClasses(BackgroundItem.class, "models", ".conf");
		for (final BackgroundItem background : BackgroundItems.values()) {
			itemsList.add(background);
		}*/
		
		Map<String, PlatformEntityModel> platforms = ClassGenerator.generateClasses(PlatformEntityModel.class, "models", ".conf");
		/*for (final PlatformEntityModel platform : platforms.values()) {
			itemsList.add(platform);
		}*/
		
		// Level loading (should be moved elsewhere & automated)
		PlatformEntityModel block = platforms.get("Block");
		ItemState ballPhy = physics.createItem("BlockItem", block.modelPhy, new Vec2(10,10));
		Sprite ballSprite = renderer.createSprite("BlockSprite", block.modelVis, ballPhy);

		Map<String, BallEntityModel> balls = ClassGenerator.generateClasses(BallEntityModel.class, "models", ".conf");
		BallEntityModel bouncer = balls.get("Bouncer");
		ItemState bouncerPhy = physics.createItem("BouncerItem", bouncer.modelPhy, new Vec2(10,0));
		Sprite bouncerSprite = renderer.createSprite("BouncerSprite", bouncer.modelVis, bouncerPhy);

		return new GameState(Color.black);
	}	
}
