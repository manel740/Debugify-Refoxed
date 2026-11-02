package dev.isxander.debugify.client.mixins.basic.mc93384;

import com.llamalad7.mixinextras.sugar.Local;
import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@BugFix(id = "MC-93384", category = FixCategory.BASIC, env = BugFix.Env.CLIENT)
@Mixin(CommonHooks.class)
public class LivingEntityMixin {

    @Redirect(
            method = "onLivingBreathe",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
            )
    )
    private static void redirectBubbleParticle(
            Level level, ParticleOptions particle,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed,
            @Local(argsOnly = true) LivingEntity entity
    ) {
        level.addParticle(particle, x, y + entity.getEyeHeight(), z, xSpeed, ySpeed, zSpeed);
    }
}
