**SENG 637 - Software Testing, Reliability, and Quality**

**Lab. Report \#2 – Requirements-Based Test Generation**

| Group: 2 |
| -------- |
| Corey Yang-Smith |
| Eric (Sieu) Diep |
| Hao Liu |
| Mehreen Akmal |
| Jenn Bushey |

# 1. Introduction

Our work in this lab was to test the Range and DataUtilities class in the JFreeChart framework, a charting framework for Java. Since the source codes were not available,  we developed test plans and test cases using black-box-testing for 10 methods in the two classes mentioned.

We began this lab by setting up our eclipse environments with JFreeChart and perusing the JavaDoc for the framework. Afterwards, we split up the methods to test amongst our team and developed test plans individually. We evaluated the test plans as a group and then implemented the test cases individually. Finally, we compiled all test cases on one machine and ran our final evaluation.

# 2. Detailed description of unit test strategy

## 2.1 Scope of Testing

### 2.1.1 Methods to be Tested

|<h3>Range Class Methods</h3> | <h3>Description</h3> | <h3>Tested By </h3>|
| ------------------------ | ---------------------------------|-------------------------- |
| `getUpperBound()`        | Returns the upper bound for the range.| Mehreen |
| `getLowerBound()`        | Returns the lower bound for the range.| Mehreen |
| `getLength()`            | Returns the length of the range.| Hao |
| `getCentralValue()`      | Returns the central (or median) value for the range.| Hao |
| `contains(double value)` | Returns true if the specified value is within the range and false otherwise. | Eric|

| <h3>DataUtilities Class Methods</h3> | <h3>Description</h3>  | <h3>Tested By </h3>|
| ----------------------------| -------------------------------|--------------------- |
| `getCumulativePercentages(KeyedValues data)`      | Returns a KeyedValues instance that contains the cumulative percentage values for the data in another KeyedValues instance. | Eric |
| `createNumberArray2D(double[][] data)`            | Constructs an array of arrays of Number objects from a corresponding structure containing double primitives.| Jenn |
| `createNumberArray(double[] data)`                | Constructs an array of Number objects from an array of double primitives.| Jenn |
| `calculateRowTotal(Values2D data, int row)`       | Returns the sum of the values in one row of the supplied data table.| Corey |
| `calculateColumnTotal(Values2D data, int column)` | Returns the sum of the values in one column of the supplied data table.| Corey |

### 2.1.2 Methods Not to be Tested
| <h3>Range Class Methods</h3>                |<h3> Description   </h3>|
| -----------------------------------| ------------------------------------------------|
| `toString()`                                                  | Returns a string representation of this Range.                                                                  |
| `shift(Range base, double delta, boolean allowZeroCrossing)`  | Returns a range the size of the input range, which has been moved positively (to the right) by the delta value. |
| `shift(Range base, double delta)`                             | Returns a range the size of the input range, which has been moved positively (to the right) by the delta value. |
| `intersects(double lower, double upper)`                      | Returns true if the range intersects (overlaps) with the specified range, and false otherwise.                  |
| `hashCode()`                                                  | Returns a hash code.                                                                                            |
| `constrain(double value)`                                     | Returns the value within the range that is closest to the specified value.                                      |
| `expandToInclude(Range range, double value)`                  | Returns a range that includes all the values in the specified range AND contains the specified value.           |
| `expand(Range range, double lowerMargin, double upperMargin)` | Creates a new range by adding margins to an existing range.                                                     |
| `combine(Range range1, Range range2)`                         | Creates a new range by combining two existing ranges.                                                           |
| `equals(java.lang.Object obj)`                                | Tests this object for equality with an arbitrary object.                                                        |



## 2.2 Test Type

The test plan for the Range and DataUtilities class methods was designed based on the specific functionality and the input domain of each method using black box testing techniques. 
No explicit boundaries were outlined in the documentation therefore we mainly employed the Equivalent class partitioning technique to test our functions.

Note that four out of the five methods tested in the Range Class do not have any input arguments. However, these methods perform operations against the lower bound and upper bound double attributes of the Range class and return a result accordingly. The only method, that has a double input, also operates in this manner. Thus, the lower bound and upper bound are considered the input for these methods, and will be referred to as “Range Input” from now on. Therefore, we created our test cases to test all the equivalent classes and the boundary values based on these lower bound and upper bound.

It was difficult to apply the Equivalent Class and Boundary technique to the methods in DataUtilities class as there are no clear boundary to be used for the partitioning. Note that all the methods in this class are static method. And the input arguments for all these methods are either an array (1D/2D) or an interface. A mocking technique was used where the input is an interface. 

Since it's difficult to come up with the equivalent class, a non-exhautive approach was used for these methods. The test cases were deveveloped based on the tester's intuition and experience after reviewing the behavior specification of the methods. 

The createNumberArray and createNumberArray2D methods were tested and using double values, empty data, and null values input since they take double values as input. Although not clear, these test cases can be considered as equivalent class. 

The calculateColumnTotal and calculateRowTotal methods were tested by passing in an array of data and different values of the row/column values. The row/column are either inbound (contained inside the array) or out-of-bound (not included in the array). The array can either contain valid data (doubles) or null.

The getCumulativePercentage method was tested using a mock object. This mock objects simulated different KeyValued interface (a list of key/value pair). The number of key/value pair were varied from 1 to 4. The tester manually calculted the expected cumulative percentage of each key/value pair and "assert" them accordingly with the output of the program. Note that the test case, where the Keyvalued (or the key/value pair) was empty, was not included in this test due to the difficulty to create a mocking ojbect for this case.

### 2.2.1 Equivalent Classes for Range Class based on the upper bound and lower bound range input:

Each point below is a partrition of the ECT that we came up with.

Legal inputs (or range inputs):
- Negative range: [- lower bound, - upper bound]
- Positive range: [+ lower bound, + upper bound]
- Zero as the value for upper or lower bound
- Range includes 0: [- lower bound, + upper bound] 
- Double.NAN value as either upper bound  or lower bound
- Range of length 0: (lower bound = upper bound)
- Extreme ranges: max or min value that a double variable can hold. It is worth to consider this situation to test the number wrap-around behaviour due to the limitation a double number can hold. However, due to the scope and limitation of this lab, a test case for this situation is not developed as it would require careful and complex strategy to carry out the test.

Illegal Inputs:  
- Lower bound input is greater than upper bound input 

### 2.2.2 Boundary Values for Range Class (only applicable to the contains(double) method):
- Lower bound
- Upper bound
- Midpoint of lower bound and upper bound
- Lower bound + 1
- Upper bound - 1


### 2.2.3 Equivalent Classes for DataUtilities Class - createNumberArray & createNumberArray2D:

Legal Inputs
- Negative double values
- Positive double values 
- Double.NAN values
- Array with length 0
- Array = null

Illegal Inputs
- Non-double values

### 2.2.4 Equivalent Classes for DataUtilities Class - calculateColumnTotal and calculateRowTotal:

Legal Inputs:
- Valid data array
- Inbound row/column value
- Max/min row/column value where the variable (eg. double) can hold

Illegal Inputs:
- Out-of-bound row/column value
- Null data array
	
### 2.2.5 Equivalent Classes for DataUtilities Class - getCumulativePercentage:
Not applicable.

## 2.3 Risks and Issues

Since the source codes were not available, the testers had difficulties to implement the KeyValues interface/object to create the input for the getCumulativePercentages method. This issue was resolved by using mocking. It allowed the tester to isolate the components being tested from its dependencies. Thus, the tester can simulate the testing inputs without implementing the actual Keyvalues interface/object.

However, the learning curve to use the mocking object is quite steep. A significant amount of time was used to create the mocking object itself. The tester had consulted many different online resources and the documentation of jmock. But either the resources were limited nor the documentation were unclear. As mentioned in section 2.2 test type, the tester would like to include a test case where the Keyvalued object is empty for the getCumulativePercentages method. But it was dropped because the tester doesn't know how to create such a test case with jMock given the limited time and resource available. This is an issue with using jMock. 

# 3. Test cases developed

## Class: Range

### `Range.contains()`
Lower bound selected: -10
Upper bound selected: 10
| Test Case                          | Method Input                   | Status |
|------------------------------------|------------------------------------|--------|
|`containsLowerBound() `| -10 | Pass |
|`containsUpperBound() `| 10  | Pass |
|`containsMidpoint() `| 0  | Pass |
|`containsOutboundUpper() `| 11 | Pass |
|`containsOutboundLower() `| -11 | Pass |

###  `Range.getCentralValue()`

| Test Case                                   | Range Input       | Status |
|---------------------------------------------|---------------------------|--------|
| `centralValueShouldBeHalfForPositiveRange()`  | (2, 10)                   | Pass   |
| `centralValueShouldBeHalfForNegativeRange()` | (-10, -2)                 | Pass   |
| `centralValueShouldBeCorrectForLargeRange()`  | (-1.5E308, 1.5E308)       | Pass   |
| `centralValueShouldBeSameForZeroRange()`      | (0, 0)                    | Pass   |
| `testCentralValueWithIllegalArgument()`       | (10, 0)                   | Pass   |
| `testCentralValueWithNullArgument()`          | (10, (Double) null)       | Pass   |
| `centralValueShouldWorkForDecimalRange()`     | (2.234, 10.235)           | Pass   |


### `Range.getLength()`

| Test Case                                  | Range Input        | Status |
|------------------------------------------  |-------------------------|--------|
| `lengthValueShouldWorkForDecimalRange()`   |(2.234, 10.235)        | Pass   |
| `lengthValueShouldWorkForPositiveRange()`  | (2, 10)                | Pass   |
| `lengthValueShouldWorkForNegativeRange()`  | (-10, -2)               | Pass   |
| `lengthValueShouldBeCorrectForLargeRange()`| (-1.5E307, 1.5E307)     | Pass   |
| `lengthValueShouldBeSameForZeroRange()`    | (0, 0)                  | Pass   |
| `testLengthWithIllegalArgument()`          | (10, 0)                | Pass   |
| `testLengthWithNullArgument()`             | (10, (Double) null)     | Pass   |

### `Range.getLowerBound()`

| Test Case                          | Range Input                        | Status |
|------------------------------------|------------------------------------|--------|
| `lowerBoundShouldBeNegativeOne()`  | (-1, 1)                            | Pass   |
| `lowerBoundShouldBeZero()`         | (0, 1)                             | Pass   |
| `lowerBoundShouldBePositiveNumber()`| (3, 10)                           | Pass   |
| `lowerBoundShouldBeFloatValue()`   | (3.5, 4.7)                         | Pass   |
| `lowerBoundForRangeWithSameNumber()` | (3, 3)                           | Pass   |
| `lowerBoundShouldBeNAN()`          | (Double.NAN, 2)                    | Pass   |
| `lowerBoundForInvalidRange()`      | (5, 2)                             | Pass   |

### `Range.getUpperBound()`

| Test Case                             | Range Input         | Status |
|-------------------------------------  |---------------------|--------|
| `UpperBoundShouldBeNegativeOne()`     | (-6, -2)            | Fail   |
| `UpperBoundShouldBeZero()`            | (-5, 0)             | Fail   |
| `UpperBoundShouldBePositiveNumber()`  | (-1, 1)             | Fail   |
| `UpperBoundShouldBeFloatValue()`      | (3.5, 4.7)          | Fail   |
| `UpperBoundForRangeWithSameNumber()`  | (3, 3)              | Pass   |
| `UpperBoundShouldBeNAN()`             | (2, Double.NAN)     | Fail   |
| `UpperBoundForInvalidRange()`         | (5, 2)              | Pass   |



## Class: DataUtilities

### `DataUtilities.calculateColumnTotal()`
|Test Case | Input Partitions | Status|
|---------------------------------------|---------------------|-------|
|`calculateColumnTotalFirstColumn()`    | [1.0, 2.0, 3.0], 0   | Pass |
|`calculateColumnTotalMiddleColumn()`   | [1.0, 2.0, 3.0], 1   | Pass |
|`calculateColumnTotalLastColumn()`     | [1.0, 2.0, 3.0], 2 | Pass |
|`calculateColumnTotalWithNegativeColumnIndex()`  | [1.0, 2.0, 3.0], -1 | Pass |
|`calculateColumnTotalWithOutOfBoundsColumnIndex() `   | [1.0, 2.0, 3.0], 3 | Pass |
|`calculateColumnTotalWithNullData() `   | null, 0 | Pass |
|`calculateColumnTotalWithMaxValueRowIndex()`   | [1.0, 2.0, 3.0], INTEGER.MAX_VALUE |Pass
|`calculateColumnTotalWithMaxValueInRows()`   | [DOUBLE.MAX_VALUE, DOUBLE.MAX_VALUE, DOUBLE.MAX_VALUE], 0 | Pass |
|`calculateColumnTotalWithMinValueInRows()`   | [DOUBLE.MIN_VALUE, DOUBLE.MIN_VALUE, DOUBLE.MIN_VALUE], 0 | Pass |


### `DataUtilities.calculateRowTotal()`

| Test Case | Input Partitions | Status |
|---------------------------------------|---------------------|-------|
|`calculateRowTotalFirstRow()`   | [1.0, 2.0, 3.0], 0 | Fail |
|`calculateRowTotalMiddleRow()`   | [1.0, 2.0, 3.0], 1 | Fail |
|`calculateRowTotalLastRow()`   | [1.0, 2.0, 3.0], 2 | Fail |
|`calculateRowTotalWithNegativeRowIndex()`   | [1.0, 2.0, 3.0], -1 | Pass| 
|`calculateRowTotalWithOutOfBoundsRowIndex()`   | [1.0, 2.0, 3.0], 3 | Pass |
|`calculateRowTotalWithNullData()`   | null, 0 | Pass| 
|`calculateRowTotalWithMaxValueRowIndex()`   | [1.0, 2.0, 3.0], INTEGER.MAX_VALUE | Pass| 
|`calculateRowTotalWithMaxValueInCols()`   | [DOUBLE.MAX_VALUE, DOUBLE.MAX_VALUE, DOUBLE.MAX_VALUE], 0 | Pass |
|`calculateRowTotalWithMinValueInCols()`   | [DOUBLE.MIN_VALUE, DOUBLE.MIN_VALUE, DOUBLE.MIN_VALUE], 0 | Pass |

### `DataUtilities.createNumberArray()`

| Test Case | Input Partitions | Status |
|---------------------------------------|---------------------|-------|
| `testCreateNumberArray_ValidDoubleData_CheckNotNull()`    | { 1.0, -2.5, 7 }   | Pass     |
| `testCreateNumberArray_ValidDoubleData_CheckLength()`     | { 1.0, -2.5,7 }    | Pass     |
| `testCreateNumberArray_ValidDoubleData_CheckElement0Match()` | { 1.0, -2.5, 7 } | Pass    |
| `testCreateNumberArray_ValidDoubleData_CheckElement1Match()` | { 1.0, -2.5, 7 } | Pass    |
| `testCreateNumberArray_ValidDoubleData_CheckElement2Match()` | { 1.0, -2.5, 7 } | Fail    |
| `testCreateNumberArray_NullArray_CheckNotNull()`    | { Double.NaN, Double.NaN, Double.NaN } | Pass |
| `testCreateNumberArray_NullArray_CheckLength()`    | { Double.NaN, Double.NaN, Double.NaN } | Pass  |
| `testCreateNumberArray_NullArray_CheckElement0Match()` | {Double.NaN, Double.NaN, Double.NaN } | Pass  |
| `testCreateNumberArray_NullArray_CheckElement1Match()` | { Double.NaN, Double.NaN, Double.NaN } | Pass  |
| `testCreateNumberArray_NullArray_CheckElement2Match()` | { Double.NaN, Double.NaN, Double.NaN } | Fail    |
| `testCreateNumberArray_EmptyArray_CheckNotNull()`      | {}    | Pass        |
| `testCreateNumberArray_EmptyArray_CheckLength()`       | {}    | Pass        |
| `testCreateNumberArray_nullValue()`                    |  null | Pass        |

### `DataUtilities.createNumberArray2D()`

|Test Case          | Input Partitions   | Test Status |
| ---------------------| -----------------------| ----------- |
| `testCreateNumberArray2D_ValidData_CheckNotNull()` | { { 1.0, -2.5 }, { 4.2, 5.0 } }  | Pass  |
| `testCreateNumberArray2D_ValidData_CheckRowLength()`  | { { 1.0, -2.5 }, { 4.2, 5.0 } } | Pass|
| `testCreateNumberArray2D_ValidData_CheckColumn0Length()`| { { 1.0, -2.5 }, { 4.2, 5.0 } } | Pass |
| `testCreateNumberArray2D_ValidData_CheckColumn1Length()`          | { { 1.0, -2.5 }, { 4.2, 5.0 } }                            | Pass        |
| `testCreateNumberArray2D_ValidData_CheckElement00Match()`         | { { 1.0, -2.5 }, { 4.2, 5.0 } }                            | Pass        |
| `testCreateNumberArray2D_ValidData_CheckElement01Match()`         | { { 1.0, -2.5 }, { 4.2, 5.0 } }                            | Fail        |
| `testCreateNumberArray2D_ValidData_CheckElement10Match()`         | { { 1.0, -2.5 }, { 4.2, 5.0 } }                            | Pass        |
| `testCreateNumberArray2D_ValidData_CheckElement11Match()`         | { { 1.0, -2.5 }, { 4.2, 5.0 } }                            | Fail        |
| `testCreateNumberArray2D_NullDouble2dArray_CheckElement00Match()` | { { -2.5, 3.7 }, { Double.NaN, 6.3 } }                     | Pass        |
| `testCreateNumberArray2D_NullDouble2dArray_CheckElement01Match()` | { { -2.5, 3.7 }, { Double.NaN, 6.3 } }                     | Fail        |
| `testCreateNumberArray2D_NullDouble2dArray_CheckElement10Match()` | { { -2.5, 3.7 }, { Double.NaN, 6.3 } }                     | Pass        |
| `testCreateNumberArray2D_NullDouble2dArray_CheckElement11Match()` | { { -2.5, 3.7 }, { Double.NaN, 6.3 } }                     | Fail        |
| `testCreateNumberArray2D_Null2dArray_CheckElement00Match()`       | { { Double.NaN, Double.NaN }, { Double.NaN, Double.NaN } } | Pass        |
| `testCreateNumberArray2D_Null2dArray_CheckElement01Match()`       | { { Double.NaN, Double.NaN }, { Double.NaN, Double.NaN } } | Fail        |
| `testCreateNumberArray2D_Null2dArray_CheckElement10Match()`       | { { Double.NaN, Double.NaN }, { Double.NaN, Double.NaN } } | Pass        |
| `testCreateNumberArray2D_Null2dArray_CheckElement11Match()`       | { { Double.NaN, Double.NaN }, { Double.NaN, Double.NaN } } | Fail        |
| `testCreateNumberArray2D_Empty2dArray_CheckNotNull()`            | { {}, {} }    | Pass  |
| `testCreateNumberArray2D_Empty2dArray_CheckRowLength()`           | { {}, {} }   | Pass  |
| `testCreateNumberArray2D_Empty2dArray_CheckColumnLength()`        | { {}, {} }   | Pass |
| `testCreateNumberArray2D_nullValue()`                             | null  | Pass    |

### `DataUtilities.getCumulativePercentages()`

| Test Case                          | Input                   | Status |
|------------------------------------|------------------------------------|--------|
| `getCumulativePercentagesTestingOneKpairWithZeroValue() ` | (0,0) | Pass|
| `getCumulativePercentagesTestingOneValidKpair() ` | (0,1) | Fail|
| `getCumulativePercentagesTestingOneKpairWithNullValue() ` | (0,null ) | Pass|
| `getCumulativePercentageTestingTwoKeyPairWithOneZeroValue() ` | { (0,0), (1,1) } | Pass|
| `getCumulativePercentageTestingThreeKeyPair()`| { (0,2) (1,3) (2,4) } | Fail |
|` getCumulativePercentageTestingFourKeyPairWithNegative()`| { (0,0) (1,3), (2,-2), (3,4) }| Fail |


# 4. How the team work/effort was divided and managed

The team individually read the assignment description and initialized eclipse and our project environments, including completing section 2.1. Afterwards, we met to discuss and select 5 of the 15 methods for org.jfree.data.Range. Together with all 5 methods in the org.jfree.data.DataUtilities class, we divided the 10 total methods between our group as shown in section 2.1.1. 

We individually developed test plans for our two assigned methods and reconvened to discuss our test plans before coding. We double-checked and ensured each test plan was adequate and followed the guidelines outlined in the lecture notes regarding black-box testing techniques. Afterwards, we split up individually again to implement our test code.

After our code was complete, we compiled on a single file and ran the test cases, evaluating each test to ensure the proper functionality.

A rough draft of the report was created as a group to define the structure, and basic contents. Then each member reviewed the report individually, and wrote the test cases/test plan that they are responsible for. Each member also added to each section of the report as they saw fit. Finally, all group members reviewed the report together to create a final submission.

# 5. Difficulties encountered, challenges overcome, and lessons learned

Difficulties encountered during black box testing are the following:
1. It was difficult to come up with what to test and what behavior to expect as we had to rely on the documentation which was unclear and not very detailed. For example, is the method designed to be able to handle illegal values such as null/nan values. 
1. Difficult to determine what a boundary is. For example, if the method is designed for data of type double, do we test a char type?
1. There was a level of bias and assumptions introduced when creating black box testing cases which could lead to not having full test coverage. 
1. It was difficult to incorporate mock objects during testing as the documentation for that was hard to understand. 
1. It was hard to determine what number of test cases was sufficient.
1. Since internal workings is unknown for the methods it was often hard to determine what the root cause of the failures and unexpected behavior was. For example, the method getUpperBound() failed most of its test cases. For example, it failed for when the upper bound was a negative value at first glance it would be unknown whether the method failed because it can’t handle a negative number or for another reason. With further testing, it was evident that the test failed because this method was returning the lower bound instead of the upper bound. 

The challenges faced during testing were overcome by creating a variety of test cases using the different methods learnt in class : Equivalent testing, boundary value analysis and robustness testing and analyzing the results from all the test cases. Referencing class notes and resources available were also used to overcome the challenges. 

We learned that black box testing can be very tedious and confusing as we have to rely on documentation that didn’t clearly define the requirements for the method. We also learned that black box testing alone may not have 100% test coverage. Lastly, there are assumptions made by the testers when creating the test cases. 


# 6 Comments/feedback on the lab itself

The layout and steps of the lab itself made it easy to follow and complete the assignment tasks. The lab exercises were useful as they allowed us to learn the basics of automated unit testing using JUnit in test code development. 