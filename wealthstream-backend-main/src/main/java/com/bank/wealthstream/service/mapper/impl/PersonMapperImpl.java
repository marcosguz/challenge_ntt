package com.bank.wealthstream.service.mapper.impl;

import com.bank.wealthstream.model.Person;
import com.bank.wealthstream.service.dto.PersonDto;
import com.bank.wealthstream.service.mapper.PersonMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {
    @Override
    public PersonDto personToPersonDto(Person person) {
        return new PersonDto(
                person.getIdPer(),
                person.getName(),
                person.getSurname(),
                person.getGender(),
                person.getAge(),
                person.getIdentification(),
                person.getAddress(),
                person.getPhone()
        );
    }

    @Override
    public Person personDtoToPerson(PersonDto personDto) {
        return new Person(
                personDto.getIdPer(),
                personDto.getName(),
                personDto.getSurname(),
                personDto.getGender(),
                personDto.getAge(),
                personDto.getIdentification(),
                personDto.getAddress(),
                personDto.getPhone()
        );
    }
}
