package com.gmail.lynx7478.kits.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.kits.KitUtils;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.kits.base.AnniKit;
import com.gmail.lynx7478.yanpclib.YANPC;

public class Shadow extends AnniKit 
{
	
	private static HashMap<AnniPlayer, YANPC> npcs;
	
	public static HashMap<AnniPlayer, YANPC> getNPCList()
	{
		return npcs;
	}
	
	@Override
	protected void onInitialize()
	{
		npcs = new HashMap<AnniPlayer, YANPC>();
	}

	@Override
	protected ItemStack specialItem()
	{
		ItemStack firestorm  = KitUtils.addSoulbound(new ItemStack(Material.COAL_BLOCK));
		ItemMeta meta = firestorm.getItemMeta();
		meta.setDisplayName(getSpecialItemName()+" "+ChatColor.GREEN+"READY");
		firestorm.setItemMeta(meta);
		return firestorm;
	}

	@Override
	protected String defaultSpecialItemName()
	{
		return ChatColor.AQUA+"Shadow";
	}

	@Override
	protected boolean isSpecialItem(ItemStack stack)
	{
		if(stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName())
		{
			String name = stack.getItemMeta().getDisplayName();
			if(name.contains(getSpecialItemName()) && KitUtils.isSoulbound(stack))
				return true;
		}
		return false;
	}

	@Override
	protected long getDelayLength()
	{
		return 0;
	}

	@Override
	protected String getInternalName()
	{
		return "Shadow";
	}

	@Override
	protected ItemStack getIcon()
	{
		return new ItemStack(Material.COAL_BLOCK);
	}

	@Override
	protected List<String> getDefaultDescription()
	{
        final List<String> toReturn = new ArrayList<String>();
        final ChatColor aqua = ChatColor.AQUA;
        toReturn.add(aqua + "Under construction.");
        return toReturn;
	}

	@Override
	public void cleanup(Player player)
	{
		
	}
	
	@Override
	protected Loadout getFinalLoadout()
	{
		return new Loadout().addWoodSword().addWoodPick().addWoodAxe().addItem(super.getSpecialItem());
	}

	@Override
	protected boolean useDefaultChecking()
	{
		return true;
	}

	@Override
	protected boolean performPrimaryAction(Player player, final AnniPlayer p) 
	{
		if(p.getTeam() != null)
		{
			if(!npcs.containsKey(p))
			{
				YANPC npc = new YANPC();
				npc.spawn(player.getLocation(), p.getName());
				npcs.put(p, npc);
				Bukkit.getScheduler().scheduleSyncDelayedTask(AnnihilationMain.getInstance(), new Runnable()
				{
					public void run()
					{
						if(Shadow.getNPCList().containsKey(p))
						{
							Shadow.getNPCList().get(p).destroy();
							Shadow.getNPCList().remove(p);
						}
					}
				}, 10*20L);
			}else
			{
				player.teleport(npcs.get(p).getSpawnLocation());
				npcs.get(p).destroy();
				npcs.remove(p);
			}
		}
		return false;
	}

	@Override
	protected boolean performSecondaryAction(Player player, AnniPlayer p) 
	{
		return false;
	}

	@Override
	protected boolean passive() 
	{
		return false;
	}

}
