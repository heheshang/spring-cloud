package com.example.vue.config;

import com.example.vue.VueDemoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by kaenry on 2016/5/21.
 * for war, run server error if not
 * ServletInitializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VueDemoApplication.class);
    }
}
