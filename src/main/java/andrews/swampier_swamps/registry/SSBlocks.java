package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.objects.blocks.CattailBlock;
import andrews.swampier_swamps.objects.blocks.MudSlabBlock;
import andrews.swampier_swamps.objects.blocks.MudStairsBlock;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SSBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    // Mud Blocks
    public static final RegistryObject<Block> MUD_SLAB              = createBlock("mud_slab", () -> new MudSlabBlock(BlockBehaviour.Properties.copy(Blocks.MUD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MUD_STAIRS            = createBlock("mud_stairs", () -> new MudStairsBlock(Blocks.MUD::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.MUD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    // Frog Lights
    public static final RegistryObject<Block> WHITE_FROG_LIGHT      = createBlock("white_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.SNOW).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> MAGENTA_FROG_LIGHT    = createBlock("magenta_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_MAGENTA).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_BLUE_FROG_LIGHT = createBlock("light_blue_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_LIGHT_BLUE).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);

    // Plants
    public static final RegistryObject<Block> CATTAIL            = createBlock("cattail", () -> new CattailBlock(), CreativeModeTab.TAB_BUILDING_BLOCKS);

    private static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group)
    {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        SSItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
        return block;
    }

    public static void registerBlockRenderTypes()
    {
        ItemBlockRenderTypes.setRenderLayer(CATTAIL.get(), RenderType.cutout());
    }
}