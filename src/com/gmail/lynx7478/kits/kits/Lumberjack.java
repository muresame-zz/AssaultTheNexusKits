package com.gmail.lynx7478.kits.kits;

import java.util.ArrayList;
import java.util.List;

import com.gmail.lynx7478.anni.anniEvents.ResourceBreakEvent;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.anni.utils.VersionUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.kits.base.KitBase;

public class Lumberjack extends KitBase
{
		@Override
		protected void setUp()
		{
		}

		@Override
		protected String getInternalName()
		{
			return "Lumberjack";
		}

		@Override
		protected ItemStack getIcon()
		{
			return new ItemStack(Material.STONE_AXE);
		}

		@Override
		protected int setDefaults(ConfigurationSection section)
		{
			return 0;
		}

		@Override
		protected List<String> getDefaultDescription()
		{
			List<String> l = new ArrayList<String>();
			addToList(l,
					aqua+"You are the wedge.",
					"",
					aqua+"Gather wood with an efficiency",
					aqua+"axe and with the chance",
					aqua+"of gaining double yeild,",
					aqua+"ensuring quick work of",
					aqua+"any trees in your way."
				);
			return l;
		}

		@Override
		protected Loadout getFinalLoadout()
		{
			return new Loadout().addWoodSword().addWoodPick().addSoulboundEnchantedItem(new ItemStack(Material.STONE_AXE), Enchantment.DIG_SPEED, 1);
		}

		@Override
		public void cleanup(Player arg0)
		{
		}
		
		//Does the double loot for logs
		@EventHandler
		public void onResourceBreak(ResourceBreakEvent event)
		{
			if(event.getPlayer().getKit().equals(this))
			{
// 			Checking for the name log should work on all versions.
				if(event.getResource().toString().contains("LOG"))
				{
					ItemStack[] stacks = event.getProducts();
					for(int x = 0; x < stacks.length; x++)
						stacks[x].setAmount(stacks[x].getAmount()*2);
					event.setProducts(stacks);	
				}
				
/*				if(!VersionUtils.getVersion().contains("13"))
				{
					if(event.getResource().Type == Material.LOG)
					{
						ItemStack[] stacks = event.getProducts();
						for(int x = 0; x < stacks.length; x++)
							stacks[x].setAmount(stacks[x].getAmount()*2);
						event.setProducts(stacks);	
					}
				}else
				{
					if(event.getResource().toString().contains("LOG"))
					{
						ItemStack[] stacks = event.getProducts();
						for(int x = 0; x < stacks.length; x++)
							stacks[x].setAmount(stacks[x].getAmount()*2);
						event.setProducts(stacks);	
					}
					}
				} */
			}
		}
}
