package andrews.swampier_swamps.events;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.registry.SSEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class EntityAttributesHandler
{
    // Getting called from SSEntities init
    public static void registerEntityAttributes()
    {
        FabricDefaultAttributeRegistry.register(SSEntities.DRAGONFLY, Dragonfly.createAttributes());
    }
}