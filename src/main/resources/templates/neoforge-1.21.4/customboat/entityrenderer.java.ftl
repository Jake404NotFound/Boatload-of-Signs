<#-- @ftl {"imports":<#include "procedures.java_imports.ftl">} -->
package ${package}.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import ${package}.entity.${name}Entity;
import ${package}.client.model.${name}Model;

public class ${name}Renderer extends EntityRenderer<${name}Entity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("${modid}", "textures/entity/${registryname}.png");
    private static final ResourceLocation CHEST_TEXTURE = new ResourceLocation("${modid}", "textures/entity/${registryname}_chest.png");
    
    protected final ${name}Model<${name}Entity> model;

    public ${name}Renderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
        this.model = new ${name}Model<>(context.bakeLayer(${name}Model.LAYER_LOCATION));
    }

    @Override
    public void render(${name}Entity entity, float entityYaw, float partialTicks, PoseStack poseStack, 
                      MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.375D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)entity.getHurtTime() - partialTicks;
        float f1 = entity.getDamage() - partialTicks;
        
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)entity.getHurtDir()));
        }

        float f2 = entity.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(f2));
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        
        // Choose texture based on boat type
        ResourceLocation textureLocation = entity instanceof ${name}ChestEntity ? CHEST_TEXTURE : TEXTURE;
        
        this.model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(textureLocation));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(${name}Entity entity) {
        return entity instanceof ${name}ChestEntity ? CHEST_TEXTURE : TEXTURE;
    }
}
