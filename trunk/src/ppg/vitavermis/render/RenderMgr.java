package ppg.vitavermis.render;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.GameState;

public class RenderMgr {
	final static float SCALE = 32f;
	
	private List<Sprite> spriteList;
	
	private DebugDrawPPG debugDraw;
	public DebugDrawPPG getDebugDraw() {
		return debugDraw;
	}
	
	public RenderMgr() {
		this.spriteList = new ArrayList<Sprite>();
	}
	
	public void init(GameState gameState, Graphics graphics) {
		graphics.setBackground(gameState.backgroundColor);
		debugDraw = new DebugDrawPPG(this);
	}
	
	public final Sprite createSprite(String spriteName, SpriteModel model, PositionProvider posRef) {
		Sprite sprite = new Sprite(spriteName, model, posRef);
		spriteList.add(sprite);
		return sprite;
	}
	
	public void render(Graphics graphics, GameState gameState) throws SlickException {
		renderSpriteList(graphics, gameState);
		debugDraw.renderDebugShapes(graphics, gameState.getTimeSinceStart());
	}

	private void renderSpriteList(Graphics graphics, GameState gameState) throws SlickException {
		for (Sprite sprite : spriteList) {
			final Vec2 pos = sprite.posRef.getPos().mul(SCALE);
			final Vector2f dimensions = sprite.model.dimensions.copy().scale(SCALE);
			final Renderable renderable = sprite.model.renderable;
			// Ugly hack because Renderable doesn't have a draw(x, y, width, height) 
			// Fixed by submitting a patch to Slick : we need to update our Slick version to fix this
			if (renderable instanceof Animation) {
				((Animation)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);
			} else if (renderable instanceof Image) {
				((Image)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);				
			} else {
				throw new IllegalArgumentException("Invalid renderable : " + renderable);
			}
		}
	}
}
