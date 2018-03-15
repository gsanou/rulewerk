package org.semanticweb.vlog4j.core.model.impl;

import org.semanticweb.vlog4j.core.model.api.TermType;
import org.semanticweb.vlog4j.core.model.api.Variable;
import org.semanticweb.vlog4j.core.model.validation.IllegalEntityNameException;

/*
 * #%L
 * VLog4j Core Components
 * %%
 * Copyright (C) 2018 VLog4j Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Implements {@link #VARIABLE} terms. A variable is a parameter that stands for an arbitrary domain element.
 *
 * @author david.carral@tu-dresden.de
 */
public class VariableImpl extends AbstractTerm implements Variable {

	/**
	 * Instantiates a <b>{@code VariableImpl}</b> object with the name <b>{@code name}</b>.
	 *
	 * @param name
	 *            cannot be a blank String (null, " ", empty string...).
	 * @throws IllegalEntityNameException
	 *             if the given name <b>{@code name}</b> is a blank String.
	 */
	public VariableImpl(final String name) throws IllegalEntityNameException {
		super(name);
	}

	/**
	 * Deep copy constructor (the newly instantiated object does not contain any reference to original fields in the copied object).
	 */
	public VariableImpl(final Variable copyVariable) throws IllegalEntityNameException {
		super(new String(copyVariable.getName()));
	}

	@Override
	public TermType getType() {
		return TermType.VARIABLE;
	}
}