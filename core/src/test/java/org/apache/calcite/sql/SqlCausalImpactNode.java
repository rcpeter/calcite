package org.apache.calcite.sql;

import java.util.List;

import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlVisitor;

public class SqlCausalImpactNode extends SqlCall {
    private final SqlIdentifier sourceVariable;
    private final SqlIdentifier targetVariable;

    public SqlCausalImpactNode(SqlParserPos pos, SqlIdentifier sourceVariable, SqlIdentifier targetVariable) {
        super(pos);
        this.sourceVariable = sourceVariable;
        this.targetVariable = targetVariable;
    }

    @Override
    public SqlKind getKind() {
        return SqlKind.OTHER;
    }

    @Override
    public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
        int prec = 1000;
        if (leftPrec > prec) {
            writer.print("(");
        }
        writer.keyword("CAUSAL IMPACT OF");
        sourceVariable.unparse(writer, prec, prec);
        writer.keyword("ON");
        targetVariable.unparse(writer, prec, rightPrec);
        if (rightPrec > prec) {
            writer.print(")");
        }
    }

    @Override
    public <R> void accept(SqlVisitor<R> visitor) {
        // TODO Fix issue with void acceptance
        visitor.visit(this);
    }

    public SqlIdentifier getSourceVariable() {
        return sourceVariable;
    }

    public SqlIdentifier getTargetVariable() {
        return targetVariable;
    }

    @Override
    public SqlOperator getOperator() {
        return new SqlSpecialOperator("CAUSAL IMPACT", SqlKind.OTHER);
    }

    @Override
    public List<SqlNode> getOperandList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOperandList'");
    }
}
