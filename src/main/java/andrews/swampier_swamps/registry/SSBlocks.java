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
    public static final RegistryObject<Block> YELLOW_FROG_LIGHT     = createBlock("yellow_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_YELLOW).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIME_FROG_LIGHT       = createBlock("lime_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_LIGHT_GREEN).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PINK_FROG_LIGHT       = createBlock("pink_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_PINK).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GRAY_FROG_LIGHT       = createBlock("gray_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_GRAY).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_GRAY_FROG_LIGHT = createBlock("light_gray_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_LIGHT_GRAY).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CYAN_FROG_LIGHT       = createBlock("cyan_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_CYAN).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLUE_FROG_LIGHT       = createBlock("blue_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_BLUE).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BROWN_FROG_LIGHT      = createBlock("brown_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_BROWN).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> RED_FROG_LIGHT        = createBlock("red_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_RED).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLACK_FROG_LIGHT      = createBlock("black_froglight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT, MaterialColor.COLOR_BLACK).strength(0.3F).lightLevel((brightness) -> 15).sound(SoundType.FROGLIGHT)), CreativeModeTab.TAB_DECORATIONS);

    // Plants
    public static final RegistryObject<Block> CATTAIL               = createBlock("cattail", CattailBlock::new, CreativeModeTab.TAB_BUILDING_BLOCKS);

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