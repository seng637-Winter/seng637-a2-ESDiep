package org.jfree.data.test;

import static org.junit.Assert.*;


import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.util.Arrays;
import java.security.InvalidParameterException;
import org.jmock.api.ExpectationError;

public class DataUtilitiesTest {	
     
     private Mockery mockingContext;
     private Values2D values;
     private KeyedValues keyValues;
     
     //Array objects to test createNumberArray() method
     private double[] doubleArray;
     double[] nullArray;
     double[] emptyArray;
     double[] nullValue;

     double[][] double2dArray;
     double[][] nullDouble2dArray;
     double[][] null2dArray;
     double[][] empty2dArray;
     double[][] null2dValue;
     
     
     @Before
     public void setUp() throws Exception{
    	 //mocking object
    	 mockingContext = new Mockery();
    	 keyValues = mockingContext.mock(KeyedValues.class);
    	 values = mockingContext.mock(Values2D.class);
    	 
    	// Initialize values for NumberArray
         doubleArray = new double[] { 1.0, -2.5, 7 };
         nullArray = new double[] { Double.NaN, Double.NaN, Double.NaN };
         emptyArray = new double[] {};
         nullValue = null;

         // Initialize values for NumberArray2D
         double2dArray = new double[][] { { 1.0, -2.5 }, { 4.2, 5.0 } };
         nullDouble2dArray = new double[][] { { -2.5, 3.7 }, { Double.NaN, 6.3 } };
         null2dArray = new double[][] { { Double.NaN, Double.NaN }, { Double.NaN, Double.NaN } };
         empty2dArray = new double[][] { {}, {} };
         null2dValue = null;
     }
     
     //Eric test cases for getCumulativePercentages() method
     @Test
     public void getCumulativePercentagesTestingOneKpairWithZeroValue() {
         mockingContext.checking(new Expectations() {
             {	//test case (0,0)
                 allowing(keyValues).getItemCount();
                 will(returnValue(1));
                 allowing(keyValues).getKey(0);
                 will(returnValue(0));
                 allowing(keyValues).getValue(0);
                 will(returnValue(0));
             }
         });

         KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
         assertEquals("The cumulative percentage of key pair (0,0)should be 0.0/0.0", 0.0 / 0.0,
                 result.getValue(0));
     }

     @Test
     public void getCumulativePercentagesTestingOneValidKpair() {
         mockingContext.checking(new Expectations() {
             {	//test case (0,1)
                 allowing(keyValues).getItemCount();
                 will(returnValue(1));
                 allowing(keyValues).getKey(0);
                 will(returnValue(0));
                 allowing(keyValues).getValue(0);
                 will(returnValue(1));
             }
         });

         KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
         assertEquals("The cumulative percentage of key pair (1,1) should be 1", 1.0,
                 result.getValue(0));
     }
     
     @Test
     public void getCumulativePercentagesTestingOneKpairWithNullValue() {
         mockingContext.checking(new Expectations() {
             { //test case (0,null)
                 allowing(keyValues).getItemCount();
                 will(returnValue(1));
                 allowing(keyValues).getKey(0);
                 will(returnValue(0));
                 allowing(keyValues).getValue(0);
                 will(returnValue(null));
             }
         });

         KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
         assertEquals("The cumulative percentage of null in data should be NaN", Double.NaN, result.getValue(0));
     }
   
   
    @Test
   public void getCumulativePercentageTestingTwoKeyPairWithOneZeroValue() {               
    	//test case (0,0), (1,1)
        mockingContext.checking(new Expectations() {
        	{
	           allowing(keyValues).getItemCount(); //counting the number of keypair
	           will(returnValue(2)); // 2 key pair (0,0), (1,1)
	           
	           //set first key-pair(0,0)
	           allowing(keyValues).getKey(0);
	           will(returnValue(0));
	           allowing(keyValues).getValue(0);
	           will(returnValue(0));
	           
	           //set second key-pair (1,1)
	           allowing(keyValues).getKey(1);
	           will(returnValue(1));
	           allowing(keyValues).getValue(1);
	           will(returnValue(1));
        	}
        });
        
                
        KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
        assertEquals("The cumulative Percentage of key pair (0,0) should be 0. ", 0.0, result.getValue(0));
        assertEquals("The cumulative Percentage of key pair (1,1) should be 0. ", 1.0 , result.getValue(1));
        
    }
    
    @Test
    public void getCumulativePercentageTestingThreeKeyPair() {               
     	//test case (0,2) (1,3) (2,4), expected result: (0, 2/9), (1,5/9), (2,9/9)

         mockingContext.checking(new Expectations() {
        	 {
        		allowing(keyValues).getItemCount(); //counting the number of keypair
  	           	will(returnValue(3)); // 2 key pair (0,0), (1,1)
  	           
  	           //set first key-pair(0,0)
  	           	allowing(keyValues).getKey(0);
  	           	will(returnValue(0));
  	           	allowing(keyValues).getValue(0);
  	           	will(returnValue(2));
  	           
  	           //set second key-pair (1,1)
  	           	allowing(keyValues).getKey(1);
  	           	will(returnValue(1));
  	           	allowing(keyValues).getValue(1);
  	           	will(returnValue(3));
  	           	
  	           	//set second key-pair (1,1)
  	           	allowing(keyValues).getKey(2);
  	           	will(returnValue(2));
  	           	allowing(keyValues).getValue(2);
  	           	will(returnValue(4));
        	 }
         });         

         
         KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
         assertEquals("The cumulative Percentage of key pair (0,2) should be 0.22222 ", 2.0/9, result.getValue(0));
         assertEquals("The cumulative Percentage of key pair (1,3) should be 0.5555555 ", 5.0/9 , result.getValue(1));
         assertEquals("The cumulative Percentage of key pair (2,4) should be 1.0 ", 1.0 , result.getValue(2));
         
     }
    
    @Test
    public void getCumulativePercentageTestingFourKeyPairWithNegative() {               
     	//test case (0,0) (1,3), (2,-2), (3,4), expected result: (0, 0), (1,3/5), (2,-2/5), (3,4/5)

         mockingContext.checking(new Expectations() {
        	 {
        		allowing(keyValues).getItemCount(); 
  	           	will(returnValue(4)); 
  	           
  	           //set first key-pair(0,0)
  	           	allowing(keyValues).getKey(0);
  	           	will(returnValue(0));
  	           	allowing(keyValues).getValue(0);
  	           	will(returnValue(0));
  	           
  	           //set second key-pair (1,3)
  	           	allowing(keyValues).getKey(1);
  	           	will(returnValue(1));
  	           	allowing(keyValues).getValue(1);
  	           	will(returnValue(3));
  	           	
  	           	//set second key-pair (2,-2)
  	           	allowing(keyValues).getKey(2);
  	           	will(returnValue(2));
  	           	allowing(keyValues).getValue(2);
  	           	will(returnValue(-2));
  	           	
  	      	//set second key-pair (3,4)
  	           	allowing(keyValues).getKey(3);
  	           	will(returnValue(3));
  	           	allowing(keyValues).getValue(3);
  	           	will(returnValue(4));
        	 }
         });
	         
         
         KeyedValues result = DataUtilities.getCumulativePercentages(keyValues);
         assertEquals("The cumulative Percentage of key pair (0,0) should be 0.0 ", 0.0, result.getValue(0));
         assertEquals("The cumulative Percentage of key pair (1,3) should be 0.6 ", 0.6 , result.getValue(1));
         assertEquals("The cumulative Percentage of key pair (2,-2) should be -0.4 ", -0.4 , result.getValue(2));
         assertEquals("The cumulative Percentage of key pair (3,4) should be 0.8 ", 0.8 , result.getValue(3));
         
     }
     
    // Jenn test cases for CreateNumberArray() methods
        
    //// Double Array ////
    @Test
    public void testCreateNumberArray_ValidDoubleData_CheckNotNull() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(doubleArray);

        // Verify
        assertNotNull("NumberArray is null and should have contain: " + Arrays.toString(doubleArray), newNumberArray);
    }

    @Test
    public void testCreateNumberArray_ValidDoubleData_CheckLength() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(doubleArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", doubleArray.length,
                newNumberArray.length);
    }

    @Test
    public void testCreateNumberArray_ValidDoubleData_CheckElement0Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(doubleArray);

        // Verify
        assertEquals("Element 0 in Original array and NumberArray do not match.", doubleArray[0],
                newNumberArray[0]);
    }

    @Test
    public void testCreateNumberArray_ValidDoubleData_CheckElement1Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(doubleArray);

        // Verify
        assertEquals("Element 1 in Original array and NumberArray do not match.", doubleArray[1],
                newNumberArray[1]);
    }

    @Test
    public void testCreateNumberArray_ValidDoubleData_CheckElement2Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(doubleArray);

        // Verify
        assertEquals("Element 2 in Original array and NumberArray do not match.", doubleArray[2],
                newNumberArray[2]);
    }

    //
    //// Null Array ////
    @Test
    public void testCreateNumberArray_NullArray_CheckNotNull() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(nullArray);

        // Verify
        assertNotNull("NumberArray is null and should have contain: " + Arrays.toString(nullArray), newNumberArray);
    }

    @Test
    public void testCreateNumberArray_NullArray_CheckLength() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(nullArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", nullArray.length,
                newNumberArray.length);
    }

    @Test
    public void testCreateNumberArray_NullArray_CheckElement0Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(nullArray);

        // Verify
        assertEquals("Element 0 in Original array and NumberArray do not match.", nullArray[0],
                newNumberArray[0]);

    }

    @Test
    public void testCreateNumberArray_NullArray_CheckElement1Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(nullArray);

        // Verify
        assertEquals("Element 1 in Original array and NumberArray do not match.", nullArray[1],
                newNumberArray[1]);
    }

    @Test
    public void testCreateNumberArray_NullArray_CheckElement2Match() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(nullArray);

        // Verify
        assertEquals("Element 2 in Original array and NumberArray do not match.", nullArray[2],
                newNumberArray[2]);
    }

    //
    //// Empty Array ////
    @Test
    public void testCreateNumberArray_EmptyArray_CheckNotNull() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(emptyArray);

        // Verify
        assertNotNull("NumberArray is null and should have contain: " + Arrays.toString(emptyArray), newNumberArray);
    }

    @Test
    public void testCreateNumberArray_EmptyArray_CheckLength() {
        // Exercise
        Number[] newNumberArray = DataUtilities.createNumberArray(emptyArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", emptyArray.length,
                newNumberArray.length);
    }

    //
    //// Null Value ////
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray_nullValue() {
        // Exercise
        DataUtilities.createNumberArray(nullValue);
    }

    //////// CreateNumberArray2D Tests ////////
    //
    //// Double Array ////
    @Test
    public void testCreateNumberArray2D_ValidData_CheckNotNull() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertNotNull("NumberArray is null for input: " + Arrays.deepToString(double2dArray), newNumberArray);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckRowLength() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", double2dArray.length,
                newNumberArray.length);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckColumn0Length() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", double2dArray[0].length,
                newNumberArray[0].length);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckColumn1Length() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", double2dArray[1].length,
                newNumberArray[1].length);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckElement00Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Element [0][0] in Original array and NumberArray do not match.", double2dArray[0][0],
                newNumberArray[0][0]);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckElement01Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Element [0][1] in Original array and NumberArray do not match.", double2dArray[0][1],
                newNumberArray[0][1]);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckElement10Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Element [1][0] in Original array and NumberArray do not match.", double2dArray[1][0],
                newNumberArray[1][0]);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckElement11Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(double2dArray);

        // Verify
        assertEquals("Element [1][1] in Original array and NumberArray do not match.", double2dArray[1][1],
                newNumberArray[1][1]);
    }

    //// Mixed Input Array ////

    @Test
    public void testCreateNumberArray2D_NullDouble2dArray_CheckElement00Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(nullDouble2dArray);

        // Verify
        assertEquals("Element [0][0] in Original array and NumberArray do not match.", nullDouble2dArray[0][0],
                newNumberArray[0][0]);
    }

    @Test
    public void testCreateNumberArray2D_NullDouble2dArray_CheckElement01Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(nullDouble2dArray);

        // Verify
        assertEquals("Element [0][1] in Original array and NumberArray do not match.", nullDouble2dArray[0][1],
                newNumberArray[0][1]);
    }

    @Test
    public void testCreateNumberArray2D_NullDouble2dArray_CheckElement10Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(nullDouble2dArray);

        // Verify
        assertEquals("Element [1][0] in Original array and NumberArray do not match.", nullDouble2dArray[1][0],
                newNumberArray[1][0]);
    }

    @Test
    public void testCreateNumberArray2D_NullDouble2dArray_CheckElement11Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(nullDouble2dArray);

        // Verify
        assertEquals("Element [1][1] in Original array and NumberArray do not match.", nullDouble2dArray[1][1],
                newNumberArray[1][1]);
    }

    //// Null Array ////

    @Test
    public void testCreateNumberArray2D_Null2dArray_CheckElement00Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(null2dArray);

        // Verify
        assertEquals("Element [0][0] in Original array and NumberArray do not match.", null2dArray[0][0],
                newNumberArray[0][0]);
    }

    @Test
    public void testCreateNumberArray2D_Null2dArray_CheckElement01Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(null2dArray);

        // Verify
        assertEquals("Element [0][1] in Original array and NumberArray do not match.", null2dArray[0][1],
                newNumberArray[0][1]);
    }

    @Test
    public void testCreateNumberArray2D_Null2dArray_CheckElement10Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(null2dArray);

        // Verify
        assertEquals("Element [1][0] in Original array and NumberArray do not match.", null2dArray[1][0],
                newNumberArray[1][0]);
    }

    @Test
    public void testCreateNumberArray2D_Null2dArray_CheckElement11Match() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(null2dArray);

        // Verify
        assertEquals("Element [1][1] in Original array and NumberArray do not match.", null2dArray[1][1],
                newNumberArray[1][1]);
    }

    //
    //// Empty Array ////
    @Test
    public void testCreateNumberArray2D_Empty2dArray_CheckNotNull() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(empty2dArray);

        // Verify
        assertNotNull("NumberArray is null and should have contain: " + Arrays.toString(empty2dArray), newNumberArray);
    }

    @Test
    public void testCreateNumberArray2D_Empty2dArray_CheckRowLength() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(empty2dArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", empty2dArray.length,
                newNumberArray.length);
    }

    @Test
    public void testCreateNumberArray2D_ValidData_CheckColumnLength() {
        // Exercise
        Number[][] newNumberArray = DataUtilities.createNumberArray2D(empty2dArray);

        // Verify
        assertEquals("Original array and NumberArray do not have the same length.", empty2dArray[0].length,
                newNumberArray[0].length);
    }

    //
    //// Null Value ////
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2D_nullValue() {
        // Exercise
        DataUtilities.createNumberArray2D(null2dValue);
    }

    @After
    public void tearDown() {
        // Destroy 1D array objects
        doubleArray = null;
        nullValue = null;
        emptyArray = null;
        nullArray = null;

        // Destroy 2D array objects
        double2dArray = null;
        nullDouble2dArray = null;
        null2dArray = null;
        empty2dArray = null;
        null2dValue = null;
    }
    
    //Corey test cases for
    
    // #########################
 	// calcualteColumnTotal()
 	// #########################
     @Test
     public void calculateColumnTotalFirstColumn() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 0);
                 will(returnValue(3.0));                        
             }
         });
     	
         double result = DataUtilities.calculateColumnTotal(values, 0);
         assertEquals(6.0, result, .000000001d);
     }
     
     @Test
     public void calculateColumnTotalMiddleColumn() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 1);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 1);
                 will(returnValue(3.0));                        
             }
         });    	
     	
         double result = DataUtilities.calculateColumnTotal(values, 1);
         assertEquals(6.0, result, .000000001d);
     }    
     
     @Test
     public void calculateColumnTotalLastColumn() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 2);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 2);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 2);
                 will(returnValue(3.0));                        
             }
         });    	
     	
         double result = DataUtilities.calculateColumnTotal(values, 2);
         assertEquals(6.0, result, .000000001d);
     } 
     
     @Test(expected = ExpectationError.class)
     public void calculateColumnTotalWithNegativeColumnIndex() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 0);
                 will(returnValue(3.0));                        
             }
         });    	
     	
         DataUtilities.calculateColumnTotal(values, -1);
     }      
     
     @Test(expected = ExpectationError.class)
     public void calculateColumnTotalWithOutOfBoundsColumnIndex() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 0);
                 will(returnValue(3.0));                        
             }
         });    	
     	
     	DataUtilities.calculateColumnTotal(values, 3);
     }
     
     @Test(expected = NullPointerException.class)
     public void calculateColumnTotalWithNullData() {    	
         DataUtilities.calculateRowTotal(null, 0);
     }       
     
     @Test(expected = ExpectationError.class)
     public void calculateColumnTotalWithMaxValueRowIndex() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(Integer.MAX_VALUE));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(2.0));
                 
                 one(values).getValue(Integer.MAX_VALUE, 0);
                 will(returnValue(3.0));                 
             }
     	});
     	
         DataUtilities.calculateColumnTotal(values, Integer.MAX_VALUE);
     }       
     
     @Test
     public void calculateColumnTotalWithMaxValueInRows() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(Double.MAX_VALUE));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(Double.MAX_VALUE));
                 
                 one(values).getValue(2, 0);
                 will(returnValue(Double.MAX_VALUE));                 
             }
     	});
     	
         double result = DataUtilities.calculateColumnTotal(values, 0);
         assertEquals(Double.POSITIVE_INFINITY, result, .000000001d);
     }      

     @Test
     public void calculateColumnTotalWithMinValueInRows() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getRowCount();
                 will(returnValue(3));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(Double.MIN_VALUE));
                 
                 one(values).getValue(1, 0);
                 will(returnValue(Double.MIN_VALUE));
                 
                 one(values).getValue(2, 0);
                 will(returnValue(Double.MIN_VALUE));                 
             }
     	});
     	
         double result = DataUtilities.calculateColumnTotal(values, 0);
         assertEquals(Double.MIN_VALUE + Double.MIN_VALUE + Double.MIN_VALUE, result, .000000001d);
     }      

     
 	// #########################
 	// calculateRowTotal()
 	// #########################
     
     @Test
     public void calculateRowTotalFirstRow() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(0, 2);
                 will(returnValue(3.0));                        
             }
         });    	
     	
         double result = DataUtilities.calculateRowTotal(values, 0);
         assertEquals(6.0, result, .000000001d);
     } 
     
     @Test
     public void calculateRowTotalMiddleRow() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(1, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(1, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(1, 2);
                 will(returnValue(3.0));                        
             }
         });    	
     	
         double result = DataUtilities.calculateRowTotal(values, 1);
         assertEquals(6.0, result, .000000001d);
     }       
     
     @Test
     public void calculateRowTotalLastRow() {	
         mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(2, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(2, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(2, 2);
                 will(returnValue(3.0));                        
             }
         });    	    	
     	
         double result = DataUtilities.calculateRowTotal(values, 2);
         assertEquals(6.0, result, .000000001d);
     }      
     
     @Test(expected = ExpectationError.class)
     public void calculateRowTotalWithNegativeRowIndex() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(0, 2);
                 will(returnValue(3.0));                        
             }
         });    	    	
     	
         double result = DataUtilities.calculateRowTotal(values, -1);
         assertEquals(0.0, result, .000000001d);
     }

     @Test(expected = ExpectationError.class)
     public void calculateRowTotalWithOutOfBoundsRowIndex() {
         mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));                
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(0, 2);
                 will(returnValue(3.0));                        
             }
         });    	    	
         double result = DataUtilities.calculateRowTotal(values, -1);
         assertEquals(0.0, result, .000000001d);
     }    
     
     @Test(expected = NullPointerException.class)
     public void calculateRowTotalWithNullData() {    	
         double result = DataUtilities.calculateRowTotal(null, -1);
         assertEquals(0.0, result, .000000001d);
     }

     @Test(expected = ExpectationError.class)
     public void calculateRowTotalWithMaxValueColIndex() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(Integer.MAX_VALUE));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(1.0));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(2.0));
                 
                 one(values).getValue(0, Integer.MAX_VALUE);
                 will(returnValue(3.0));                 
             }
     	});
     	
         DataUtilities.calculateRowTotal(values, Integer.MAX_VALUE);
     }   
     
     @Test
     public void calculateRowTotalWithMaxValueInCols() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(Double.MAX_VALUE));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(Double.MAX_VALUE));
                 
                 one(values).getValue(0, 2);
                 will(returnValue(Double.MAX_VALUE));                 
             }
     	});
     	
         double result = DataUtilities.calculateRowTotal(values, 0);
         assertEquals(Double.POSITIVE_INFINITY, result, .000000001d);
     }      

     @Test
     public void calculateRowTotalWithMinValueInCols() {
     	
     	mockingContext.checking(new Expectations() {
             {
                 one(values).getColumnCount();
                 will(returnValue(3));
                 
                 one(values).getValue(0, 0);
                 will(returnValue(Double.MIN_VALUE));
                 
                 one(values).getValue(0, 1);
                 will(returnValue(Double.MIN_VALUE));
                 
                 one(values).getValue(0, 2);
                 will(returnValue(Double.MIN_VALUE));                 
             }
     	});
     	
         double result = DataUtilities.calculateRowTotal(values, 0);
         assertEquals(Double.MIN_VALUE + Double.MIN_VALUE + Double.MIN_VALUE, result, .000000001d);
     }   
    
}
     