package alexthw.starbunclemania;

import alexthw.starbunclemania.common.item.cosmetic.PlayerCurioCosmetic;
import alexthw.starbunclemania.registry.ModRegistry;
import com.alexthw.sauce.Sauce;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForgeMod;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(StarbuncleMania.MODID)
public class StarbuncleMania {
    public static final String MODID = "starbunclemania";

    public StarbuncleMania(ModContainer modContainer, IEventBus modbus) {
        //ArsNouveau.isDebug = false;
        NeoForgeMod.enableMilkFluid();
        Sauce.ENABLE_LIQUID_SOURCE = true;
        ModRegistry.registerRegistries(modbus);
        modContainer.registerConfig(ModConfig.Type.SERVER, Configs.SERVER_SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, Configs.COMMON_SPEC);
        ArsNouveauRegistry.register();
        modbus.addListener(this::setup);
        if (FMLEnvironment.dist.isClient()) modbus.addListener(PlayerCurioCosmetic::registerRenderers);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent ignoredEvent) {
        ArsNouveauRegistry.postInit();
    }

}
