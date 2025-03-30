<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import ${package}.entity.${name}Entity;

public class ${name}Model<T extends ${name}Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("${modid}", "${registryname}"), "main");
    
    private final ModelPart bottom;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart paddles;
    private final ModelPart chest; // Only used for chest boats

    public ${name}Model(ModelPart root) {
        this.bottom = root.getChild("bottom");
        this.back = root.getChild("back");
        this.front = root.getChild("front");
        this.right = root.getChild("right");
        this.left = root.getChild("left");
        this.paddles = root.getChild("paddles");
        this.chest = root.getChild("chest");
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), 
                PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        
        partdefinition.addOrReplaceChild("front", CubeListBuilder.create()
                .texOffs(0, 27).addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), 
                PartPose.offsetAndRotation(-15.0F, 4.0F, 4.0F, 0.0F, ((float)Math.PI * 1.5F), 0.0F));
        
        partdefinition.addOrReplaceChild("back", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), 
                PartPose.offsetAndRotation(15.0F, 4.0F, 0.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
        
        partdefinition.addOrReplaceChild("right", CubeListBuilder.create()
                .texOffs(0, 35).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), 
                PartPose.offsetAndRotation(0.0F, 4.0F, -9.0F, 0.0F, (float)Math.PI, 0.0F));
        
        partdefinition.addOrReplaceChild("left", CubeListBuilder.create()
                .texOffs(0, 43).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), 
                PartPose.offset(0.0F, 4.0F, 9.0F));

        PartDefinition paddles = partdefinition.addOrReplaceChild("paddles", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));
        PartDefinition paddle_left = paddles.addOrReplaceChild("paddle_left", CubeListBuilder.create()
                .texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
                .texOffs(62, 20).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
                PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        
        PartDefinition paddle_right = paddles.addOrReplaceChild("paddle_right", CubeListBuilder.create()
                .texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
                .texOffs(62, 20).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
                PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float)Math.PI, 0.19634955F));
        
        // Add chest for chest boats
        partdefinition.addOrReplaceChild("chest", CubeListBuilder.create()
                .texOffs(0, 76).addBox(-5.5F, -14.0F, -8.0F, 11.0F, 8.0F, 8.0F), 
                PartPose.offset(0.0F, 4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        animatePaddle(entity, 0, this.paddles.getChild("paddle_right"), limbSwing);
        animatePaddle(entity, 1, this.paddles.getChild("paddle_left"), limbSwing);
    }

    private void animatePaddle(T boat, int side, ModelPart paddle, float limbSwing) {
        float f = boat.getRowingTime(side, limbSwing);
        paddle.xRot = (float)(-Math.PI/4F + Math.cos((double)(f * (float)Math.PI * 2.0F)) * Math.PI * 0.25F);
        paddle.yRot = (float)(-Math.PI/4F + Math.cos((double)(f * (float)Math.PI * 2.0F)) * Math.PI * 0.25F);
        if (side == 1) {
            paddle.yRot = (float)Math.PI - paddle.yRot;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bottom.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        front.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        right.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        left.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        paddles.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        
        // Only render chest for chest boats
        if (this.chest != null && boat instanceof ${name}ChestEntity) {
            chest.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}
