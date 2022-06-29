package andrews.swampier_swamps.entities.model;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

// Made with Blockbench by Apprentice Necromancer
public class DragonflyModel<E extends Dragonfly> extends EntityModel<E>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "dragonfly"), "main");
    private final ModelPart body;
    private final ModelPart rightbackwing;
    private final ModelPart rightfrontwing;
    private final ModelPart leftbackwing;
    private final ModelPart leftfrontwing;

    public DragonflyModel(ModelPart root)
    {
        this.body = root.getChild("body");
        this.rightbackwing = this.body.getChild("rightbackwing");
        this.rightfrontwing = this.body.getChild("rightfrontwing");
        this.leftbackwing = this.body.getChild("leftbackwing");
        this.leftfrontwing = this.body.getChild("leftfrontwing");

    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 22.5F, -4.0F));
        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -10.5F, -1.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 9).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 11).addBox(-1.5F, -1.0F, -1.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.25F));
        PartDefinition leftbackwing = body.addOrReplaceChild("leftbackwing", CubeListBuilder.create().texOffs(1, 0).addBox(-0.25F, 0.0F, 0.0F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -1.75F, 0.25F));
        PartDefinition rightbackwing = body.addOrReplaceChild("rightbackwing", CubeListBuilder.create().texOffs(1, 0).mirror().addBox(-9.75F, 0.0F, 0.0F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -1.75F, 0.25F));
        PartDefinition leftfrontwing = body.addOrReplaceChild("leftfrontwing", CubeListBuilder.create(), PartPose.offset(1.0F, -1.75F, -1.25F));
        PartDefinition cube_r2 = leftfrontwing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(7, 6).mirror().addBox(0.0F, 0.0F, -1.0F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0F));
        PartDefinition rightfrontwing = body.addOrReplaceChild("rightfrontwing", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.75F, -1.25F));
        PartDefinition cube_r3 = rightfrontwing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(7, 6).addBox(-11.0F, 0.0F, -1.0F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, -0.0873F, 0.0F));
        PartDefinition leftlegs = body.addOrReplaceChild("leftlegs", CubeListBuilder.create().texOffs(12, 10).mirror().addBox(-0.25F, 0.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, -0.5F, -0.25F));
        PartDefinition rightlegs = body.addOrReplaceChild("rightlegs", CubeListBuilder.create().texOffs(12, 10).addBox(-0.75F, 0.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.5F, -0.25F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if(entity.isMoving() || entity.getBlockStateOn().is(Blocks.AIR))
        {
            // Right Wings
            this.rightbackwing.zRot = Mth.cos((entity.tickCount + 0.5F) * 1.5F) * 0.6F;
            this.rightfrontwing.zRot = Mth.cos(entity.tickCount * 1.5F) * 0.6F;
            // Left Wings
            this.leftbackwing.zRot = -Mth.cos((entity.tickCount + 0.5F) * 1.5F) * 0.6F;
            this.leftfrontwing.zRot = -Mth.cos(entity.tickCount * 1.5F) * 0.6F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}