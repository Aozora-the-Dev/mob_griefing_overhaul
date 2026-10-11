package com.aozorathedev.mob_griefing_overhaul.mixin;

import com.aozorathedev.mob_griefing_overhaul.gamerule.MobGriefingOverhaulGameRules;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enderman;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.Enderman$EndermanTakeBlockGoal")
public abstract class AbstractEndermanTakeBlockGoal extends Goal {
    @Shadow
    @Final
    private Enderman enderman;

    @Inject(
            method = "canUse",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"),
            cancellable = true)
    private void canUse(CallbackInfoReturnable<Boolean> cir) {
        if (!getServerLevel(this.enderman).getGameRules().get(MobGriefingOverhaulGameRules.ENDERMAN_GRIEFING)) {
            cir.setReturnValue(false);
        }
    }
}
