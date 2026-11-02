package dev.isxander.debugify.client.mixins.basic.mc90683;

import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@BugFix(id = "MC-90683", category = FixCategory.BASIC, env = BugFix.Env.CLIENT)
@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(
            method = "handleSetEntityPassengersPacket(Lnet/minecraft/network/protocol/game/ClientboundSetPassengersPacket;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHandleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet, CallbackInfo ci) {
        if (net.minecraft.client.Minecraft.getInstance().level != null
                && net.minecraft.client.Minecraft.getInstance().level.getEntity(packet.getVehicle()) == null) {
            ci.cancel();
        }
    }
}
