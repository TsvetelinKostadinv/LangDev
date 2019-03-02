/*
 * 24/12/2018 14:54:07
 * MethodPromise.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.methods.promise;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.methods.Parameter;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class MethodPromise extends SyntaxElement
{

    private final Parameter [] parameters;

    private String returnType;

    private final boolean areAllDifferentTypes;


    /**
     * @param name
     * @param accessModifier
     */
    public MethodPromise ( AccessModifier accessModifier , String returnType , String name , Parameter [] parameters )
    {
        super( name , accessModifier );
        this.parameters = parameters;
        this.setReturnType( returnType );
        areAllDifferentTypes = areAllDifferentTypes( parameters );
    }


    public boolean canRunWithValues ( Value... values )
    {

        if ( values.length != parameters.length ) return false;


        if ( areAllDifferentTypes )
        {
            List< String > requiredTypes = Arrays.stream( parameters ).map( x -> x.getType() )
                    .collect( Collectors.toList() );
            List< String > valueTypes = Arrays.stream( values ).map( x -> x.getType() ).collect( Collectors.toList() );

            for ( int i = 0 ; i < requiredTypes.size() ; i++ )
            {
                if ( valueTypes.contains( requiredTypes.get( i ) ) )
                {
                    valueTypes.remove( requiredTypes.get( i ) );
                }
            }
            if ( valueTypes.size() != 0 ) return false;
            else return true;

        } else
        {
            for ( int i = 0 ; i < values.length ; i++ )
            {
                if ( !values[i].getType().equals( parameters[i].getType() ) ) return false;
            }
            return true;
        }
    }


    public List< Variable > constructInputVariablesList ( Value... values )
    {
        if ( this.canRunWithValues( values ) )
        {
            List< Variable > inputVars = new ArrayList<>();
            if ( areAllDifferentTypes )
            {
                for ( Parameter param : parameters )
                {
                    for ( Value val : values )
                    {
                        if ( val.getType().equals( param.getType() ) )
                            inputVars.add( new Variable( param.getName() , val , AccessModifier.LOCAL ) );
                    }
                }
            } else
            {
                for ( int i = 0 ; i < parameters.length ; i++ )
                {
                    inputVars.add( new Variable( parameters[i].getName() , values[i] , AccessModifier.LOCAL ) );
                }
            }
            return inputVars;
        } else
        {
            throw new IllegalArgumentException(
                    "Expected " + Arrays.toString( parameters ) + " but got " + Arrays.toString( values ) );
        }
    }


    /**
     * @return the parameters
     */
    public Parameter [] getParameters ()
    {
        return parameters;
    }


    /**
     * @return the areAllDifferentTypes
     */
    public boolean areAllParamethersWithDifferentTypes ()
    {
        return areAllDifferentTypes;
    }


    /**
     * 
     * Checks if all the parameters are from different types
     * 
     * @param parameters2
     * @return
     */
    private static boolean areAllDifferentTypes ( Parameter [] parameters )
    {
        List< String > occuredTypes = new ArrayList<>();

        for ( Parameter parameter : parameters )
        {
            if ( occuredTypes.contains( parameter.getType() ) ) return false;
            else occuredTypes.add( parameter.getType() );
        }

        return true;
    }


    /**
     * @return the returnType
     */
    public String getReturnType ()
    {
        return returnType;
    }


    /**
     * @param returnType
     *            the returnType to set
     */
    public void setReturnType ( String returnType )
    {
        this.returnType = returnType;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return String.format( "%s %s %s = " , this.getAccessModifier() , this.getReturnType() , this.getName() )
                + Arrays.toString( parameters );
    }
}
