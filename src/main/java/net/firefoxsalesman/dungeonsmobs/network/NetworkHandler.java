package net.firefoxsalesman.dungeonsmobs.network;

import net.firefoxsalesman.dungeonsmobs.network.message.AncientMessage;
import net.firefoxsalesman.dungeonsmobs.network.message.BossBarMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {

	protected static int PACKET_COUNTER = 0;

	public NetworkHandler() {
	}

	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(AncientMessage.TYPE, AncientMessage.STREAM_CODEC, AncientMessage::handle);
		registrar.playToClient(BossBarMessage.TYPE, BossBarMessage.STREAM_CODEC, BossBarMessage::onPacketReceived);
		// registrar.playToClient(AnimatedPropsMessage.TYPE, AnimatedPropsMessage.STREAM_CODEC, AnimatedPropsMessage::handle);
	}

	public static int incrementAndGetPacketCounter() {
		return PACKET_COUNTER++;
	}
}
