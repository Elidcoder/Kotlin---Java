package concurrency.statements;

import concurrency.Store;
import concurrency.expressions.*;

public class WaitStmt implements Stmt{

    private final Expr LHS;
    private final Expr RHS;

    public WaitStmt(Expr LHS, Expr RHS) {
        this.LHS = LHS;
        this.RHS = RHS;
    }

    @Override
    public boolean isEnabled(Store store) {
        return RHS.eval(store) == LHS.eval(store);
    }

    @Override
    public void execute(Store store) {}
}
