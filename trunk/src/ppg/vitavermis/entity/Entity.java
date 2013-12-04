package ppg.vitavermis.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	static final EntityMgr entityMgr = EntityMgr.getInstance();
	private final List<EntityBehaviour> behaviours;

	Entity() {
		entityMgr.registerEntity(this);
		behaviours = new ArrayList<EntityBehaviour>();
	}

	protected void addBehaviour(EntityBehaviour behaviour) {
		behaviours.add(behaviour);
	}
	
	void update() {
		for (EntityBehaviour behaviour : behaviours) {
			behaviour.update();
		}
	}
	
	void consumeMsg(EntityMessage msg) {
		for (EntityBehaviour behaviour : behaviours) {
			if (behaviour.consumeMsg(msg)) {
				return;
			}
		}
		throw new RuntimeException("Non-consumed message : " + msg);
	}
}
