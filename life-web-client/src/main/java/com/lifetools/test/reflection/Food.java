package com.lifetools.test.reflection;

/**
 * Created by Zheng.Ke
 * Date 2016/11/8.
 */
@HelloWorld
public class Food {
    @HelloWorld(value = "fieldValue")
    public String id = "public_id";
    private String name = "private_name";

    private Food() {

    }

    public Food(String id) {
        this.id = id;
    }

    @HelloWorld(value = "methodValue")
    public String hello(@HelloWorld("paramsValue") String s) {
        System.out.println("hello i am hello method");
        return "Hello";
    }

    private String privateHello() {
        System.out.println("privateHello....");
        return "privateHello";
    }
}
