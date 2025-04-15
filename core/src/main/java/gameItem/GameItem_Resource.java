package gameItem;

import com.badlogic.gdx.graphics.Texture;

public class GameItem_Resource extends GameItem{

    public GameItem_Resource (String name) {
        this.name = name;
        setupResource();
        this.image = new Texture(this.imagePath);
        this.type = "consumable";
        this.countable = true;
        this.count = 1;
    }

    public void setupResource () {

        switch (this.name) {
            case "rock":
                this.imagePath = "rockTile.png";
                this.count = 1;
                this.countable = false;
                break;
            case "wood":
                this.imagePath = "lumberResourceIcon.png";
                this.count = 1;
                this.countable = false;
                break;
        }

    }


}
