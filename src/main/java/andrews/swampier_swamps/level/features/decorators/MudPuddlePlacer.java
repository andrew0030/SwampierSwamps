package andrews.swampier_swamps.level.features.decorators;

import andrews.swampier_swamps.registry.SSPlacements;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class MudPuddlePlacer extends PlacementModifier
{
    private static final MudPuddlePlacer INSTANCE = new MudPuddlePlacer();
    public static final Codec<MudPuddlePlacer> CODEC = Codec.unit(() -> INSTANCE);

    public static MudPuddlePlacer mudPuddlePlacer()
    {
        return INSTANCE;
    }

    @Override
    public PlacementModifierType<?> type()
    {
        return SSPlacements.MUD_PUDDLE_PLACER.get();
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource rand, BlockPos pos)
    {
        int xPos = rand.nextInt(10) + 3 + pos.getX();
        int zPos = rand.nextInt(10) + 3 + pos.getZ();
        return Stream.of(new BlockPos(xPos, pos.getY(), zPos));
    }
}
