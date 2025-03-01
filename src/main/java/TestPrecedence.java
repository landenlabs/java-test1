public class TestPrecedence {

/*
    https://introcs.cs.princeton.edu/java/11precedence/

    Level	Operator	Description                 Associativity
    16	    []          access array element        left to right
             .          access object member
            ()	        parentheses
    15	    ++          unary post-increment        not associative
            --	        unary post-decrement

    14	    ++          unary pre-increment         right to left
            --          unary pre-decrement
            +           unary plus
            -           unary minus
            !           unary logical NOT
            ~	        unary bitwise NOT
    13	    ()          cast                        right to left
            new	        object creation
    12	    * / %	    multiplicative	            left to right
    11	    + -         additive                    left to right
            +	        string concatenation
    10	    << >>       shift	                    left to right
            >>>
    9	    < <=        relational                  not associative
            > >=
            instanceof
    8	    ==          equality	                left to right
            !=
    7	    &	        bitwise AND	                left to right
    6	    ^	        bitwise XOR	                left to right
    5	    |	        bitwise OR	                left to right
    4	    &&	        logical AND	                left to right
    3	    ||	        logical OR	                left to right
    2	    ?:	        ternary	                    right to left
    1	    =   +=   -= assignment	                right to left
            *=  /=   %=
            &=  ^=   |=
            <<= >>=  >>>=
*/
    static public void test() {

        int a = 1;
        int c = 2;
        int d = 3;
        boolean b = true;

        if (a >= d - c ) {
            // Math operator high precedence then >=
            //  a >= (d - c)
            //  1 >= (3 - 2)
        }

        int f = (a > c && (c > d) ? c : d);
        //       ((a > c) && (c > d)) ? c : d

        int x = 5;
        int y = 10;
        int z = ++x * y--;
        System.out.println("z=" + z);

        System.out.println("1 + 2 = " + 1 + 2);
        System.out.println("1 + 2 = " + (1 + 2));

        System.out.println(1 + 2 + "abc");
        System.out.println("abc" + 1 + 2);
    }
}
