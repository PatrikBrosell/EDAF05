# Example Makefile for automatic testing of lab solutions in EDAF05
#
# Run 'make test' or 'make testall' to perform the tests.


# Search for .out files:
# Change to make sure the path is where the testdata is stored on your system.
TESTDIR=./testFiles
OUTS=$(wildcard $(TESTDIR)/*.out)


test :
	java Alignment testFiles/BLOSUM62.txt testFiles/HbB_FASTAs.in | diff --side-by-side --suppress-common-lines testFiles/HbB_FASTAs.out -

showall :
	java Alignment testFiles/BLOSUM62.txt testFiles/HbB_FASTAs.in | diff --side-by-side testFiles/HbB_FASTAs.out -
	
