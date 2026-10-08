package net.firefoxsalesman.dungeonsmobs.network.message;

import java.util.UUID;

import net.firefoxsalesman.dungeonsmobs.client.BossBarClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * This was largely borrowed from Goety, because I am a talentless hack.
 * Many thanks to Polarice.
 */
public class BossBarMessage implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<BossBarMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("dungeonsmobs", "boss_bar"));
	public static final StreamCodec<FriendlyByteBuf, BossBarMessage> STREAM_CODEC = StreamCodec.ofMember(BossBarMessage::encode, BossBarMessage::decode);

	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	private final UUID bar;
	private final int boss;
	private final boolean remove;

	public BossBarMessage(UUID bar, int boss, boolean remove) {
		this.bar = bar;
		this.boss = boss;
		this.remove = remove;
	}

	public BossBarMessage(UUID bar, Mob boss, boolean remove) {
		this(bar, boss.getId(), remove);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUUID(bar);
		buffer.writeInt(boss);
		buffer.writeBoolean(remove);
	}

	public static BossBarMessage decode(FriendlyByteBuf buffer) {
		return new BossBarMessage(buffer.readUUID(), buffer.readInt(), buffer.readBoolean());
	}

	public static void onPacketReceived(BossBarMessage message, IPayloadContext ctx) {
		if (ctx.flow() == PacketFlow.CLIENTBOUND) {
			ctx.enqueueWork(() -> BossBarClientHandler.handle(message.bar, message.boss, message.remove));
		}
	}
}
