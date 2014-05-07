# Example Makefile for automatic testing of lab solutions in EDAF05
#
# Run 'make test' or 'make testall' to perform the tests.


# Search for .out files:
# Change to make sure the path is where the testdata is stored on your system.
TESTDIR=./testFiles
OUTS=$(wildcard $(TESTDIR)/*.out)


test :
	java WordLadders $(TESTDIR)/words-10.dat $(TESTDIR)/words-10-test.in | diff -q $(TESTDIR)/words-10-test.out -
	java WordLadders $(TESTDIR)/words-250.dat $(TESTDIR)/words-250-test.in | diff -q $(TESTDIR)/words-250-test.out -
	java WordLadders $(TESTDIR)/words-5757.dat $(TESTDIR)/words-5757-test.in | diff -q $(TESTDIR)/words-5757-test.out -

testall :  $(OUTS:.out=.test)

%.test : %.in
	java -Xmx2g  WordLadders $< | diff -q $*.out -

