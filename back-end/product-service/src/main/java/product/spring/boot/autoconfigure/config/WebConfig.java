package product.spring.boot.autoconfigure.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> arg0) {
		

	}

	@Override
	public void addFormatters(final FormatterRegistry arg0) {
		

	}

	@Override
	public void addInterceptors(final InterceptorRegistry arg0) {
		

	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry arg0) {
		

	}

	@Override
	public void addReturnValueHandlers(final List<HandlerMethodReturnValueHandler> arg0) {
		

	}

	@Override
	public void addViewControllers(final ViewControllerRegistry arg0) {
		

	}

	@Override
	public void configureAsyncSupport(final AsyncSupportConfigurer arg0) {
		

	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer arg0) {
		

	}

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer arg0) {
		

	}

	@Override
	public void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> arg0) {
		

	}

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> arg0) {
		

	}

	@Override
	public void configurePathMatch(final PathMatchConfigurer arg0) {
		

	}

	@Override
	public void configureViewResolvers(final ViewResolverRegistry arg0) {
		

	}

	@Override
	public void extendHandlerExceptionResolvers(final List<HandlerExceptionResolver> arg0) {
		

	}

	@Override
	public void extendMessageConverters(final List<HttpMessageConverter<?>> arg0) {
		

	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		
		return null;
	}

	@Override
	public Validator getValidator() {
		
		return null;
	}

}
