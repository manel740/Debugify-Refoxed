package dev.isxander.debugify.client.mixins.basic.mc176559;

import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.fixes.FixCategory;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;

@BugFix(id = "MC-176559", category = FixCategory.BASIC, env = BugFix.Env.CLIENT, modConflicts = "neoforge")
@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
}
