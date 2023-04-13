package xyz.j8bit_forager.nillachoco.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.entity.custom.ChocolateArrowEntity;

@OnlyIn(Dist.CLIENT)
public class ChocolateArrowRenderer extends ArrowRenderer<ChocolateArrowEntity> {
    public ChocolateArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(ChocolateArrowEntity pEntity) {
        return new ResourceLocation(NillaChocoMod.MOD_ID, "textures/entity/projectiles/chocolate_arrow.png");
    }

    @Override
    public void render(ChocolateArrowEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        //if (pEntity.getFallAmount() >= pEntity.getAppearGoal())
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
