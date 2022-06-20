package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.decorators.LeaveSwampVineDecorator;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSTreeDecorators
{
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Reference.MODID);

    public static final RegistryObject<TreeDecoratorType<LeaveSwampVineDecorator>> LEAVE_SWAMP_VINE     = TREE_DECORATORS.register("leave_swamp_vine", () -> new TreeDecoratorType<>(LeaveSwampVineDecorator.CODEC));
}
