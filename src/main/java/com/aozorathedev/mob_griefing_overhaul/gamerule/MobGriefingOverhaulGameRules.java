package com.aozorathedev.mob_griefing_overhaul.gamerule;

import com.aozorathedev.mob_griefing_overhaul.MobGriefingOverhaul;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class MobGriefingOverhaulGameRules {
    public static final GameRule<Boolean> CREEPER_GRIEFING = GameRuleBuilder
            .forBoolean(true)
            .category(GameRuleCategory.MOBS)
            .buildAndRegister(MobGriefingOverhaul.id("creeper_griefing"));

    public static final GameRule<Boolean> GHAST_FIREBALL_GRIEFING = GameRuleBuilder
            .forBoolean(true)
            .category(GameRuleCategory.MOBS)
            .buildAndRegister(MobGriefingOverhaul.id("ghast_fireball_griefing"));

    public static final GameRule<Boolean> ENDERMAN_GRIEFING = GameRuleBuilder
            .forBoolean(true)
            .category(GameRuleCategory.MOBS)
            .buildAndRegister(MobGriefingOverhaul.id("enderman_griefing"));

    public static void registerGameRules() {
        MobGriefingOverhaul.LOGGER.info("Registering GameRules for " + MobGriefingOverhaul.MOD_ID);
    }
}
