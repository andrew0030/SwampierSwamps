package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.objects.blocks.BigLilyPadBlock;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin
{
    @Unique AtomicBoolean isRandomTicking = new AtomicBoolean();

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void injectTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci)
    {
        if(!isRandomTicking.get())
            if(state.is(Blocks.LILY_PAD))
                level.setBlock(pos, SSBlocks.SINKING_LILY_PAD.get().defaultBlockState(), 2);
    }

    @Inject(method = "randomTick", at = @At(value = "HEAD"))
    public void injectRandomTickHead(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci)
    {
        if(state.is(Blocks.LILY_PAD))
        {
            // We use this to prevent the code inside tick() from running without canceling randomTick
            isRandomTicking.set(true);

            // We reduce how often the Block attempts to grow
            if (rand.nextInt(25) == 0)
            {
                boolean canGrow = true;
                int lilyPadLimiter = 2; // We use this to determine if there are less than a given amount of Big Lily Pads around
                // We check 8 Blocks in all directions
                for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-8, -1, -8), pos.offset(8, 1, 8)))
                {
                    if (level.getBlockState(blockpos).is(SSBlocks.BIG_LILY_PAD.get()) && level.getBlockState(blockpos).getValue(BigLilyPadBlock.LILY_PAD_PART) == 0)
                    {
                        --lilyPadLimiter;
                        if (lilyPadLimiter <= 0)
                            canGrow = false; // If there are too many Lily Pads we prevent it from growing
                    }
                }

                // We get a random Rotation for the Big Lily Pad
                Direction direction;
                switch (rand.nextInt(4))
                {
                    default -> direction = Direction.NORTH;
                    case 1 -> direction = Direction.SOUTH;
                    case 2 -> direction = Direction.WEST;
                    case 3 -> direction = Direction.EAST;
                }
                // We make sure there is enough space to grow
                FluidState fluidState1 = level.getFluidState(pos.below().relative(direction));
                BlockState blockStateAbove1 = level.getBlockState(pos.relative(direction));
                if(!(fluidState1.is(FluidTags.WATER)) || !(fluidState1.getAmount() == FluidState.AMOUNT_FULL) || !(blockStateAbove1.is(Blocks.AIR)))
                    canGrow = false;
                FluidState fluidState2 = level.getFluidState(pos.below().relative(direction.getClockWise()));
                BlockState blockStateAbove2 = level.getBlockState(pos.relative(direction.getClockWise()));
                if(!(fluidState2.is(FluidTags.WATER)) || !(fluidState2.getAmount() == FluidState.AMOUNT_FULL) || !(blockStateAbove2.is(Blocks.AIR)))
                    canGrow = false;
                FluidState fluidState3 = level.getFluidState(pos.below().relative(direction).relative(direction.getClockWise()));
                BlockState blockStateAbove3 = level.getBlockState(pos.relative(direction).relative(direction.getClockWise()));
                if(!(fluidState3.is(FluidTags.WATER)) || !(fluidState3.getAmount() == FluidState.AMOUNT_FULL) || !(blockStateAbove3.is(Blocks.AIR)))
                    canGrow = false;

                // If everything went well we grow the Lily Pad into a Big one
                if  (canGrow)
                {
                    level.setBlock(pos, SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 0), 2);
                    level.setBlock(pos.relative(direction), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 1), 2);
                    level.setBlock(pos.relative(direction.getClockWise()), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 2), 2);
                    level.setBlock(pos.relative(direction).relative(direction.getClockWise()), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 3), 2);
                }
            }
        }
    }

    @Inject(method = "randomTick", at = @At(value = "TAIL"))
    public void injectRandomTickTail(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci)
    {
        isRandomTicking.set(false);
    }
}