/*
 * 08/12/2018 at 14:11:39 �.
 * TestDifferentBlocks.java created by Tsvetelin
 */

package com.cake.running.runtime;


import java.util.List;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.ParsersContainer;


/**
 * @author Tsvetelin
 *
 */
public class TestDifferentBlocks
{

    public static void main ( String [] args )
    {
        
        CakeRuntime runtime = Runner.getNewProjectRuntime();
        
        String code = "local testInteger = 5+3";
        Block block1 = new Block( "MethodBlock1" , AccessModifier.PUBLIC , null );
        Block block2 = new Block( "MethodBlock2" , AccessModifier.PUBLIC , null );
        Block nested = new Block( "nestedBlock" , AccessModifier.GLOBAL , block1 );

        Tokenizator< String > tokenizer = new StringTokenizer();
        
        List< Token > tokenizedCode = tokenizer.tokenize( code );
        
        Parser< ? > pars = ParsersContainer.INSTANCE.getParserFor( tokenizedCode ).get( 0 );
        
        pars.parseWithRuntime( runtime , block1 , tokenizedCode );
        pars.parseWithRuntime( runtime , block2 , tokenizedCode );
        pars.parseWithRuntime( runtime , nested , tokenizedCode );
        
        runtime.forEach( (x,y) -> System.out.println( "Address: " + x + " and value: " + y ) );
        
    }
}
