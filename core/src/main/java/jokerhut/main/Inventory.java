package jokerhut.main;

import entity.Player;
import gameItem.GameItem;
import gameItem.GameItem_Resource;
import gameItem.GameItem_Weapon;

public class Inventory {

    public GameItem[] inventoryArray;
    public GameItem currentItem;
    public int currentSlotIndex;
    Player player;

    public Inventory (Player player) {

        this.inventoryArray = new GameItem[12];
        this.currentSlotIndex = 0;
        this.player = player;
        initialiseInventory();
        this.currentItem = inventoryArray[currentSlotIndex];
    }

    public void initialiseInventory () {
        this.inventoryArray[0] = new GameItem_Weapon("axe");
        this.inventoryArray[1] = new GameItem_Weapon("pickaxe");
    }

    public void previousItem () {
        if (currentSlotIndex > 0) {
            currentSlotIndex--;
        }
    }

    public void nextItem () {
        if (currentSlotIndex < 11) {
            currentSlotIndex++;
        }
    }

    public void updateItem (int i) {
        currentSlotIndex = i;
        currentItem = this.inventoryArray[i];
        if (currentItem != null) {
            System.out.println(" SELECTED A " + currentItem.name);
        }

    }

    public void addToInventory (String name, String type) {

        int foundIndex = -1;
        int emptyIndex = -1;

        for (int i = 0; i < inventoryArray.length; i++) {
            if (inventoryArray[i] != null) {
                if (inventoryArray[i].name.equals(name)) {
                    foundIndex = i;
                    break;
                }
            } else if (emptyIndex == -1) {
                emptyIndex = i;
            }
        }

        System.out.println(foundIndex);
        System.out.println(emptyIndex);

        if (foundIndex != -1) {
            inventoryArray[foundIndex].count += 1;
        } else if (emptyIndex != -1) {
            // Add new item to first empty slot
            GameItem newItem;
            if (type.equals("weapon")) {
                newItem = new GameItem_Weapon(name);
                inventoryArray[emptyIndex] = newItem;
            } else if (type.equals("resource")) {
                newItem = new GameItem_Resource(name);
                inventoryArray[emptyIndex] = newItem;
            }
        } else {
            System.out.println("Inventory is full!");
        }

        player.screen.hud.inventoryTable.refreshInventory();


    }

}
