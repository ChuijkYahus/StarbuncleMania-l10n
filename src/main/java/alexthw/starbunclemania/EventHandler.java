package alexthw.starbunclemania;

import alexthw.starbunclemania.common.block.fluids.LiquidJarTile;
import alexthw.starbunclemania.common.block.fluids.MixinFluidProvider;
import alexthw.starbunclemania.registry.ModRegistry;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

@EventBusSubscriber(modid = StarbuncleMania.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EventHandler {


    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, BlockRegistry.MOB_JAR_TILE.get(), (be, side) -> {
                    if (be instanceof MixinFluidProvider mfp) {
                        return mfp.getStarbuncleMania$tank();
                    }
                    return null;
                }
        );

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModRegistry.FLUID_JAR_TILE.get(), (be, side) -> be.tank
        );

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModRegistry.FLUID_SOURCELINK_TILE.get(), (be, side) -> be.tank
        );

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModRegistry.SOURCE_CONDENSER_TILE.get(), (be, side) -> be.tank
        );

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModRegistry.MIXER_WIXIE_CAULDRON_TILE.get(), (be, side) -> be.tank
        );

        event.registerItem(Capabilities.FluidHandler.ITEM, (s, c) -> new FluidHandlerItemStack(ModRegistry.FLUID_CONTENT, s, LiquidJarTile.capacity),
                ModRegistry.FLUID_JAR.get().asItem());
    }
}
