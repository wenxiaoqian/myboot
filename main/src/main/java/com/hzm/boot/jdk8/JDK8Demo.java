package com.hzm.boot.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiaoqian.wen
 * @create 2016-12-21 14:53
 **/
public class JDK8Demo {

    public static void main(String[] args) {
        Arrays.asList("aa", "bb", "cc", "dd", "ee").forEach(e -> {
            System.out.println(e);
        });

        Thread thread = new Thread(() -> {
            List<String> list = new ArrayList<String>();
            list.add("a");
            list.add("b");
            list.add("c");
            list.add("aa");
            list.add("bb");
            list.add("cc");

            list.stream().map(String::toUpperCase).forEach(e -> {
                System.out.println(e);
            });

            list.stream().filter(s -> s.length() == 2).forEach(s -> {
                System.out.println(s);
            });
        });
        thread.start();

        distinct("1","2","6","3","5","8","9","3","512","45","46","987","11");
        //平方数
        List<Integer> nums = Arrays.asList(1,2,3,4);
        List<Integer> squareNums = nums.stream()
                .map(n -> n*n).collect(Collectors.toList());
        squareNums.forEach(e -> {
            System.out.println(e);
        });


        Stream<List<Integer>> stream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2,3),
                Arrays.asList(4,5,6)
        );
        Stream<Integer> output = stream.flatMap((childList) -> childList.stream());
        output.forEach(e -> {
            System.out.println(e);
        });

        //filter
        Integer[] sixNums = {1,2,3,4,5,6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n%2==0).toArray(Integer[]::new);
        System.out.println(evens.toString());

        String aa = Arrays.asList("aa", "bb", "cc", "dd", "ee")
                .stream().filter(e -> "aa".equals(e)).findFirst().get();
        System.out.println(aa);

        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);

        int sum = Stream.of(1,2,3,4).reduce(0, Integer::sum);
        System.out.println("sum:"+sum);

        sum = Stream.of(1,3,4,5,6).reduce(Integer::sum).get();
        System.out.println(sum);

        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }

        List<String> unameList = persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(unameList);

        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).limit(20).collect(Collectors.toList());
        personList2.stream().forEach(person -> {
            System.out.println(person.getName());
        });
    }

    private static void distinct(String... args){
        List<String> list = Arrays.asList(args);

        List<Integer> listInt = list.stream()
                .map(s -> Integer.parseInt(s))
                .filter(a -> isPrime(a))
                .distinct()
                .collect(Collectors.toList());

        listInt.stream().forEach(e -> {
            System.out.println(e);
        });

        int max = listInt.stream().reduce((a,b) -> a>b ? a:b).get();
        System.out.println("max:" + max);

        int min = listInt.stream().reduce((a,b) -> a<b ? a:b).get();
        System.out.println("min:" + min);

        int sum = listInt.stream().reduce((a,b) -> a+b).get();
        System.out.println("sum:" + sum);


    }

    private static class Person {
        public int no;
        private String name;
        public Person (int no, String name) {
            this.no = no;
            this.name = name;
        }
        public String getName() {
            System.out.println(name);
            return name;
        }
    }


    private static boolean isPrime(int a) {
        boolean flag = true;
        for (int i = 2; i <= a / 2; i++) {
            if (a % i == 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
