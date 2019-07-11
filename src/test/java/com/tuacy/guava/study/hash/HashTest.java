package com.tuacy.guava.study.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import org.junit.Test;

/**
 * @name: HashTest
 * @author: tuacy.
 * @date: 2019/7/11.
 * @version: 1.0
 * @Description:
 */
public class HashTest {


    @Test
    public void hashTest() {
        // 各种算法对应的hash code
        String input = "hello, world";
        // MD5
        System.out.println(Hashing.md5().hashBytes(input.getBytes()).toString());
        // sha256
        System.out.println(Hashing.sha256().hashBytes(input.getBytes()).toString());
        // sha512
        System.out.println(Hashing.sha512().hashBytes(input.getBytes()).toString());
        // crc32
        System.out.println(Hashing.crc32().hashBytes(input.getBytes()).toString());
        // MD5
        System.out.println(Hashing.md5().hashUnencodedChars(input).toString());



        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher()
                .putLong(10)
                .putString("abc", Charsets.UTF_8)
                .hash();
        System.out.println(hc.toString());



        HashFunction hf1 = Hashing.md5();
        HashCode hc1 = hf.newHasher()
                .putString("abc", Charsets.UTF_8)
                .putLong(10)
                .hash();
        System.out.println(hc1.toString());
    }

    @Test
    public void intToHashCode() {
        // 把一个int转换为HashCode
        System.out.println(HashCode.fromInt(10).toString());
    }

    @Test
    public void objectToHashCode() {

        // 需要hash的对象
        Person person = new Person(27, "wu", "xing", 1990);

        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person person, PrimitiveSink into) {
                into
                        .putInt(person.id)
                        .putString(person.firstName, Charsets.UTF_8)
                        .putString(person.lastName, Charsets.UTF_8)
                        .putInt(person.birthYear);
            }
        };

        // md5算法
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher()
                .putString("abc", Charsets.UTF_8)
                .putObject(person, personFunnel)
                .hash();
        System.out.println(hc.toString());
    }

    class Person {
        int id;
        String firstName;
        String lastName;
        int birthYear;

        public Person(int id, String firstName, String lastName, int birthYear) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthYear = birthYear;
        }
    }

}
