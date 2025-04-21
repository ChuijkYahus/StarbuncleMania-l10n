package alexthw.starbunclemania.datagen;

import alexthw.starbunclemania.StarbuncleMania;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StarbLangProvider extends LanguageProvider {

    public StarbLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {

        add("itemGroup.starbunclemania", "StarbuncleMania");
        add("starbunclemania.glyph_name.glyph_", "");
        add("block.starbunclemania.fluid_jar", "Fluid Containment Jar");
        add("block.starbunclemania.fluid_sourcelink", "Fluid Sourcelink");
        add("block.starbunclemania.source_condenser", "Source Condenser");
        add("block.starbunclemania.smelting_wixie_cauldron", "Wixie's Furnace");
        add("block.starbunclemania.stonecutting_wixie_cauldron", "Wixie's Stonecutter");
        add("block.starbunclemania.cutting_wixie_cauldron", "Wixie's Cutting Board");
        add("block.starbunclemania.cooking_pot_wixie_cauldron", "Wixie's Cooking Pot");
        add("block.starbunclemania.crucible_wixie_cauldron", "Wixie's Crucible");
        add("block.starbunclemania.mixer_wixie_cauldron", "Wixie's Mixer");
        add("block.starbunclemania.source_fluid_block", "Liquified Source");
        add("item.starbunclemania.direction_scroll", "Direction Scroll");
        add("item.starbunclemania.fluid_scroll_allow", "Fluid Scroll: Allow");
        add("item.starbunclemania.fluid_scroll_deny", "Fluid Scroll: Deny");
        add("item.starbunclemania.star_hat", "Blues Hat");
        add("item.starbunclemania.wyrm_degree", "Wyrm Degree");
        add("item.starbunclemania.robin_mask", "Square Robin Mask");
        add("item.starbunclemania.star_battery", "StarbAttery");
        add("item.starbunclemania.star_bucket", "Starbucket");
        add("item.starbunclemania.star_balloon", "Starballoon");
        add("item.starbunclemania.star_bin", "StarBin");
        add("item.starbunclemania.star_saddle", "Starddle [WIP]");
        add("item.starbunclemania.star_sword", "Sword of the Starb [WIP]");
        add("item.starbunclemania.star_wand", "Wand of Sacred Nug [WIP]");
        add("item.starbunclemania.star_miner", "Starminer");
        add("item.starbunclemania.star_build", "Starbuilder");
        add("item.starbunclemania.starby_ears", "Starby Ears");
        add("item.starbunclemania.chef_hat", "Chef Hat");
        add("starbunclemania.starby_ears.tooltip", "Cosmetic curio for Players");
        add("item.starbunclemania.drygmy_horns", "Drygmy Horns");
        add("starbunclemania.drygmy_horns.tooltip", "Cosmetic curio for Players");
        add("item.starbunclemania.whirli_prop", "Whirli Propeller");
        add("starbunclemania.whirli_prop.tooltip", "Cosmetic curio for Players");
        add("item.starbunclemania.alak_hat", "Alakakrinos Hat");
        add("starbunclemania.alak_hat.tooltip", "Cosmetic curio for Players");
        add("item.starbunclemania.sea_bunny", "Sea Bunny");
        add("starbunclemania.sea_bunny.tooltip", "Cosmetic curio for Players");
        add("starbunclemania.chef_hat.tooltip", "Cosmetic curio for Players, Starbuncles and Wixie Familiars.");
        add("starbunclemania.player_cosmetic", "Cosmetic Accessories");
        add("starbunclemania.page.player_cosmetic", "Have you ever wanted to look a bit more like a Starbuncle? Or have a cool sandy hat, horns or even a propeller? Now you can! These accessories are purely cosmetic and can be worn by players in the Head curio slot.");
        add("item.starbunclemania.fluid_jar", "Fluid Jar");
        add("item.starbunclemania.source_fluid_bucket", "Liquefied Source Bucket");
        add("fluid_type.starbunclemania.source_fluid", "Liquefied Source");
        add("chemical.starbunclemania.source_gas", "Gaseous Source");
        add("entity.starbunclemania.starby_mount", "Starbuncle");
        add("ars_nouveau.starbuncle.sided_item_behavior_set", "Starbuncle will now insert/extract items from specified sides");
        add("ars_nouveau.starbuncle.robin_item_behavior_set", "Starbuncle will now round-robin items between multiple inventories");
        add("ars_nouveau.starbuncle.fluid_behavior_set", "Starbuncle will now transport fluids");
        add("ars_nouveau.starbuncle.energy_behavior_set", "Starbuncle will now transport energy");
        add("ars_nouveau.starbuncle.gas_behavior_set", "Starbuncle will now transport gasses");
        add("ars_nouveau.starbuncle.trash_behavior_set", "Starbuncle will trash items");
        add("ars_nouveau.starbuncle.sword_behavior_set", "Starbuncle will now choose violence");
        add("ars_nouveau.starbuncle.heal_behavior_set", "Starbuncle will now heal creatures");
        add("ars_nouveau.starbuncle.default_behavior", "Defaulted to item transportation");
        add("ars_nouveau.starbuncle.saddle_behavior_set", "Starbuncle will now transport players");
        add("ars_nouveau.starbuncle.miner_behavior_set", "Starbuncle will now mine blocks");
        add("ars_nouveau.starbuncle.placer_behavior_set", "Starbuncle will now place blocks");
        add("ars_nouveau.starbuncle.miner", "Starbuncle will mine blocks in this spot");
        add("ars_nouveau.starbuncle.place", "Starbuncle will place blocks in this spot");
        add("ars_nouveau.starbuncle.energy_to", "Starbuncle will store energy here");
        add("ars_nouveau.starbuncle.energy_from", "Starbuncle will take energy from this battery");
        add("starbunclemania.tooltip.fluid_jar", "Stored %d mb of ");
        add("ars_nouveau.starbuncle.fluid_to", "Starbuncle will store fluids here");
        add("ars_nouveau.starbuncle.fluid_from", "Starbuncle will take fluids from this tank");
        add("starbunclemania.tooltip.gas", "Stored %d mb of ");
        add("ars_nouveau.starbuncle.gas_to", "Starbuncle will store gas here");
        add("ars_nouveau.starbuncle.gas_from", "Starbuncle will take gas from this tank");
        add("ars_nouveau.starbuncle.trashing_items", "Trashing items from ground and %d locations");
        add("ars_nouveau.starbuncle.storing_energy", "Storing energy at %d locations");
        add("ars_nouveau.starbuncle.taking_energy", "Taking energy from %d locations");
        add("ars_nouveau.starbuncle.storing_fluid", "Storing fluids at %d locations");
        add("ars_nouveau.starbuncle.taking_fluid", "Taking fluids from %d locations");
        add("ars_nouveau.starbuncle.storing_gas", "Storing gasses at %d locations");
        add("ars_nouveau.starbuncle.taking_gas", "Taking gasses from %d locations");
        add("ars_nouveau.starbuncle.mining", "Mining blocks at %d locations");
        add("ars_nouveau.starbuncle.placing", "Placing blocks at %d locations");
        add("ars_nouveau.starbuncle.tool", "Using %s as tool");
        add("starbunclemania.simple_cosmetic.tooltip", "Simple cosmetic for your familiars. Supported familiars: Starbuncle, Wixie, Drygmy, Bookwyrm.");
        add("starbunclemania.degree_hat.tooltip", "Cosmetic for your familiars. Supported creatures: Starbuncles, Bookwyrm (Familiar). Also allows item starbuncles to read Direction Scrolls.");
        add("starbunclemania.robin_mask.tooltip", "Tweaks the starbuncle's item transport to round-robin items between inventories, also stops pickup. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.bucket.tooltip", "Changes a starbuncle's job to transport fluids around. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.balloon.tooltip", "Changes a starbuncle's job to transport Mekanism chemical around. Can be dyed and used as a simple cosmetic by sneaking while using it, without changing its current behavior. Supported familiars: Starbuncle, Drygmy, Wixie.");
        add("starbunclemania.battery.tooltip", "Changes a starbuncle's job to transport FE energy around. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.trash_bin.tooltip", "Changes a starbuncle's job to void the items it pickups and from linked chests. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.builder_hat.tooltip", "Changes a starbuncle's job to place blocks in specified spots. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.miner_hat.tooltip", "Changes a starbuncle's job to mine blocks in specified spots. You can give it a tool to use, defaults to an iron pick. Can be used as a simple cosmetic by sneaking while using it, without changing its current behavior.");
        add("starbunclemania.cosmetic", "Cosmetic Accessories");
        add("starbunclemania.page.direction_scroll", "Provides a face/direction to Starbuncle automations. When the scroll is used on a starbuncle or placed in an item frame on a block, the starbuncle will interact with the block from the specified face.");
        add("starbunclemania.page.fluid_scroll", "Provides a filter to Starbuncle fluid automations. Works like the item counterpart, used on a starbuncle or placed in an item frame on the tank.");
        add("starbunclemania.page.fluid_jar", "A tank made from cascading archwood logs that can hold up to 16 buckets of fluid. If used to store a potion fluid and a potion jar is placed above it, the fluid will be converted to the jar for flask/melding use.");
        add("starbunclemania.page.fluid_sourcelink", "Consumes fluids to generate source. Will automatically drain from tanks below it if compatible.");
        add("starbunclemania.page.source_condenser", "Condense the Source from Jars into a stable fluid. Will automatically output to tanks below it if possible.");
        add("starbunclemania.page.star_hat", "Accessories from StarbuncleMania aren't just for the worker starbuncles to enjoy, you can use them on your starbuncle familiar too! This one doesn't do anything, but is surely stylish. Shift-clicking a starby with an accessory will only set it as cosmetic, without setting the job.");
        add("starbunclemania.page.star_battery", "Allows Starbuncles to transport energy. Once wearing the accessory, use the Dominion wand to connect them between FE blocks.");
        add("starbunclemania.page.star_bucket", "Allows Starbuncles to transport fluids. Once wearing the accessory, use the Dominion wand to connect them between tanks.");
        add("starbunclemania.page.star_balloon", "Allows Starbuncles to transport simple gasses [Mekanism]. Can be dyed if used as cosmetic, but will use the stored gas color when used for transport. Once wearing the accessory, use the Dominion wand to connect them between gas tanks.");
        add("starbunclemania.page.wyrm_degree", "Allows Starbuncles to insert/extract items from a specific side with Direction Scrolls.");
        add("starbunclemania.page.star_bin", "Allows Starbuncles to embrace their inner raccoon and trash items. While wearing the accessory the Starbuncle will void any nearby item on the ground.");
        add("starbunclemania.page.star_saddle", "Allows Starbuncles to transport players. While wearing the accessory, the Starbuncle becomes big enough to be rideable. [Still a bit wip, might be a bit wonky]");
        add("starbunclemania.page.robin_mask", "Allows Starbuncles to round-robin items between inventories. While wearing the accessory, the Starbuncle will only pickup items from the ground. Each stacked starbuncle will carry maxStack/numDestInventories items, before moving to the next inventory.");
        add("starbunclemania.page.item_transfer", "Advanced Item Transfer");
        add("starbunclemania.page.wixie_jobs", "Extra jobs for the Wixie");
        add("starbunclemania.page.wixie_cook", "The Wixie can now cook! Place a Wixie on a furnace to allow it to smelt and smoke items, also compatible with a cooking pot [Farmers Delight] and crucibles [EidolonRepraised] to automate them.");
        add("starbunclemania.page.wixie_cut", "Place a Wixie charm on a stonecutter or on a cutting board to have it use their cutting recipes for craftings.");
        add("starbunclemania.page.wixie_mixer", "Place a Wixie charm on a fluid jar to convert it to a different type of cauldron. The Mixer has two internal tanks, for water and milk, to avoid using buckets in recipes. Only works for crafting table recipes, will always consume only one unit of the fluid needed.");
        add("starbunclemania.adv.title.wyrm_degree", "It's also thanks to my two degrees...");
        add("starbunclemania.adv.desc.wyrm_degree", "Acquire a Wyrm Degree ");
        add("starbunclemania.adv.title.star_battery", "Energy Transport");
        add("starbunclemania.adv.desc.star_battery", "Acquire a StarbAttery");
        add("starbunclemania.adv.title.star_bucket", "Fluid Transport");
        add("starbunclemania.adv.desc.star_bucket", "Acquire a Starbucket");
        add("starbunclemania.adv.title.star_balloon", "Gas Transport");
        add("starbunclemania.adv.desc.star_balloon", "Acquire a Starballoon");
        add("starbunclemania.adv.title.star_bin", "Trash Lover");
        add("starbunclemania.adv.desc.star_bin", "Acquire a StarBin");
        add("starbunclemania.adv.title.robin_mask", "Fighting for Equality");
        add("starbunclemania.adv.desc.robin_mask", "Acquire a Robin Mask for Starbuncle");
        add("starbunclemania.adv.title.fluid_jar", "What if...");
        add("starbunclemania.adv.desc.fluid_jar", "Acquire a Fluid Jar");
        add("starbunclemania.adv.title.fluid_sourcelink", "A Source Sprinkler");
        add("starbunclemania.adv.desc.fluid_sourcelink", "Acquire a Fluid Sourcelink");
        add("starbunclemania.adv.title.source_condenser", "The Heretic Goo");
        add("starbunclemania.adv.desc.source_condenser", "Acquire a Source Condenser");
        add("starbunclemania.adv.title.wixie_cook", "What is the Wixie cooking?");
        add("starbunclemania.adv.desc.wixie_cook", "Place a Wixie on a Furnace, a Crucible or a Cooking Pot");
        add("starbunclemania.adv.title.wixie_stoneworks", "Cut it Wixie, cut it!");
        add("starbunclemania.adv.desc.wixie_stoneworks", "Place a Wixie on a Stonecutter or on a Cutting Board");
        add("starbunclemania.adv.title.wixie_mixer", "Wixie Mixie");
        add("starbunclemania.adv.desc.wixie_mixer", "Place a Wixie on a fluid jar to make the Wixie Mixer");

        for (Supplier<Glyph> supplier : GlyphRegistry.getGlyphItemMap().values()) {
            Glyph glyph = supplier.get();
            AbstractSpellPart spellPart = glyph.spellPart;
            ResourceLocation registryName = glyph.spellPart.getRegistryName();
            if (registryName.getNamespace().equals(StarbuncleMania.MODID)) {
                add("starbunclemania.glyph_desc." + registryName.getPath(), spellPart.getBookDescription());
                add("starbunclemania.glyph_name." + registryName.getPath(), spellPart.getName());

                Map<AbstractAugment, String> augmentDescriptions = new HashMap<>();
                spellPart.addAugmentDescriptions(augmentDescriptions);

                for (AbstractAugment augment : augmentDescriptions.keySet()) {
                    add("ars_nouveau.augment_desc." + registryName.getPath() + "_" + augment.getRegistryName().getPath(), augmentDescriptions.get(augment));
                }
            }
        }
    }

}
