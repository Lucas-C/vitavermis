all : test clean

LDFLAGS := -lsfml-graphics -lsfml-audio -lsfml-window -lsfml-system

SOURCES := main.cpp
BIN := $(SOURCES:.cpp=.o)
EXEC := test

$(EXEC) : $(BIN)
	$(CXX) $(CXXFLAGS) -o $@ $^ $(LDFLAGS)

clean :
	rm -f $(BIN)

dist-clean :
	rm -f $(BIN) $(EXEC)
