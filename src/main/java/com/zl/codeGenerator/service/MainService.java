package com.zl.codeGenerator.service;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/9.
 */
public interface MainService {
    /**
     * 生成代码
     */
    void generate() throws Exception;

    /**
     * 编译代码并加载
     */
    void compileAndLoad();
}
