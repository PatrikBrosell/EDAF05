# Example Makefile for automatic testing of lab solutions in EDAF05
#
# Run 'make test' or 'make testall' to perform the tests.


# Search for .out files:
# Change to make sure the path is where the testdata is stored on your system.
TESTDIR=./testFiles
OUTS=$(wildcard $(TESTDIR)/*.tsp)


test :
	java ClosestPairs $(TESTDIR) | diff --side-by-side --suppress-common-lines $(TESTDIR)/closest-pair.out -
