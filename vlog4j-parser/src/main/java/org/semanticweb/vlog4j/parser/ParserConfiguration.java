package org.semanticweb.vlog4j.parser;

import java.util.HashMap;

import org.semanticweb.vlog4j.core.model.api.DataSource;
import org.semanticweb.vlog4j.parser.javacc.ParseException;

/*-
 * #%L
 * vlog4j-parser
 * %%
 * Copyright (C) 2018 - 2019 VLog4j Developers
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
 * Class to keep parser configuration.
 *
 * @author Maximilian Marx
 */
public class ParserConfiguration {
    /**
     * Register a new Data Source.
     *
     * @param name
     *        Name of the data source, as it appears in the declaring directive.
     *
     * @param handler
     *        Handler for parsing a data source declaration.
     *
     * @throws IllegalArgumentException if the provided name is already registered.
     * @return this
     */
    public ParserConfiguration registerDataSource(String name, DataSourceDeclarationHandler handler) throws IllegalArgumentException {
        if (dataSources.containsKey(name)) {
            throw new IllegalArgumentException("Data source \"" + name + "\" is already registered.");
        }

        this.dataSources.put(name, handler);
        return this;
    }

    /**
     * Parse a Data Source declaration.
     *
     * @param name
     *        Name of the data source.
     *
     * @param args
     *        arguments given in the data source declaration.
     *
     * @throws ParsingException when the declaration is invalid, e.g., if the Data Source is not known.
     *
     * @return the Data Source instance.
     */
    public DataSource parseDataSourceDeclaration(String name, String[] args) throws ParsingException {
        DataSourceDeclarationHandler handler = dataSources.get(name);

        if (handler == null) {
            throw new ParsingException("Data source \"" + name + "\" is not known.");
        }

        return handler.handleDeclaration(args);
    }

    /**
     * The registered data sources.
     */
    HashMap<String, DataSourceDeclarationHandler> dataSources = new HashMap<>();
}
