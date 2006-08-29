/*
 * (c) Copyright IBM Corp 2006 
 */

package com.ibm.wsdl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.ibm.wsdl.xml.ImportWSDLTest;
import com.ibm.wsdl.xml.SetFactoryNameTest;
import com.ibm.wsdl.xml.WSDLExceptionTest;

public class WSDL4JTestSuite extends TestCase
{
  public static Test suite()
  {
    TestSuite testSuite = new TestSuite();

    testSuite.addTestSuite(ImportWSDLTest.class);
    testSuite.addTestSuite(SetFactoryNameTest.class);
    testSuite.addTestSuite(WSDLExceptionTest.class);

    return testSuite;
  }
}