package com.siri.retail_management_system.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author SiriYang
 * @title: RandUtil
 * @projectName siriblog
 * @description: TODO
 * @date 2019/4/10 9:53
 */
@Component
public class RandUtil {

    public static int getIntFromOne(int bound){
        Random rand = new Random();
        return rand.nextInt(bound)%bound+1;
    }
}
