package andrews.swampier_swamps.events;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.registry.SSEntities;
import andrews.swampier_swamps.util.Reference;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributesHandler
{
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(SSEntities.DRAGONFLY.get(), Dragonfly.createAttributes().build());
    }
}
