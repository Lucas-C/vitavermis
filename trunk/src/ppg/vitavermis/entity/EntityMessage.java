package ppg.vitavermis.entity;

public class EntityMessage {
	public final EntityMessageType type;
	public final Entity entity;
	public EntityMessage(EntityMessageType type, Entity entity) {
		this.type = type;
		this.entity = entity;
	}
	public String toString () {
		return entity + " -> " + type;
	}
}
