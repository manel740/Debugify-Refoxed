package dev.isxander.debugify.mixins.basic.mc121903;

import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.nbt.CompoundTag;

@BugFix(id = "MC-121903", category = FixCategory.BASIC, env = BugFix.Env.SERVER)
@Mixin(MinecartCommandBlock.class)
public class MinecraftCommandBlockMixin {
    @Shadow private int lastActivated;

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"), require = 0)
    private void readNbt(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("LastExecuted")) {
            this.lastActivated = nbt.getInt("LastExecuted");
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"), require = 0)
    private void writeNbt(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("LastExecuted", this.lastActivated);
    }
}
