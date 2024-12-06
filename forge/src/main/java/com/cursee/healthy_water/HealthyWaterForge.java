package com.cursee.healthy_water;

import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class HealthyWaterForge {
    
    public HealthyWaterForge() {
        HealthyWater.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingEntityFinishUsingItem(LivingEntityUseItemEvent.Finish event) {

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack usedItemStack = event.getItem();

        if (usedItemStack.getItem() == Items.POTION && PotionUtils.getPotion(usedItemStack) == Potions.WATER) {
            player.getFoodData().eat(1, 0.5F);
            player.heal(1.0F);
        }
    }
}