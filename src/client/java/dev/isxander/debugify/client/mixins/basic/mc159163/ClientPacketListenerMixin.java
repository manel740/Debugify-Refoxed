package dev.isxander.debugify.client.mixins.basic.mc159163;

import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@BugFix(id = "MC-159163", category = FixCategory.BASIC, env = BugFix.Env.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
    protected ClientPacketListenerMixin(Minecraft client, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(client, connection, commonListenerCookie);
    }

    @ModifyVariable(method = "handleSetEntityData", at = @At("HEAD"), argsOnly = true)
    private ClientboundSetEntityDataPacket removeLocalEntityPose(ClientboundSetEntityDataPacket packet) {
        if (Minecraft.getInstance().player != null && packet.id() == Minecraft.getInstance().player.getId()) {
            packet.packedItems().removeIf(p -> p.serializer().equals(EntityDataSerializers.POSE));
        }
        return packet;
    }
}
