package com.gmail.lynx7478.kits.kits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.kits.KitUtils;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.anni.utils.VersionUtils;
import com.gmail.lynx7478.kits.base.AnniKit;

public class Revealer extends AnniKit {
	
	private ArrayList<AnniPlayer> revealedPlayers;
	
	@Override
	protected void onInitialize()
	{
		this.revealedPlayers = new ArrayList<AnniPlayer>();
	}

	@Override
	protected ItemStack specialItem()
	{
		Material material;
		if(!VersionUtils.getVersion().contains("13"))
		{
			material = Material.EYE_OF_ENDER;
		}else
		{
			material = Material.ENDER_EYE;
		}
		ItemStack firestorm  = KitUtils.addSoulbound(new ItemStack(material));
		ItemMeta meta = firestorm.getItemMeta();
		meta.setDisplayName(getSpecialItemName()+" "+ChatColor.GREEN+"READY");
		firestorm.setItemMeta(meta);
		return firestorm;
	}

	@Override
	protected String defaultSpecialItemName()
	{
		return ChatColor.AQUA+"Eye of the Revealer";
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
		return 40000;
	}

	@Override
	protected String getInternalName()
	{
		return "Revealer";
	}

	@Override
	protected ItemStack getIcon()
	{
		return new ItemStack(Material.EYE_OF_ENDER);
	}

	@Override
	protected List<String> getDefaultDescription()
	{
        final List<String> toReturn = new ArrayList<String>();
        final ChatColor aqua = ChatColor.AQUA;
        toReturn.add(aqua + "You are the vision.");
        toReturn.add(" ");
        toReturn.add(aqua + "Use your Eye of the Revealer");
        toReturn.add(aqua + "to reveal any enemy players under");
        toReturn.add(aqua + "the effect of invisibility.");
        toReturn.add(" ");
        toReturn.add(aqua + "The effect will only last");
        toReturn.add(aqua + "a few seconds, so think");
        toReturn.add(aqua + "and act fast to repel");
        toReturn.add(aqua + "the enemy attack,");
        toReturn.add(" ");
        toReturn.add(aqua + "The effect can only be");
        toReturn.add(aqua + "used once on the same player");
        toReturn.add(aqua + "before their death.");
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
	protected boolean performPrimaryAction(Player player, AnniPlayer p) 
	{
		if(p.getTeam() != null)
		{
			for(Entity nearby : player.getNearbyEntities(8.0, 8.0, 8.0))
			{
				if(nearby instanceof Player)
				{
					Player nP = (Player) nearby;
					AnniPlayer nPA = AnniPlayer.getPlayer(nP.getUniqueId());
					if(nPA.getTeam() != null && nPA.getTeam() != p.getTeam())
					{
						if(nP.hasPotionEffect(PotionEffectType.INVISIBILITY) && !(this.revealedPlayers.contains(p)))
						{
							final Collection<PotionEffect> effs = nP.getActivePotionEffects();
							nP.removePotionEffect(PotionEffectType.INVISIBILITY);
							this.revealedPlayers.add(p);
							/** Bukkit.getScheduler().runTaskLater(AnnihilationMain.getInstance(), new Runnable()
							{
								public void run()
								{
									PotionEffect invs = null;
									for(PotionEffect eff : effs)
									{
										if(eff.getType() == PotionEffectType.INVISIBILITY)
										{
											invs = eff;
										}
									}
									nP.getActivePotionEffects().add(invs);
								}
							}, 60L); **/
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		AnniPlayer p = AnniPlayer.getPlayer(e.getEntity().getUniqueId());
		if(this.revealedPlayers.contains(p))
		{
			this.revealedPlayers.remove(p);
		}
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
