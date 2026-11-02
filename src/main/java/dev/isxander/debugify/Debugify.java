package dev.isxander.debugify;

import dev.isxander.debugify.config.DebugifyConfig;
import dev.isxander.debugify.fixes.BugFix;
import dev.isxander.debugify.mixinplugin.DebugifyErrorHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixins;

import java.util.List;
import java.util.Map;

@Mod("debugify")
public class Debugify {
    public static final Logger LOGGER = LoggerFactory.getLogger("Debugify");
    public static final String VERSION = "1.21.1+1.1";
    public static final DebugifyConfig CONFIG = new DebugifyConfig();

    public Debugify(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        List<String> enabledBugs = CONFIG.getBugFixes().entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(entry -> entry.getKey().bugId())
                .toList();
        LOGGER.info("Enabled {} bug fixes: {}", enabledBugs.size(), enabledBugs);
        LOGGER.info("Successfully Debugify'd your game!");
    }

    public static void onPreInitialize() {
        CONFIG.preload();
        Mixins.registerErrorHandlerClass(DebugifyErrorHandler.class.getName());
    }

    public static BugFix.Env getEnv() {
        return FMLEnvironment.dist == Dist.CLIENT ? BugFix.Env.CLIENT : BugFix.Env.SERVER;
    }
}
