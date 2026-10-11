package com.aozorathedev.mob_griefing_overhaul.mixin;

import com.aozorathedev.mob_griefing_overhaul.MobGriefingOverhaul;
import com.aozorathedev.mob_griefing_overhaul.gamerule.MobGriefingOverhaulGameRules;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntityGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class AbstractServerLevelMixin extends Level implements WorldGenLevel, ServerEntityGetter {
	@Shadow
	public abstract GameRules getGameRules();

	protected AbstractServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
		super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
	}

	@Inject(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V"))
	private void disableMobExplosions(@Nullable Entity source, @Nullable DamageSource damageSource,
									  @Nullable ExplosionDamageCalculator damageCalculator,
									  double x, double y, double z, float r, boolean fire,
									  ExplosionInteraction interactionType, ParticleOptions smallExplosionParticles,
									  ParticleOptions largeExplosionParticles, WeightedList<ExplosionParticleInfo> blockParticles,
									  Holder<SoundEvent> explosionSound, CallbackInfo ci,
									  @Local(name = "blockInteraction") LocalRef<Explosion.BlockInteraction> blockInteractionReference) {
		if (
				interactionType == ExplosionInteraction.MOB
						&& this.getGameRules().get(GameRules.MOB_GRIEFING)
						&& (
								(source instanceof Creeper && !this.getGameRules().get(MobGriefingOverhaulGameRules.CREEPER_GRIEFING))
								|| (source instanceof LargeFireball && !this.getGameRules().get(MobGriefingOverhaulGameRules.GHAST_FIREBALL_GRIEFING))
				)) {
			blockInteractionReference.set(Explosion.BlockInteraction.KEEP);
			MobGriefingOverhaul.LOGGER.info("Block Interaction has been overwritten to " + blockInteractionReference.get());
		}
	}
}