package net.whg.we.util;

/**
 * A small utility class for handling pairs of objects.<br>
 * <br>
 * This class can be used as:
 * 
 * <pre>
 * // Get args
 * String arg1 = "Hello";
 * int arg2 = 123;
 * Foo arg3 = new Foo();
 * 
 * // Create tuple
 * var tuple = Tuple.of(arg1, arg2, arg3);
 * 
 * // Use tuple
 * sayHello(tuple.a);
 * addNumber(tuple.b);
 * var(tuple.c);
 * </pre>
 */
public class Tuple
{
    /**
     * An immutable tuple with 2 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     */
    public static class Tuple2<A, B>
    {
        public final A a;
        public final B b;

        /**
         * Creates a new tuple.
         * 
         * @param a
         *     - The first object.
         * @param b
         *     - The second object.
         */
        public Tuple2(A a, B b)
        {
            this.a = a;
            this.b = b;
        }
    }

    /**
     * Creates an immutable tuple with 2 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param a
     *     - The first object.
     * @param b
     *     - The second object.
     * @return The tuple.
     */
    public static <A, B> Tuple2<A, B> of(A a, B b)
    {
        return new Tuple2<A, B>(a, b);
    }

    /**
     * An immutable tuple with 3 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     */
    public static class Tuple3<A, B, C>
    {
        public final A a;
        public final B b;
        public final C c;

        /**
         * Creates a new tuple.
         * 
         * @param a
         *     - The first object.
         * @param b
         *     - The second object.
         * @param c
         *     - The third object.
         */
        public Tuple3(A a, B b, C c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    /**
     * Creates an immutable tuple with 3 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     * @param a
     *     - The first object.
     * @param b
     *     - The second object.
     * @param c
     *     - The third object.
     * @return The tuple.
     */
    public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c)
    {
        return new Tuple3<>(a, b, c);
    }

    /**
     * An immutable tuple with 3 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     * @param <D>
     *     - The fourth argument type.
     */
    public static class Tuple4<A, B, C, D>
    {
        public final A a;
        public final B b;
        public final C c;
        public final D d;

        /**
         * Creates a new tuple.
         * 
         * @param a
         *     - The first object.
         * @param b
         *     - The second object.
         * @param c
         *     - The third object.
         * @param d
         *     - The fourth object.
         */
        public Tuple4(A a, B b, C c, D d)
        {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    /**
     * Creates an immutable tuple with 4 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     * @param <D>
     *     - The fourth argument type.
     * @param a
     *     - The first object.
     * @param b
     *     - The second object.
     * @param c
     *     - The third object.
     * @param d
     *     - The fourth object.
     * @return The tuple.
     */
    public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d)
    {
        return new Tuple4<>(a, b, c, d);
    }

    /**
     * An immutable tuple with 3 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     * @param <D>
     *     - The fourth argument type.
     * @param <E>
     *     - The fifth argument type.
     */
    public static class Tuple5<A, B, C, D, E>
    {
        public final A a;
        public final B b;
        public final C c;
        public final D d;
        public final E e;

        /**
         * Creates a new tuple.
         * 
         * @param a
         *     - The first object.
         * @param b
         *     - The second object.
         * @param c
         *     - The third object.
         * @param d
         *     - The fourth object.
         * @param e
         *     - The fifth object.
         */
        public Tuple5(A a, B b, C c, D d, E e)
        {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }
    }

    /**
     * Creates an immutable tuple with 5 arguments.
     * 
     * @param <A>
     *     - The first argument type.
     * @param <B>
     *     - The second argument type.
     * @param <C>
     *     - The third argument type.
     * @param <D>
     *     - The fourth argument type.
     * @param <D>
     *     - The fifth argument type.
     * @param a
     *     - The first object.
     * @param b
     *     - The second object.
     * @param c
     *     - The third object.
     * @param d
     *     - The fourth object.
     * @param e
     *     - The fifth object.
     * @return The tuple.
     */
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e)
    {
        return new Tuple5<>(a, b, c, d, e);
    }
}
