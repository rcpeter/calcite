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
