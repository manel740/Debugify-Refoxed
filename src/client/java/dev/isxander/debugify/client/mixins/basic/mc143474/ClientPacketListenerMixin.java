package dev.isxander.debugify.client.mixins.basic.mc143474;

import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@BugFix(id = "MC-143474", category = FixCategory.BASIC, env = BugFix.Env.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {

    @Unique
    private int debugify$cachedSlot = 0;

    protected ClientPacketListenerMixin(Minecraft client, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(client, connection, commonListenerCookie);
    }


    @Inject(method = "handleRespawn", at = @At("HEAD"))
    private void saveSlotBeforeRespawn(ClientboundRespawnPacket packet, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            debugify$cachedSlot = Minecraft.getInstance().player.getInventory().selected;
        }
    }


    @Inject(method = "handleRespawn", at = @At("RETURN"))
    private void restoreSlotAfterRespawn(ClientboundRespawnPacket packet, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {

            Minecraft.getInstance().player.getInventory().selected = debugify$cachedSlot;


            if (Minecraft.getInstance().getConnection() != null) {
                Minecraft.getInstance().getConnection().send(new ServerboundSetCarriedItemPacket(debugify$cachedSlot));
            }
        }
    }
}
