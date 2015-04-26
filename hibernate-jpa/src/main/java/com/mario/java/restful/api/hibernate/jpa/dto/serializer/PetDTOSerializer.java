package com.mario.java.restful.api.hibernate.jpa.dto.serializer;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.property.PetDTOProperty;

public class PetDTOSerializer extends JsonSerializer<PetDTO> {

	private static final Logger LOGGER = Logger.getLogger(PetDTOSerializer.class.getName());

	private static final String UNKOWN_PROPERTY = "unkown property";

	@Override
	public void serialize(PetDTO pet, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		LOGGER.info("serialize(pet=?, jgen=?, provider=?)");

		jgen.writeStartObject();
		this.writeAttributesWrapper(pet, jgen);
		jgen.writeEndObject();
	}

	private void writeAttributesWrapper(PetDTO pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeAttributes(pet=?, jgen=?)");

		if(pet.getPropertiesToBeDisplayed() != null && !pet.getPropertiesToBeDisplayed().isEmpty()){
			this.writeCustomAttributes(pet, jgen);
		} else {
			this.writeDefaultAttributes(pet, jgen);
		}
	}

	private void writeCustomAttributes(PetDTO pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeCustomAttributes(pet=?, jgen=?)");

		for(String propertyName : pet.getPropertiesToBeDisplayed()){
			if(propertyName.equals(PetDTOProperty.ID.getName())){
				jgen.writeNumberField(PetDTOProperty.ID.getName(), pet.getId());
			} else if(propertyName.equals(PetDTOProperty.CREATED_AT.getName())){
				jgen.writeObjectField(PetDTOProperty.CREATED_AT.getName(), pet.getCreatedAt());
			} else if(propertyName.equals(PetDTOProperty.UPDATED_AT.getName())){
				jgen.writeObjectField(PetDTOProperty.UPDATED_AT.getName(), pet.getUpdatedAt());
			} else if(propertyName.equals(PetDTOProperty.NAME.getName())){
				jgen.writeStringField(PetDTOProperty.NAME.getName(), pet.getName());
			}else if(propertyName.equals(PetDTOProperty.AGE.getName())){
				jgen.writeNumberField(PetDTOProperty.AGE.getName(), pet.getAge());
			} else if(propertyName.equals(PetDTOProperty.USER_ID.getName())){
				jgen.writeNumberField(PetDTOProperty.USER_ID.getName(), pet.getUserId());
			} else {
				jgen.writeStringField(propertyName, UNKOWN_PROPERTY);
			}
		}
	}

	private void writeDefaultAttributes(PetDTO pet, JsonGenerator jgen) throws IOException {
		LOGGER.info("writeDefaultAttributes(pet=?, jgen=?)");

		jgen.writeNumberField(PetDTOProperty.ID.getName(), pet.getId());
		jgen.writeObjectField(PetDTOProperty.CREATED_AT.getName(), pet.getCreatedAt());
		jgen.writeObjectField(PetDTOProperty.UPDATED_AT.getName(), pet.getUpdatedAt());
		jgen.writeStringField(PetDTOProperty.NAME.getName(), pet.getName());
		jgen.writeNumberField(PetDTOProperty.AGE.getName(), pet.getAge());
		jgen.writeNumberField(PetDTOProperty.USER_ID.getName(), pet.getUserId());
	}
}