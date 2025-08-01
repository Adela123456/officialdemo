package com.sun;
import org.junit.Test;
public class TestString {
    @Test
    public void testString() {
        String s1="hello";
        String s2 = "world";
        String s3 = s1+s2;
        String s4 = "helloworld";
        System.out.println(s3.equals(s4));

    }
}
