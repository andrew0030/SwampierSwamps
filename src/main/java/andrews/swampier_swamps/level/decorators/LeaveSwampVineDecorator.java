package andrews.swampier_swamps.level.decorators;

import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSTreeDecorators;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class LeaveSwampVineDecorator extends TreeDecorator
{
    public static final Codec<LeaveSwampVineDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(LeaveSwampVineDecorator::new, (decorator) -> decorator.probability).codec();
    private final float probability;

    protected TreeDecoratorType<?> type()
    {
        return SSTreeDecorators.LEAVE_SWAMP_VINE.get();
    }

    public LeaveSwampVineDecorator(float probability)
    {
        this.probability = probability;
    }

    public void place(TreeDecorator.Context context)
    {
        RandomSource randomsource = context.random();
        context.leaves().forEach((pos) ->
        {
            if (randomsource.nextFloat() < this.probability)
            {
                BlockPos blockpos = pos.west();
                if (context.isAir(blockpos))
                    addHangingVine(blockpos, VineBlock.EAST, context);
            }

            if (randomsource.nextFloat() < this.probability)
            {
                BlockPos blockpos1 = pos.east();
                if (context.isAir(blockpos1))
                    addHangingVine(blockpos1, VineBlock.WEST, context);
            }

            if (randomsource.nextFloat() < this.probability)
            {
                BlockPos blockpos2 = pos.north();
                if (context.isAir(blockpos2))
                    addHangingVine(blockpos2, VineBlock.SOUTH, context);
            }

            if (randomsource.nextFloat() < this.probability)
            {
                BlockPos blockpos3 = pos.south();
                if (context.isAir(blockpos3))
                    addHangingVine(blockpos3, VineBlock.NORTH, context);
            }

        });
    }

    private static void addHangingVine(BlockPos pos, BooleanProperty property, TreeDecorator.Context context)
    {
        context.setBlock(pos, SSBlocks.SWAMP_VINE.get().defaultBlockState().setValue(property, true));
        int i = 4;

        for(BlockPos blockpos = pos.below(); context.isAir(blockpos) && i > 0; --i)
        {
            context.setBlock(blockpos, SSBlocks.SWAMP_VINE.get().defaultBlockState().setValue(property, true));
            blockpos = blockpos.below();
        }
    }
}