package engine.utils;

/**
 * This class is not meant to be instantiated.
 * It contains static methods for string operations.
 * 
 * @author NextLegacy
 */
public final class StringUtils 
{
    private StringUtils() { }
    
    /**
     * <p>
     * Returns the number of occurences of a given string inside another string.
     * </p>
     * <p>
     * This algorithm has a worst time complexity of O(s + c + sc) -> O(n^2),
     * in which s stands for stringLength and c for countLength
     * </p>
     * @param string the string from which the number of occurences of a given string is gonna be counted.
     * @param count the string that is getting counted.
     * @return the number of occurences.
     */
    public static int count(String string, String count)
    {
        if (string == null || count == null) return 0;

        if (string == count) return 1;

        final int stringLength = string.length();
        final int countLength  = count .length();
        
        if (stringLength == 0) return 0; 

        if (countLength == 0) return stringLength + 1;

        int counter = 0;

        final char[] stringChars = new char[stringLength];
        final char[] countChars  = new char[countLength ];

        string.getChars(0, stringLength, stringChars, 0); //O(s)
        count .getChars(0, countLength , countChars , 0); //O(c)

        for (int i = 0; i < stringChars.length; i++) //O(s)
        {
            int j = 0;

            while (j < countLength && i + j < stringLength && stringChars[i + j] == countChars[j]) //O(c)
            {
                j++;
            }

            if (j != countLength) continue;

            i += countLength - 1;

            counter++;
        }

        return counter;
    }
}
