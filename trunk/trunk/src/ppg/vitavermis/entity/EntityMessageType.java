package ppg.vitavermis.entity;

import java.util.EnumSet;

public enum EntityMessageType {
	STAY_IDLE, MOVE_RIGHT, MOVE_LEFT;
	
	public static final EnumSet<EntityMessageType> MOVEMENT_MSG = EnumSet.range(STAY_IDLE, MOVE_LEFT);
	public final boolean isMovementMsg() {
		return MOVEMENT_MSG.contains(this);
	}
}
