package manager;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import constants.Const;
import entity.Entity;
import entity.Entity_Rock;
import entity.Entity_Tree;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;

public class MapManager {

    GameScreen screen;
    private String mapPath = "VillageGDXMap.tmx";

    public MapObjects rockObjects;
    public MapObjects treeObjects;

    public Array<Entity_Tree> treeArray;
    public Array<Entity_Rock> rockArray;


    public MapManager (GameScreen screen) {
        this.screen = screen;
    }

    public TiledMap initialiseGameMap () {
        return new TmxMapLoader().load(mapPath);
    }

    public void initialiseWorldObjects () {

        MapLayer treeLayer = screen.gameMap.getLayers().get("Tree");
        MapLayer rockLayer = screen.gameMap.getLayers().get("Rock");

        this.treeObjects = treeLayer.getObjects();
        this.rockObjects = rockLayer.getObjects();

    }

    public void initialiseObjectArrays () {

        initialiseWorldObjects();

        this.treeArray = new Array<>();
        this.rockArray = new Array<>();

        for (MapObject obj : treeObjects) {

            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                float x = rectObj.getRectangle().x / Const.OGTILESIZE;
                float y = rectObj.getRectangle().y / Const.OGTILESIZE;
                Vector2 newPos = new Vector2(x, y);

                Entity_Tree tree = new Entity_Tree(newPos, 1, 2, screen);
                tree.createBody(screen.box2DWorld);
                treeArray.add(tree);
                screen.box2DWorld.addEntityToMap(tree);
            }

        }

        for (MapObject obj : rockObjects) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                float x = rectObj.getRectangle().x / Const.OGTILESIZE;
                float y = rectObj.getRectangle().y / Const.OGTILESIZE;
                Vector2 newPos = new Vector2(x, y);

                Entity_Rock rock = new Entity_Rock(newPos, 1, 1, screen);
                rock.createBody(screen.box2DWorld);
                rockArray.add(rock);
                screen.box2DWorld.addEntityToMap(rock);
            }
        }

    }

}
