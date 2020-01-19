package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.main.Scene;

public class SceneTest
{
    @Test
    public void addGameObjects()
    {
        Scene scene = new Scene();
        assertEquals(0, scene.countGameObjects());

        scene.addGameObject(new GameObject());
        assertEquals(1, scene.countGameObjects());
    }

    @Test
    public void addGameObject_Twice_null()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.addGameObject(go);
        scene.addGameObject(null);

        assertEquals(1, scene.countGameObjects());
        assertTrue(scene.hasGameObject(go));
    }

    @Test
    public void removeGameObject()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.removeGameObject(go);

        assertEquals(0, scene.countGameObjects());
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

        assertEquals(3, scene.countGameObjects());
        assertTrue(scene.hasGameObject(go1));
        assertFalse(scene.hasGameObject(go2));
        assertTrue(scene.hasGameObject(go3));
        assertTrue(scene.hasGameObject(go4));
    }

    @Test
    public void gameObject_iterator()
    {
        Scene scene = new Scene();

        GameObject[] go = new GameObject[4];
        for (int i = 0; i < go.length; i++)
        {
            go[i] = new GameObject();
            scene.addGameObject(go[i]);
        }

        int index = 0;
        for (GameObject g : scene)
            assertEquals(go[index++], g);

        assertEquals(4, index);
    }
}
