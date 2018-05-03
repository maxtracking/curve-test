# curve-test
## Task

● Create an app that shows six numbers (fields) on screen, laid out in two columns.

● Below these fields, a seventh field should show the sum of these six numbers.

● The user can tap on any of the six numbers and edit the number.

● Editing the number should cause the sum to be updated.

● Tapping on the sum should toggle flashing of this field, defined as follows: when flashing, the value should be shown 
for 500ms, then hidden for 500ms, repeated indefinitely; when not flashing, the value should be visible.

● Please include unit tests.

## Bonus points:

● Demonstrate at least two different ways of implementing the flashing total, including at least one method not using RxJava.

● Use Dependency Injection to create the ViewModel for the Activity.

## Implementation

The sample application has 2 'main' activities that are visible in Android Launcher. The purpose is to illustrate various 
approaches such as MVVM and 'no pattern' way (could be more cases of course :) ).

There are 2 configurations to run the app from Android Studio - app and app2 for MainActivity and MainActivity2 respectively.
Basic unit tests can be found under src/test folder. Espresso tests were not a requirement so they are not implemented.
