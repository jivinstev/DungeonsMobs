package net.firefoxsalesman.dungeonsmobs.capabilities;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.capabilities.convertible.Convertible;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import java.util.function.Supplier;

public class ModCapabilities {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
			DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DungeonsMobs.MOD_ID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Convertible>> CONVERTIBLE_CAPABILITY =
			ATTACHMENTS.register("convertible", () -> AttachmentType.builder(Convertible::new)
					.serialize(nbt(Convertible::new)).build());

	private static <A extends INBTSerializable<CompoundTag>> IAttachmentSerializer<CompoundTag, A> nbt(
			Supplier<A> make) {
		return new IAttachmentSerializer<>() {
			@Override
			public A read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
				A value = make.get();
				value.deserializeNBT(provider, tag);
				return value;
			}

			@Override
			public CompoundTag write(A value, HolderLookup.Provider provider) {
				return value.serializeNBT(provider);
			}
		};
	}
}
