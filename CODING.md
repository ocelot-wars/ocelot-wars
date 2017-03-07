# Ocelot Wars Coding Style Diskussion

## Test Naming
tests are named "test" + method + "context"

## Final fields
- final fields should be used in immutable objects
- final should be omitted if an object is partially mutable (has effectively final and also non-final fields)

## Data Objects
- introduce them only if the code gets more readable (e.g. if a result contains multiple parts)
- do not use them if the use of the parts is more convenient.

## Mocking
Mockito

## Formatting
- do not join already (manually) wrapped lines (no need for the nesty line-end-// hack) 