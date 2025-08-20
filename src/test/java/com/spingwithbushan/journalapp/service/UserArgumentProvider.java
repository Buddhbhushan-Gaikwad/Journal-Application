package com.spingwithbushan.journalapp.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.boot.test.context.SpringBootTest;
import com.spingwithbushan.journalapp.entity.User;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(User.builder().userName("hariom").password("hariom").build()),
                Arguments.of(User.builder().userName("Kiran").password("Kiran").build()));
    }
}
