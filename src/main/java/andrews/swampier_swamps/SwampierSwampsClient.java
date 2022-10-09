package andrews.swampier_swamps;

import andrews.swampier_swamps.entities.model.DragonflyModel;
import andrews.swampier_swamps.entities.renderer.DragonflyRenderer;
import andrews.swampier_swamps.entities.renderer.SwampGasRenderer;
import andrews.swampier_swamps.events.ClientEvents;
import andrews.swampier_swamps.network.SSNetwork;
import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSEntities;
import andrews.swampier_swamps.registry.SSParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SwampierSwampsClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        // Entity Renderers
        EntityRendererRegistry.register(SSEntities.DRAGONFLY, DragonflyRenderer::new);
        EntityRendererRegistry.register(SSEntities.SWAMP_GAS, SwampGasRenderer::new);
        // Model Layers
        EntityModelLayerRegistry.registerModelLayer(DragonflyModel.LAYER, DragonflyModel::createBodyLayer);
        // Particles
        SSParticles.registerParticles();
        // Block Render Types
        SSBlocks.registerBlockRenderTypes();
        // Block Colors
        ClientEvents.registerBlockColors();
        // Client Networking
        SSNetwork.registerClientNetworkMessages();
    }
}