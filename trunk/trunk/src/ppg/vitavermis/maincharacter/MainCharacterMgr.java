package ppg.vitavermis.maincharacter;

import org.newdawn.slick.Input;

import ppg.vitavermis.entity.EntityMessage;
import ppg.vitavermis.entity.EntityMessageType;
import ppg.vitavermis.entity.EntityMgr;
import ppg.vitavermis.input.InputEvent;

public class MainCharacterMgr {
	RememberingComboBuffer comboBuffer;
	EntityMgr entityMgr;
	MainCharacter hero;
	
	public void init(MainCharacter mainCharacter, EntityMgr entityMgr) {
		this.comboBuffer = new RememberingComboBuffer();
		this.hero = mainCharacter;
		this.entityMgr = entityMgr;
	}
	
	public boolean consumeEvent(InputEvent event) {
		//System.out.println("MainCharacterMgr consumeEvent: " + event);
		return comboBuffer.consumeEvent(event);
	}

	public void update(Input inputContext) {
		EntityMessageType moveMsg = comboBuffer.getMoveMsgAndFlushMemory(inputContext);
		EntityMessage heroMovemsg = new EntityMessage(moveMsg, hero);
		entityMgr.sendMsg(heroMovemsg);
	}
}
