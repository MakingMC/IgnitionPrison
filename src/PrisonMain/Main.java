package PrisonMain;

import com.connorlinfoot.titleapi.TitleAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

    ArrayList<Material> li = new ArrayList<>();
    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
        li.add(Material.COAL_ORE);
        li.add(Material.IRON_ORE);
        li.add(Material.GOLD_ORE);
        li.add(Material.DIAMOND_ORE);
        li.add(Material.REDSTONE_ORE);
        li.add(Material.LAPIS_ORE);
    }
    @Override
    public void onDisable() {
    }


    Random rand = new Random();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {

        //TitleAPI.sendTitle(e.getPlayer(), 140, 150, 140, "Lost & Found", String.format("&bWe found your items %s.", e.getPlayer().getName()));
        Block b = e.getBlock();
        Player p = e.getPlayer();
        Inventory inv = p.getInventory();
        Material a = Material.STONE;
        Dye dye = new Dye(Material.INK_SACK);
        dye.setColor(DyeColor.BLUE);
        ItemStack lapis = dye.toItemStack();
        World world = Bukkit.getWorlds().get(0);
        Location l = new Location(world, b.getX(), b.getY(), b.getZ());
        Block bss = l.getBlock();
        int amount = 2*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)+1;
        lapis.setAmount(2*amount);
        if(p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
            switch (b.getType()) {
                case COAL_ORE: {
                    inv.addItem(new ItemStack(Material.COAL,amount));
                    b.setType(a);
                    e.setCancelled(true);
                   Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                case GOLD_ORE: {
                    inv.addItem(new ItemStack(Material.GOLD_INGOT,amount));
                    b.setType(a);
                    e.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                case DIAMOND_ORE:{
                    inv.addItem(new ItemStack(Material.DIAMOND,amount));
                    b.setType(a);
                    e.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                case IRON_ORE:{
                    inv.addItem(new ItemStack(Material.IRON_INGOT,amount));
                    b.setType(a);
                    e.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                case GLOWING_REDSTONE_ORE:{
                    inv.addItem(new ItemStack(Material.REDSTONE,2*amount));
                    b.setType(a);
                    e.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                case LAPIS_ORE:{
                    inv.addItem(lapis);
                    b.setType(a);
                    e.setCancelled(true);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            bss.setType(li.get(rand.nextInt(5)+1));
                        }
                    }, 60);
                    break;
                }
                default:{
                    e.setCancelled(true);
                    p.sendMessage(String.format("%sPlease break a block in the mine!", ChatColor.RED));
                    break;
                }

            }
        }
        else{
            if(!p.isOp()) {
                e.setCancelled(true);
            }
        }


    }
    @EventHandler
    public void onSomething(SignChangeEvent e){
        if(e.getLine(0).equalsIgnoreCase("[Ignition]")){
            if(e.getLine(1).equalsIgnoreCase("a")) {
                    e.setLine(0, String.format("%s[Ignition]", ChatColor.BLUE));
                    e.setLine(1, String.format("%s[Sell]", ChatColor.RED));
                    e.setLine(2, String.format("%s"+e.getLine(2).toUpperCase(), ChatColor.AQUA));
                    e.setLine(3, "");
                }
            }
        }

    @EventHandler
    public void Eats(PlayerItemConsumeEvent e){
        if(e.getItem().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 1000){
            e.getPlayer().chat("Nub");
        }
    }

    @EventHandler
    public void SignClickEvent (PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        if(e.getClickedBlock().getType() == Material.SIGN ||e.getClickedBlock().getType() == Material.SIGN_POST ||e.getClickedBlock().getType() == Material.WALL_SIGN) {
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(String.format("%s[Sell]", ChatColor.RED))) {
                TitleAPI.sendTitle(e.getPlayer(), 140, 150, 140, "Lost & Found", String.format("&bWe found your items %s.", e.getPlayer().getName()));
                }
            }
        }
    }
}
