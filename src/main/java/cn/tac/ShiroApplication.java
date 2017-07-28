package cn.tac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : tac
 * @date : 26/07/2017
 */

@SpringBootApplication
public class ShiroApplication {
    private static Logger logger = LoggerFactory.getLogger(ShiroApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
        logger.info("application running");
    }
}
