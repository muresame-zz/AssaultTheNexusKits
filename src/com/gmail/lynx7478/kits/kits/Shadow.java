package com.gmail.lynx7478.kits.kits;

import com.gmail.lynx7478.anni.anniGame.AnniPlayer;
import com.gmail.lynx7478.anni.kits.Loadout;
import com.gmail.lynx7478.kits.base.AnniKit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by SKA4 on 27/11/2016.
 */
public class Shadow extends AnniKit {
    @Override
    protected void onInitialize() {

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
    protected boolean performPrimaryAction(Player player, AnniPlayer p) {
        return false;
    }

    @Override
    protected boolean performSecondaryAction(Player player, AnniPlayer p) {
        return false;
    }

    @Override
    protected long getDelayLength() {
        return 0;
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
