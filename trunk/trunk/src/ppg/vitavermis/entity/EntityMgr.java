package ppg.vitavermis.entity;

public class EntityMgr {
	private final EntityStack entityStack;

	EntityMgr() {
		entityStack = new EntityStack();
	}
	
	public void register(EntityMessage msg) {
		entityStack.pushMessage(msg);
	}
	
	void update() {
		while (!entityStack.isEmpty()) {
			EntityMessage msg = entityStack.popMessage();
			msg.getConsumed();
		}
	}
}
