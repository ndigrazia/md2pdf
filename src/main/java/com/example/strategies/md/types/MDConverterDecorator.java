package com.example.strategies.md.types;

import java.io.IOException;

public abstract class MDConverterDecorator implements MDConverterItf {

    private final MDConverterItf decoratedMDConverter;

    public MDConverterDecorator(MDConverterItf c) {
        this.decoratedMDConverter = c;
    }

    public String convert(String source) throws IOException {
        return decoratedMDConverter.convert(source);
    }
    
}
