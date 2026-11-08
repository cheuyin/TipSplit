# TipSplit

This is a CLI tool for calculating how to equally split a restaurant bill across a group of people, with at most $0.01 of difference between splits.

## Requirements
Requires JDK 25+.

## How To Run
1. Clone the repo
2. Move to the `src/` directory with `cd src`
3. Run `javac TipSplit.java`
4. Run `java TipSplit` to start the program

## Example
```aiignore
$ java TipSplit 
Subtotal: 100
Tax: 20.5
Tip %: 18.5
People: 4

Subtotal           100.00
Tax                 20.50
Tip                 18.50
Total              139.00

Person 1:           34.75
Person 2:           34.75
Person 3:           34.75
Person 4:           34.75
```

## Rules
- Inputs for Subtotal, Tax, Tip can have at most 2 decimal places
- Input for People must be a whole number
- Subtotal > 0
- Tax >= 0
- 0 <= Tip <= 100
- 1 <= People <= 100