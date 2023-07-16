package xyz.j8bit_forager.nillachoco.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.shapes.CollisionContext;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;

import java.util.List;
import java.util.stream.Collectors;

public class WarmthEffect extends MobEffect {

    public WarmthEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        Level world = pLivingEntity.level();

        if (pLivingEntity.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect() == ModEffects.CHILLING_EFFECT.get()))){
            for (MobEffectInstance me : pLivingEntity.getActiveEffects().stream().toList()){
                if (me.getEffect() == ModEffects.WARMTH_EFFECT.get() || me.getEffect() == ModEffects.CHILLING_EFFECT.get()){
                    pLivingEntity.getActiveEffects().remove(me);
                }
            }
        }
        else {

            pLivingEntity.setTicksFrozen(0);

            List<BlockPos> blocks = BlockPos.betweenClosedStream(
                            pLivingEntity.getBoundingBox().inflate(2.0 + pAmplifier))
                    .filter((bs) -> world.getBlockState(bs).getTags().toList().contains(ModBlocks.Tags.DESTROYED_BY_WARMTH))
                    .map(BlockPos::immutable)
                    .collect(Collectors.toList());
            if (blocks.size() > 0) {
                for (BlockPos bp : blocks) {
                    world.destroyBlock(bp, true, pLivingEntity);
                }
            }

            List<LivingEntity> entities = world.getEntities(EntityTypeTest.forClass(LivingEntity.class),
                    pLivingEntity.getBoundingBox().inflate(2.0),
                    (entity) -> entity.isPickable() && entity.getType().is(ModEntityTypes.Tags.HURT_BY_WARMTH));
            if (entities.size() > 0){
                for (LivingEntity le : entities){
                    le.hurt(pLivingEntity.damageSources().magic(), 2.0f);
                }
            }

            if (pLivingEntity instanceof Player p) {

                String s = world.getBiome(p.blockPosition()).get().toString();
                if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() >= 2) {
                    p.displayClientMessage(Component.translatable(this.getDescriptionId() + ".warning_text"), true);
                    if (!pLivingEntity.isInWaterRainOrBubble()) {
                        pLivingEntity.setSecondsOnFire(1);
                    }
                }

            }

        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
