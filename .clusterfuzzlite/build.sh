#!/bin/bash -eu

# ./buildconf.sh
# configure scripts usually use correct environment variables.
# ./configure

make clean
make -j$(nproc) all

$CXX $CXXFLAGS -std=c++11 -Ilib/ \
    $SRC/parse_fuzzer.cc -o $OUT/parse_fuzzer \
    $LIB_FUZZING_ENGINE .libs/libexpat.a

# Optional: Copy dictionaries and options files.
cp $SRC/*.dict $SRC/*.options $OUT/