/*
 * 10/12/2018 14:55:46
 * MisplacedConstruct.java created by Tsvetelin
 */

package com.cake.interpreter.utils.commmandSegregation.segregatorExceptions;

/**
 * 
 * Exception thrown when something is structurally incorrect
 * 
 * @author Tsvetelin
 *
 */
public class MisplacedConstruct extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * @param message
     */
    public MisplacedConstruct ( String message )
    {
        super( message );
    }


}
