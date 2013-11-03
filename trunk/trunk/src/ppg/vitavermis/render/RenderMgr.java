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
import ppg.vitavermis.items.Item;
import ppg.vitavermis.physics.ItemModel;
import ppg.vitavermis.physics.ItemState;

public class RenderMgr {
	private final static float SCALE = 50f;
	
	private List<Sprite> spriteList;
	
	public RenderMgr() {
		this.spriteList = new ArrayList<Sprite>();
	}
	
	public void init(GameState gameState, Graphics graphics) {
		graphics.setBackground(gameState.backgroundColor);
	}
	
	public final Sprite createSprite(String spriteName, SpriteModel model, PositionProvider posRef) {
		Sprite sprite = new Sprite(spriteName, model, posRef);
		spriteList.add(sprite);
		return sprite;
	}
	
	public void render(Graphics _g, GameState gameState, boolean debug_mode, boolean debug_mode_2) throws SlickException {
		for (Sprite sprite : spriteList) {
			final Vec2 pos = sprite.posRef.getPos().mul(SCALE);
			final Vector2f dimensions = sprite.model.dimensions.copy().scale(SCALE);
			final Renderable renderable = sprite.model.renderable;
			// Ugly hack because Renderable doesn't have a draw(x, y, width, height) 
			// Fixed by submitting a patch to Slick : we need to update our SLick version to fix this
			if (renderable instanceof Animation) {
				((Animation)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);
			} else if (renderable instanceof Image) {
				((Image)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);				
			} else {
				throw new IllegalArgumentException("Invalid renderable : " + renderable);
			}
			
			/*if (debug_mode == true) {
				Rectangle rectangle = item.getBoundingBox();
				_g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
			if (debug_mode_2 == true) {
				_g.drawString(item.getName(), pos.x + 10, pos.y + 10);
			}*/
			_g.drawString("TIME: " + gameState.getTimeSinceStart() / 1000f, 0, 50);
		}
	}
}
