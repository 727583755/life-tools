package com.lifetools.test.reflection;

import com.lifetools.vo.request.MortgageCalculateVo;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * http://ifeve.com/java-reflection/
 * Created by Zheng.Ke
 * Date 2016/11/8.
 */
public class HelloTest {

    @Test
    public void test1() {
        Class c = Food.class;
        try {
            c = Class.forName("com.lifetools.test.reflection.Food");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(c.getInterfaces().length);
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.hashCode());
        }

        System.out.println(Modifier.isPublic(c.getModifiers()));
        Method[] methods = c.getMethods();
        System.out.println(MortgageCalculateVo.class.getPackage());
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    @Test
    public void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class c = Food.class;
        Constructor[] constructors = c.getConstructors();
        System.out.println(constructors.length);
        for (Constructor constructor : constructors) {
            Class[] cc = constructor.getParameterTypes();
            for (Class c1 : cc) {
                System.out.println(c1.getSimpleName());
            }
        }

        Constructor constructor = c.getConstructor(String.class);
        Food food = (Food) constructor.newInstance("good");
    }

    @Test
    public void test3() throws IllegalAccessException {
        Class c = Food.class;
        Field[] fields = c.getFields();
        Food food = new Food("hehe");
        for (Field field : fields) {
            System.out.println(field.get(food));
        }
    }

    @Test
    public void test4() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class c = Food.class;
        Food food = new Food("123");
        Method[] methods = c.getMethods();
        Method mm = c.getMethod("hello", String.class);
        mm.invoke(food,"good");
        for (Method m : methods) {
            System.out.print(m.getName() + "--");
            System.out.print(m.getReturnType().getName());
            Class[] ccs = m.getParameterTypes();
            for (Class cc : ccs) {
                System.out.print("--" + cc.getName());
            }
            System.out.println();
        }
    }

    @Test
    public void test5() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class c = Food.class;
        Field[] fields = c.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        System.out.println("-----------------");
        Field[] fields2 = c.getDeclaredFields();
        for (Field field : fields2) {
            System.out.println(field.getName());
        }
        Field field = c.getDeclaredField("name");
        field.setAccessible(true);
        System.out.println(field.get(new Food("")));

        Method method = c.getDeclaredMethod("privateHello");
        method.setAccessible(true);
        method.invoke(new Food(""));
    }

    @Test
    public void test6() {
        Class c = Food.class;
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof HelloWorld) {
                HelloWorld helloWorld = (HelloWorld) annotation;
                System.out.println(helloWorld.value());
            }
        }

        Annotation annotation = c.getAnnotation(HelloWorld.class);

    }

    @Test
    public void test7() throws NoSuchMethodException {
        Class c = Food.class;
        Method method = c.getMethod("hello", String.class);
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof HelloWorld) {
                HelloWorld helloWorld = (HelloWorld) annotation;
                System.out.println(helloWorld.value());
            }
        }

    }

    @Test
    public void test8() throws NoSuchMethodException {
        Class c = Food.class;
        Method method = c.getMethod("hello", String.class);
        Annotation[][] annotationArr = method.getParameterAnnotations();
        Class[] cc = method.getParameterTypes();
        int i=0;
        for (Annotation[] annotations : annotationArr) {
            Class c1 = cc[i++];
            for (Annotation annotation : annotations) {
                if (annotation instanceof HelloWorld) {
                    HelloWorld helloWorld = (HelloWorld) annotation;
                    System.out.print(c1.getName() + "----");
                    System.out.println(helloWorld.value());
                }
            }
        }
    }

    @Test
    public void test9() throws NoSuchFieldException {
        Class c = Food.class;
        Field field = c.getField("id");
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof HelloWorld) {
                HelloWorld helloWorld = (HelloWorld) annotation;
                System.out.println(helloWorld.value());
            }
        }
    }

}
