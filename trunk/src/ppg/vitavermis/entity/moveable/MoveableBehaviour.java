package ppg.vitavermis.entity.moveable;

import org.jbox2d.common.Vec2;

import ppg.vitavermis.entity.EntityBehaviour;
import ppg.vitavermis.entity.EntityMessage;
import ppg.vitavermis.entity.EntityMessageType;
import ppg.vitavermis.physics.ItemState;

public class MoveableBehaviour implements EntityBehaviour {
	EntityMessageType moveState = EntityMessageType.STAY_IDLE; // Should always be a MOVEMENT_MSG
	final MoveableEntity moveable;
	final float maxSpeed;
	final float fastSpeedChangeFactor;

	public MoveableBehaviour(MoveableEntity moveable, float maxSpeed, float fastSpeedChangeFactor, float autoIdleFramesCount) {
		this.moveable = moveable;
		this.maxSpeed = maxSpeed;
		this.fastSpeedChangeFactor = fastSpeedChangeFactor;
	}

	@Override
	public void update() {
		ItemState itemPhyState = moveable.getItemState();
		Vec2 velocity = itemPhyState.getVelocity();
		//System.out.println("Moveable update: " + moveState + " | " + velocity);
		float desiredVelX = getDesiredVelocityX();
		velocity.x = velocity.x + (desiredVelX - velocity.x) * fastSpeedChangeFactor;
		itemPhyState.setVelocity(velocity);		
		//float velChangeX = desiredVelX - velocity.x;
		//float impulseX = itemPhyState.getMassInKg() * velChangeX;
		//itemPhyState.applyLinearImpulse(new Vec2(impulseX, 0));
	}
	
	private float getDesiredVelocityX() {
		switch(moveState) {
		case STAY_IDLE:
			return 0;
		case MOVE_LEFT:
			return -maxSpeed;
		case MOVE_RIGHT:
			return maxSpeed;
		}
		return 0;
	}

	@Override
	public boolean consumeMsg(EntityMessage msg) {
		//System.out.println("Moveable consumeMsg: " + msg);
		if (msg.type.isMovementMsg()) {
			transitionToNewState(msg.type);
			return true;
		}
		return false;
	}
	
	private void transitionToNewState(EntityMessageType newState) {
		moveState = newState;
	}
}
