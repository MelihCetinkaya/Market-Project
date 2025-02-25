package MarketProject.backend.api.DevelopingApi;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Conf implements WebMvcConfigurer {


        @Bean
        public ThreadPoolTaskExecutor mvcTaskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2); // Minimum iş parçacığı sayısı
            executor.setMaxPoolSize(3); // Maksimum iş parçacığı sayısı
            executor.setQueueCapacity(0); // Kuyruktaki görev sayısı
            executor.setThreadNamePrefix("MvcAsyncExecutor-");
            executor.initialize();
            return executor;
        }

        @Override
        public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
            configurer.setTaskExecutor(mvcTaskExecutor()); // Özel executor atama
            configurer.setDefaultTimeout(30000);
        }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}


