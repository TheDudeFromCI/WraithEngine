package unit.util;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import net.whg.we.util.Tuple;

public class TupleTest
{
    @Test
    public void tuple2()
    {
        var a = "Hello";
        var b = 1234;

        var t = Tuple.of(a, b);

        assertEquals(a, t.a);
        assertEquals(b, (int) t.b);
    }

    @Test
    public void tuple3()
    {
        var a = true;
        var b = "meh";
        var c = new Object();

        var t = Tuple.of(a, b, c);

        assertEquals(a, t.a);
        assertEquals(b, t.b);
        assertEquals(c, t.c);
    }

    @Test
    public void tuple4()
    {
        var a = '3';
        var b = new ArrayList<Integer>();
        var c = "27";
        var d = 564.15;

        var t = Tuple.of(a, b, c, d);

        assertEquals(a, (char) t.a);
        assertEquals(b, t.b);
        assertEquals(c, t.c);
        assertEquals(d, (double) t.d, 0);
    }

    @Test
    public void tuple5()
    {
        var a = -234f;
        var b = 71;
        var c = false;
        var d = '\n';
        var e = "watermark";

        var t = Tuple.of(a, b, c, d, e);

        assertEquals(a, (float) t.a, 0);
        assertEquals(b, (int) t.b);
        assertEquals(c, t.c);
        assertEquals(d, (char) t.d);
        assertEquals(e, t.e);
    }
}
