package dev.isxander.debugify.mixins.basic.mc121706;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@BugFix(id = "MC-121706", env = BugFix.Env.SERVER, category = FixCategory.BASIC)
@Mixin(RangedBowAttackGoal.class)
public abstract class RangedBowAttackGoalMixin<T extends Mob & RangedAttackMob> {


    @Shadow @Final private T mob;


    @WrapWithCondition(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/MoveControl;strafe(FF)V")
    )
    private boolean debugify$lookAtTarget(MoveControl moveControl, float forward, float strafe) {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {

            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        return true;
    }
}
