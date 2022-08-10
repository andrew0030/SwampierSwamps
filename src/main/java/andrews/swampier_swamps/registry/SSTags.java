package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class SSTags
{
    public static class Blocks
    {
        public static final TagKey<Block> FROGLIGHTS                = createTag("froglights");
        public static final TagKey<Block> DRAGONFLIES_SPAWNABLE_ON  = createTag("dragonflies_spawnable_on");
        public static final TagKey<Block> MUD_PUDDLE_CAN_REPLACE    = createTag("mud_puddle_can_replace");
        public static final TagKey<Block> DECAYING_KELP_CAN_REPLACE = createTag("decaying_kelp_can_replace");

        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(new ResourceLocation(Reference.MODID, name));
        }
    }

    public static class Biomes
    {
        public static final TagKey<Biome> CAN_BALD_CYPRESS_GROW_IN  = createTag("can_bald_cypress_grow_in");
        public static final TagKey<Biome> CAN_LILY_PAD_GROW_IN      = createTag("can_lily_pad_grow_in");
        // Frog Variants Spawning
        public static final TagKey<Biome> WHITE_FROG_BIOMES         = createFrogTag("white_frog_biomes");
        public static final TagKey<Biome> ORANGE_FROG_BIOMES        = createFrogTag("orange_frog_biomes");
        public static final TagKey<Biome> MAGENTA_FROG_BIOMES       = createFrogTag("magenta_frog_biomes");
        public static final TagKey<Biome> LIGHT_BLUE_FROG_BIOMES    = createFrogTag("light_blue_frog_biomes");
        public static final TagKey<Biome> YELLOW_FROG_BIOMES        = createFrogTag("yellow_frog_biomes");
        public static final TagKey<Biome> LIME_FROG_BIOMES          = createFrogTag("lime_frog_biomes");
        public static final TagKey<Biome> PINK_FROG_BIOMES          = createFrogTag("pink_frog_biomes");
        public static final TagKey<Biome> GRAY_FROG_BIOMES          = createFrogTag("gray_frog_biomes");
        public static final TagKey<Biome> LIGHT_GRAY_FROG_BIOMES    = createFrogTag("light_gray_frog_biomes");
        public static final TagKey<Biome> CYAN_FROG_BIOMES          = createFrogTag("cyan_frog_biomes");
        public static final TagKey<Biome> PURPLE_FROG_BIOMES        = createFrogTag("purple_frog_biomes");
        public static final TagKey<Biome> BLUE_FROG_BIOMES          = createFrogTag("blue_frog_biomes");
        public static final TagKey<Biome> BROWN_FROG_BIOMES         = createFrogTag("brown_frog_biomes");
        public static final TagKey<Biome> GREEN_FROG_BIOMES         = createFrogTag("green_frog_biomes");
        public static final TagKey<Biome> RED_FROG_BIOMES           = createFrogTag("red_frog_biomes");
        public static final TagKey<Biome> BLACK_FROG_BIOMES         = createFrogTag("black_frog_biomes");

        private static TagKey<Biome> createTag(String name)
        {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, name));
        }

        private static TagKey<Biome> createFrogTag(String name)
        {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, "frog_variants/" + name));
        }
    }
}