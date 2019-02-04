package com.gmail.lynx7478.kits.kits;

import java.util.ArrayList;
import java.util.List;

import com.gmail.lynx7478.anni.anniEvents.PlayerKilledEvent;
import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.kits.KitUtils;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.anni.utils.VersionUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.lynx7478.kits.base.KitBase;

public class Defender extends KitBase
{

	@Override
	protected void setUp() 
	{
		
	}

	@Override
	protected String getInternalName() 
	{
		return "Defender";
	}

	@Override
	protected ItemStack getIcon() 
	{
		if(!VersionUtils.getVersion().contains("13"))
			return new ItemStack(Material.WOOD_SWORD);
		else
			return new ItemStack(Material.WOODEN_SWORD);
	}

	@Override
	protected int setDefaults(ConfigurationSection section) 
	{
		return 0;
	}
	
//	@Override
//	public boolean hasPermission(Player player)
//	{
//		return false;
//	}
	
	@Override
	protected List<String> getDefaultDescription() 
	{
		List<String> l = new ArrayList<String>();
		addToList(l,
					aqua+"You are the last line.",
					"",
					aqua+"While around the nexus",
					aqua+"you gain the regeneration",
					aqua+"buff and killing players",
					aqua+"while in the vicinity",
					aqua+"of the nexus rewards you",
					aqua+"with extra experience",
					aqua+"points."
				);
		return l;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkXP(PlayerKilledEvent event)
	{
		if(event.getKiller().getTeam().equals(this) && event.getAttributes().contains(PlayerKilledEvent.KillAttribute.NEXUSDEFENSE))
			event.getKiller().getPlayer().giveExp(20);
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void damageHandler(EntityDamageEvent event) 
	{
		if(event.getEntityType() == EntityType.PLAYER)
		{
			AnniPlayer p = AnniPlayer.getPlayer(event.getEntity().getUniqueId());
			if(p != null && p.getTeam() != null && !p.getTeam().isTeamDead() && p.getTeam().getNexus().getLocation() != null && p.getKit().equals(this))
			{
				Player player = (Player)event.getEntity();
				if(player.getLocation().distanceSquared(p.getTeam().getNexus().getLocation().toLocation()) <= 20*20)
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,Integer.MAX_VALUE,0));
				else 
					player.removePotionEffect(PotionEffectType.REGENERATION);
			}
		}
	}

	@Override
	public void cleanup(Player arg0) 
	{
		
	}

	@Override
	protected Loadout getFinalLoadout()
	{
		return new Loadout().addWoodSword().addWoodPick().addWoodAxe().addWoodShovel().setUseDefaultArmor(true).setArmor(2, KitUtils.addSoulbound(new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
	}

}
