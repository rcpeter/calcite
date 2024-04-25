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

/*
 * Overrides: overrides the createCall method to return a new SqlCausalImpactNode. It also overrides the unparser method to define how the CAUSAL IMPACT keyowrd should be unparsed to SQL. 
 */
package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;

public class SqlCausalImpactOperator extends SqlSpecialOperator {
    public SqlCausalImpactOperator() {
        super("CAUSAL IMPACT", SqlKind.OTHER_FUNCTION);
    }

    @Override
    public SqlCall createCall(SqlLiteral functionQualifier, SqlParserPos pos, SqlNode... operands) {
        return new SqlCausalImpactNode(pos, (SqlIdentifier) operands[0], (SqlIdentifier) operands[1]);
    }

    @Override
    public void unparse(SqlWriter writer, SqlCall call, int leftPrec, int rightPrec) {
        final SqlWriter.Frame frame = writer.startFunCall(getName());
        call.operand(0).unparse(writer, leftPrec, rightPrec);
        writer.keyword("ON");
        call.operand(1).unparse(writer, leftPrec, rightPrec);
        writer.endFunCall(frame);
    }
}
