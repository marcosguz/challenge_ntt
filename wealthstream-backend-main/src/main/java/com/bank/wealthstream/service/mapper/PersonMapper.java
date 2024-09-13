package com.bank.wealthstream.service.mapper;

import com.bank.wealthstream.model.Person;
import com.bank.wealthstream.service.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDto personToPersonDto(Person person);
    Person personDtoToPerson(PersonDto personDto);
}
