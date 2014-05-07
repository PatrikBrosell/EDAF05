# Example Makefile for automatic testing of lab solutions in EDAF05
#
# Run 'make test' or 'make testall' to perform the tests.


# Search for .out files:
# Change to make sure the path is where the testdata is stored on your system.
TESTDIR=./testFiles
OUTS=$(wildcard $(TESTDIR)/*.out)


test :
	cat $(TESTDIR)/sm-random-5000.in.* > $(TESTDIR)/sm-random-5000.in
	java StableMarriage $(TESTDIR)/sm-kt-p-4.in | diff $(TESTDIR)/sm-kt-p-4.out -
	java StableMarriage $(TESTDIR)/sm-kt-p-5.in | diff $(TESTDIR)/sm-kt-p-5.out -
	java StableMarriage $(TESTDIR)/sm-friends.in | diff $(TESTDIR)/sm-friends.out -
	java StableMarriage $(TESTDIR)/sm-illiad.in | diff $(TESTDIR)/sm-illiad.out -
	java StableMarriage $(TESTDIR)/sm-random-500.in | diff $(TESTDIR)/sm-random-500.out -
	java StableMarriage $(TESTDIR)/sm-random-50.in | diff $(TESTDIR)/sm-random-50.out -
	java StableMarriage $(TESTDIR)/sm-random-5.in | diff $(TESTDIR)/sm-random-5.out -
	java StableMarriage $(TESTDIR)/sm-worst-500.in | diff $(TESTDIR)/sm-worst-500.out -
	java StableMarriage $(TESTDIR)/sm-worst-50.in | diff $(TESTDIR)/sm-worst-50.out -
	java StableMarriage $(TESTDIR)/sm-worst-5.in | diff $(TESTDIR)/sm-worst-5.out -
	java -Xmx2g StableMarriage $(TESTDIR)/sm-random-5000.in | diff $(TESTDIR)/sm-random-5000.out -
	rm $(TESTDIR)/sm-random-5000.in

testall :  $(OUTS:.out=.test)

%.test : %.in
	java -Xmx2g  StableMarriage $< | diff -q $*.out -

