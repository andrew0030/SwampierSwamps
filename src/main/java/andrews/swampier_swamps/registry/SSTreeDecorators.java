package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.decorators.LeaveSwampVineDecorator;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SSTreeDecorators
{
    public static final TreeDecoratorType<LeaveSwampVineDecorator> LEAVE_SWAMP_VINE     = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, new ResourceLocation(Reference.MODID, "leave_swamp_vine"), new TreeDecoratorType<>(LeaveSwampVineDecorator.CODEC));

    public static void init() {}
}