package com.gendata.faces.validation.core;

import java.util.List;

import com.gendata.faces.validation.core.strategy.AbstractConstraintStrategy;

public abstract interface AbstractValidator {

   List<ValidationMessage> accept(AbstractConstraintStrategy constraintStrategy) throws ValidationException;
}
