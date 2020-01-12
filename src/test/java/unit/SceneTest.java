package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.main.Scene;

public class SceneTest
{
    @Test
    public void addGameObjects()
    {
        Scene scene = new Scene();
        assertEquals(0, scene.getGameObjects()
                             .size());

        scene.addGameObject(new GameObject());
        assertEquals(1, scene.getGameObjects()
                             .size());
    }

    @Test
    public void addGameObject_Twice_null()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.addGameObject(go);
        scene.addGameObject(null);

        List<GameObject> list = scene.getGameObjects();
        assertEquals(1, list.size());
        assertEquals(go, list.get(0));
    }

    @Test
    public void removeGameObject()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.removeGameObject(go);

        List<GameObject> list = scene.getGameObjects();
        assertEquals(0, list.size());
    }

    @Test
    public void cullGameObjects()
    {
        Scene scene = new Scene();
        GameObject go1 = new GameObject();
        GameObject go2 = new GameObject();
        GameObject go3 = new GameObject();
        GameObject go4 = new GameObject();

        scene.addGameObject(go1);
        scene.addGameObject(go2);
        scene.addGameObject(go3);
        scene.addGameObject(go4);
        go2.markForRemoval();

        scene.cullGameObjects();

        List<GameObject> list = scene.getGameObjects();
        assertEquals(3, list.size());
        assertTrue(list.contains(go1));
        assertTrue(list.contains(go3));
        assertTrue(list.contains(go4));
    }
}
