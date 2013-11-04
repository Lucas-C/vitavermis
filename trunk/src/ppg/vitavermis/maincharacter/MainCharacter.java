package ppg.vitavermis.maincharacter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.Param;
import ppg.vitavermis.entity.Entity;
import ppg.vitavermis.entity.kicker.Kicker;

public class MainCharacter implements Entity, Kicker {

	private boolean jumping;	
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
			@Param("width") int width,
			@Param("height") int height,
			@Param("spriteName") String spriteName,
			@Param("name") String name
			) throws SlickException {
	}

}