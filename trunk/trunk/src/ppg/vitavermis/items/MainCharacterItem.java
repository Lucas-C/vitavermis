package ppg.vitavermis.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ppg.vitavermis.config.Param;

public class MainCharacterItem extends MobileItem {

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

	public MainCharacterItem(@Param("name") String name, @Param("spriteName") String spriteName) throws SlickException {
		super(new Image(spriteName), name);
	}

}