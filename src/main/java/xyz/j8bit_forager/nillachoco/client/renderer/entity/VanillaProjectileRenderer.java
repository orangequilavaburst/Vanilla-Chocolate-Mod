package xyz.j8bit_forager.nillachoco.client.renderer.entity;

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
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.entity.custom.VanillaProjectileEntity;

public class VanillaProjectileRenderer<T extends VanillaProjectileEntity> extends EntityRenderer<T> {

    public VanillaProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public ResourceLocation getTextureLocation(VanillaProjectileEntity pEntity) {
        return new ResourceLocation(NillaChocoMod.MOD_ID, "textures/entity/projectiles/vanilla_projectile.png");
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.tickCount * pEntity.getRotateDirection() * 30.0f));

        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(pEntity)));
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        this.vertex(matrix4f, matrix3f, vertexconsumer, -1,0, -1, 0.0F, 0.0F, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 1,0, -1,  1.0F, 0.0F, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 1,0, 1, 1.0f, 1.0f, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, -1,0,1,  0.0F, 1.0f, 0, 1, 0, pPackedLight);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.vertex(matrix4f, matrix3f, vertexconsumer, -1,0, -1, 0.0F, 0.0F, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 1,0, -1,  1.0F, 0.0F, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 1,0, 1, 1.0f, 1.0f, 0, 1, 0, pPackedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, -1,0,1,  0.0F, 1.0f, 0, 1, 0, pPackedLight);

        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public void vertex(Matrix4f pMatrix, Matrix3f pNormal, VertexConsumer pConsumer, int pX, int pY, int pZ, float pU, float pV, int pNormalX, int pNormalZ, int pNormalY, int pPackedLight) {
        pConsumer.vertex(pMatrix, (float)pX, (float)pY, (float)pZ).color(255, 255, 255, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(pNormal, (float)pNormalX, (float)pNormalY, (float)pNormalZ).endVertex();
    }
}
