package com.mario.java.restful.api.hibernate.jpa.dto.serializer;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mario.java.restful.api.hibernate.jpa.dto.property.UserDTOProperty;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;

public class UserDTOSerializer extends JsonSerializer<UserEntity> {

	private static final Logger LOGGER = Logger.getLogger(UserDTOSerializer.class.getName());

	private static final String UNKOWN_PROPERTY = "unkown property";

	@Override
	public void serialize(UserEntity user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		LOGGER.info("serialize(user=?, jgen=?, provider=?)");

		jgen.writeStartObject();
		this.writeAttributesWrapper(user, jgen);
		jgen.writeEndObject();
	}

	private void writeAttributesWrapper(UserEntity user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeAttributes(user=?, jgen=?)");

		if(user.getPropertiesToBeDisplayed() != null && !user.getPropertiesToBeDisplayed().isEmpty()){
			this.writeCustomAttributes(user, jgen);
		} else {
			this.writeDefaultAttributes(user, jgen);
		}
	}

	private void writeCustomAttributes(UserEntity user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeCustomAttributes(user=?, jgen=?)");

		for(String propertyName : user.getPropertiesToBeDisplayed()){
			if(propertyName.equals(UserDTOProperty.ID.getName())){
				jgen.writeNumberField(UserDTOProperty.ID.getName(), user.getId());
			} else if(propertyName.equals(UserDTOProperty.CREATED_AT.getName())){
				jgen.writeObjectField(UserDTOProperty.CREATED_AT.getName(), user.getCreatedAt());
			} else if(propertyName.equals(UserDTOProperty.UPDATED_AT.getName())){
				jgen.writeObjectField(UserDTOProperty.UPDATED_AT.getName(), user.getUpdatedAt());
			} else if(propertyName.equals(UserDTOProperty.NAME.getName())){
				jgen.writeStringField(UserDTOProperty.NAME.getName(), user.getName());
			} else {
				jgen.writeStringField(propertyName, UNKOWN_PROPERTY);
			}
		}
	}

	private void writeDefaultAttributes(UserEntity user, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeDefaultAttributes(user=?, jgen=?)");

		jgen.writeNumberField(UserDTOProperty.ID.getName(), user.getId());
		jgen.writeObjectField(UserDTOProperty.CREATED_AT.getName(), user.getCreatedAt());
		jgen.writeObjectField(UserDTOProperty.UPDATED_AT.getName(), user.getUpdatedAt());
		jgen.writeStringField(UserDTOProperty.NAME.getName(), user.getName());
	}
}