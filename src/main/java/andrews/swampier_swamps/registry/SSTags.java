package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
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

        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(new ResourceLocation(Reference.MODID, name));
        }
    }
}