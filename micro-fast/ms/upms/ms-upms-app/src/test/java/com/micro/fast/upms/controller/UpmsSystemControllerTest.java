package com.micro.fast.upms.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpmsSystemControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    /**
     * 每个单元测试前构建mvc环境
     */
    @Before
    public void init(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试添加系统
     * @throws Exception
     */
    @Test
    public void addSystem() throws Exception {
        //前台传入的是长整形
        Date date = new Date();
        String content = "{'name':'tom','password':'','birthday':'" + date.getTime() + "'}";
        try {
            String tom = mvc.perform(MockMvcRequestBuilders.post("/user")
                    .contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tom"))
                    .andReturn().getResponse().getContentAsString();
            //后台返回的日期格式也是长整形
            System.out.println(tom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSystemById() throws Exception {

    }

    @Test
    public void getSystemByCondition() throws Exception {

    }

    @Test
    public void updateSystem() throws Exception {
    }

    @Test
    public void deleteSystem() throws Exception {
    }

}