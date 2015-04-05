package com.mario.java.restful.api.hibernate.jpa.domain.serializer;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.property.PetProperty;

public class PetDomainSerializer extends JsonSerializer<PetDomain> {

	private static final Logger LOGGER = Logger.getLogger(PetDomainSerializer.class.getName());

	private static final String UNKOWN_PROPERTY = "unkown property";

	@Override
	public void serialize(PetDomain pet, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		LOGGER.info("serialize(pet=?, jgen=?, provider=?)");

		jgen.writeStartObject();
		this.writeAttributesWrapper(pet, jgen);
		jgen.writeEndObject();
	}

	private void writeAttributesWrapper(PetDomain pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeAttributes(pet=?, jgen=?)");

		if(pet.getPropertiesToBeDisplayed() != null && !pet.getPropertiesToBeDisplayed().isEmpty()){
			this.writeCustomAttributes(pet, jgen);
		} else {
			this.writeDefaultAttributes(pet, jgen);
		}
	}

	private void writeCustomAttributes(PetDomain pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeCustomAttributes(pet=?, jgen=?)");

		for(String propertyName : pet.getPropertiesToBeDisplayed()){
			if(propertyName.equals(PetProperty.ID.getName())){
				jgen.writeNumberField(PetProperty.ID.getName(), pet.getId());
			} else if(propertyName.equals(PetProperty.CREATED_AT.getName())){
				jgen.writeObjectField(PetProperty.CREATED_AT.getName(), pet.getCreatedAt());
			} else if(propertyName.equals(PetProperty.UPDATED_AT.getName())){
				jgen.writeObjectField(PetProperty.UPDATED_AT.getName(), pet.getUpdatedAt());
			} else if(propertyName.equals(PetProperty.NAME.getName())){
				jgen.writeStringField(PetProperty.NAME.getName(), pet.getName());
			}else if(propertyName.equals(PetProperty.AGE.getName())){
				jgen.writeNumberField(PetProperty.AGE.getName(), pet.getAge());
			} else if(propertyName.equals(PetProperty.USER_ID.getName())){
				jgen.writeNumberField(PetProperty.USER_ID.getName(), pet.getUserId());
			} else {
				jgen.writeStringField(propertyName, UNKOWN_PROPERTY);
			}
		}
	}

	private void writeDefaultAttributes(PetDomain pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeDefaultAttributes(pet=?, jgen=?)");

		jgen.writeNumberField(PetProperty.ID.getName(), pet.getId());
		jgen.writeObjectField(PetProperty.CREATED_AT.getName(), pet.getCreatedAt());
		jgen.writeObjectField(PetProperty.UPDATED_AT.getName(), pet.getUpdatedAt());
		jgen.writeStringField(PetProperty.NAME.getName(), pet.getName());
		jgen.writeNumberField(PetProperty.AGE.getName(), pet.getAge());
		jgen.writeNumberField(PetProperty.USER_ID.getName(), pet.getUserId());
	}
}