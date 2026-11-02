package dev.isxander.debugify.client;

import dev.isxander.debugify.Debugify;
import dev.isxander.debugify.client.gui.ConfigGuiHelper;
import dev.isxander.debugify.client.gui.NoYACLScreen;
import dev.isxander.debugify.client.utils.BugFixDescriptionCache;
import dev.isxander.debugify.client.utils.ClientUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = "debugify", value = Dist.CLIENT)
public class DebugifyClient {
    public static BugFixDescriptionCache bugFixDescriptionCache;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        bugFixDescriptionCache = new BugFixDescriptionCache();
        bugFixDescriptionCache.loadDescriptions();
        bugFixDescriptionCache.cacheMissingDescriptions();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> {

            if (!ModList.get().isLoaded("yet_another_config_lib_v3")) {
                return new NoYACLScreen(parent);
            }
            return ConfigGuiHelper.createConfigGui(Debugify.CONFIG, parent);
        });
    }

    public static boolean isGameplayFixesEnabled() {
        return !ClientUtils.isInMultiplayerWorld() || Debugify.CONFIG.gameplayFixesInMultiplayer;
    }
}
