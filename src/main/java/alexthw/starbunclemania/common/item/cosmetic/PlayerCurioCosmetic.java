package alexthw.starbunclemania.common.item.cosmetic;

import alexthw.starbunclemania.client.HeadCosmeticRenderer;
import alexthw.starbunclemania.client.PropellerRenderer;
import alexthw.starbunclemania.registry.ModRegistry;
import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class PlayerCurioCosmetic extends ArsNouveauCurio {
    public PlayerCurioCosmetic(Properties properties) {
        super(properties);
    }

    @Override
    public PlayerCurioCosmetic withTooltip(String tip) {
        return (PlayerCurioCosmetic) super.withTooltip(tip);
    }


    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModRegistry.STARBY_EARS.get(), HeadCosmeticRenderer::new);
        CuriosRendererRegistry.register(ModRegistry.ALAK_HAT.get(), HeadCosmeticRenderer::new);
        CuriosRendererRegistry.register(ModRegistry.SEA_BUNNY.get(), HeadCosmeticRenderer::new);
        CuriosRendererRegistry.register(ModRegistry.CHEF_HAT.get(), HeadCosmeticRenderer::new);
        CuriosRendererRegistry.register(ModRegistry.DRYGMY_HORNS.get(), HeadCosmeticRenderer::new);
        CuriosRendererRegistry.register(ModRegistry.WHIRLI_PROP.get(), PropellerRenderer::new);

    }

}
