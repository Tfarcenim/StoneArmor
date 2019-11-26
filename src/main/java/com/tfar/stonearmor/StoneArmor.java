package com.tfar.stonearmor;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = StoneArmor.MODID)
@Mod(modid = StoneArmor.MODID, name = StoneArmor.NAME, version = StoneArmor.VERSION)
public class StoneArmor {
  public static final String MODID = "stonearmor";
  public static final String NAME = "Stone Armor";
  public static final String VERSION = "@VERSION@";

  public static ItemArmor.ArmorMaterial STONE_ARMOR;
  public static final List<Item> MOD_ITEMS = new ArrayList<>();

  public static Logger logger;

  @Config(modid = MODID)
  public static class Configs {

    @Config.RangeInt(min = 0)
    public static int toughness = 1;

    @Config.RangeInt(min = 0)
    public static int head = 1;
    @Config.RangeInt(min = 0)
    public static int chestplate = 4;
    @Config.RangeInt(min = 0)
    public static int leggings = 3;
    @Config.RangeInt(min = 0)
    public static int boots = 1;

    @Config.RangeInt(min = 0)
    public static int durability = 90;

    @Config.RangeInt(min = 0)
    public static int enchantability = 7;

  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    STONE_ARMOR = EnumHelper.addArmorMaterial(MODID + ":stone_armor", MODID + ":stone", Configs.durability, new int[]{Configs.head, Configs.chestplate, Configs.leggings,Configs.boots}, Configs.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, Configs.toughness);
    STONE_ARMOR.setRepairItem(new ItemStack(Blocks.STONE));
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> registry = event.getRegistry();

    registerItem(new ItemArmor(STONE_ARMOR,0, EntityEquipmentSlot.HEAD), "stone_helmet", registry);
    registerItem(new ItemArmor(STONE_ARMOR,0, EntityEquipmentSlot.CHEST), "stone_chestplate", registry);
    registerItem(new ItemArmor(STONE_ARMOR,0, EntityEquipmentSlot.LEGS), "stone_leggings", registry);
    registerItem(new ItemArmor(STONE_ARMOR,0, EntityEquipmentSlot.FEET), "stone_boots", registry);
  }

  private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
    item.setRegistryName(name);
    item.setTranslationKey(item.getRegistryName().toString());
    item.setCreativeTab(CreativeTabs.COMBAT);
    MOD_ITEMS.add(item);
    registry.register(item);
  }

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    for (Item item : MOD_ITEMS)
      registerModelLocation(item);
  }

  private static void registerModelLocation(Item item) {
    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
  }
}
