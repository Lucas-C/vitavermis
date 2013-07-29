package ppg.vitavermis.render;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.GameState;
import ppg.vitavermis.items.Item;

public class RenderMgr {
	public void init(GameState gameState, Graphics graphics) {
		graphics.setBackground(gameState.backgroundColor);
	}
	
	public void render(Graphics _g, GameState gameState, boolean debug_mode, boolean debug_mode_2) throws SlickException {
		ArrayList<Item> itemsList = gameState.itemsList;
		
		for ( Item item : itemsList) {
			final Vector2f pos = item.getPosition();
			final Vector2f dimensions = item.getDimensions();
			final Renderable renderable = item.getRenderable();
			// Ugly hack because Renderable doesn't have a draw(x, y, width, height) 
			// (could be fixed by submitting a patch to Slick)
			if (renderable instanceof Animation) {
				((Animation)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);
			} else if (renderable instanceof Image) {
				((Image)renderable).draw(pos.x, pos.y, dimensions.x, dimensions.y);				
			} else {
				throw new IllegalArgumentException("Invalid renderable : " + renderable);
			}
			
			if (debug_mode == true) {
				Rectangle rectangle = item.rectangle();
				_g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
			if (debug_mode_2 == true) {
				_g.drawString(item.getName(), pos.x + 10, pos.y + 10);
			}
			_g.drawString("TIME: " + gameState.getTimeSinceStart() / 1000f, 0, 50);
		}
		/*
		liste.get(0).getItemModel().getImage().draw( liste.get(0).getPosition().x, liste.get(0).getPosition().y);
		if (debug_mode == true) {
			Rectangle rectangle = liste.get(0).rectangle();
			_g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
		if (debug_mode_2 == true) {
			_g.drawString(liste.get(0).getItemModel().com, liste.get(0).getPosition().x + 10, liste.get(0).getPosition().y + 10);
		}
	*/
	}
}
