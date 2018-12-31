/*
 * 27/12/2018 14:58:30
 * Scope.java created by Tsvetelin
 */

package com.cake.syntax.blocks.scopes;


import java.util.ArrayList;
import java.util.List;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Scope
{
    
    private final Block block;

    public Scope( Block block )
    {
        this.block = block;
    }

    /**
     * @param values
     * @param runtime
     * @return
     */
    public List< Variable > evaluate ( CakeRuntime runtime , Value [] values )
    {
        List< Variable > exitVariables = new ArrayList<>();

        for ( SyntaxElement element : block.getSubcommands() )
        {
            if ( element instanceof Variable )
            {
                exitVariables.add( (Variable) element );
            } else if ( element instanceof Block )
            {
                exitVariables.addAll( ( (Block) element ).run( runtime , values ).getExitVariables() );
            } else if ( element instanceof Operator )
            {
                Operator operator = (Operator) element;
                runtime.addDecalredElement( operator.getOperand().getName() , operator.calculate() );
            }
        }
        return exitVariables;
    }

}
