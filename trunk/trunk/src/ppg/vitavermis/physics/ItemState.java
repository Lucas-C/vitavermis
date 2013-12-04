package ppg.vitavermis.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import ppg.vitavermis.render.PositionProvider;

/*
 * Dynamic characteristics of a single item at a give time. E.g. its position. 
 */
public class ItemState implements PositionProvider {

	final public String name;
	final private ItemModel model;
	private Body body;
	
	ItemState(String name, ItemModel model, Body body) {
		this.name = name;
		this.model = model;
		this.body = body;
	}
	
	public final Vec2 getPos() {
		return body.getPosition();
	}
	
	public final float getMassInKg() {
		return this.body.getMass();
	}
	
	public Vec2 getVelocity() {
		return this.body.getLinearVelocity();
	}

	public void setVelocity(Vec2 vel) {
		this.body.setLinearVelocity(vel);
	}

	public void applyLinearImpulse(Vec2 impulse) {
		this.body.applyLinearImpulse(impulse, this.body.getWorldCenter());
	}
}
