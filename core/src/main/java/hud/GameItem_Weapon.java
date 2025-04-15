package hud;

import com.badlogic.gdx.graphics.Texture;

public class GameItem_Weapon extends GameItem {

    public GameItem_Weapon (String name) {
        this.name = name;
        setupWeapon();
        this.image = new Texture(this.imagePath);
        this.type = "weapon";
    }

    public void setupWeapon () {

        switch (this.name) {
            case "pickaxe":
                this.imagePath = "pickaxeInventory.png";
                this.count = 1;
                this.countable = false;
                break;
            case "axe":
                this.imagePath = "axeInventory.png";
                this.count = 1;
                this.countable = false;
                break;
        }

    }


}
