package org.jfree.data.test;

import static org.junit.Assert.*; 
import org.jfree.data.Range; 
import org.junit.*;

public class RangeTest {
	//declare a Range object for testing contains() method
    private Range exampleRange;   
    
   
    @Before
    public void setUp() throws Exception {
    	//initialize a range object with range (-10, 10) to test the contains() method
    	exampleRange = new Range(-10, 10);
    	    	
    }

    //Eric test case for contains() method. The range of the object is set to (-10,10)
    //The following tests use the boundary techniques to test the contains() method if 
    //it contains an upper bound, lower bound and midpoint
    @Test
    //Testing if the range contains a lower bound of -10
    public void containsLowerBound() {
        assertEquals("The object exampleRange(-10,10) should contains the lower bound of -10",
        true , exampleRange.contains(-10));
    }
    
    @Test
  //Testing if the range contains an upper bound of 10
    public void containsUpperBound() {
        assertEquals("The object exampleRange(-10,10) should contains the upper bound of 10",
        true , exampleRange.contains(10));
    }
    
    @Test
  //Testing if the range contains a mid-point of 0
    public void containsMidpoint() {
        assertEquals("The object exampleRange(-10,10) should contains the mid point of 0",
        true , exampleRange.contains(0));
    }
    
    //The following tests use the robustness technique to test out of bound (lower & upper) values
    @Test
    public void containsOutboundUpper() {
        assertEquals("The object exampleRange(-10,10) should not contains 11 which is outside the upper bound of 10",
        false , exampleRange.contains(11));
    }
    
    @Test
    public void containsOutboundLower() {
        assertEquals("The object exampleRange(-10,10) should not contains -11 which is outside the lower bound of 10",
        false , exampleRange.contains(-11));
    }
    
    //Mehreen test cases for lowerBound() method
    //The following tests creates various Range objects with different range to test
    //the getLowerBound() and getUpperBound() methods.
    //An assert statement is created in each test to see if the return value match with the expected value.
    //The name of each testing method is chosen to be self-explanatory.
    
    @Test
    //Partition: range include 0 [-lower, + upper]
    public void lowerBoundShouldBeNegativeOne() {
    	Range exampleRange = new Range(-1, 1);
        assertEquals("The lower bound of -1 and 1",
        -1, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    //Partition: zero for lower bound
    public void lowerBoundShouldBeZero() {
    	Range rangeTestCase = new Range(0, 1);
        assertEquals("The lower bound of 0 and 1",
        0, rangeTestCase.getLowerBound(), .000000001d);
    }
    
    @Test
    //Partition: positive range
    public void lowerBoundShouldBePositiveNumber() {
    	Range rangeTestCase = new Range(3, 10);
        assertEquals("The lower bound of 3 and 10",
        3, rangeTestCase.getLowerBound(), .000000001d);
    }
    
    @Test
  //Partition: positive range with float number
    public void lowerBoundShouldBeFloatValue() {
    	Range rangeTestCase = new Range(3.5, 4.7);
        assertEquals("The lower bound of 3.5 and 4.7",
        3.5, rangeTestCase.getLowerBound(), .000000001d);
    }
    
    @Test
    ////Partition: positive range, lower bound = upperbound
    public void lowerBoundForRangeWithSameNumber() {
    	Range rangeTestCase = new Range(3, 3);
        assertEquals("The lower bound of 3 and 3",
        3, rangeTestCase.getLowerBound(), .000000001d);
    }
    
    @Test
    ////Partition: invalid lower bound, NaN
    public void lowerBoundShouldBeInvalidDueToNAN() {
    	Range rangeTestCase = new Range(Double.NaN, 2);
    	assertEquals("The lower bound of NAN and 2",
    	        Double.NaN, rangeTestCase.getLowerBound(), .000000001d);
    }
    
   @Test(expected = IllegalArgumentException.class)
   //lower bound greater than upper bound
    public void lowerBoundForInvalidRange() {
	   Range rangeTestCase = new Range(5, 2);
	   rangeTestCase.getLowerBound();
  }
    
       
    //Mehreen test Cases for getUpperBounds()
   
   @Test
   ////Partition: negative range
   public void upperBoundShouldBeNegativeOne() {
	   Range rangeTestCase = new Range(-6, -2);
       assertEquals("The upper bound of -6 and -2",
       -2, rangeTestCase.getUpperBound(), .000000001d);
   }
   @Test
   ////Partition: upper bound is 0
   public void upperBoundShouldBeZero() {
	   Range rangeTestCase = new Range(-5, 0);
       assertEquals("The upper bound of -5 and 0",
       0, rangeTestCase.getUpperBound(), .000000001d);
   }
    @Test
    ////Partition: range include 0 (-1,1)
    public void upperBoundShouldBePositiveNumber() {
    	Range exampleRange = new Range(-1, 1);
        assertEquals("The upper bound of -1 and 1",
        1, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test
  //Partition: positive range with float
    public void upperBoundShouldBeFloatValue() {
    	Range rangeTestCase = new Range(3.5, 4.7);
        assertEquals("The upper bound of 3.5 and 4.7",
        4.7, rangeTestCase.getUpperBound(), .000000001d);
    }
    
    @Test
  //Partition: positive range, lower bound = upper bound
    public void upperBoundForRangeWithSameNumber() {
    	Range rangeTestCase = new Range(3, 3);
        assertEquals("The upper bound of 3 and 3",
        3, rangeTestCase.getUpperBound(), .000000001d);
    }
    
    @Test 
    //invalid upper bound
    public void upperBoundShouldBeNAN() {
    	Range rangeTestCase = new Range(2, Double.NaN);
    	assertEquals("The lower bound of 2 and NAN",
    	        Double.NaN, rangeTestCase.getUpperBound(), .000000001d);
    }
    
   @Test(expected = IllegalArgumentException.class)
   //lower bound > upper bound
    public void upperBoundForInvalidRange() {
	   Range rangeTestCase = new Range(5, 2);
	   rangeTestCase.getUpperBound();
  }
   
   //Hao test cases for getCentralValue() method
   
   @Test
   public void centralValueShouldBeHalfForPositiveRange() {
       Range positiveRange = new Range(2, 10); // Range from 2 to 10
       assertEquals("The central value of 2 and 10 should be 6",
               6, positiveRange.getCentralValue(), .000000001d);
   }

   @Test
   public void centralValueShouldBeHalfForNegativeRange() {
       Range negativeRange = new Range(-10, -2); // Range from -10 to -2
       assertEquals("The central value of -10 and -2 should be -6",
               -6, negativeRange.getCentralValue(), .000000001d);
   }

   @Test
   public void centralValueShouldBeCorrectForLargeRange() {
       Range largeRange = new Range(-1.5E308, 1.5E308); // Range from -1.5E308 to 1.5E308
       assertEquals("The central value of -1.5E308 and 1.5E308 should be 0",
               0, largeRange.getCentralValue(), .000000001d);
   }

   @Test
   public void centralValueShouldBeSameForZeroRange() {
       Range zeroRange = new Range(0, 0); // Range starts and ends at 0
       assertEquals("The central value of 0 and 0 should be 0",
               0, zeroRange.getCentralValue(), .000000001d);
   }
   
   @Test(expected =	IllegalArgumentException.class)
   public void testCentralValueWithIllegalArgument() {
       Range invalidRange = new Range(10, 0); // Invalid Range of 10 to 0
       invalidRange.getCentralValue();
   }
   
   @Test(expected=	NullPointerException.class)
   public void testCentralValueWithNullArgument() {
       Range nullRange = new Range(10, (Double) null); // Invalid Range of 10 and null
       nullRange.getCentralValue();
   }
   
   @Test
   public void centralValueShouldWorkForDecimalRange() {
       Range decimalRange = new Range(2.234, 10.235); // Range from 2 to 10
       assertEquals("The central value of range 2.234 and 10.235 should be 6.2345",
       		6.2345, decimalRange.getCentralValue(), .000000001d);
   }
   
   //Hao test cases for getLength() method
   @Test
   public void lengthValueShouldWorkForDecimalRange() {
       Range decimalRange = new Range(2.234, 10.235); // Range from 2 to 10
       assertEquals("The length value of range 2.234 and 10.235 should be 8.001",
               8.001, decimalRange.getLength(), .000000001d);
   }
   
   @Test
   public void lengthValueShouldWorkForPositiveRange() {
       Range positiveRange = new Range(2, 10); // Range from 2 to 10
       assertEquals("The length value of range 2 and 10 should be 8",
               8, positiveRange.getLength(), .000000001d);
   }

   @Test
   public void lengthValueShouldWorkForNegativeRange() {
       Range negativeRange = new Range(-10, -2); // Range from -10 to -2
       assertEquals("The length value of range -10 and -2 should be 8",
               8, negativeRange.getLength(), .000000001d);
   }

   @Test
   public void lengthValueShouldBeCorrectForLargeRange() {
       Range largeRange = new Range(-1.5E307, 1.5E307); // Range from -1.5E307 to 1.5E307
       assertEquals("The length value of range -1.5E307 and 1.5E307 should be 3E307",
               3E307, largeRange.getLength(), .000000001d);
   }
   
   @Test
   public void lengthValueShouldBeSameForZeroRange() {
       Range zeroRange = new Range(0, 0); // Range starts and ends at 0
       assertEquals("The length value of range 0 and 0 should be 0",
               0, zeroRange.getLength(), .000000001d);
   }

   @Test(expected =	IllegalArgumentException.class)
   public void testLengthWithIllegalArgument() {
       Range invalidRange = new Range(10, 0); // Invalid Range of 10 to 0
       invalidRange.getLength();
   }
   
   @Test(expected =	NullPointerException.class)
   public void testLengthWithNullArgument() {
       Range nullRange = new Range(10, (Double) null); // Invalid Range of 10 and null
       nullRange.getLength();
   }
   
   

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
