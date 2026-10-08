package net.firefoxsalesman.dungeonsmobs.mod;

import net.minecraft.core.registries.Registries;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.blocks.MagicFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModBlocks {
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK,
			DungeonsMobs.MOD_ID);

	public static Supplier<Block> MAGIC_FIRE = BLOCKS.register("magic_fire",
			() -> new MagicFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
					.replaceable().noCollission().instabreak().lightLevel(x -> 10)
					.sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
