package com.mario.java.restful.api.hibernate.jpa.domain.serializer;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.property.UserProperty;

public class UserDomainSerializer extends JsonSerializer<UserDomain> {

	private static final Logger LOGGER = Logger.getLogger(UserDomainSerializer.class.getName());

	private static final String UNKOWN_PROPERTY = "unkown property";

	@Override
	public void serialize(UserDomain user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		LOGGER.info("serialize(user=?, jgen=?, provider=?)");

		jgen.writeStartObject();
		this.writeAttributesWrapper(user, jgen);
		jgen.writeEndObject();
	}

	private void writeAttributesWrapper(UserDomain user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeAttributes(user=?, jgen=?)");

		if(user.getPropertiesToBeDisplayed() != null && !user.getPropertiesToBeDisplayed().isEmpty()){
			this.writeCustomAttributes(user, jgen);
		} else {
			this.writeDefaultAttributes(user, jgen);
		}
	}

	private void writeCustomAttributes(UserDomain user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeCustomAttributes(user=?, jgen=?)");

		for(String propertyName : user.getPropertiesToBeDisplayed()){
			if(propertyName.equals(UserProperty.ID.getName())){
				jgen.writeNumberField(UserProperty.ID.getName(), user.getId());
			} else if(propertyName.equals(UserProperty.CREATED_AT.getName())){
				jgen.writeObjectField(UserProperty.CREATED_AT.getName(), user.getCreatedAt());
			} else if(propertyName.equals(UserProperty.UPDATED_AT.getName())){
				jgen.writeObjectField(UserProperty.UPDATED_AT.getName(), user.getUpdatedAt());
			} else if(propertyName.equals(UserProperty.NAME.getName())){
				jgen.writeStringField(UserProperty.NAME.getName(), user.getName());
			} else {
				jgen.writeStringField(propertyName, UNKOWN_PROPERTY);
			}
		}
	}

	private void writeDefaultAttributes(UserDomain user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeDefaultAttributes(user=?, jgen=?)");

		jgen.writeNumberField(UserProperty.ID.getName(), user.getId());
		jgen.writeObjectField(UserProperty.CREATED_AT.getName(), user.getCreatedAt());
		jgen.writeObjectField(UserProperty.UPDATED_AT.getName(), user.getUpdatedAt());
		jgen.writeStringField(UserProperty.NAME.getName(), user.getName());
	}
}