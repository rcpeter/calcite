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
package org.apache.calcite.sql.validate;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlCausalImpact;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.util.Static;
import org.apache.calcite.util.Util;

/**
 * Validates a {@link SqlCausalImpact} call.
 */
public class SqlCausalImpactValidator extends SqlValidatorImpl {

  public SqlCausalImpactValidator(SqlOperatorTable opTab, SqlValidatorCatalogReader catalogReader,
            RelDataTypeFactory typeFactory) {
    super(opTab, catalogReader, typeFactory, Config.DEFAULT);
  }

  @Override public void validateCall(SqlCall call, SqlValidatorScope scope) {
    if (call instanceof SqlCausalImpact) {
      validateCausalImpact((SqlCausalImpact) call, scope);
    } else {
      super.validateCall(call, scope);
    }
  }

  private void validateCausalImpact(SqlCausalImpact call, SqlValidatorScope scope) {
    SqlIdentifier sourceVariable = call.getSourceVariable();
    SqlIdentifier targetVariable = call.getTargetVariable();

        // Validate source variable
    SqlValidatorNamespace sourceNamespace = getNamespaceOrThrow(scope, sourceVariable);
    if (sourceNamespace == null) {
      throw newValidationError(
        sourceVariable, Static.RESOURCE.unknownIdentifier(sourceVariable.toString()));
    }

        // Validate target variable
    SqlValidatorNamespace targetNamespace = getNamespaceOrThrow(scope, targetVariable);
    if (targetNamespace == null) {
      throw newValidationError(
        targetVariable, Static.RESOURCE.unknownIdentifier(targetVariable.toString()));
    }

        // Additional validation logic can be added here if necessary
  }

  private SqlValidatorNamespace getNamespaceOrThrow(SqlValidatorScope scope, SqlIdentifier id) {
    final SqlValidatorNamespace namespace = getNamespace(id);
    if (namespace == null) {
      throw Util.needToImplement("Namespace not found for identifier: " + id);
    }
    return namespace;
  }

  @Override public void validateQuery(
    SqlNode node, SqlValidatorScope scope, RelDataType targetRowType) {
    if (node instanceof SqlCausalImpact) {
      validateCausalImpact((SqlCausalImpact) node, scope);
    } else {
      super.validateQuery(node, scope, targetRowType);
    }
  }
}
