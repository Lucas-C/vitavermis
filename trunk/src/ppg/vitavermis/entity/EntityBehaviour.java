package ppg.vitavermis.entity;

public interface EntityBehaviour {
	boolean consumeMsg(EntityMessage msg);
	void update();
}
