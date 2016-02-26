package com.example.dimaj.myapplication.lib;

/**
 * Created by dimaj on 27.02.2016.
 */
public class Test {
    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    private Test() {
    }
}
