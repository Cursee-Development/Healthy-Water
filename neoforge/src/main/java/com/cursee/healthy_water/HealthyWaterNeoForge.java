package com.cursee.healthy_water;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@Mod(Constants.MOD_ID)
public class HealthyWaterNeoForge {
    
    public HealthyWaterNeoForge(IEventBus modEventBus) {
        HealthyWater.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingEntityFinishUsingItem(LivingEntityUseItemEvent.Finish event) {

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack usedItemStack = event.getItem();

        if (!usedItemStack.getComponents().has(DataComponents.POTION_CONTENTS)) return;

        PotionContents potionContents = usedItemStack.get(DataComponents.POTION_CONTENTS);

        if (potionContents != null && potionContents.is(Potions.WATER)) {
            player.getFoodData().eat(1, 0.5F);
            player.heal(1.0F);
        }
    }
}