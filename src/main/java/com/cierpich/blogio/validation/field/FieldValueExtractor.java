package com.cierpich.blogio.validation.field;

@FunctionalInterface
public interface FieldValueExtractor<T> {
    T extract();
}
