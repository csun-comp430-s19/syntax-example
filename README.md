# syntax-example
Code written in class related to lexing and parsing.

We have the following grammar for this:

```
i is an integer
var is a variable
op ::= + | - | * | /
exp ::= additive
additive ::= multiplicative ( ('+' | '-') multiplicative)*
multiplicative ::= primary ( ('*' | '/') primary)*
primary ::= i | var | - exp | if (exp) { exp } else { exp } | ( exp )
```
