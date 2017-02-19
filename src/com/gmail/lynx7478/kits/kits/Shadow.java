package com.gmail.lynx7478.kits.kits;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.gmail.lynx7478.kits.base.AnniKit;
import com.gmail.lynx7478.yanpclib.YANPC;

/**
 * Created by SKA4 on 27/11/2016.
 */
public class Shadow extends AnniKit {

	//TODO: Finish the kit.

    private static HashMap<AnniPlayer, YANPC> npcs;

	private YANPC getNPCFrom(AnniPlayer anniPlayer)
    {
        return npcs.get(anniPlayer);
    }

    @Override
    protected void onInitialize() {
        npcs = new HashMap<AnniPlayer, YANPC>();
    }

    @Override
    protected ItemStack specialItem() {
        ItemStack i = new ItemStack(Material.COAL_BLOCK);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(ChatColor.AQUA+"Shadow");
        i.setItemMeta(m);
        return i;
    }

    @Override
    protected String defaultSpecialItemName() {
        return ChatColor.AQUA+"Shadow"+ChatColor.GREEN+" READY";
    }

    @Override
    protected boolean isSpecialItem(ItemStack stack) {
        if(stack.hasItemMeta() && stack.getItemMeta().hasDisplayName())
        {
            if(stack.getItemMeta().getDisplayName().contains(ChatColor.AQUA+"Shadow"))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean performPrimaryAction(Player player, final AnniPlayer p) {
        if(p.getTeam() != null)
        {
            if(!npcs.containsKey(p))
            {
                npcs.put(p, new YANPC());
                npcs.get(p).spawn(player.getLocation(), p.getName());
                Bukkit.getScheduler().runTaskLater(AnnihilationMain.getInstance(), new Runnable()
                {
                    public void run()
                    {
                        if(npcs.containsKey(p))
                        {
                            npcs.get(p).destroy();
                            npcs.remove(p);
                        }
                    }
                }, 20 * 20L);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean performSecondaryAction(Player player, AnniPlayer p) {
        if(p.getTeam() != null)
        {
            if(npcs.containsKey(p))
            {
                player.teleport(npcs.get(p).getEntity().getLocation());
                npcs.get(p).destroy();
                npcs.remove(p);
                //TODO: Here is were we should apply the cooldown if we wish to.
                return true;
            }
        }
        return false;
    }

    //TODO: We'll probably need to have to delays, one for the primary action and another for the secondary action, or just simply apply the cooldown once the player returns to the shadow.
    @Override
    protected long getDelayLength() {
        return 60*1000L;
    }

    @Override
    protected boolean useDefaultChecking() {
        return false;
    }

    @Override
    protected boolean passive() {
        return false;
    }

    @Override
    protected String getInternalName() {
        return "Shadow";
    }

    @Override
    protected ItemStack getIcon() {
        return null;
    }

    @Override
    protected List<String> getDefaultDescription() {
        return null;
    }

    @Override
    protected Loadout getFinalLoadout() {
        return null;
    }

    @Override
    public void cleanup(Player player) {

    }
}
