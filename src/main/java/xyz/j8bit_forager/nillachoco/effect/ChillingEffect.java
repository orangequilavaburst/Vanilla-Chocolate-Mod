package xyz.j8bit_forager.nillachoco.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.BiomeManager;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;

import java.util.List;
import java.util.stream.Collectors;

public class ChillingEffect extends MobEffect {
    protected ChillingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if (!pLivingEntity.level().isClientSide()) {

            Level world = pLivingEntity.level();

            List<BlockPos> blocks = BlockPos.betweenClosedStream(
                            pLivingEntity.getBoundingBox().inflate(2.0 + pAmplifier))
                    .filter( (bs) -> world.getBlockState(bs).getTags().toList().contains(ModBlocks.Tags.DESTROYED_BY_CHILLING))
                    .map(BlockPos::immutable)
                    .collect(Collectors.toList());
            if (blocks.size() > 0){
                for (BlockPos bp : blocks){
                    world.destroyBlock(bp, true);
                }
            }
            List<BlockPos> chillBlocks = BlockPos.betweenClosedStream(
                            pLivingEntity.getBoundingBox().inflate(2.0 + pAmplifier))
                    .filter( (bs) -> world.getBlockState(bs).is(Blocks.WATER) || world.getBlockState(bs).is(Blocks.LAVA))
                    .map(BlockPos::immutable)
                    .collect(Collectors.toList());
            if (chillBlocks.size() > 0){
                for (BlockPos bp : blocks){
                    if (world.getBlockState(bp) == Blocks.LAVA.defaultBlockState()){
                        world.setBlockAndUpdate(bp, Blocks.BASALT.defaultBlockState());
                    }
                    else if (world.getBlockState(bp) == Blocks.WATER.defaultBlockState()){
                        world.setBlockAndUpdate(bp, Blocks.FROSTED_ICE.defaultBlockState());
                        world.scheduleTick(bp, Blocks.FROSTED_ICE, Mth.nextInt(pLivingEntity.getRandom(), 60, 120));
                    }
                }
            }


            if (pLivingEntity instanceof Player p) {

                String s = world.getBiome(p.blockPosition()).get().toString();
                if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() <= 0.33 ||
                        world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.ICY)) {
                    p.displayClientMessage(Component.translatable(this.getDescriptionId() + ".warning_text"), true);
                }

            }

            /*if (world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.WARM) ||
                    world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.DESERT)){*/
            if (world.getBiome(pLivingEntity.blockPosition()).get().getBaseTemperature() <= 0.33 ||
                    world.getBiome(pLivingEntity.blockPosition()).equals(BiomeManager.BiomeType.ICY)) {
                pLivingEntity.setTicksFrozen(pLivingEntity.getTicksFrozen() + 5);
            }

        }

        if (pLivingEntity.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect() == ModEffects.WARMTH_EFFECT.get()))){
            for (MobEffectInstance me : pLivingEntity.getActiveEffects().stream().toList()){
                if (me.getEffect() == ModEffects.WARMTH_EFFECT.get() || me.getEffect() == ModEffects.CHILLING_EFFECT.get()){
                    pLivingEntity.getActiveEffects().remove(me);
                }
            }
        }
    }

}
