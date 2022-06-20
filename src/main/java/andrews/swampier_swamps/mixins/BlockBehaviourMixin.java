package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Tilt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin
{
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void inject(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci)
    {
        if(state.is(Blocks.LILY_PAD))
            if(!level.isClientSide)
                level.setBlock(pos, SSBlocks.SINKING_LILY_PAD.get().defaultBlockState(), 2);
    }
}