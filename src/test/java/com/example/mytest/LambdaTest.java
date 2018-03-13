package com.example.mytest;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class LambdaTest {

    public static void main(String[] args) {

        String[] str = new String[] { "Lambdas", "Lambdas", "Default Method", "Stream API", "Date and Time API" };
        List<String> strList = Arrays.asList(str);

        System.out.println("---------------------------->    默认遍历");
        strList.stream().forEach(item -> {
            System.out.println(item);
        });
        System.out.println("---------------------------->    默认遍历简化写法");
        strList.stream().forEach(System.out::println);

        // limit输出指定个数
        System.out.println("limit   ---------------------------->");
        strList.stream().limit(2).forEach(System.out::println);

        // 去掉重复数据
        System.out.println("distinct    ---------------------------->");
        strList.stream().distinct().forEach(System.out::println);

        // filter过滤器，筛选出符合条件的值
        System.out.println("filter  ---------------------------->");
        Predicate<String> contain = item -> item.contains("API");// 只是用于匹配条件的如int可以用条件运算符等
        strList.stream().filter(contain).forEach(System.out::println);
        System.out.println("filter简化写法  ---------------------------->");
        strList.stream().filter(item -> item.contains("API")).forEach(System.out::println);

        System.out.println("AND ---------------------------->");
        Predicate<String> contain1 = item -> item.contains("API");
        Predicate<String> contain2 = item -> item.contains("Time");
        strList.stream().filter(contain1.and(contain2)).forEach(System.out::println);
        System.out.println("OR  ---------------------------->");
        strList.stream().filter(contain1.or(contain2)).forEach(System.out::println);

        // 向每个字符后追加
        System.out.println("map ---------------------------->");
        // 对Stream中包含的元素使用给定的转换函数进行转换操作，生成的Stream只包含转换生成的元素。
        // mapToInt，mapToLong和mapToDouble是对int、long、double进行操作的
        strList.stream().map(item -> item + String.valueOf(1)).forEach(System.out::println);

        // 向每个字符后追加
        System.out.println("flatMap ---------------------------->");
        // flatMap：和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合
        strList.stream().flatMap(item -> getCharacter(item)).forEach(System.out::println);

        System.out.println("peek    ---------------------------->");
        // peek 需调用collect
        strList.stream().map(String::toUpperCase).peek(System.out::println).collect(Collectors.toList());

        System.out.println("skip    ---------------------------->");
        // 丢弃原Stream的前N个元素后剩下元素组成的新Stream
        strList.stream().skip(3).forEach(System.out::println);

        // 统计个数
        System.out.println("count   ---------------------------->" + strList.stream().count());

        // allMatch：是不是Stream中的所有元素都满足给定的匹配条件
        boolean allMatch1 = strList.stream().allMatch(item -> item.contains("a"));
        System.out.println("allMatch    -------------->  " + allMatch1);
        boolean allMatch2 = strList.stream().allMatch(item -> item.contains("API"));
        System.out.println("allMatch    -------------->  " + allMatch2);

        // anyMatch：Stream中是否存在任何一个元素满足匹配条件
        boolean anyMatch1 = strList.stream().anyMatch(item -> item.contains("Stream API"));
        System.out.println("anyMatch    -------------->  " + anyMatch1);
        boolean anyMatch2 = strList.stream().anyMatch(item -> item.contains("Stream API1"));
        System.out.println("anyMatch    -------------->  " + anyMatch2);

        // findFirst: 返回Stream中的第一个元素，如果Stream为空，返回空Optional
        Optional<String> findFirst = strList.stream().findFirst();
        // isPresent方法用来检查Optional实例是否有值。
        if (findFirst.isPresent()) {
            // 调用get()返回Optional值。如果Optional没有值调和则抛出NoSuchElementException。
            System.out.println("findFirst   -------------->  " + findFirst.get());
        }
        System.out.print("findFirst简化写法 -------------->  ");
        // 如果存在值，则使用该值调用，否则不执行任何操作。
        strList.stream().findFirst().ifPresent(System.out::println);

        // noneMatch：是不是Stream中的所有元素都不满足给定的匹配条件
        boolean noneMatch1 = strList.stream().noneMatch(item -> item.contains("Stream API"));
        System.out.println("noneMatch   -------------->  " + noneMatch1);
        boolean noneMatch2 = strList.stream().noneMatch(item -> item.contains("zzzzz"));
        System.out.println("noneMatch   -------------->  " + noneMatch2);

        System.out.println("newStrList  ---------------------------->");
        List<String> newStrList = strList.stream().filter(item -> item != null)
                .collect(() -> new ArrayList<String>(), (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
        newStrList.stream().forEach(System.out::println);
        System.out.println("newStrList简化写法  ---------------------------->");
        List<String> newStrList1 = strList.stream().filter(item -> item != null).collect(Collectors.toList());
        newStrList1.stream().forEach(System.out::println);

        System.out.println("sorted 排序---------------------------->");
        // strList.stream().sorted();
        strList.stream().sorted(Comparator.comparing(String::length)).forEach(System.out::println);
        ;

        // max和min：使用给定的比较器（Operator），返回Stream中的最大|最小值
        Integer[] ints = new Integer[] { 7, 2, 3, 10, 5, 1, 6, 8, 9, 4 };
        List<Integer> intList = new ArrayList<Integer>();
        intList = Arrays.asList(ints);
        System.out.print("max   -------------->  ");
        intList.stream().max((o1, o2) -> o1.compareTo(o2)).ifPresent(System.out::println);
        System.out.print("max简化写法   -------------->  ");
        // Comparable<Integer> Integer.compare(int arg0, int arg1)
        intList.stream().max(Integer::compare).ifPresent(System.out::println);
        System.out.print("min   -------------->  ");
        intList.stream().min((o1, o2) -> o1.compareTo(o2)).ifPresent(System.out::println);
        System.out.print("min简化写法   -------------->  ");
        // Comparable<Integer> Integer.compare(int arg0, int arg1)
        intList.stream().min(Integer::compare).ifPresent(System.out::println);

        System.out.println("reduce单参数   ---------------------------->");
        System.out.println(intList.stream().reduce((result, element) -> result = result + element));
        System.out.println("reduce双参数   ---------------------------->");
        System.out.println(intList.stream().reduce(0, (result, element) -> result = result + element));

        System.out.println("generate    ---------------------------->");
        // 生成一个无限长度的Stream，其中值是随机的。这个无限长度Stream是懒加载，一般这种无限长度的Stream都会配合Stream的limit()方法来用。
        Stream.generate(Math::random).limit(2).forEach(System.out::println);

        System.out.println("iterate ---------------------------->");
        // 也是生成无限长度的Stream，和generator不同的是，其元素的生成是重复对给定的种子值,调用用户指定函数来生成的
        Stream.iterate(12, item -> item + 1).limit(2).forEach(System.out::println);
    }

    public static Stream<String> getCharacter(String s) {
        Builder<String> builder = Stream.builder();
        builder.add(s);
        builder.accept("1");
        return builder.build();
    }
}
