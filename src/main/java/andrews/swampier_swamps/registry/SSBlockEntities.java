package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.block_entities.DecayingKelpBlockEntity;
import andrews.swampier_swamps.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SSBlockEntities
{
    public static final BlockEntityType<DecayingKelpBlockEntity> DECAYING_KELP = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "decaying_kelp"), FabricBlockEntityTypeBuilder.create(DecayingKelpBlockEntity::new, SSBlocks.DECAYING_KELP).build());

    public static void init() {}
}