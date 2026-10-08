package net.firefoxsalesman.dungeonsmobs.network.message;

import net.firefoxsalesman.dungeonsmobs.network.message.client.AncientClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class AncientMessage implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<AncientMessage> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("dungeonsmobs", "ancient"));

	public static final StreamCodec<FriendlyByteBuf, AncientMessage> STREAM_CODEC =
			StreamCodec.of((buffer, message) -> message.encode(buffer), AncientMessage::decode);

	private final int entityId;
	private final boolean ancient;

	public AncientMessage(int entityId, boolean ancient) {
		this.entityId = entityId;
		this.ancient = ancient;
	}

	public static AncientMessage decode(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();
		boolean ancient = buffer.readBoolean();

		return new AncientMessage(entityId, ancient);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public void handle(IPayloadContext context) {
		if (context.flow() == PacketFlow.CLIENTBOUND) {
			context.enqueueWork(() -> AncientClientHandler.handle(this.entityId, this.ancient));
		}
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeBoolean(ancient);
	}
}
