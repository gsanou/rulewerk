package org.semanticweb.rulewerk.core.model.api;

import org.semanticweb.rulewerk.core.model.implementation.Serializer;

/*-
 * #%L
 * Rulewerk Core Components
 * %%
 * Copyright (C) 2018 - 2020 Rulewerk Developers
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
 * A declaration for an external data source, which assigns a predicate to a
 * source.
 * 
 * @author Markus Kroetzsch
 *
 */
public interface DataSourceDeclaration extends Statement, Entity {

	/**
	 * Returns the {@link Predicate} that this source applies to.
	 * 
	 * @return predicate into which data from the given source is loaded
	 */
	Predicate getPredicate();

	/**
	 * Returns the {@link DataSource} that the data is loaded from.
	 * 
	 * @return data source specification
	 */
	DataSource getDataSource();

	@Override
	default String getSyntacticRepresentation() {
		return Serializer.getString(this);
	}
}
