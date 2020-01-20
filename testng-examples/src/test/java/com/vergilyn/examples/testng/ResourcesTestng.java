package com.vergilyn.examples.testng;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

/**
 * @author VergiLyn
 * @date 2020-01-16
 */
@Slf4j
public class ResourcesTestng {

    /**
     * 读取`src/test/resources`下的文件
     */
    @Test
    public void readResources(){
        try(InputStream in = this.getClass().getResourceAsStream("/data.json")) {
            log.info("11111111111");
            String data = IOUtils.toString(in, Charset.defaultCharset());
            System.out.println(data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
