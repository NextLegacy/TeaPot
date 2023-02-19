package engine.utils.random;

import engine.utils.time.Time;

/**
 * A random number generator that uses a stateful random number generator to generate random numbers.
 * 
 * Default stateful random number generator is xoshiro256ss.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Random
{
    public final static Random RANDOM;

    static
    {
        RANDOM = new Random();
    }

    public final State state;

    public Random() { this(Time.nanos()); }
    public Random(final long seed) { this.state = new xoshiro256ss(seed); }
    public Random(final State state) { this.state = state; }

    /**
     * @return a random long
     */
    public final long next()
    {
        return state.next();
    }

    /**
     * @return a random double in the range [0, 1)
     */
    public final double randomDouble()
    {
        return (next() >>> 11) * 0x1.0p-53;
    }

    /**
     * @param bound the upper bound (exclusive)
     * @return a random int in the range [0, bound)
     */
    public final int randomInt(int bound)
    {
        return (int) randomLong(bound);
    }

    /**
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a random int in the range [min, max]
     */
    public final int randomInt(int min, int max)
    {
        return randomInt(max - min + 1) + min;
    }

    /**
     * @param bound the upper bound (exclusive)
     * @return a random long in the range [0, bound)
     */
    public final long randomLong(long bound)
    {
        return (next() & Long.MAX_VALUE) % bound;
    }

    /**
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a random long in the range [min, max]
     */
    public final long randomLong(int min, int max)
    {
        return randomLong(max - min + 1) + min;
    }

    /**
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random double in the range [min, max)
     */
    public final double randomDouble(double min, double max)
    {
        return randomDouble() * (max - min) + min;
    }

    /**
     * @param probability the probability of returning true
     * @return a random boolean
     */
    public final boolean randomBoolean(double probability)
    {
        return randomDouble() < probability;
    }

    /**
     * @param length the length of the string
     * @param charSet the set of characters to choose from
     * @return a random string of length length using characters from charSet
     */
    public final String randomString(int length, char[] charSet)
    {
        char[] chars = new char[length];

        for (int i = 0; i < length; i++)
            chars[i] = charSet[randomInt(0, charSet.length - 1)];

        return new String(chars);
    }


    /**
     * A stateful random number generator.
     * 
     * @author NextLegacy
     * @version 1.0
     */
    public static abstract class State
    {
        final long[] state;

        protected State(final long seed)
        {
            this.state = new long[] { seed };
        }

        protected State(final long seed, final int stateLength)
        {
            this.state = new long[stateLength];

            splitmix64 splitmix64 = new splitmix64(seed);

            for (int i = 0; i < stateLength; i++)
                state[i] = splitmix64.next();
        }

        protected abstract long next();
    }

    //https://www.wikiwand.com/en/Xorshift#Initialization
    public static class splitmix64 extends State
    {
        public splitmix64(final long seed) 
        {
            super(seed);
        }

        @Override
        protected long next() 
        {
            long result = (state[0] += 0x9E3779B97F4A7C15L);
            result = (result ^ (result >> 30)) * 0xBF58476D1CE4E5B9L;
            result = (result ^ (result >> 27)) * 0x94D049BB133111EBL;

            return result ^ (result >> 31);
        }
    }

    //https://www.wikiwand.com/en/Xorshift#xoshiro256**
    public static class xoshiro256ss extends State
    {
        public xoshiro256ss(final long seed) 
        {
            super(seed, 4);
        }

        @Override
        protected long next() 
        {
            final long result = rol64(state[1] * 5, 7) * 9;

            final long t = state[1] << 17;
    
            state[2] ^= state[0];
            state[3] ^= state[1];
            state[1] ^= state[2];
            state[0] ^= state[3];
    
            state[2] ^= t;

            state[3] = rol64(state[3], 45);
    
            return result;
        }

        private static long rol64(long x, long k)
        {
            return (x << k) | (x >>> (64 - k));
        }
    } 
}
