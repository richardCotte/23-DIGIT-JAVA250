package com.example.demo.entity;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class ClientTest {

    @Test
    public void getAge() {
        //Given
        Client client = new Client();
        LocalDate date = LocalDate.parse("2000-05-25");
        client.setDateNaissance(date);
        //when
        LocalDate now = LocalDate.parse("2023-01-25");
        int age = client.getAge(now).intValue();
        //then
        Assert.assertEquals(22, age);
    }
}