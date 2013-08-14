package ppg.vitavermis.entity.kicker;

import ppg.vitavermis.entity.EntityMessage;

public class KickMessage implements EntityMessage {
	final Kicker kicker;
	
	KickMessage(Kicker kicker) {
		this.kicker = kicker;
	}

	@Override
	public void getConsumed() {
	}
}
