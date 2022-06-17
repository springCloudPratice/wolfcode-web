package cn.wolfcode.web.commons.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import link.ahsj.core.constants.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/6/10 23:07
 * @mail chenshiyun2011@163.com
 */
@Configuration
public class JacksonConfig {

    @Autowired
    public void configureFreemarker(freemarker.template.Configuration configuration) {
        configuration.setObjectWrapper (new DefaultObjectWrapper (freemarker.template.Configuration.VERSION_2_3_28) {

            @Override
            public TemplateModel wrap(Object object) throws TemplateModelException {
                if (object instanceof LocalDate) {
                    return new SimpleDate (Date.valueOf ((LocalDate) object));
                }
                if (object instanceof LocalTime) {
                    return new SimpleDate (Time.valueOf ((LocalTime) object));
                }
                if (object instanceof LocalDateTime) {
                    return new SimpleDate (Timestamp.valueOf ((LocalDateTime) object));
                }
                return super.wrap (object);
            }

        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper ( );
        objectMapper.setDateFormat (new SimpleDateFormat (TimeFormat.DEFAULT_DATE_TIME_FORMAT));
        objectMapper.disable (SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable (DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        //反序列化时忽略json中多余的字段
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule ( );
        javaTimeModule.addSerializer (LocalDateTime.class, new LocalDateTimeSerializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer (LocalDate.class, new LocalDateSerializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer (LocalTime.class, new LocalTimeSerializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer (LocalDateTime.class, new LocalDateTimeDeserializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer (LocalDate.class, new LocalDateDeserializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer (LocalTime.class, new LocalTimeDeserializer (DateTimeFormatter.ofPattern (TimeFormat.DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule (javaTimeModule).registerModule (new ParameterNamesModule ( ));
        return objectMapper;
    }

}
