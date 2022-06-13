package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.objects.blocks.MudSlabBlock;
import andrews.swampier_swamps.objects.blocks.MudStairsBlock;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SSBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    public static final RegistryObject<Block> MUD_SLAB      = createBlock("mud_slab", () -> new MudSlabBlock(BlockBehaviour.Properties.copy(Blocks.MUD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_STAIRS    = createBlock("mud_stairs", () -> new MudStairsBlock(Blocks.MUD::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.MUD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    private static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group)
    {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        SSItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
        return block;
    }
}