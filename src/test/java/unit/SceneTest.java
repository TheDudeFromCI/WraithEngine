package unit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.whg.we.main.GameObject;
import net.whg.we.main.Scene;

public class SceneTest
{
    @Test
    public void addGameObjects()
    {
        Scene scene = new Scene();
        assertEquals(0, scene.getSize());

        scene.addGameObject(new GameObject());
        scene.addGameObject(new GameObject());
        scene.addGameObject(new GameObject());
        assertEquals(3, scene.getSize());
    }

    @Test
    public void addGameObject_Twice_null()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.addGameObject(go);
        scene.addGameObject(null);

        assertEquals(1, scene.getSize());
        assertEquals(go, scene.getObjectAt(0));
    }

    @Test
    public void removeGameObject()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.removeGameObject(go);

        assertEquals(0, scene.getSize());
    }
}
