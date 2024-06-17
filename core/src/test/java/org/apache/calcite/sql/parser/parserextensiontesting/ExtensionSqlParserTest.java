/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.sql.parser.parserextensiontesting;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParserFixture;
import org.apache.calcite.sql.parser.SqlParserTest;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

/**
 * Testing for extension functionality of the base SQL parser impl.
 *
 * <p>This test runs all test cases of the base {@link SqlParserTest}, as well
 * as verifying specific extension points.
 */
class ExtensionSqlParserTest extends SqlParserTest {

  @Override public SqlParserFixture fixture() {
    return super.fixture()
        .withConfig(c -> c.withParserFactory(ExtensionSqlParserImpl.FACTORY));
  }

  @Test void testAlterSystemExtension() {
    sql("alter system upload jar '/path/to/jar'")
        .ok("ALTER SYSTEM UPLOAD JAR '/path/to/jar'");
  }

  @Test void testAlterSystemExtensionWithoutAlter() {
    // We need to include the scope for custom alter operations
    sql("^upload^ jar '/path/to/jar'")
        .fails("(?s).*Encountered \"upload\" at .*");
  }

  @Test void testCreateTable() {
    sql("CREATE TABLE foo.baz(i INTEGER, j VARCHAR(10) NOT NULL)")
        .ok("CREATE TABLE `FOO`.`BAZ` (`I` INTEGER, `J` VARCHAR(10) NOT NULL)");
  }

  @Test void testExtendedSqlStmt() {
    sql("DESCRIBE SPACE POWER")
        .node(new IsNull<SqlNode>());
    sql("DESCRIBE SEA ^POWER^")
        .fails("(?s)Incorrect syntax near the keyword 'POWER' at line 1, column 14.*");
  }

  @Test public void SqlCausalImpact() {
    sql("select causal impact of ^x^ on y")
        .fails("Unknown identifier 'X'");
    sql("select causal impact of x on ^y^")
        .fails("Unknown identifier 'Y'");
    sql("select causal impact of x on y")
        .ok("SELECT CAUSAL IMPACT OF `X` ON `Y`");
  }
}
