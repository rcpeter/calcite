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
package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.util.ImmutableNullableList;

import java.util.List;

/**
 * Parse tree node that represents a CAUSAL IMPACT statement.
 */
public class SqlCausalImpact extends SqlCall {
  private final SqlIdentifier sourceVariable;
  private final SqlIdentifier targetVariable;

  /**
   * Creates a SqlCausalImpact.
   *
   * @param pos            Position.
   * @param sourceVariable Source variable.
   * @param targetVariable Target variable.
   */
  public SqlCausalImpact(SqlParserPos pos,
      SqlIdentifier sourceVariable, SqlIdentifier targetVariable) {
    super(pos);
    this.sourceVariable = sourceVariable;
    this.targetVariable = targetVariable;
  }

  @Override public SqlKind getKind() {
    return SqlKind.OTHER;
  }

  @Override public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword("CAUSAL IMPACT OF");
    sourceVariable.unparse(writer, leftPrec, rightPrec);
    writer.keyword("ON");
    targetVariable.unparse(writer, leftPrec, rightPrec);
  }

  @Override public <R> R accept(SqlVisitor<R> visitor) {
    return visitor.visit(this);
  }

  public SqlIdentifier getSourceVariable() {
    return sourceVariable;
  }

  public SqlIdentifier getTargetVariable() {
    return targetVariable;
  }

  private static final SqlOperator OPERATOR = new
      SqlSpecialOperator("CAUSAL IMPACT", SqlKind.OTHER);

  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Override public List<SqlNode> getOperandList() {
    return ImmutableNullableList.of(sourceVariable, targetVariable);
  }
}
