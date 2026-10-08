package net.firefoxsalesman.dungeonsmobs.items.shield.client;

import net.firefoxsalesman.dungeonsmobs.items.shield.bewlr.RoyalGuardShieldBEWLR;
import net.firefoxsalesman.dungeonsmobs.items.shield.bewlr.VanguardShieldBEWLR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.Lazy;

/** Client-only; only reached from Item#initializeClient. */
public class ShieldClientExtensions {
    private static final Lazy<BlockEntityWithoutLevelRenderer> ROYAL_GUARD = Lazy.of(() -> new RoyalGuardShieldBEWLR(
            Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));
    private static final Lazy<BlockEntityWithoutLevelRenderer> VANGUARD = Lazy.of(() -> new VanguardShieldBEWLR(
            Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

    public static IClientItemExtensions royalGuard() {
        return new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ROYAL_GUARD.get();
            }
        };
    }

    public static IClientItemExtensions vanguard() {
        return new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return VANGUARD.get();
            }
        };
    }
}
