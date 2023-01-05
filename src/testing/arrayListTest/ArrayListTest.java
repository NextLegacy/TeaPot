package testing.arrayListTest;

import static engine.utils.ArrayUtils.*;

import javax.swing.JFrame;

import engine.utils.ArrayUtils;

public class ArrayListTest 
{
    public static void main(String[] args)
    {
        char[] someArray = { 'a', 'b', 'c' };

        System.out.println(ArrayUtils.toString(someArray));

        someArray = push(someArray, 'd', 'e', 'f', 'g', 'h', 'i');

        System.out.println(ArrayUtils.toString(someArray));
        
        someArray = remove(someArray, 'b');

        System.out.println(ArrayUtils.toString(someArray));
        
        someArray = removeIndecies(someArray, 0, 1, 2, 5, 6, 7);
        
        System.out.println(ArrayUtils.toString(someArray));
        
        someArray = push(someArray, "ihvwwdvfuiwbr".toCharArray());
        someArray = sort(someArray);

        System.out.println(ArrayUtils.toString(someArray));

        someArray = resize(someArray, 6);

        System.out.println(ArrayUtils.toString(someArray));

        someArray = pushAtIndex(someArray, 1, "peter".toCharArray());
        
        System.out.println(ArrayUtils.toString(someArray));
        
        System.out.println(ArrayUtils.<String>array(10));
    }
}
