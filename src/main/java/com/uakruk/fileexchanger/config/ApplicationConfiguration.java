package com.uakruk.fileexchanger.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */

@Configuration
@ComponentScan({"com.uakruk.fileexchanger.*"})
@PropertySource({"classpath:/application.properties"})
public class ApplicationConfiguration {


}
