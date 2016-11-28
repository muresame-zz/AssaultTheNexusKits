package com.gmail.lynx7478.kits.utils.npc;

import com.gmail.lynx7478.anni.anniGame.Game;
import com.gmail.lynx7478.anni.main.AnnihilationMain;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by SKA4 on 27/11/2016.
 */
public class NPC {

    //TODO: Since we're using NMS, we should probably use reflection so it works on all versions.

    private EntityPlayer entity;

    // Constructor.
    public NPC(String name, Location spawnLocation)
    {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) Game.getGameMap().getWorld()).getHandle();
        entity = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(UUID.randomUUID(), name), new PlayerInteractManager(nmsWorld));
        entity.setLocation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());

        for(Player p : Bukkit.getOnlinePlayers())
        {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entity));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(entity));
        }
    }

    // We despawn the entity and set it to null.
    // works like this? We'll see.
    public void destroyNPC()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity));
            connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
            entity = null;
        }
    }

    public EntityPlayer getEntity()
    {
        return entity;
    }
}