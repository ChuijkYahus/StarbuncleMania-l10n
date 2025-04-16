package alexthw.starbunclemania;

import com.hollingsworth.arsnouveau.api.documentation.DocCategory;
import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent;
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder;
import com.hollingsworth.arsnouveau.api.documentation.entry.DocEntry;
import com.hollingsworth.arsnouveau.api.documentation.entry.TextEntry;
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import static alexthw.starbunclemania.registry.ModRegistry.*;
import static com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry.*;
import static com.hollingsworth.arsnouveau.setup.registry.Documentation.addPage;
import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

@EventBusSubscriber
public class StarbDocs {

    @SubscribeEvent
    public static void addPages(ReloadDocumentationEvent.AddEntries event) {
        init(event);
    }

    private static void init(ReloadDocumentationEvent.AddEntries event) {
        addBasicItem(FLUID_JAR.get(), SOURCE);
        var condenser = addBasicItem(SOURCE_CONDENSER.get(), SOURCE);
        addBasicItem(FLUID_SOURCELINK.get(), SOURCE).withRelation(condenser);

        var dirScroll = addBasicItem(DIRECTION_SCROLL.get(), CRAFTING);
        var prof = addBasicItem(PROFHAT.get(), CRAFTING);
        var fluid = addPage(new DocEntryBuilder(CRAFTING, STARBUCKET.get())
                .withTextPage("starbunclemania.page.star_bucket")
                .withCraftingPages(STARBUCKET.get())
                .withTextPage("starbunclemania.page.fluid_scroll")
                .withCraftingPages(FLUID_SCROLL_ALLOW.get())
                .withCraftingPages(FLUID_SCROLL_DENY.get())).withRelation(dirScroll);
        var balloon = addBasicItem(STARBALLON.get(), CRAFTING);
        var thrash = addBasicItem(STARTRASH.get(), CRAFTING);
        var battery = addBasicItem(STARBATTERY.get(), CRAFTING);
        var saddle = addBasicItem(STARSADDLE.get(), CRAFTING);

        addPage(new DocEntryBuilder(StarbuncleMania.MODID, CRAFTING, "starbunclemania.page.wixie_jobs")
                .withIcon(ItemsRegistry.WIXIE_CHARM)
                .withTextPage("starbunclemania.page.wixie_cook")
                .withTextPage("starbunclemania.page.wixie_cut")
                .withTextPage("starbunclemania.page.wixie_mixer")
        );

        addPage(new DocEntryBuilder(DocumentationRegistry.ITEMS, STARBY_EARS.get()).withName("starbunclemania.player_cosmetic")
                .withTextPage("starbunclemania.page.player_cosmetic")
                .withCraftingPages(STARBY_EARS.get())
                .withCraftingPages(ALAK_HAT.get()).withCraftingPages(SEA_BUNNY.get())
                .withCraftingPages(DRYGMY_HORNS.get()).withCraftingPages(WHIRLI_PROP.get()));

        addBasicItem(STARHAT.get(), FAMILIARS).withRelations(balloon, thrash, battery, fluid, prof);
    }

    public static DocEntry addBasicItem(ItemLike item, DocCategory category) {
        ItemStack stack = new ItemStack(item);
        return addPage(new DocEntryBuilder(category, stack.getDescriptionId())
                .withIcon(item)
                .withSortNum(100)
                .withPage(TextEntry.create(Component.translatable("starbunclemania.page." + getRegistryName(item.asItem()).getPath()), stack.getItem().getDescription(), stack)).withCraftingPages(item));
    }

    @SubscribeEvent
    public static void editPages(ReloadDocumentationEvent.Post event) {
        postInit(event);
    }

    private static void postInit(ReloadDocumentationEvent.Post event) {
    }


}
