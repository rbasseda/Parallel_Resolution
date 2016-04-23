# Makefile for Cilk++ example.  See source code for details.

CILKPP	= "/home/rbasseda/cilk/bin/cilk++"
LIBARG	= -O1 -g -lcilkutil
TARGET	= WumpusGame
SRC	= $(addsuffix .cilk,$(TARGET))

all: $(TARGET)

$(TARGET): $(SRC)
	$(CILKPP) $(SRC) $(LIBARG) -o $@

clean:
	rm -f $(TARGET)
