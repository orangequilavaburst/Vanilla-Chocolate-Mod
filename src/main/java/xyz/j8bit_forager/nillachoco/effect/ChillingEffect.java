package xyz.j8bit_forager.nillachoco.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraftforge.common.BiomeManager;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;

import java.util.List;

public class ChillingEffect extends MobEffect {
    protected ChillingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level world = pLivingEntity.level();

        if (pLivingEntity.getEffect(this).endsWithin(1) && pLivingEntity instanceof Skeleton skeleton) {
            skeleton.convertTo(EntityType.STRAY, true);
        }

        if (pLivingEntity.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect() == ModEffects.WARMTH_EFFECT.get()))){
            for (MobEffectInstance me : pLivingEntity.getActiveEffects().stream().toList()){
                if (me.getEffect() == ModEffects.WARMTH_EFFECT.get() || me.getEffect() == ModEffects.CHILLING_EFFECT.get()){
                    pLivingEntity.getActiveEffects().remove(me);
                }
            }
        } else {
            List<BlockPos> blocks = BlockPos.betweenClosedStream(
                            pLivingEntity.getBoundingBox().inflate(2.0 + pAmplifier))
                    .filter((bs) -> world.getBlockState(bs).getTags().toList().contains(ModBlocks.Tags.DESTROYED_BY_CHILLING)
                            || world.getBlockState(bs).is(Blocks.LAVA) || world.getBlockState(bs).is(Blocks.WATER))
                    .map(BlockPos::immutable)
                    .toList();
            if (blocks.size() > 0) {
                for (BlockPos bp : blocks) {
                    if (world.getBlockState(bp) == Blocks.LAVA.defaultBlockState()) {
                        world.setBlockAndUpdate(bp, Blocks.BASALT.defaultBlockState());
                    } else if (world.getBlockState(bp) == Blocks.WATER.defaultBlockState()) {
                        world.setBlockAndUpdate(bp, Blocks.FROSTED_ICE.defaultBlockState());
                        world.scheduleTick(bp, Blocks.FROSTED_ICE, Mth.nextInt(pLivingEntity.getRandom(), 60, 120));
                    } else {
                        world.destroyBlock(bp, true, pLivingEntity);
                    }

                }
            }
            List<LivingEntity> entities = world.getEntities(EntityTypeTest.forClass(LivingEntity.class),
                    pLivingEntity.getBoundingBox().inflate(2.0),
                    (entity) -> entity.isPickable() && entity.getType().is(ModEntityTypes.Tags.HURT_BY_CHILLING));
            if (entities.size() > 0){
                for (LivingEntity le : entities){
                    le.hurt(pLivingEntity.damageSources().magic(), 2.0f);
                }
            }


            if (pLivingEntity instanceof Player p) {
                // String s = world.getBiome(p.blockPosition()).get().toString();
                if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() <= 0.33 ||
                        world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.ICY)) {
                    p.displayClientMessage(Component.translatable(this.getDescriptionId() + ".warning_text"), true);
                    pLivingEntity.setTicksFrozen(Math.min(pLivingEntity.getTicksFrozen() + 5, pLivingEntity.getTicksRequiredToFreeze() + 5));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
