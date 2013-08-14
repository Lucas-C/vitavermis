package ppg.vitavermis.maincharacter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.entity.Entity;
import ppg.vitavermis.entity.kicker.Kicker;
import ppg.vitavermis.items.Item;
import ppg.vitavermis.items.MobilItem;

public class MainCharacter extends MobilItem implements Entity, Kicker {

	private boolean jumping;	
	private Item contactItem;
	private boolean contact;
	private float jumpPow;
	
	public final float getJumpPow() {
		return jumpPow;
	}

	public final void setJumpPow(float jumpPow1) {
		this.jumpPow = jumpPow1;
	}

	public final boolean isJumping() {
		return jumping;
	}

	public final Item getContactItem() {
		return contactItem;
	}

	public final void setContactItem(Item contactItem1) {
		this.contactItem = contactItem1;
	}

	public final boolean isContact() {
		return contact;
	}

	public final void setContact(boolean contact1) {
		this.contact = contact1;
	}

	public final void setJumping(boolean jumping1) {
		this.jumping = jumping1;
	}

	private MainCharacter(
			@Param("x") int x,
			@Param("y") int y,
			@Param("width") int width,
			@Param("height") int height,
			@Param("spriteName") String spriteName,
			@Param("name") String name
			) throws SlickException {
		super(new Image(spriteName), new Vector2f(width,height), name);
		this.setPosition(new Vector2f(x, y));
	}

}