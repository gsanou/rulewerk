package org.semanticweb.vlog4j.core.reasoner.implementation;

/*-
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

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.semanticweb.vlog4j.core.model.api.Atom;
import org.semanticweb.vlog4j.core.model.api.Constant;
import org.semanticweb.vlog4j.core.model.api.Predicate;
import org.semanticweb.vlog4j.core.model.implementation.Expressions;
import org.semanticweb.vlog4j.core.reasoner.DataSource;
import org.semanticweb.vlog4j.core.reasoner.exceptions.EdbIdbSeparationException;
import org.semanticweb.vlog4j.core.reasoner.exceptions.ReasonerStateException;

import karmaresearch.vlog.EDBConfigurationException;

public class AddDataSourceTest {

	private static final String CSV_FILE_PATH = "src/test/data/input/unaryFacts.csv";

	@Test
	public void testAddDataSourceExistentDataForDifferentPredicates()
			throws ReasonerStateException, EdbIdbSeparationException, EDBConfigurationException, IOException {
		final Predicate predicateParity1 = Expressions.makePredicate("p", 1);
		final Constant constantA = Expressions.makeConstant("a");
		final Atom factPredicateParity2 = Expressions.makeAtom("p", constantA, constantA);
		final Atom factPredicateQarity1 = Expressions.makeAtom("q", constantA);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));

		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addFacts(factPredicateParity2, factPredicateQarity1);
			reasoner.addDataSource(Expressions.makePredicate("p", 3), dataSource);
			reasoner.addDataSource(predicateParity1, dataSource);
			reasoner.load();
		}
	}

	public void testAddDataSourceBeforeLoading()
			throws ReasonerStateException, EdbIdbSeparationException, EDBConfigurationException, IOException {
		final Predicate predicateP = Expressions.makePredicate("p", 1);
		final Predicate predicateQ = Expressions.makePredicate("q", 1);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(predicateP, dataSource);
			reasoner.addDataSource(predicateQ, dataSource);
			reasoner.load();
		}
	}

	@Test(expected = ReasonerStateException.class)
	public void testAddDataSourceAfterLoading()
			throws ReasonerStateException, EdbIdbSeparationException, EDBConfigurationException, IOException {
		final Predicate predicateP = Expressions.makePredicate("p", 1);
		final Predicate predicateQ = Expressions.makePredicate("q", 1);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(predicateP, dataSource);
			reasoner.load();
			reasoner.addDataSource(predicateQ, dataSource);
		}
	}

	@Test(expected = ReasonerStateException.class)
	public void testAddDataSourceAfterReasoning()
			throws ReasonerStateException, EdbIdbSeparationException, EDBConfigurationException, IOException {
		final Predicate predicateP = Expressions.makePredicate("p", 1);
		final Predicate predicateQ = Expressions.makePredicate("q", 1);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(predicateP, dataSource);
			reasoner.load();
			reasoner.reason();
			reasoner.addDataSource(predicateQ, dataSource);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddDataSourceNoMultipleDataSourcesForPredicate() throws ReasonerStateException, IOException {
		final Predicate predicate = Expressions.makePredicate("p", 1);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(predicate, dataSource);
			reasoner.addDataSource(predicate, dataSource);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddDataSourceNoFactsForPredicate() throws ReasonerStateException, IOException {
		final Predicate predicate = Expressions.makePredicate("p", 1);
		final DataSource dataSource = new CsvFileDataSource(new File(CSV_FILE_PATH));
		final Atom fact = Expressions.makeAtom(Expressions.makePredicate("p", 1), Expressions.makeConstant("a"));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addFacts(fact);
			reasoner.addDataSource(predicate, dataSource);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testAddDataSourcePredicateNotNull() throws ReasonerStateException, IOException {
		final DataSource dataSource = new CsvFileDataSource(new File("src/test/data/unaryFacts.csv"));
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(null, dataSource);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testAddDataSourceNotNullDataSource() throws ReasonerStateException {
		final Predicate predicate = Expressions.makePredicate("p", 1);
		try (final VLogReasoner reasoner = new VLogReasoner()) {
			reasoner.addDataSource(predicate, null);
		}
	}

}