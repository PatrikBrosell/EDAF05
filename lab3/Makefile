# Example Makefile for automatic testing of lab solutions in EDAF05
#
# Run 'make test' or 'make testall' to perform the tests.


# Search for .out files:
# Change to make sure the path is where the testdata is stored on your system.
TESTDIR=./testFiles


test :
	java   SpammingUSA  $(TESTDIR)/USA-highway-miles.in | diff $(TESTDIR)/USA-highway-miles.out -
