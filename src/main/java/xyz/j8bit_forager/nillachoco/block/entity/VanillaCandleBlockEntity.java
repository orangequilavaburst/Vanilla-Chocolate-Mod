package xyz.j8bit_forager.nillachoco.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.j8bit_forager.nillachoco.block.VanillaScentCandleBlock;

public class VanillaCandleBlockEntity extends BlockEntity {
    public VanillaCandleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VANILLA_CANDLE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        int candles = state.getValue(VanillaScentCandleBlock.CANDLES);
        AABB area = new AABB(pos).inflate(candles * 8);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, entity -> !(entity instanceof Player))) {
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0));
        }
    }
}
