package com.springBoot.beer;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domain.ColorBean;
import domain.ColorExpertBean;

public class ColorBeanTest {

	private ColorBean colorBeanService;

    @BeforeEach
    void before() {
        colorBeanService = new ColorExpertBean();
    }

    @Test
    void test() {
    	assertIterableEquals(List.of("light", "brown", "dark"), colorBeanService.getColorsList());
    }
}
