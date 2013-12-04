package ppg.vitavermis.maincharacter;

import org.newdawn.slick.Input;

import ppg.vitavermis.entity.EntityMessageType;
import ppg.vitavermis.input.InputEvent;

public class RememberingComboBuffer {
	//TODO: put those attributes in proper classes like 'InputMemory'
	int consecFramesIdle = 0;
	boolean movedRightSinceLastFrame = false;
	boolean movedLeftSinceLastFrame = false;

	public RememberingComboBuffer() {
		resetShortTermMemory();
	}
	
	private void resetShortTermMemory() {
		movedRightSinceLastFrame = false;
		movedLeftSinceLastFrame = false;
	}


	private void flushShortTermMemory(boolean idleSinceLastFrame) {
		if (idleSinceLastFrame) {
			consecFramesIdle++;
		} else {
			consecFramesIdle = 0;
		}
		resetShortTermMemory();
	}

	public boolean consumeEvent(InputEvent event) {
		switch (event) {
		case GO_LEFT:
			movedLeftSinceLastFrame = true;
			return true;
		case GO_RIGHT:
			movedRightSinceLastFrame = true;
			return true;
		}
		return false;
	}

	public EntityMessageType getMoveMsgAndFlushMemory(Input inputContext) {
		//System.out.println("inputContext.isKeyDown(InputEvent.GO_RIGHT.key_code)= " + inputContext.isKeyDown(InputEvent.GO_RIGHT.key_code));
		//System.out.println("inputContext.isKeyDown(InputEvent.GO_LEFT.key_code)= " + inputContext.isKeyDown(InputEvent.GO_LEFT.key_code));
		movedRightSinceLastFrame |= inputContext.isKeyDown(InputEvent.GO_RIGHT.key_code);
		movedLeftSinceLastFrame |= inputContext.isKeyDown(InputEvent.GO_LEFT.key_code);
		boolean idleSinceLastFrame = !(movedRightSinceLastFrame ^ movedLeftSinceLastFrame);
		EntityMessageType moveMsg = getMoveMsg(idleSinceLastFrame);
		flushShortTermMemory(idleSinceLastFrame);
		return moveMsg;
	}
	
	private EntityMessageType getMoveMsg(boolean idleSinceLastFrame) {
		//System.out.println("movedLeftSinceLastFrame= " + movedLeftSinceLastFrame);
		//System.out.println("movedRightSinceLastFrame= " + movedRightSinceLastFrame);
		//System.out.println("idleSinceLastFrame= " + idleSinceLastFrame);
		if (movedRightSinceLastFrame && !movedLeftSinceLastFrame) {
			return EntityMessageType.MOVE_RIGHT;
		}
		if (movedLeftSinceLastFrame && !movedRightSinceLastFrame) {
			return EntityMessageType.MOVE_LEFT;
		}
		assert idleSinceLastFrame;
		return EntityMessageType.STAY_IDLE;
	}
}
