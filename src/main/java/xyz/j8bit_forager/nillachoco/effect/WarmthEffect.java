package xyz.j8bit_forager.nillachoco.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;

public class WarmthEffect extends MobEffect {

    public WarmthEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if (!pLivingEntity.level().isClientSide()) {

            Level world = pLivingEntity.level();

            pLivingEntity.setTicksFrozen(0);

            for (int i = -1; i <= 1; i++) {
                if (world.getBlockState(pLivingEntity.blockPosition().offset(0, i, 0)).getBlock().defaultBlockState().is(ModBlocks.Tags.DESTROYED_BY_WARMTH)) {
                    world.destroyBlock(pLivingEntity.blockPosition().offset(0, i, 0), true);
                }
            }

            if (pLivingEntity instanceof Player p) {

                String s = world.getBiome(p.blockPosition()).get().toString();
                if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() >= 2) {
                    p.displayClientMessage(Component.translatable(this.getDescriptionId() + ".warning_text"), true);
                }

            }

            /*if (world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.WARM) ||
                    world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.DESERT)){*/
            if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() >= 2) {
                if (!pLivingEntity.isInWaterRainOrBubble()) {
                    pLivingEntity.setSecondsOnFire(1);
                }
            }

        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
