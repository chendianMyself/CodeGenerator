package com.zl.codeGenerator;

import com.zl.codeGenerator.service.MainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/12/9.
 */
public class Generator {

    /**
     * spring配置文件
     * @return
     */
    private static String[] getConfigLocations() {
        return new String[]{
                "applicationContext-codeGenerator.xml"};
    }

    /**
     * 获取MainService对象
     * @return
     */
    public static MainService setUp() {
        ApplicationContext atx = new ClassPathXmlApplicationContext(
                getConfigLocations());
        return (MainService) atx.getBean("mainService");
    }
    public static void main(String[] args){
        MainService mainService = setUp();
        try {
            mainService.generate();
//            mainService.compileAndLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
