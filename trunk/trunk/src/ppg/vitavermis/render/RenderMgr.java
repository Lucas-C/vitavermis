package ppg.vitavermis.render;


import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import ppg.vitavermis.items.ItemsPhy;

public class RenderMgr {
	public void init() {
	}
	
	/**
	 * 
	 * @param _g
	 * @param liste
	 * @param debug_mode
	 * @param debug_mode_2
	 * @throws SlickException
	 */
	public void render(Graphics _g, ArrayList<ItemsPhy> liste, boolean debug_mode, boolean debug_mode_2) throws SlickException {
		
		for ( ItemsPhy item : liste) {
			item.getItemModel().getImage().draw( item.getPosition().x, item.getPosition().y,  item.getWidth(), item.getHeight());
			if (debug_mode == true) {
				Rectangle rectangle = item.rectangle();
				_g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
			if (debug_mode_2 == true) {
				_g.drawString(item.getItemModel().com, item.getPosition().x + 10, item.getPosition().y + 10);
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
