package gameItem;

import com.badlogic.gdx.graphics.Texture;

public class GameItem {

    public String type;
    public String name;
    public boolean countable;
    public int count;
    protected String imagePath;
    public Texture image;

    public GameItem() {

        this.countable = true;
        this.count = 1;


    }

}
