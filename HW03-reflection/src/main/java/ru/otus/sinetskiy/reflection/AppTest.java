package ru.otus.sinetskiy.reflection;

import ru.otus.sinetskiy.reflection.annotations.Before;
import ru.otus.sinetskiy.reflection.annotations.After;
import ru.otus.sinetskiy.reflection.annotations.Test;

/**
 * @author Andrei Sinetskii
 */
public class AppTest {

    @Before
    public void setUp1() throws Exception {
        System.out.println("setUp1");
        throw new Exception("Err on setUp1");
    }

    @Before
    public void setUp2() throws Exception {
        System.out.println("setUp2");
    }

    @Test
    public void test1() throws Exception{
        System.out.println("test1");
    }

    @Test
    public void test2() throws Exception{
        System.out.println("test2");
        throw new Exception("Err on test2");
    }

    @Test
    public void test3() throws Exception{
        System.out.println("test3");
    }

    @After
    public void reset1() throws Exception{
        System.out.println("reset1");
        throw new Exception("Err on reset1");
    }

    @After
    public void reset2() throws Exception {
        System.out.println("reset2");
    }

}
