package andrews.swampier_swamps.level.decorators;

import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class LeaveSwampVineDecorator extends TreeDecorator
{
    public static final Codec<LeaveSwampVineDecorator> CODEC = RecordCodecBuilder.create((builder) ->
    builder.group(
        Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((getter) -> getter.probability),
        Codec.intRange(1, 8).fieldOf("length").forGetter((getter) -> getter.length))
    .apply(builder, LeaveSwampVineDecorator::new));
    // Values
    protected final float probability;
    protected final int length;

    protected TreeDecoratorType<?> type()
    {
        return SSTreeDecorators.LEAVE_SWAMP_VINE;
    }

    public LeaveSwampVineDecorator(float probability, int length)
    {
        this.probability = probability;
        this.length = length;
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

    private void addHangingVine(BlockPos pos, BooleanProperty property, TreeDecorator.Context context)
    {
        context.setBlock(pos, SSBlocks.SWAMP_VINE.defaultBlockState().setValue(property, true));
        int i = this.length;

        for(BlockPos blockpos = pos.below(); (context.isAir(blockpos) || context.level().isStateAtPosition(blockpos, state -> state.is(Blocks.WATER))) && i > 0; --i)
        {
            context.setBlock(blockpos, SSBlocks.SWAMP_VINE.defaultBlockState().setValue(property, true).setValue(BlockStateProperties.WATERLOGGED, context.level().isStateAtPosition(blockpos, state -> state.is(Blocks.WATER))));
            blockpos = blockpos.below();
        }
    }
}