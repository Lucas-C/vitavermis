package ppg.vitavermis.render;


import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.items.Item;

public class RenderMgr {
	public void init() {
	}
	
	/**
	 * 
	 * @param _g
	 * @param listeItems
	 * @param debug_mode
	 * @param debug_mode_2
	 * @throws SlickException
	 */
	public void render(Graphics _g, ArrayList<Item> listeItems, boolean debug_mode, boolean debug_mode_2) throws SlickException {
		
		for ( Item item : listeItems) {
			item.getImage().draw( item.getPosition().x, item.getPosition().y,  item.getWidth(), item.getHeight());
			
			if (debug_mode == true) {
				Rectangle rectangle = item.rectangle();
				_g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
			if (debug_mode_2 == true) {
				_g.drawString(item.name, item.getPosition().x + 10, item.getPosition().y + 10);
			}
			
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
