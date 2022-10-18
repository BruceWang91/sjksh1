package datart.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter  {

    @Value("${spring.upload}")
    private String localurl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //Windows下
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+localurl);
        //Mac或Linux下(没有CDEF盘符)
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/uploads/");
        super.addResourceHandlers(registry);
    }
}
