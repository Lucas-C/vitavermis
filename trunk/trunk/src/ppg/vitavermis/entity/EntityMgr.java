package ppg.vitavermis.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ppg.vitavermis.input.InputEvent;

public class EntityMgr {
	private static final EntityMgr INSTANCE = new EntityMgr();
	public static EntityMgr getInstance() {
        return INSTANCE;
    }
	
	//TODO: Handle order here, we may want some entities to update before others
	private final List<Entity> entities;
	private final Queue<EntityMessage> entityQueue;

	private EntityMgr() {
		entityQueue = new LinkedList<EntityMessage>();
		entities = new ArrayList<Entity>();
	}
	
	public void sendMsg(EntityMessage msg) {
		entityQueue.add(msg);
	}
	
	void registerEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void update() {
		while (!entityQueue.isEmpty()) {
			EntityMessage msg = entityQueue.remove();
			msg.entity.consumeMsg(msg);
		}
		for (Entity entity : entities) {
			entity.update();
		}
	}
}
