package com.lnho.aaplan.commons.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.IOException;

/**
 * User: littlehui
 * Date: 14-6-12
 * Time: 下午4:24
 */
public class MappingJacksonJsonpHttpMessageConverter extends MappingJacksonHttpMessageConverter {
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator = this.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            //String jsonPadding = "callback";
            if (object instanceof JSONPObject) {
                //jsonGenerator.writeRaw(jsonPadding);
                jsonGenerator.writeRaw("");
                String fuction = ((JSONPObject) object).getFunction();
                Object value = ((JSONPObject) object).getValue();
                String ob = fuction + "(" + value + ")";
                jsonGenerator.writeObject(fuction + "(" + value + ")");
                //this.getObjectMapper().writeValueAsString(fuction + "(" + value + ")");
                //this.getObjectMapper().writeValue(jsonGenerator, object);
                jsonGenerator.writeRaw("");
                jsonGenerator.flush();
            }
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON:"+ex.getMessage(), ex);
        }
    }
}


